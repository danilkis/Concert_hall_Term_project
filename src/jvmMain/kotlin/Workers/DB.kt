package Workers

import java.sql.Connection
import java.sql.DriverManager
class DB {
    companion object
    {
        var user_glob: String = "postgres";
        var password_glob: String = "hassPASS";
    }
    fun establishPostgreSQLConnection(user: String, pass: String): Connection {
        val jdbcUrl = "jdbc:postgresql://pavlovskhomev3.duckdns.org:5432/Concert_hall"
        user_glob = user
        password_glob = pass
        return DriverManager.getConnection(jdbcUrl, user, pass)
    }
}
