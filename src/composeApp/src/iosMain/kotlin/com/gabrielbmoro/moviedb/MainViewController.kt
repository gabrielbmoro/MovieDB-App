import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello Compose",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}