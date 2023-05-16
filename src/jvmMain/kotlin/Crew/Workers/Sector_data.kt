package Crew.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class Sector_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = false
    companion object
    {
        var Sector = mutableStateListOf<Data_types.Companion.Sector>()
    }
    fun getSectors() {
        Sector.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Sectors\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val sectr = mutableStateListOf<Data_types.Companion.Sector>()

        while(result.next()){

            // getting the value of the id column
            val id = result.getInt("SectorId")

            // getting the value of the name column
            val SeatsTotal = result.getInt("SeatsTotal")

            val Seats_start = result.getInt("Seats_start")
            val Seats_end = result.getInt("Seats_end")
            val Name = result.getString("Name")

            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            sectr.add(Data_types.Companion.Sector(id, SeatsTotal, Seats_start, Seats_end, Name))
        }
        Sector.addAll(sectr)
    }
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
}