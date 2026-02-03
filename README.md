# OTP Authentication App

A native Android application built with **Kotlin** and **Jetpack Compose** that implements a secure, passwordless login flow using **Email + OTP**.  
All authentication logic is handled locally, as no backend integration is required.

---

## 1. OTP Logic and Expiry Handling

The core business logic is encapsulated within `OtpManager.kt` to ensure correctness, security, and separation of concerns.

- **Generation:**  
  A 6-digit OTP is generated using:
  ```kotlin
  Random.nextInt(100000, 1000000)
This guarantees a full 6-digit numeric range.

### Storage
The generated OTP is stored in memory along with:
- Creation timestamp
- Number of validation attempts

### Expiry Rule
During validation, the current system time is compared against the OTP creation timestamp.  
If the difference exceeds **60 seconds**, the OTP is treated as expired and rejected.

### Attempt Limit
A maximum of **3 validation attempts** is allowed.  
If the user exceeds this limit, OTP verification is blocked and the user must request a new OTP.

### Resend OTP
Resending OTP generates a new code, invalidates the previous OTP, and resets the attempt counter.

All validation rules are enforced in the data layer to keep UI logic clean and predictable.


## 2. Data Structures Used & Reasoning

### `MutableMap<String, OtpData>`
**Usage:**  
Stores active OTP data keyed by the user’s email address.

**Why:**
- Provides **O(1)** average lookup time for OTP validation
- Ensures only one active OTP exists per email
- Makes OTP invalidation simple by overwriting existing entries

---

### `OtpData` (data class)
```kotlin
data class OtpData(
    val otp: String,
    val timeStamp: Long,
    var attempts: Int
)
```
**Why:**
- Groups all OTP-related fields logically
- Improves readability and maintainability
- Avoids scattered state variables

---

### Enum (`OtpRes`)
**Usage:**  
Represents OTP validation outcomes:
- `SUCCESS`
- `INVALID`
- `EXPIRED`
- `EXCEEDED`

**Why:**
- A Boolean return type was insufficient for handling multiple edge cases
- Enables precise error handling and user feedback
- Improves clarity and debuggability of validation logic

---

### Compose `MutableState`
**Usage:**  
Used in `AuthViewModel` and `SessionScreen`.

**Why:**
- Enables reactive UI updates
- Ensures UI recomposes automatically when state changes
- Keeps UI lifecycle-aware and configuration-change safe


## 3. External SDK Choice

### Timber (Logging)

**Timber** was selected for logging and analytics-related event tracking.

**Why Timber:**
- Reduces boilerplate compared to the standard `Log` API
- Eliminates the need for manual TAG management
- Allows clean removal of logs in production builds using `DebugTree`
- Fits the scope of a local-only assignment better than full analytics platforms

**Events Logged:**
- OTP generated
- OTP validation success
- OTP validation failure
- Logout action


## 4. AI Assistance Statement

In accordance with the assignment’s AI usage guidelines, AI tools were used responsibly as a productivity and learning aid.

### Strategic Planning
- Used AI to create a high-level development roadmap from the assignment PDF.
- Converted requirements into an actionable Notion checklist for organized and efficient implementation.

### Concept Learning
- Used AI to better understand Timber configuration for production-safe logging.
- Used AI to learn correct usage of `LaunchedEffect` for lifecycle-aware coroutine handling.

### Implemented and Understood by Me
- OTP generation, expiry, and attempt tracking logic
- `OtpManager` validation flow and state handling
- Enum-based result modeling for OTP validation
- MVVM architecture and one-way data flow
- ViewModel-backed UI state management
- Screen navigation based on observable state using `viewModel()`

All core business logic and UI flow were implemented manually to ensure full understanding rather than relying on generated templates or copied solutions.
