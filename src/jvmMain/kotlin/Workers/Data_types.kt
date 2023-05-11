package Workers

class Data_types {
    companion object {
        data class Equipment_types(val id: Int?, val Type: String, val Subtype: String)
        data class Equipment(val id: Int?, val Manufacturer: String, val Stock: Int, val EquipmentTypeId: Int)
        data class Sector(val id: Int?, val SeatsTotal: Int, val SeatsStart: Int?, val SeatsEnd: Int, val Name: String)
        data class Stage(val id: Int?, val StageCapacity: Int, val Name: String)
        data class EventEquipment(val id: Int?, val StageCapacity: Int, val Name: String)
    }
}