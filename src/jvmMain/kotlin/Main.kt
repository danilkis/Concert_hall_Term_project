import Auth.AuthScreen
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable
import com.example.compose.ReplyTheme

@Composable
fun App() {
    ReplyTheme(false)
    {
        AuthScreen()
    }
    }
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
