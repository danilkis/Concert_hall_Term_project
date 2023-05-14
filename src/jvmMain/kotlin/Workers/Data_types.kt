package Workers

import java.sql.Array

class Data_types {
    companion object {
        data class Equipment_types(val id: Int?, val Type: String, val Subtype: String)
        data class Equipment(val id: Int?, val Manufacturer: String, val Stock: Int, val EquipmentTypeId: Int)
        data class Sector(val id: Int?, val SeatsTotal: Int, val SeatsStart: Int?, val SeatsEnd: Int, val Name: String)
        data class Stage(val id: Int?, val StageCapacity: Int, val Name: String)
        data class EventEquipment(val EventName: String, val StageName: String, val EventId: Int, val Type: String, val Subtype: String, val Manufacturer: String, val Stock: Int, val EquipmentId: Int)

        data class SectorStages(val StageId: Int, val StageName: String, val SectorId: Int, val SectorName: String)

        data class Artists(val id: Int?, val Name: String, val Manager_name: String, val Manager_phone: String, val Manager_email: String)

        data class EventCrew(val EventName: String, val StageId: Int, val Workers: Array)

    }
}