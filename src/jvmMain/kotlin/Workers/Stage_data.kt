package Workers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Stage_data {
    var database = DB()
    var pass = DB().password_glob
    var login = DB().user_glob
    companion object
    {
        var Stages = mutableStateListOf<Data_types.Companion.Stage>()
        var State = false
    }
    fun getStages() {
        Stages.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = connection.prepareStatement("Select * from \"Hall\".\"Stages\"")

        // the query is executed and results are fetched
        val result = query.executeQuery()

        // an empty list for holding the results
        val stg = mutableStateListOf<Data_types.Companion.Stage>()

        while(result.next()){

            // getting the value of the id column
            val id = result.getInt("StageId")

            // getting the value of the name column
            val StageName = result.getString("StageName")

            val StageCapacity = result.getInt("StageCapacity")

            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            stg.add(Data_types.Companion.Stage(id,StageCapacity, StageName))
        }
        Stages.addAll(stg)
    }
    fun AddStage(Type: Data_types.Companion.Stage)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."Stages"
        |("StageId", "StageName", "StageCapacity")
        |VALUES (?, ?, ?)
        |ON CONFLICT ("StageId") DO UPDATE
        |SET "StageId" = excluded."StageId",
        |    "StageName" = excluded."StageName",
        |    "StageCapacity" = excluded."StageCapacity"
        |""".trimMargin()
        return connection.prepareStatement(query).use {
            it.setObject(1, Type.id)
            it.setObject(2, Type.Name)
            it.setObject(3, Type.StageCapacity)
            it.executeUpdate()
        }
        State = false
    }
    fun RemoveStage(Type: Data_types.Companion.Stage) {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."Stages"
        |WHERE "StageId" = ? AND "StageName" = ? AND "StageCapacity" = ?
        |""".trimMargin()
        return connection.prepareStatement(query).use {
            it.setObject(1, Type.id)
            it.setObject(2, Type.Name)
            it.setObject(3, Type.StageCapacity)
            it.executeUpdate()
        }
        State = false
    }
}