package uz.ttpu.composearchlab.ui.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    // _uiState is private so only this ViewModel can change it, uiState outside is just
    // a read-only view - this way the UI can't mess with the state directly, only this class can
    private val _uiState = mutableStateOf<SignInUiState>(SignInUiState.SignedOut)
    val uiState: State<SignInUiState>
        get() = _uiState

    fun onSignIn(email: String, password: String) {
        if (_uiState.value is SignInUiState.InProgress) return

        _uiState.value = SignInUiState.InProgress
        viewModelScope.launch {
            delay(1500)

            _uiState.value = if (password == "kotlin123") {
                SignInUiState.SignedIn(email)
            } else {
                SignInUiState.Error("Wrong email or password")
            }
        }
    }

    fun onErrorShown() {
        if (_uiState.value is SignInUiState.Error) {
            _uiState.value = SignInUiState.SignedOut
        }
    }
}
