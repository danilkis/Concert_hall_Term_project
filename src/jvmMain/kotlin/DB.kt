import java.sql.Connection
import java.sql.DriverManager
data class Equipment_types(val id: Int?, val Type: String, val Subtype: String)
data class Equipment(val id: Int?, val Manufacturer: String, val Stock: Int, val EquipmentTypeId: Int)
class DB {
    var user_glob: String = "user";
    var password_glob: String = "user";
    fun establishPostgreSQLConnection(user: String, pass: String): Connection {
        val jdbcUrl = "jdbc:postgresql://localhost:5432/Concert_hall"
        user_glob = user
        password_glob = pass
        return DriverManager.getConnection(jdbcUrl, user, pass)
    }

    fun getEquipmentTypes(): MutableList<Equipment_types> {
        val connection = establishPostgreSQLConnection(user_glob, password_glob)
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
    fun getEquipmentPlain(): MutableList<Equipment> {
        val connection = establishPostgreSQLConnection(user_glob, password_glob)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val equipment = mutableListOf<Equipment>()

        while(result.next()){

            // getting the value of the id column
            val id = result.getInt("EquipmentId")
            val EquipmentTypeId = result.getInt("EquipmentTypeId")
            // getting the value of the name column
            val Manufacturer = result.getString("Manufacturer")

            val Stock = result.getString("Stock")

            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            equipment.add(Equipment(id, Manufacturer, Stock.toInt(), EquipmentTypeId))
        }
        return equipment
    }
    fun AddType(Type: Equipment_types)
    {
        val connection = establishPostgreSQLConnection(user_glob, password_glob)
        val query = """
        |INSERT INTO "Hall"."Equipment_types"
        |("Type", "Subtype")
        |VALUES (?, ?)
        |""".trimMargin()
        return connection.prepareStatement(query).use {
            it.setObject(1, Type.Type)
            it.setObject(2, Type.Subtype)
            it.executeUpdate()
        }
    }
    fun AddEquipment(Type: Equipment)
    {
        val connection = establishPostgreSQLConnection(user_glob, password_glob)
        val query = """
        INSERT INTO "Hall"."Equipment" ("EquipmentId", "Manufacturer", "Stock", "EquipmentTypeId")
        |VALUES (?, ?, ?, ?)
        |ON CONFLICT ("EquipmentId") DO UPDATE
        |SET "Manufacturer" = excluded."Manufacturer",
        |    "Stock" = excluded."Stock",
        |    "EquipmentTypeId" = excluded."EquipmentTypeId"
        |""".trimMargin()
        return connection.prepareStatement(query).use {
            it.setObject(1, Type.id)
            it.setObject(2, Type.Manufacturer)
            it.setObject(3, Type.Stock)
            it.setObject(4, Type.EquipmentTypeId)
            it.executeUpdate()
        }
    }
}
