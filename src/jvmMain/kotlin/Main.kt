import Auth.AuthScreen
import Manager.Elements.ArtistList
import Manager.ManagerWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable

@Composable
fun App() {
    AuthScreen()
    //ManagerWindow()
    }
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
