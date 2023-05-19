package Manager.Workers
import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class EventCrew_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object{
        var EventCrew = mutableStateListOf<Data_types.Companion.EventCrew>()
    }


    fun getEventCrew() { //TODO: Обновление листа лагает
        EventCrew.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT e."EventName", e."Stage", array_agg(c."Name" || ' ' || c."Surname") AS CrewMembers
        |FROM "Hall"."Events" e
        |JOIN "Hall"."EventCrew" ec ON e."EventId" = ec."EventId"
        |JOIN "Hall"."Crew" c ON ec."CrewMemberId" = c."CrewMemberId"
        |GROUP BY e."EventName", e."Stage";
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val evcr = mutableStateListOf<Data_types.Companion.EventCrew>()

        while(result.next()){

            // getting the value of the id column
            val EventName = result.getString("EventName")

            // getting the value of the name column
            val Stage = result.getInt("Stage")
            val Crewmembers = result.getArray("crewmembers")
            evcr.add(
                Data_types.Companion.EventCrew(
                    EventName,
                    Stage,
                    Crewmembers
                )
            )
        }
        EventCrew.addAll(evcr)
    }
    fun AddEventCrew(Type: Data_types.Companion.EventCrewAdd)
    {
        try {
            val connection = database.establishPostgreSQLConnection(login, pass)
            val query = """
        |INSERT INTO "Hall"."EventCrew" ("EventId", "CrewMemberId")
        |SELECT e."EventId", c."CrewMemberId"
        |FROM "Hall"."Events" e
        |JOIN "Hall"."Crew" c ON c."Name" = ? AND c."Surname" = ?
        |WHERE e."EventName" = ?;
        |""".trimMargin()
            return connection.prepareStatement(query).use {
                it.setString(1, Type.Name)
                it.setString(2, Type.Surname)
                it.setString(3, Type.EventName)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveEventCrew(Type: Data_types.Companion.EventCrewAdd)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."EventCrew"
        |WHERE "EventId" = (SELECT "EventId" FROM "Hall"."Events" WHERE "EventName" = ?)
        |AND "CrewMemberId" = (SELECT "CrewMemberId" FROM "Hall"."Crew" WHERE "Name" = ? AND "Surname" = ?);
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setString(1, Type.EventName)
                it.setString(2, Type.Name)
                it.setString(3, Type.Surname)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}