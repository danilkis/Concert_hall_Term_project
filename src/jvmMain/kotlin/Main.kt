import Auth.AuthScreen
import Tickets.TicketWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable
import com.example.compose.ReplyTheme

@Composable
fun App() {
    ReplyTheme(false)
    {
        TicketWindow()
    }
    }
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
