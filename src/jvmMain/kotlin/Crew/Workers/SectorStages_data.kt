package Crew.Workers

import Workers.DB
import Workers.Data_types
import androidx.compose.runtime.mutableStateListOf

class SectorStages_data {
    var database = DB()
    var pass = DB.password_glob
    var login = DB.user_glob
    var State = false
    companion object
    {
        var StagesSectors = mutableStateListOf<Data_types.Companion.SectorStages>()

    }
    fun getStagesSectors() {
        StagesSectors.clear()
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |SELECT "Hall"."Stages"."StageId", "Hall"."Stages"."StageName", "Hall"."Sectors"."SectorId", "Hall"."Sectors"."Name"
        |FROM "Hall"."Stages"
        |JOIN "Hall"."StageSectors" ON "Hall"."Stages"."StageId" = "Hall"."StageSectors"."StageId"
        |JOIN "Hall"."Sectors" ON "Hall"."StageSectors"."SectorId" = "Hall"."Sectors"."SectorId"
        |GROUP BY "Hall"."Stages"."StageId", "Hall"."Sectors"."SectorId";
        |""".trimMargin()
        val query1 = connection.prepareStatement(query)

        // the query is executed and results are fetched
        val result = query1.executeQuery()

        // an empty list for holding the results
        val stg = mutableStateListOf<Data_types.Companion.SectorStages>()

        while(result.next()){

            // getting the value of the id column
            val StageId = result.getInt("StageId")

            // getting the value of the name column
            val StageName = result.getString("StageName")

            val SectorId = result.getInt("SectorId")
            val SectorName = result.getString("Name")

            /*
            constructing an Equipment_types object and
            putting data into the list
             */
            stg.add(Data_types.Companion.SectorStages(StageId, StageName, SectorId, SectorName))
        }
        StagesSectors.addAll(stg)
    }
    fun AddStageSector(Type: Data_types.Companion.SectorStages)
    {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |INSERT INTO "Hall"."StageSectors"
        |("StageId", "SectorId")
        |VALUES (?, ?)
        |ON CONFLICT ("StageId", "SectorId") DO UPDATE
        |SET "StageId" = excluded."StageId",
        |    "SectorId" = excluded."SectorId"
        |""".trimMargin()
        try {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.StageId)
                it.setObject(2, Type.SectorId)
                it.executeUpdate()
            }
            State = true
            this.getStagesSectors()
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
    fun RemoveSectorStage(Type: Data_types.Companion.SectorStages) {
        val connection = database.establishPostgreSQLConnection(login, pass)
        val query = """
        |DELETE FROM "Hall"."StageSectors"
        |WHERE "StageId" = ? AND "SectorId" = ?
        |""".trimMargin()
        try
        {
            return connection.prepareStatement(query).use {
                it.setObject(1, Type.StageId)
                it.setObject(2, Type.SectorId)
                it.executeUpdate()
            }
            this.getStagesSectors()
            State = true
        }
        catch (ex: Exception)
        {
            State = false
        }
    }
}