package Manager.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class Event_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = false
    companion object{
        var Event = mutableStateListOf<Data_types.Companion.Events>()
    }


    fun getEvents() {
        Event.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT "Hall"."Events"."EventName","Hall"."Events"."Start", "Hall"."Events"."End", "Hall"."Stages"."StageName", "Hall"."Artists"."Name"
        |FROM "Hall"."Events"
        |INNER JOIN "Hall"."EventArtists" ON "Hall"."Events"."EventId" = "Hall"."EventArtists"."EventId"
        |INNER JOIN "Hall"."Artists" ON "Hall"."EventArtists"."ArtistId" = "Hall"."Artists"."ArtistId"
        |INNER JOIN "Hall"."Stages" ON "Hall"."Events"."Stage" = "Hall"."Stages"."StageId";
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val ev = mutableStateListOf<Data_types.Companion.Events>()

        while(result.next()){

            // getting the value of the id column
            val EventName = result.getString("EventName")

            // getting the value of the name column
            val Start = result.getTimestamp("Start")
            val End = result.getTimestamp("End")
            val Stage = result.getString("StageName")
            val Name = result.getString("Name")
            ev.add(
                Data_types.Companion.Events(
                    EventName,
                    Start,
                    End,
                    Stage,
                    Name
                )
            )
        }
        Event.addAll(ev)
    }
    fun AddEvent(Type: Data_types.Companion.Events)
    {
        try
        {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |WITH stage AS (
        |SELECT "StageId"
        |FROM "Hall"."Stages"
        |WHERE "StageName" = ?
        |)
        |INSERT INTO "Hall"."Events" ("EventName", "Start", "End", "Stage")
        |SELECT ?, ?, ?, stage."StageId"
        |FROM stage; -- Include the CTE in the main query
        |INSERT INTO "Hall"."EventArtists" ("ArtistId", "EventId")
        |SELECT
        |   a."ArtistId",
        |   e."EventId"
        |FROM
        |"Hall"."Artists" a
        |JOIN
        |"Hall"."Events" e ON 1 = 1
        |WHERE
        |a."Name" = ?
        |AND e."EventName" = ?;
    """.trimMargin()
        return connection.prepareStatement(query).use {
            it.setString(1, Type.Stage)
            it.setString(2, Type.EventName)
            it.setTimestamp(3, Type.Start)
            it.setTimestamp(4, Type.End)
            it.setString(5, Type.ArtistName)
            it.setString(6, Type.EventName)
            it.executeUpdate()
        }
        }
        catch (ex: Exception)
        {
            State = false
        }

    }
    fun RemoveEvent(Type: Data_types.Companion.Events)
    {
        try
        {
            val connection = database.establishPostgreSQLConnection(login, pass)
            val query = """
        |DELETE FROM "Hall"."Events"
        |WHERE "Hall"."Events"."EventId" IN (
        |SELECT "Hall"."EventArtists"."EventId"
        |FROM "Hall"."EventArtists"
        |INNER JOIN "Hall"."Artists" ON "Hall"."EventArtists"."ArtistId" = "Hall"."Artists"."ArtistId"
        |INNER JOIN "Hall"."Events" ON "Hall"."EventArtists"."EventId" = "Hall"."Events"."EventId"
        |INNER JOIN "Hall"."Stages" ON "Hall"."Events"."Stage" = "Hall"."Stages"."StageId"
        |WHERE "Hall"."Events"."EventName" = ?
        |AND "Hall"."Events"."Start" = ?
        |AND "Hall"."Events"."End" = ?
        |AND "Hall"."Stages"."StageName" = ?
        |AND "Hall"."Artists"."Name" = ?
        |);
        |""".trimMargin()

            return connection.prepareStatement(query).use {
                it.setString(1, Type.EventName)
                it.setTimestamp(2, Type.Start)
                it.setTimestamp(3, Type.End)
                it.setString(4, Type.Stage)
                it.setString(5, Type.ArtistName)
                it.executeUpdate()
            }
            this.getEvents()
            State = false
        }
        catch (ex: Exception)
        {
            State = true
        }
    }
}