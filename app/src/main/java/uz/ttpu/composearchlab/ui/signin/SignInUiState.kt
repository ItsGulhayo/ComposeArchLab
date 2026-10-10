package uz.ttpu.composearchlab.ui.signin

// used sealed interface instead of enum here because Error and SignedIn need to carry
// their own data (message / email), an enum can't really do that cleanly
sealed interface SignInUiState {
    data object SignedOut : SignInUiState
    data object InProgress : SignInUiState
    data class Error(val message: String) : SignInUiState
    data class SignedIn(val email: String) : SignInUiState
}
