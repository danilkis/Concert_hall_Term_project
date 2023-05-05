package Workers

class Equipment_data {
    var database = DB()
    var pass = DB().password_glob
    var login = DB().user_glob
    fun getEquipmentTypes(): MutableList<Data_types.Companion.Equipment_types> {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment_types\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val equipmentTypes = mutableListOf<Data_types.Companion.Equipment_types>()

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
            equipmentTypes.add(Data_types.Companion.Equipment_types(id, type, subtype))
        }
        return equipmentTypes
    }
    fun getEquipmentPlain(): MutableList<Data_types.Companion.Equipment> {
        val connection = database.establishPostgreSQLConnection(pass, login)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val equipment = mutableListOf<Data_types.Companion.Equipment>()

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
            equipment.add(Data_types.Companion.Equipment(id, Manufacturer, Stock.toInt(), EquipmentTypeId))
        }
        return equipment
    }
    fun AddType(Type: Data_types.Companion.Equipment_types)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
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
    fun AddEquipment(Type: Data_types.Companion.Equipment)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
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