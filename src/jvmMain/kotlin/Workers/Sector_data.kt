package Workers

class Sector_data {
    var database = DB()
    var pass = DB().password_glob
    var login = DB().user_glob
    fun getSectors(): MutableList<Data_types.Companion.Sector> {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Sectors\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val Sectors = mutableListOf<Data_types.Companion.Sector>()

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
            Sectors.add(Data_types.Companion.Sector(id, SeatsTotal, Seats_start, Seats_end, Name))
        }
        return Sectors
    }
    fun AddType(Type: Data_types.Companion.Sector)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Equipment_types"
        |("Type", "Subtype")
        |VALUES (?, ?)
        |""".trimMargin()
        return connection.prepareStatement(query).use {
            it.setObject(1, Type.id)
            it.setObject(2, Type.SeatsTotal)
            it.setObject(3, Type.SeatsStart)
            it.setObject(4, Type.SeatsEnd)
            it.setObject(5, Type.SeatsEnd)
            it.executeUpdate()
        }
    }
}