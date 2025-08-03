package com.example.assignmate

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AssignmentListScreen(
    subject: String,
    assignments: List<Assignment>,
    rollNumber: String?,
    onUploadClick: (Assignment) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Assignments for $subject",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        assignments.forEach { assignment ->
            val submissionStatus = assignment.studentSubmissions[rollNumber]
            val isSubmitted = submissionStatus?.isSubmitted == true

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Title: ${assignment.title}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Due Date: ${assignment.dueDate}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display assignment status
                    Text(
                        text = "Status: ${if (isSubmitted) "Submitted" else "Pending"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSubmitted) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )

                    if (!isSubmitted) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onUploadClick(assignment) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Upload Assignment")
                        }
                    }
                }
            }
        }
    }
}
