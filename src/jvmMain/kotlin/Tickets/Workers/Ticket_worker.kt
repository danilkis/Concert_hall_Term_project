package Tickets.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class Ticket_worker {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = false
    companion object
    {
        var Tickets = mutableStateListOf<Data_types.Companion.Ticket>()
    }
    fun getTickets() {
        Tickets.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT
        |   T."Price",
        |   T."DateOfPurchanse",
        |   T."Used",
        |   EV."EventName",
        |   TT."Name" AS "TicketTypeName",
        |   SS."Name"
        |FROM
        |   "Hall"."Tickets" AS T
        |JOIN "Hall"."Ticket_types" AS TT ON T."TicketType" = TT."TicketTypeId"
        |JOIN "Hall"."Sectors" AS SS ON TT."Sector" = SS."SectorId"
        |JOIN "Hall"."Events" AS EV ON EV."EventId" = T."Event";  
        |""".trimMargin() //TODO: Посмотреть что с загрузкой, и поставить в случае чего лимит на 100
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val tick = mutableStateListOf<Data_types.Companion.Ticket>()

        while(result.next()){

            // getting the value of the id column
            val Price = result.getInt("Price")

            // getting the value of the name column
            val DateOfPurchanse = result.getTimestamp("DateOfPurchanse")

            val Used = result.getBoolean("Used")
            val EventName = result.getString("EventName")
            val TicketTypeName = result.getString("TicketTypeName")
            val SectorName = result.getString("Name")
            tick.add(Data_types.Companion.Ticket(Price, DateOfPurchanse, Used, EventName, TicketTypeName,SectorName))
        }
        Tickets.addAll(tick)
    }
    /*
    fun AddSector(Type: Data_types.Companion.Sector)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Sectors"
        |("SectorId", "SeatsTotal", "Seats_start", "Seats_end", "Name")
        |VALUES (?, ?, ?, ?, ?)
        |ON CONFLICT ("SectorId") DO UPDATE
        |SET "SeatsTotal" = excluded."SeatsTotal",
        |    "Seats_start" = excluded."Seats_start",
        |    "Seats_end" = excluded."Seats_end",
        |    "Name" = excluded."Name"
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.SeatsTotal)
                it.setObject(3, Type.SeatsStart)
                it.setObject(4, Type.SeatsEnd)
                it.setObject(5, Type.Name)
                it.executeUpdate()
            }
            State = true
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveSectors(Type: Data_types.Companion.Sector) {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Sectors"
        |WHERE "SectorId" = ? AND "SeatsTotal" = ? AND "Seats_start" = ? AND "Seats_end" = ? AND "Name" = ?
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.SeatsTotal)
                it.setObject(3, Type.SeatsStart)
                it.setObject(4, Type.SeatsEnd)
                it.setObject(5, Type.Name)
                it.executeUpdate()
            }
            State = true
            this.getSectors()
        }
        catch (ex: Exception)
        {
            State = false
        }
    }

     */
}