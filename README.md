# MyCalculator

A sleek Android calculator built with **Kotlin** and a native **C++ (JNI)** math engine. Modern dark UI, correct operator precedence, and clean number formatting.

## ✨ Features

- **Correct order of operations:** a custom Shunting-yard parser in C++ handles parentheses, multiplication, division, addition and subtraction with proper precedence.
- **Native performance:** expression evaluation runs in C++ (`native-lib.cpp`) and is called from Kotlin through JNI.
- **Dark mode UI:** "squircle" buttons with high-contrast neon accents.
- **Clean results:** removes unnecessary trailing zeros (e.g. `1 + 1 = 2`, not `2.0`).

## 🛠️ Tech Stack

| Layer | Tech |
|---|---|
| UI | Kotlin, View Binding, ConstraintLayout, Material Components |
| Math engine | C++ with the STL, exposed through JNI |
| Build | Gradle (Kotlin DSL) and CMake |

## 📁 Project Structure

```text
app/src/main/
├── java/com/example/mycalculator/MainActivity.kt   # UI and input handling
├── cpp/native-lib.cpp                              # Shunting-yard parser and evaluator
├── cpp/CMakeLists.txt                              # Native build config
└── res/                                            # Layouts, colours, themes, icons
```

## ▶️ Build and Run

Requirements: Android Studio with the **NDK** and **CMake** installed (SDK Manager → SDK Tools). Min SDK 24.

1. Clone the repo and open it in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or a device.

Or from the command line:

```bash
./gradlew assembleDebug
```
