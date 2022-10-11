![Banner](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Play%20Store/Crypto-Tracker-Banner.jpg)<br><br>

<p align="center">
Crypto tracker is a free app build for wearable and allows users to quickly check the latest information about their favorite crypto.
The app shows the information by presenting the user with a clear list that has holds a chart with the historical information of the price and the latest price itself.
Users can request more information by tapping on a specify coin, and the app will show more in depth information about the coin (24h & 7d high and low, market cap, circulation etc..).
customize the app according to your preference, and change the currency and the dept of chart with the historical information (amount of days).
<br><br>

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Krakert/Crypto-Tracker?label=Latest%20version&style=for-the-badge)
</p>

# 🛠 Built With

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s
  modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - A coroutine is a
  concurrency design pattern that you can use on Android to simplify code that executes
  asynchronously.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous
  version of a Sequence, a type of collection whose values are lazily produced.
- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing
      manual dependency injection in your project.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [CoinGecko API](https://www.coingecko.com/en/api/documentation) - Power your applications with
  CoinGecko’s independently sourced crypto data such as live prices, historical data, images and
  more.
- [Landscapist](https://github.com/skydoves/landscapist) - Landscapist is a Jetpack Compose image
  loading solution that fetches and displays network images with Glide, Coil, and Fresco.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
    - [ROOM](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence
      library provides an abstraction layer over SQLite to allow for more robust database access
      while harnessing the full power of SQLite.
    - [Stateflow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is
      a state-holder observable flow that emits the current and new state updates to its collectors.
    - [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous
      version of a Sequence, a type of collection whose values are lazily produced.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn"t destroyed on UI changes.
    - [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation) - The
      Navigation component provides support for Jetpack Compose applications.
- [Material Components for Android](https://github.com/material-components/material-components-android)
    - Modular and customizable Material Design UI components for Android.
- [Figma](https://figma.com/) - Figma is a vector graphics editor and prototyping tool which is
  primarily web-based.

# 📦 Package Structure

 ```
com.krakert.tracker
├── api                         # Retrofit API calls and cacheratelimiter
├── database                    # Contains DAO for ROOM database with data converters
├── di                          # Package tha holds all the HILT dependency injection files
├── models          
│   ├── database                # Wrappers dataclasses used to store the dataclasses in package `responses` in a ROOM database  
│   ├── responses               # Raw JSON to dataclass models, only filtered by data that is needed
│   └── ui                      # Complete models that are used in the UI, includes all data needed to draw chart and show stats, data classes are not edit at this point
├── navigation                  # Navcontroller for the app
├── repository                  # Interface and Repository for the API, checks the cacheratelimiter and ROOM database before making a API call
├── ui                  
│   ├── shared                  # All shared Compose elements and states
│   ├── theme                   # Files for font and colours
│   ├── tile                    # Tile STILL WIP
│   ├── viewmodel               # Viewmodel for the Compose files
│   ├── AddCoinCompose.kt
│   ├── DetailsCompose.kt
│   ├── OverviewCompose.kt
│   └── SettingsCompose.kt
├── AppClass.kt
├── MainActivity.kt             
└── SharedPreference.kt         # Sharepref, used to store user settings 
```

# 🪲 Connect watch to debugger

## Samsung Galaxy Wear 4

1. Enable developers mode
    1. go to settings
    2. information about the clock
    3. Software-info
    4. Click on software version until you see the written developer mode activated
2. Enabling ADB
    1. Go to developer options
    2. Enable adb debugging and debugging via wifi
    3. Wait unit a IP address shows up below debugging via wifi
3. Connect ADB
    1. Open a terminal and type: `abd connect <ip address from previous step>`,
       example: `abd connect 192.168.2.8`
4. Accept the incoming connection on the watch

The watch is now connected to the debugger and you're ready to start debugging.

## Native WearOS

1. On the phone, open the Wear OS companion app.
    1. Scroll down to **Advanced Settings** and tap to view the **Advanced Settings** options.
    2. Enable **Debugging over Bluetooth**. A status message appears under the option. It looks like
       this:

``` 
Host: disconnected
Target: connected
```

At this point the development machine (the host) is not communicating with the watch (the target).
You need to complete the link.

In this final step, you'll use everything: the debugger, the phone, and the watch.

2. Connect the phone to your development machine with a USB cable.
3. Run these two commands in the debugger/terminal:

```
adb forward tcp:4444 localabstract:/adb-hub
adb connect 127.0.0.1:4444
```

4. After you type the connect command, look at the watch. It will ask you to confirm that you are
   allowing **ADB Debugging**.
5. On the phone, check the status display in the Wear OS companion app. It should look like this:

```
Host: connected
Target: connected
```

The watch is now connected to the debugger and you're ready to start debugging.

When you debug a watch using Bluetooth, adb always uses the IP address `127.0.0.1` plus the port
that you assigned. In this example, the port is 4444. All adb commands use the following format:

```
adb -s 127.0.0.1:4444 <command> 
```

# ❤️ Support my work

<a href="https://www.buymeacoffee.com/stefandekraker" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/default-orange.png" alt="Buy Me A Coffee" height="41" width="174"></a>
