![Banner](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Play%20Store/Crypto-Tracker-Banner.jpg)<br><br>

<p align="center">
Crypto tracker is a free app build for wearable and allows users to quickly check the latest information about their favorite crypto.
The app shows the information by presenting the user with a clear list that has holds a chart with the historical information of the price and the latest price itself.
Users can request more information by tapping on a specify coin, and the app will show more in depth information about the coin (24h & 7d high and low, market cap, circulation etc..).
customize the app according to your preference, and change the currency and the dept of chart with the historical information (amount of days).
<br><br>

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Krakert/Crypto-Tracker?label=Latest%20version&style=for-the-badge)
</p>

# Screens
The app is designed with four main screens, each serving a specific purpose. Here's a detailed overview of each screen and the functionalities they offer:
## Overview
The **Overview** screen displays a list of coins that users have added from the [List of Coins to Follow](#coins-to-follow). The list includes the following information for each coin:
1. Coin Name
2. Latest Price
3. Market Chart for the last few days, based on user settings (refer to [Settings](#settings) for customization).

The list is sorted alphabetically, and at the end of the list, there are two buttons that allow users to navigate to the screen containing settings or to a list showing the top 100 coins.

## Coins to follow
This screen loads the top 100 current coins and presents them in a straightforward list. Each item has a heart icon on the right side. When a user clicks on a coin, it is added to the list of favorite coins, and the heart icon is filled to indicate it has been added. Previously added coins appear at the top of the list, making it convenient to remove them from favorites by clicking again. The coins marked here will be displayed on the Overview screen.

## Details
Clicking on any coin in the list on the **Overview** page opens the **Details** screen, providing comprehensive information, including:
- Percentage price change in the last 24 hours
- Percentage price change in the last 7 days
- Latest price
- Circulating supply
- Market cap
- Percentage market cap change in the last 24 hours
- Highest price in the Last 24 Hours
- Lowest price in the Last 24 Hours

Below this information, users have the option to remove the coin from their favorites.

## Settings
The settings can be found at the bottom of the overview list and the allow the user to changes some of their preferences.
- **Market chart history**
    - A slider that enables users to set the number of days the market chart will show, ranging from 1 to 14 days (default 7).
- **Max age**
    - This setting controls the cache duration for storing web results. Lowering this value increases the frequency of data loading (i.e., fetching the latest data). 
It is recommended to keep this value at the default of 5 minutes but can be adjusted between 2 and 10 minutes.
- **Currency**
    - The app supports a wide range of currencies, allowing users to choose their preferred one from options such as USD, EUR, JPY, GBP, AUD, CAD, CHF, and CNH.

Additionally, there is an option to reset the app, which removes all coins added to the favorite list and restores all settings back to default.

# 🛠 Built With

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous version of a Sequence, a type of collection whose values are lazily produced.
- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [Clean architecture](https://developer.android.com/topic/architecture) - This guide encompasses best practices and recommended architecture for building robust, high-quality apps.
- [Ktor](https://ktor.io/) - Create asynchronous client and server applications. Anything from microservices to multiplatform HTTP client apps in a simple way. 
- [KotlinX Serialization](https://kotlinlang.org/docs/serialization.html) - Serialization is the process of converting data used by an application to a format that can be transferred over a network or stored in a database or a file. 
- [CoinGecko API](https://www.coingecko.com/en/api/documentation) - Power your applications with CoinGecko’s independently sourced crypto data such as live prices, historical data images and more.
- [Landscapist](https://github.com/skydoves/landscapist) - Landscapist is a Jetpack Compose image loading solution that fetches and displays network images with Glide, Coil, and Fresco.
- [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics) - Get clear, actionable insight into app issues with this powerful crash reporting solution for Apple, Android, Flutter, and Unity.
- [Stateflow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous version of a Sequence, a type of collection whose values are lazily produced.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn"t destroyed on UI changes.
- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation) - The Navigation component provides support for Jetpack Compose applications.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Figma](https://figma.com/) - Figma is a vector graphics editor and prototyping tool which is primarily web-based.

# 📦 Package Structure

 ```
com.krakert.tracker
├── app                         # Main Application
├── buildSrc                    # Contains all dependencies and Gradle config (version types etc.)
├── data                        
│   ├── components              # Network and storage componentens (http client / mapper etc.)
│   ├── di                      # Package that holds all the providers and binders for dependency injection
│   ├── extension               # Some extension used by the module `Data`
│   └── tracker                 # All implementations of the repositories
│       ├── api                 # Object with all the API calles
│       ├── entity              # Data classes used by the module `Data`
│       └── mapper              # Mapper to map to Domain models  
├── domain          
│   ├── response                # Custom API call exceptions
│   └── tracker                 # Interface of the repositories and user stories
│       └── model               # Data classes used by module `Domain`
├── presentation      
│   ├── app                     # Navigation of the app
│   ├── components              # Shared UI components
│   ├── extension               # Some extension used by the module `Presentation`
│   ├── main                    # MainActivity of the app
│   ├── theme                   # Files for colours, shapes and sizes
│   └── tracker                 
│       ├── add                 # Viewmodel, UI, models and mappers for the add coin screen 
│       ├── detial              # Viewmodel, UI, models and mappers for the detial screen 
│       ├── formatter           # formatter used in the UI
│       ├── model               # Shared models in the UI
│       ├── overview            # Viewmodel, UI, models and mappers for the overview screen 
│       └── settings            # Viewmodel, UI, models and mappers for the settigns screen 
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