# 📚 AssignMate - Assignment Submission Android App

AssignMate is an Android application designed for students and faculty to efficiently manage assignment submissions. It allows students to upload their assignment PDFs and provides faculty with a clean dashboard to track, view, and manage submissions seamlessly.

---

# ✨ Features

* 📄 Student Role:

  * Login using Roll Number & Password.
  * View subject-wise assignments.
  * Upload assignment PDFs directly from device storage.
  * Track submission status.

* 🎓 Faculty Role:

  * Login using Faculty credentials.
  * View all assignments with student submissions.
  * Open submitted PDFs directly within the app.
  * Dashboard overview for quick access.

# 🖌️ Modern UI using Jetpack Compose

* 📁 PDF Upload & View Functionality
* 🔐 Simple Role-Based Authentication

---

# 🛠 Tech Stack

* Kotlin
* Jetpack Compose
* Android Navigation Component
* Material 3 Design
* Local State Management (no backend)

---

# 📂 Project Structure

```
AssignMate/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/assignmate/
│           │   ├── MainActivity.kt
│           │   ├── SplashScreen.kt
│           │   ├── WelcomeScreen.kt
│           │   ├── LoginScreen.kt
│           │   ├── SubjectListScreen.kt
│           │   ├── AssignmentListScreen.kt
│           │   ├── FacultyDashboardScreen.kt
│           │   └── models/Models.kt
│           ├── res/values/
│           │   ├── strings.xml
│           │   ├── colors.xml
│           │   └── themes.xml
│           └── AndroidManifest.xml
├── build.gradle (Project Level)
├── app/build.gradle (Module Level)
├── settings.gradle
└── README.md
```

---

# 🚀 How to Run

1. Clone the Repository:

   ```bash
   git clone https://github.com/MahinHaqqani/AssignMate.git
   ```

2. Open the project in **Android Studio**.

3. Sync Gradle & Build the Project.

4. Run the App on an Emulator or Physical Device.

---

# 🤝 Contributions

Feel free to fork the repo, open issues, or create pull requests!

---

