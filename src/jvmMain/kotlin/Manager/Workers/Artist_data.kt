package Manager.Workers

import Workers.Data_types
import Workers.DB
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList

class Artist_data {
    var database = DB()
    var pass = database.password_glob
    var login = database.user_glob
    var State = false
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


            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            art_dt.add(Data_types.Companion.Artists(id, name, manager_name, manager_phone, manager_email))
        }
        Artists.addAll(art_dt.toMutableStateList())
    }
    fun AddArtist(Type: Data_types.Companion.Artists)
    {
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
            //TODO: Пороверка телефона и почты
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.Name)
                it.setObject(3, Type.Manager_name)
                it.setObject(4, Type.Manager_phone)
                it.setObject(5, Type.Manager_email)
                it.executeUpdate()
            }
            State = true
            this.getArtists()
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveType(Type: Data_types.Companion.Equipment_types)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Equipment_types"
        |WHERE "Type" = ? AND "Subtype" = ?
        |""".trimMargin()
        try {
            if (Type.Type.isBlank() || Type.Subtype.isBlank()) {throw IllegalStateException("Пустые поля!")}
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.Type)
                it.setObject(2, Type.Subtype)
                it.executeUpdate()
            }
            this.getArtists()
            State = false
        }
        catch (ex: Exception)
        {
            State = true
        }
    }
}