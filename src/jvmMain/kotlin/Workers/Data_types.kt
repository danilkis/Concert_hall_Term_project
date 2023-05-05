package Workers

class Data_types {
    companion object {
        data class Equipment_types(val id: Int?, val Type: String, val Subtype: String)
        data class Equipment(val id: Int?, val Manufacturer: String, val Stock: Int, val EquipmentTypeId: Int)
        data class Sector(val id: Int?, val SeatsTotal: Int, val SeatsStart: Int?, val SeatsEnd: Int, val Name: String)
    }
}