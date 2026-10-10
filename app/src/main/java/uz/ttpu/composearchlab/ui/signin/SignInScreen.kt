package uz.ttpu.composearchlab.ui.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignIn: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    when (uiState) {
        is SignInUiState.SignedIn -> {
            Column(modifier.padding(24.dp)) {
                Text("Welcome, ${uiState.email}")
            }
        }

        else -> {
            Column(
                modifier = modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(
                    onClick = { onSignIn(email, password) },
                    enabled = uiState !is SignInUiState.InProgress,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Sign in")
                }
                if (uiState is SignInUiState.InProgress) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(),
) {
    val uiState by viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        if (uiState is SignInUiState.Error) {
            snackbarHostState.showSnackbar((uiState as SignInUiState.Error).message)
            viewModel.onErrorShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { innerPadding ->
        SignInScreen(
            uiState = uiState,
            onSignIn = viewModel::onSignIn,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview(showBackground = true, name = "Signed out")
@Composable
private fun SignInScreenSignedOutPreview() {
    ComposeArchLabTheme {
        SignInScreen(uiState = SignInUiState.SignedOut, onSignIn = { _, _ -> })
    }
}

@Preview(showBackground = true, name = "In progress")
@Composable
private fun SignInScreenInProgressPreview() {
    ComposeArchLabTheme {
        SignInScreen(uiState = SignInUiState.InProgress, onSignIn = { _, _ -> })
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
private fun SignInScreenErrorPreview() {
    ComposeArchLabTheme {
        SignInScreen(uiState = SignInUiState.Error("Wrong email or password"), onSignIn = { _, _ -> })
    }
}

@Preview(showBackground = true, name = "Signed in")
@Composable
private fun SignInScreenSignedInPreview() {
    ComposeArchLabTheme {
        SignInScreen(uiState = SignInUiState.SignedIn("amin@ttpu.uz"), onSignIn = { _, _ -> })
    }
}
