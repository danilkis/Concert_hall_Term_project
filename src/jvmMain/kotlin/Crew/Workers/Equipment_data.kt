package Crew.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList

class Equipment_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = true
    companion object{
        var Eq_types = mutableStateListOf<Data_types.Companion.Equipment_types>()
        var equipment = mutableStateListOf<Data_types.Companion.Equipment>()

    }


    fun getEquipmentTypes() {
        Eq_types.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment_types\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()
        val equipmentTypes = mutableStateListOf<Data_types.Companion.Equipment_types>()
        // an empty list for holding the results

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
        Eq_types.addAll(equipmentTypes.toMutableStateList())
    }
    fun getEquipmentPlain(){
        equipment.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Equipment\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val equipment = mutableStateListOf<Data_types.Companion.Equipment>()

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
        Companion.equipment.addAll(equipment.toMutableStateList())
    }
    fun AddType(Type: Data_types.Companion.Equipment_types)
    {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Equipment_types"
        |("Type", "Subtype")
        |VALUES (?, ?)
        |""".trimMargin()
        try {
            if (Type.Type.isBlank() || Type.Subtype.isBlank()) {throw IllegalStateException("Пустые поля!")}
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.Type)
                it.setObject(2, Type.Subtype)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveType(Type: Data_types.Companion.Equipment_types)
    {
        State = true
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
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun AddEquipment(Type: Data_types.Companion.Equipment) {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        INSERT INTO "Hall"."Equipment" ("EquipmentId", "Manufacturer", "Stock", "EquipmentTypeId")
        |VALUES (?, ?, ?, ?)
        |ON CONFLICT ("EquipmentId") DO UPDATE
        |SET "Manufacturer" = excluded."Manufacturer",
        |    "Stock" = excluded."Stock",
        |    "EquipmentTypeId" = excluded."EquipmentTypeId"
        |""".trimMargin()
        try
        {
            if (Type.Manufacturer.isBlank()) {
                throw IllegalStateException("Пустые поля!")
            }
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.Manufacturer)
                it.setObject(3, Type.Stock)
                it.setObject(4, Type.EquipmentTypeId)
                it.executeUpdate()
            }
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveEquipment(Type: Data_types.Companion.Equipment) {
        State = true
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Equipment"
        |WHERE "EquipmentId" = ? AND "Manufacturer" = ? AND "Stock" = ? AND "EquipmentTypeId" = ?
        |""".trimMargin()
        try {
            if (Type.Manufacturer.isBlank()) {
                throw IllegalStateException("Пустые поля!")
            }
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.id)
                it.setObject(2, Type.Manufacturer)
                it.setObject(3, Type.Stock)
                it.setObject(4, Type.EquipmentTypeId)
                it.executeUpdate()
            }
        }
            catch (ex: Exception)
            {
                State = false
            }
    }
}