import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

@OptIn(ExperimentalMaterial3Api::class)
fun test() {
    ModalBottomSheet(
        onDismissRequest = {},
        windowInsets = WindowInsets.ime
    ) {}
}
