package com.example.assignmate

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FacultyDashboardScreen(
    assignments: List<Assignment>,
    onOpenPdf: (Uri) -> Unit // Callback to open PDFs
) {
    // Scroll state to enable scrolling
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Enables scrolling
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Faculty Dashboard",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // List of assignments
        assignments.forEach { assignment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Assignment Details
                    Text(
                        text = "Assignment: ${assignment.title}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Due Date: ${assignment.dueDate}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Subject: ${assignment.subject}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Submissions Section
                    val submittedStudents = assignment.studentSubmissions.filter { it.value.isSubmitted }
                    if (submittedStudents.isNotEmpty()) {
                        Text(
                            text = "Submitted Students:",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        submittedStudents.forEach { (rollNumber, submission) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Roll Number
                                Text(
                                    text = "Roll Number: $rollNumber",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                // Open PDF Button
                                Button(
                                    onClick = { submission.fileUri?.let(onOpenPdf) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(text = "View PDF", color = Color.White)
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No Submissions",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error)
                        )
                    }
                }
            }
        }
    }
}
