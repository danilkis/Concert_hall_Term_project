package Workers

import Crew.Workers.Equipment_data
import java.sql.Connection
import java.sql.DriverManager
class DB {
    var user_glob: String = "postgres";
    var password_glob: String = "hassPASS";
    companion object
    {
        var State: Boolean = false;
    }
    val jdbcUrl = "jdbc:postgresql://pavlovskhomev3.duckdns.org:5432/Concert_hall"
    fun establishPostgreSQLConnection(user: String, pass: String): Connection {
        try
        {
            user_glob = user
            password_glob = pass
            State = true
            return DriverManager.getConnection(jdbcUrl, user, pass)
        }
        catch (ex: Exception)
        {
            State = false
            return DriverManager.getConnection(jdbcUrl, user, pass)
        }
    }
}
