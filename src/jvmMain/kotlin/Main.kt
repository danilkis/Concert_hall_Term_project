import Auth.AuthScreen
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable

@Composable
fun App() {
    AuthScreen()
    }
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
