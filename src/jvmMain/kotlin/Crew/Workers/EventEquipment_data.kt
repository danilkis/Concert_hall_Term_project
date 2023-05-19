package Crew.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class EventEquipment_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object
    {
        var EventEquipment = mutableStateListOf<Data_types.Companion.EventEquipment>()

    }
    fun getEventEquipment() {
        EventEquipment.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT "Hall"."Events"."EventId", "Hall"."Events"."EventName", "Hall"."Stages"."StageName", "Hall"."Equipment_types"."Type", "Hall"."Equipment_types"."Subtype", "Hall"."Equipment"."Manufacturer", "Hall"."Equipment"."Stock", "Hall"."Equipment"."EquipmentId"
        |FROM "Hall"."Events"
        |INNER JOIN "Hall"."Stages" ON "Hall"."Events"."Stage" = "Hall"."Stages"."StageId"
        |INNER JOIN "Hall"."EventEquipment" ON "Hall"."Events"."EventId" = "Hall"."EventEquipment"."EventId"
        |INNER JOIN "Hall"."Equipment" ON "Hall"."EventEquipment"."EquipmentId" = "Hall"."Equipment"."EquipmentId"
        |INNER JOIN "Hall"."Equipment_types" ON "Hall"."Equipment"."EquipmentTypeId" = "Hall"."Equipment_types"."EquipmentTypeId";
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val eveq = mutableStateListOf<Data_types.Companion.EventEquipment>()

        while(result.next()){

            // getting the value of the id column
            val EventName = result.getString("EventName")

            // getting the value of the name column
            val StageName = result.getString("StageName")
            val EventId = result.getInt("EventId")
            val Type = result.getString("Type")
            val Subtype = result.getString("Subtype")
            val Manufacturer = result.getString("Manufacturer")
            val Stock = result.getInt("Stock")
            val EquipmentId = result.getInt("EquipmentId")
            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            eveq.add(
                Data_types.Companion.EventEquipment(
                    EventName,
                    StageName,
                    EventId,
                    Type,
                    Subtype,
                    Manufacturer,
                    Stock,
                    EquipmentId
                )
            )
        }
        EventEquipment.addAll(eveq)
    }
    fun AddEventEquipment(Type: Data_types.Companion.EventEquipment)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Sectors"
        |("SectorId", "SeatsTotal", "Seats_start", "Seats_end", "Name")
        |VALUES (?, ?)
        |ON CONFLICT ("EventId", "EquipmentId") DO UPDATE
        |SET "EquipmentId" = excluded."EquipmentId",
        |    "EventId" = excluded."EventId"
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.EventId)
                it.setObject(2, Type.EquipmentId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveEventEquipment(Type: Data_types.Companion.EventEquipment) {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."EventEquipment"
        |WHERE "EventId" = ? AND "EquipmentId" = ?
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.EventId)
                it.setObject(2, Type.EquipmentId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}