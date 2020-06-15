# Employees
Android app for displaying and adding employees.

Android // Kotlin // MVVM // Room // Retrofit // Kodein DI // ...

<a href="https://ibb.co/SN5ZsjM"><img src="https://i.ibb.co/wyB20kP/list.png" alt="list" border="0" height="400"></a>
<a href="https://ibb.co/y4mryvF"><img src="https://i.ibb.co/5KXP8ph/profile.png" alt="profile" border="0" height="400"></a>
<a href="https://ibb.co/TmXz75H"><img src="https://i.ibb.co/bzhpCnr/add.png" alt="add" border="0" height="400"></a>
<a href="https://ibb.co/hyr9Vwt"><img src="https://i.ibb.co/SPHyBG9/analytics.png" alt="analytics" border="0" height="400"></a>

Employees is a tiny android application written with Kotlin. It displays a list of employees with an option to add a new employee, shows existing employee profiles with headers of top 5 google hits on their name and some basic analytics.

In order to build and run open this project in Android Studio you firstly need to obtain an API key and create a custom search engine, all of which you can get here: 
https://developers.google.com/custom-search/v1/overview.
I configured mine to search the entire web by turning on "search the entire web" option and deleting "sites to search" options.
There is a limit of 100 free search requests per day.

You also need to have NDK and CMake enabled in SDK manager of Android Studio. The next step is to create a "native-lib.cpp" file in the app/src/main/cpp/ directory. Paste the following code and include your api key under "YOUR API KEY" ans "YOUR SEARCH ENGINE CONFIG KEY".
```cpp
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_katja_employeeslist_internal_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "YOUR API KEY";
    return env->NewStringUTF(api_key.c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_katja_employeeslist_internal_Keys_cxKey(JNIEnv *env, jobject object) {
    std::string cx_key = "YOUR SEARCH ENGINE CONFIG KEY";
    return env->NewStringUTF(cx_key.c_str());
}
```
Supposedly this is a safer way of saving API keys (source: https://www.codementor.io/blog/kotlin-apikeys-7o0g54qk5b).

At this point the app should successfuly run. :shipit:
