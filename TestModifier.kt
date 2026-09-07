import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test() {
    ModalBottomSheet(onDismissRequest = {}) {
        Column {
            Text("Header")
            // If we use weight(1f) here without fillMaxHeight, it might crash or be zero height
            Box(Modifier.weight(1f))
            Text("Footer")
        }
    }
}
