package Tickets.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class Attending_worker {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object
    {
        var People = mutableStateListOf<Data_types.Companion.Attendee>()
    }
    fun getAttendees() {
        People.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT * FROM "Hall"."Attending";  
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val Attend = mutableStateListOf<Data_types.Companion.Attendee>()

        while(result.next()){

            // getting the value of the id column
            val Name = result.getString("Name")

            // getting the value of the name column
            val Surname = result.getString("Surname")

            val TicketId = result.getInt("TicketId")
            val AttendeeId = result.getInt("AttendeeId")

            Attend.add(Data_types.Companion.Attendee(Name, Surname, TicketId, AttendeeId))
        }
        People.addAll(Attend)
    }
    fun AddAttendee(Type: Data_types.Companion.Attendee)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Attending" ("Name", "Surname", "TicketId", "AttendeeId")
        |values (?, ?, ?, ?)
        
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.Name)
                it.setObject(2, Type.Surname)
                it.setObject(3, Type.TicketId)
                it.setObject(4, Type.AttendeeId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveAttendee(Type: Data_types.Companion.Attendee) {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Attending" WHERE "AttendeeId" = ?
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.AttendeeId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}