package uz.ttpu.composearchlab.ui.taste

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TastePickerScreen(
    state: TastePickerState,
    onGenreClick: (String) -> Unit,
    onLikeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            TastePickerBottomBar(
                likedCount = state.likedCount,
                required = REQUIRED_LIKES,
                canContinue = state.canContinue,
                onContinueClick = { Log.d("TastePicker", "Continue tapped") },
                onSkipClick = { Log.d("TastePicker", "Skip tapped") },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            GenreChips(
                genres = state.genres,
                selectedGenre = state.selectedGenre,
                onGenreClick = onGenreClick,
            )
            ArtistGrid(
                artists = state.visibleArtists,
                likedIds = state.likedIds,
                onLikeClick = onLikeClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun TastePickerRoute(
    viewModel: TastePickerViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TastePickerScreen(
        state = state,
        onGenreClick = viewModel::onGenreClick,
        onLikeClick = viewModel::onArtistLikeToggled,
    )
}
