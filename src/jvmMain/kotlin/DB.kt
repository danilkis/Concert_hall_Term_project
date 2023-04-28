import java.sql.Connection
import java.sql.DriverManager
data class Equipment_types(val id: Int, val Type: String, val Subtype: String)
class DB {
    fun establishPostgreSQLConnection(user: String, pass: String): Connection {
        val jdbcUrl = "jdbc:postgresql://localhost:5432/Concert_hall"
        return DriverManager.getConnection(jdbcUrl, user, pass)
    }

    fun getEquipmentTypes(): MutableList<Equipment_types> {
        val connection = establishPostgreSQLConnection("postgres", "postgres")
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment_types\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val equipmentTypes = mutableListOf<Equipment_types>()

        while(result.next()){

            // getting the value of the id column
            val id = result.getInt("EquipmentTypeId")

            // getting the value of the name column
            val type = result.getString("Type")

            val subtype = result.getString("Subtype")

            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            equipmentTypes.add(Equipment_types(id, type, subtype))
        }
        return equipmentTypes
    }
}
