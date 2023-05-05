package Workers

import java.sql.Connection
import java.sql.DriverManager
data class Equipment_types(val id: Int?, val Type: String, val Subtype: String)
data class Equipment(val id: Int?, val Manufacturer: String, val Stock: Int, val EquipmentTypeId: Int)
class DB {
    var user_glob: String = "postgres";
    var password_glob: String = "postgres";
    fun establishPostgreSQLConnection(user: String, pass: String): Connection {
        val jdbcUrl = "jdbc:postgresql://localhost:5432/Concert_hall"
        user_glob = user
        password_glob = pass
        return DriverManager.getConnection(jdbcUrl, user, pass)
    }
}
