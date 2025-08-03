package com.example.assignmate.models

import android.net.Uri

// Model to represent an Assignment
data class Assignment(
    val title: String,
    val dueDate: String,
    val subject: String, // The subject for the assignment
    val studentSubmissions: Map<String, SubmissionStatus>, // Map of student roll numbers to their submission status
    var status: String = "Pending" // The overall assignment status (Pending/Submitted)
)

// Model to represent the status of a student's submission
data class SubmissionStatus(
    var isSubmitted: Boolean = false, // Whether the student has submitted the assignment
    var fileUri: Uri? = null // URI for the student's submitted PDF (if any)
)

