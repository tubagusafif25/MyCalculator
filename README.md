# MyCalculator
A sleek, high-performance Android calculator built with Kotlin and a native C++ (JNI) math engine. Features a modern dark UI, PEMDAS support, and high-precision calculations.

🧮 Modern Native Calculator
A high-performance Android calculator that combines the modern UI capabilities of Kotlin with the raw mathematical power of C++.

✨ Features
PEMDAS Logic: Uses a custom-built Shunting-yard algorithm in C++ to handle operator precedence correctly (Parentheses, Multiplication, Division, etc.).

Native Performance: Core logic is handled via JNI (Java Native Interface) in native-lib.cpp for fast, efficient processing.

Sleek Dark Mode: A modern "Squircle" button design with high-contrast neon accents for readability and style.

Precision Handling: Intelligently formats results to remove unnecessary trailing zeros (e.g., 1 + 1 = 2 instead of 2.0).

🛠️ Tech Stack
Frontend: Kotlin (View Binding, ConstraintLayout, Material Components)

Backend: C++ (JNI, Standard Template Library)

Build System: Gradle (Kotlin DSL) & CMake
