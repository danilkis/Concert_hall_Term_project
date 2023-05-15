package Manager.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class Crew_data {
    var database = DB()
    var pass = database.password_glob
    var login = database.user_glob
    var State = false
    companion object{
        var Crew = mutableStateListOf<Data_types.Companion.Crew>()
    }


    fun getCrew() {
        Crew.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT CONCAT(c."Name", ' ', c."Surname", ' ', c."ThirdName") AS Full_Name, "Phone", "Email", ct."Name" AS Crew_Type_Name, ct."Sphere"
        |FROM "Hall"."Crew" c
        |JOIN "Hall"."Crew_types" ct ON c."CrewMemberType" = ct."CrewTypeId"
        |ORDER BY Full_Name ASC;
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val cr = mutableStateListOf<Data_types.Companion.Crew>()

        while(result.next()){

            // getting the value of the id column
            val full_name = result.getString("full_name")

            // getting the value of the name column
            val Phone = result.getString("Phone")
            val Email = result.getString("Email")
            val crew_type = result.getString("crew_type_name")
            val Sphere = result.getString("Sphere")
            cr.add(
                Data_types.Companion.Crew(
                    full_name,
                    Phone,
                    Email,
                    crew_type,
                    Sphere
                )
            )
        }
        Crew.addAll(cr)
    }
    fun AddCrew(Type: Data_types.Companion.CrewAdd) //TODO: Написать добавление, ограничение на уникальность
    {
        try {
            val connection = database.establishPostgreSQLConnection(login, pass)
            val query = """
        |INSERT INTO "Hall"."Crew" ("Name", "Surname", "ThirdName", "Phone", "Email", "CrewMemberType")
        |SELECT ?, ?, ?, ?, ?, ct."CrewTypeId"
        |FROM "Hall"."Crew_types" ct
        |WHERE ct."Name" = ?;
        |""".trimMargin()
            return connection.prepareStatement(query).use {
                it.setString(1, Type.Name)
                it.setString(2, Type.Surname)
                it.setString(3, Type.ThirdName)
                it.setString(4, Type.Phone)
                it.setString(5, Type.Email)
                it.setString(6, Type.CrewType)
                it.executeUpdate()
            }
            State = true
        }
            catch (ex: Exception)
            {
                State = false
            }
    }
    fun RemoveCrew(Type: Data_types.Companion.CrewAdd)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Crew"
        |WHERE "Name" = ? AND "Surname" = ? AND "ThirdName" = ? AND "Phone" = ? AND "Email" = ?
        |AND "CrewMemberType" = (SELECT "CrewTypeId" FROM "Hall"."Crew_types" WHERE "Name" = ?);
        |""".trimMargin()

            return connection.prepareStatement(query).use {
                it.setString(1, Type.Name)
                it.setString(2, Type.Surname)
                it.setString(3, Type.ThirdName)
                it.setString(4, Type.Phone)
                it.setString(5, Type.Email)
                it.setString(6, Type.CrewType)
                it.executeUpdate()
            }
            this.getCrew()
            State = false
        /*
        }
        catch (ex: Exception)
        {
            State = true
        }

         */
    }
}