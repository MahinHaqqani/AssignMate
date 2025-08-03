package com.example.assignmate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(usersDatabase: Map<String, User>, onLogin: (String, String?) -> Unit) {
    var userRole by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = rollNumber,
            onValueChange = { rollNumber = it },
            label = { Text("Roll Number") },
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = {
                val user = usersDatabase[password]
                if (user != null && (user.rollNumber == rollNumber || user.role == "faculty")) {
                    userRole = user.role
                    onLogin(user.role, user.rollNumber)
                } else {
                    errorMessage = "Invalid Roll Number or Password"
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Login")
        }
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

