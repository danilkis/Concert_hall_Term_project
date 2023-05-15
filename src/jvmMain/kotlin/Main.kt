import Auth.AuthScreen
import Manager.Elements.ArtistList
import Manager.ManagerWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.Composable
import com.example.compose.ReplyTheme

@Composable
fun App() {
    ReplyTheme(false)
    {
        //AuthScreen()
        ManagerWindow()
    }
    }
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
