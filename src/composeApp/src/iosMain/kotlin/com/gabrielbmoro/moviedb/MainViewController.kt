import androidx.compose.ui.window.ComposeUIViewController
import com.gabrielbmoro.moviedb.desingsystem.theme.MovieDBAppTheme

@Suppress("FunctionNaming")
fun MainViewController() = ComposeUIViewController {
    MovieDBAppTheme {
        RootApp()
    }
}
