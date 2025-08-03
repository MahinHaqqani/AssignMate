package com.example.assignmate

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignmate.ui.theme.AssignMateTheme

data class Assignment(
    val title: String,
    val dueDate: String,
    val subject: String,
    val studentSubmissions: MutableMap<String, SubmissionStatus> = mutableMapOf()
)

data class SubmissionStatus(
    var isSubmitted: Boolean = false,
    var fileUri: Uri? = null
)

data class User(
    val role: String,
    val rollNumber: String? = null,
    val password: String
)

class MainActivity : ComponentActivity() {

    private val usersDatabase = mapOf(
        "abc" to User(role = "student", rollNumber = "1", password = "abc"),
        "def" to User(role = "student", rollNumber = "2", password = "def"),
        "ghi" to User(role = "student", rollNumber = "3", password = "ghi"),
        "jkl" to User(role = "student", rollNumber = "4", password = "jkl"),
        "mno" to User(role = "faculty", password = "mno")
    )

    private val assignmentsState = mutableStateListOf(
        Assignment("DIP Assignment 1", "2024-12-10", "DIP"),
        Assignment("DIP Assignment 2", "2024-12-20", "DIP"),
        Assignment("AI Assignment 1", "2024-12-15", "AI"),
        Assignment("AI Assignment 2", "2024-12-25", "AI"),
        Assignment("Startup Assignment 1", "2024-12-20", "Startup"),
        Assignment("Startup Assignment 2", "2024-12-30", "Startup"),
        Assignment("SE Assignment 1", "2024-12-25", "SE"),
        Assignment("SE Assignment 2", "2024-12-31", "SE"),
        Assignment("IRS Assignment 1", "2024-12-30", "IRS"),
        Assignment("IRS Assignment 2", "2025-01-05", "IRS")
    )

    private var currentRollNumber: String? = null
    private var selectedAssignment: Assignment? = null

    private val pickDocumentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedAssignment?.let { assignment ->
                updateAssignmentSubmission(uri, currentRollNumber, assignment)
            } ?: showToast("No assignment selected!")
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            showToast("Permission required to access documents.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AssignMateTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(onSplashFinished = {
                            navController.navigate("welcome") {
                                popUpTo("splash") { inclusive = true }
                            }
                        })
                    }

                    composable("welcome") {
                        WelcomeScreen(onNavigateToLogin = { role ->
                            if (role == "student" || role == "faculty") {
                                navController.navigate("login")
                            }
                        })
                    }

                    composable("login") {
                        LoginScreen(usersDatabase = usersDatabase, onLogin = { role, rollNumber ->
                            currentRollNumber = rollNumber
                            if (role == "student") {
                                navController.navigate("subject_list")
                            } else if (role == "faculty") {
                                navController.navigate("faculty_dashboard")
                            }
                        })
                    }

                    composable("subject_list") {
                        SubjectListScreen(
                            subjects = listOf("DIP", "AI", "Startup", "SE", "IRS"),
                            onSubjectClick = { subject -> navController.navigate("assignments/${Uri.encode(subject)}") }
                        )
                    }

                    composable("assignments/{subject}") { backStackEntry ->
                        val subject = backStackEntry.arguments?.getString("subject") ?: "Unknown"
                        AssignmentListScreen(
                            subject = subject,
                            assignments = assignmentsState.filter { it.subject == subject },
                            rollNumber = currentRollNumber,
                            onUploadClick = { assignment ->
                                checkPermissionAndPickDocument(assignment)
                            }
                        )
                    }

                    composable("faculty_dashboard") {
                        FacultyDashboardScreen(
                            assignments = assignmentsState,
                            onOpenPdf = { pdfUri ->
                                openPdf(this@MainActivity, pdfUri)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun updateAssignmentSubmission(uri: Uri, rollNumber: String?, assignment: Assignment) {
        rollNumber?.let {
            // Update submission status
            val updatedSubmissions = assignment.studentSubmissions.toMutableMap()
            updatedSubmissions[it] = SubmissionStatus(isSubmitted = true, fileUri = uri)

            val index = assignmentsState.indexOf(assignment)
            if (index >= 0) {
                assignmentsState[index] = assignment.copy(studentSubmissions = updatedSubmissions)
            }

            showToast("${assignment.title} for ${assignment.subject} submitted by Roll No. $it!")
        } ?: showToast("Roll number not available.")
    }

    private fun checkPermissionAndPickDocument(assignment: Assignment) {
        selectedAssignment = assignment

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickDocumentLauncher.launch("*/*")
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun openPdf(context: Context, pdfUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            showToast("No PDF viewer found.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
