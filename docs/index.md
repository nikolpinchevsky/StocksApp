# StocksApp 📈

[![](https://jitpack.io/v/nikolpinchevsky/StocksApp.svg)](https://jitpack.io/#nikolpinchevsky/StocksApp)

**StocksApp** is a complete mobile solution for tracking stock market data. The project demonstrates a full-stack architecture including a backend API service, a reusable Android SDK library, and a client demo application using modern Android development practices.

## 🏗 Architecture

The project is divided into three main layers:

1.  **API Service (Backend):**
    * Hosted on **Render**.
    * Connected to **MongoDB** for data persistence.
    * Provides RESTful endpoints for user authentication and stock data.
2.  **Android SDK (Library):**
    * A standalone library hosted on **JitPack**.
    * Handles all network communication using **Retrofit**.
    * Exposes simple `suspend` functions for the client app.
3.  **Android Example App (Client):**
    * A demo application written in **Kotlin**.
    * Demonstrates SDK usage, user login, and data visualization using **MPAndroidChart**.
    * 
## 🔗 Related Repositories

Backend API (Server):
https://github.com/nikolpinchevsky/Stocks-Server

## 🚀 Setup & Installation

To use the `StocksSDK` in your Android project, follow these steps:

### Step 1. Add the JitPack repository
Add the following to your root `settings.gradle.kts` file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("[https://jitpack.io](https://jitpack.io)") } // <--- Add this line
    }
}
```

### Step 2. Add the dependency
Add the library to your module's `build.gradle.kts`:

```kotlin
dependencies {
    // Check the badge above for the latest version
    implementation("com.github.nikolpinchevsky:StocksApp:1.1")
}
```


## 💻 Usage Example
Here is a simple example of how to use the SDK to fetch stock data:
```kotlin
// 1. Initialize the SDK with your server URL
val sdk = StocksSdk("https://stocks-server-e1vy.onrender.com/")

// 2. Use Coroutines to make async calls
lifecycleScope.launch {
    try {
        // Optional: Login to get a token
        sdk.login("user@email.com", "password123")

        // Get a specific stock quote
        val quote = sdk.quote("AAPL")
        println("Current price of Apple: $${quote.c}")

        // Get stock history for charts
        val history = sdk.getHistory("AAPL")
        
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
```

## 📸 Screenshots
<img width="370" height="825" alt="image" src="https://github.com/user-attachments/assets/3f4c80af-efd0-4aec-ad37-afdd7f6c24b9" /> <img width="375" height="825" alt="image" src="https://github.com/user-attachments/assets/e524526a-1154-4549-9c95-c38a5d942d2f" />  
<img width="373" height="825" alt="image" src="https://github.com/user-attachments/assets/acff2009-c665-47f6-9d3d-bc7355fa139d" />  <img width="376" height="829" alt="image" src="https://github.com/user-attachments/assets/b7588bc5-faf4-4508-9903-84a6434c6944" />


## 📚 Documentation
Live documentation is available here:
https://nikolpinchevsky.github.io/StocksApp/


## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

Developed by: Nikol Pinchevsky


