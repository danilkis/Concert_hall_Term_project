package Tickets.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class TicketTypes_worker {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object
    {
        var TicketTypes = mutableStateListOf<Data_types.Companion.TicketTypes>() //TODO: Закончить
    }
    fun getTT() {
        TicketTypes.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT * FROM "Hall"."Ticket_types";  
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val TT = mutableStateListOf<Data_types.Companion.TicketTypes>()

        while(result.next()){

            // getting the value of the id column
            val TTID = result.getInt("TicketTypeId")

            // getting the value of the name column
            val Sector = result.getInt("Sector")

            val Name = result.getString("TicketId")

            TT.add(Data_types.Companion.TicketTypes(Name, Sector, TTID))
        }
        TicketTypes.addAll(TT)
    }
    fun AddTT(Type: Data_types.Companion.TicketTypes)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Ticket_types" ("Name", "Sector", "TicketTypeId")
        |values (?, ?, ?)
        
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.Name)
                it.setObject(2, Type.SectorId)
                it.setObject(3, Type.TicketTypeId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveTT(Type: Data_types.Companion.TicketTypes) {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Ticket_types" WHERE "TicketTypeId" = ?
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.TicketTypeId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}