package Manager.Workers

import Workers.Data_types
import Workers.DB
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList

class Artist_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object{
        var Artists = mutableStateListOf<Data_types.Companion.Artists>()
    }


    fun getArtists() {
        Artists.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Artists\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()
        val art_dt = mutableStateListOf<Data_types.Companion.Artists>()
        // an empty list for holding the results

        while(result.next()){

            // getting the value of the id column
            val id = result.getInt("ArtistId")

            // getting the value of the name column
            val name = result.getString("Name")

            val manager_name = result.getString("Manager_name")
            val manager_phone = result.getString("Manager_phone")
            val manager_email = result.getString("Manager_email")

            art_dt.add(Data_types.Companion.Artists(id, name, manager_name, manager_phone, manager_email))
        }
        Artists.addAll(art_dt.toMutableStateList())
    }
    fun AddArtist(Type: Data_types.Companion.Artists)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Artists"
        |("ArtistId", "Name","Manager_name","Manager_phone","Manager_email")
        |VALUES (?, ?, ?, ?, ?)
        |ON CONFLICT ("ArtistId") DO UPDATE
        |SET "ArtistId" = excluded."ArtistId",
        |    "Name" = excluded."Name",
        |    "Manager_name" = excluded."Manager_name",
        |    "Manager_phone" = excluded."Manager_phone",
        |    "Manager_email" = excluded."Manager_email"
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.Name)
                it.setObject(3, Type.Manager_name)
                it.setObject(4, Type.Manager_phone)
                it.setObject(5, Type.Manager_email)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveArtist(Type: Data_types.Companion.Artists)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Artists"
        |WHERE "ArtistId" = ?
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}