package uz.ttpu.composearchlab.ui.taste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

@Composable
fun TastePickerBottomBar(
    likedCount: Int,
    required: Int,
    canContinue: Boolean,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 3.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { (likedCount.toFloat() / required).coerceAtMost(1f) },
                    modifier = Modifier.size(40.dp),
                )
                Text(likedCount.toString())
            }
            Text(
                text = "Pick $required artists you like",
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = onSkipClick) {
                Text("Later")
            }
            Button(onClick = onContinueClick, enabled = canContinue) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, name = "0 liked")
@Composable
private fun TastePickerBottomBarZeroPreview() {
    ComposeArchLabTheme {
        TastePickerBottomBar(
            likedCount = 0,
            required = REQUIRED_LIKES,
            canContinue = false,
            onContinueClick = {},
            onSkipClick = {},
        )
    }
}

@Preview(showBackground = true, name = "2 liked")
@Composable
private fun TastePickerBottomBarTwoPreview() {
    ComposeArchLabTheme {
        TastePickerBottomBar(
            likedCount = 2,
            required = REQUIRED_LIKES,
            canContinue = false,
            onContinueClick = {},
            onSkipClick = {},
        )
    }
}

@Preview(showBackground = true, name = "3 liked")
@Composable
private fun TastePickerBottomBarThreePreview() {
    ComposeArchLabTheme {
        TastePickerBottomBar(
            likedCount = 3,
            required = REQUIRED_LIKES,
            canContinue = true,
            onContinueClick = {},
            onSkipClick = {},
        )
    }
}
