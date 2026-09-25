package com.example.kisanmitra.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kisanmitra.R
import com.example.kisanmitra.data.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    onPasswordUpdated: () -> Unit
) {

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    // Localized strings used inside coroutine
    val pleaseFillBothPasswordFields =
        stringResource(R.string.please_fill_both_password_fields)

    val passwordMinimum =
        stringResource(R.string.password_minimum)

    val passwordsNotMatch =
        stringResource(R.string.passwords_not_match)

    val passwordUpdatedSuccessfully =
        stringResource(R.string.password_updated_successfully)

    val failedToUpdatePassword =
        stringResource(R.string.failed_to_update_password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.reset_password),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = stringResource(R.string.enter_new_password),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.new_password))
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.confirm_password))
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                if (password.isBlank() || confirmPassword.isBlank()) {
                    message = pleaseFillBothPasswordFields
                    return@Button
                }

                if (password.length < 6) {
                    message = passwordMinimum
                    return@Button
                }

                if (password != confirmPassword) {
                    message = passwordsNotMatch
                    return@Button
                }

                scope.launch {

                    isLoading = true
                    message = ""

                    try {

                        SupabaseClientProvider.client.auth.updateUser {
                            this.password = password
                        }

                        isLoading = false

                        message = passwordUpdatedSuccessfully

                        onPasswordUpdated()

                    } catch (e: Exception) {

                        isLoading = false

                        message =
                            e.message ?: failedToUpdatePassword
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {

            Text(
                text = if (isLoading) {
                    stringResource(R.string.updating)
                } else {
                    stringResource(R.string.update_password)
                }
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (message.isNotBlank()) {

            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}