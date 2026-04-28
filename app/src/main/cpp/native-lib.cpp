#include <jni.h>
#include <string>
#include <vector>
#include <stack>
#include <map>
#include <sstream>
#include <iostream>

// Helper to determine operator priority (PEMDAS)
int precedence(char op) {
    if (op == '+' || op == '-') return 1;
    if (op == '*' || op == '/') return 2;
    return 0;
}

double applyOp(double a, double b, char op) {
    switch (op) {
        case '+': return a + b;
        case '-': return a - b;
        case '*': return a * b;
        case '/': return (b != 0) ? a / b : 0;
        default: return 0;
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mycalculator_MainActivity_calculate(
        JNIEnv* env,
        jobject /* this */,
        jstring expression) {

    const char *nativeString = env->GetStringUTFChars(expression, 0);
    std::string str(nativeString);
    env->ReleaseStringUTFChars(expression, nativeString);

    if (str.empty()) return env->NewStringUTF("");

    std::stack<double> values;
    std::stack<char> ops;

    for (int i = 0; i < str.length(); i++) {
        if (str[i] == ' ') continue;

        if (isdigit(str[i]) || str[i] == '.') {
            std::string temp;
            while (i < str.length() && (isdigit(str[i]) || str[i] == '.')) {
                temp += str[i++];
            }
            try {
                values.push(std::stod(temp));
            } catch (...) {
                // In case of invalid double like ".."
            }
            i--;
        }
        else if (str[i] == '(') {
            ops.push(str[i]);
        }
        else if (str[i] == ')') {
            while (!ops.empty() && ops.top() != '(') {
                if (values.size() < 2) break;
                double val2 = values.top(); values.pop();
                double val1 = values.top(); values.pop();
                char op = ops.top(); ops.pop();
                values.push(applyOp(val1, val2, op));
            }
            if (!ops.empty() && ops.top() == '(') ops.pop();
        }
        else if (str[i] == '+' || str[i] == '-' || str[i] == '*' || str[i] == '/') {
            while (!ops.empty() && precedence(ops.top()) >= precedence(str[i])) {
                if (values.size() < 2) break;
                double val2 = values.top(); values.pop();
                double val1 = values.top(); values.pop();
                char op = ops.top(); ops.pop();
                values.push(applyOp(val1, val2, op));
            }
            ops.push(str[i]);
        }
    }

    while (!ops.empty()) {
        if (ops.top() == '(') {
            ops.pop();
            continue;
        }
        if (values.size() < 2) break;
        double val2 = values.top(); values.pop();
        double val1 = values.top(); values.pop();
        char op = ops.top(); ops.pop();
        values.push(applyOp(val1, val2, op));
    }

    if (values.empty()) return env->NewStringUTF("");

    double result = values.top();

    // Formatting the string to remove trailing .0
    std::ostringstream out;
    out.precision(10);
    out << result;
    std::string resStr = out.str();

    return env->NewStringUTF(resStr.c_str());
}