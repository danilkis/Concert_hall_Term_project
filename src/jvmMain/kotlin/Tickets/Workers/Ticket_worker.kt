package Tickets.Workers

import Crew.Workers.Sector_data
import Workers.DB
import Workers.Data_types
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

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
        |   T."TicketId",
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
        |JOIN "Hall"."Events" AS EV ON EV."EventId" = T."Event"
        |ORDER BY T."TicketId" DESC;
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val tick = mutableStateListOf<Data_types.Companion.Ticket>()

        while(result.next()){

            // getting the value of the id column
            val ID = result.getInt("TicketId")
            val Price = result.getInt("Price")

            // getting the value of the name column
            val DateOfPurchanse = result.getTimestamp("DateOfPurchanse")

            val Used = result.getBoolean("Used")
            val EventName = result.getString("EventName")
            val TicketTypeName = result.getString("TicketTypeName")
            val SectorName = result.getString("Name")
            tick.add(Data_types.Companion.Ticket(ID, Price, DateOfPurchanse, Used, EventName, TicketTypeName,SectorName))
        }
        Tickets.addAll(tick)
    }
    fun AddTicket(Type: Data_types.Companion.Ticket)
    {
        val connection = database.establishPostgreSQLConnection(login, pass) //TODO: ON CONFLICT DO UPDATE
        val query = """
        |INSERT INTO "Hall"."Tickets"
        |("TicketId","Price", "DateOfPurchanse", "Used", "TicketType", "Event")
        |SELECT ?, ?, ?, replace(?, '"', '')::boolean, tt."TicketTypeId", e."EventId"
        |FROM (
        |SELECT "TicketTypeId"
        |FROM "Hall"."Ticket_types"
        |WHERE "Name" = ?
        |) AS tt, "Hall"."Events" e
        |WHERE e."EventName" = ?;
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use { statement ->
                statement.setObject(1, Type.ID)
                statement.setObject(2, Type.Price)                // Set value for index 1
                statement.setTimestamp(3, Type.DateOfPurchanse) // Set value for index 2
                statement.setString(4, Type.Used.toString())   // Set value for index 3
                statement.setObject(5, Type.EventName)    // Set value for index 4
                statement.setObject(6, Type.TicketTypeName)         // Set value for index 5
                statement.executeUpdate()
            }
            State = true
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveTickets(Type: Data_types.Companion.Ticket) {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Tickets" WHERE "TicketId" = ?;
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.ID)
                it.executeUpdate()
            }
            State = true
            this.getTickets()
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}