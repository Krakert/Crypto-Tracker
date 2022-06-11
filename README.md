![GitHub release (latest by date)](https://img.shields.io/github/v/release/Krakert/Crypto-Tracker?label=Latest%20version&style=for-the-badge)
# Capstone project crypto Tracker

# 🏁 Final Product (05-06) V1.1 "somebody toucha my spaghet"
| feature set                                     | State | Note |
|---                                              |---    |---   |
Track the current price of a crypto coin          | ✔️    | With the use of the Coin Gecko API              |
Add coins to keep track of                        | ✔️    | 
Detailed screen with statistics of selected coin  | ✔️    |
Option to set a coin as a favourite for the Tile  | ✔️    | Is will be the coin that should be on the Tile  |
Show quick statistics on a Tile                   | ❌    | Making a tile is nothing like Compose           |
Remove a coin for the tracking list               | ✔️    | 
Show a chart of the price history                 | ✔️    | 
Option to change the currency                     | ✔️    | Done with SharedPreferences                            |
Optional to set the history dept in days          | ✔️    | Done with SharedPreferences                            |
Option to change styling of the app               | ❌    | 
Per devices a collection of coins to track        | ✔️    | With the use of SharedPreferences instead of FireStore |
Optimized API call                                | ❌    | In need of a new API for the project                   |

See the GIF below for a short overview of the final (for now) product.\
![](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Capstone.gif?raw=true)

# 📲 Download APK 

## ![V1.1 "somebody toucha my spaghet"](https://play.google.com/store/apps/details?id=com.krakert.tracker)

see the release notes for more information and release notes per version:
### ![Releases notes](https://github.com/Krakert/Crypto-Tracker/releases)

# 🔜 What is next
The app still is missing a Tile, so that is the first thing that needs work.

Furthermore the CoinGecko API is oke, but the need of API that can handle a list of coin is high. Now for each coin the app makes about 2/3 calls. And when the users has a bunch of coin in their app, the amount of API calls grows very quickly.

The app has been tested on a single device, but when the app is used by multiple user, there is no way yet to keep track of a separate list of "favorite coins" per device, so that every device can have a unique list.

Rolling the app out into the app store.

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
- [Firebase](https://firebase.google.com/) - Firebase is an app development platform that helps you build and grow apps users love. Backed by Google and trusted by millions of businesses.
- [CoinGecko Kotlin](https://github.com/DrewCarlson/CoinGecko-Kotlin) - Multiplatform Kotlin wrapper for the CoinGecko API using Ktor.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
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
├── model                 # Model class for [Coins], [Coin] & [Favorite]
├── navigation            # For navigation handling
├── repository            # Used to handle all data operations
├── state                 # State class to handle state of the data
├── theme                 # Theme setup for this app
├── tile                  # Tile and custom repository for it
├── ui                    # All composables screens root folder
├── viewmodel             # ViewModels 
├── SharedPreference.kt   # SharedPreference to save user settings
└── MainActivity.kt       # MainActivity 
```


# ⌚ About the capstone (idea)
For the capstone, I'm going to create an app that can display crypto statistics. During the IoT theme semester I made a similar application, only this one was not for mobile. So now I'm going to recreate this for Android. Furthermore, the app will be a complete stand-alone app for Wear OS to challenge myself.

## Make
The app will consist of 2 parts, a tile, and the overlay. Via the tile, the user can retrieve the latest statistics of his favorite currency in an easy and simple way. The overlay provides more information about different coins and here users can get more details about a coin.

### overlay
To realize this app I will make a simple layout where the user will see a simple graph per coin, in which the lowest, highest and current price of the coin is displayed. Navigation will be within the overlay with swipes. For example, the user can retrieve additional information about a coin by swiping right on the coin of his choice. this screen will become a small list about the coins and will contain additional stats, this will display things like price and 'market cap'. At the bottom of the list in the overlay is the option to add additional coins to the list.

### Tile
Tiles provide easy access to the information and actions users need to get things done in seconds. With a simple swipe from the watch face, a user can see how their favorite coin is doing. This tile does not offer too much information so that the user can see at a glance what it is all about.

## User stories
**Epic: As a user I want an app that allows me to keep track of my favorite crypto coins on my watch. Per coin I have a graph showing the price of the market. With a few swipes I want to know everything about the coin and add or remove coins from my list.**
- Objective
    - As a user I want to be able to see the current price of a coin so that I can see whether it is rising or falling
    - As a user I want to see more stats about a coin like
    - As a developer, you want to be able to make an API call via Wear OS
    - As a developer likes to use different elements and gain basic knowledge
- Optional
    - As a user I would like to be able to quickly retrieve extra information about my favorite coin
    - As a developer I would like to make a graph of current data of the coin

# 🎨 UI Design

|   Overlay    | Add a coin to track    |   Details about a selected coin   | Tile 
|---	|---	|--- |---
|  ![](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Capstone%20layout-1.png?raw=true)    |  ![](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Capstone%20layout-2.png?raw=true)    |   ![](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Capstone%20layout-4.png?raw=true) | ![](https://github.com/Krakert/Crypto-Tracker/blob/main/design/Capstone%20layout-3.png?raw=true)    

# 🪲 Connect watch to debugger

- On the phone, open the Wear OS companion app.
- Scroll down to **Advanced Settings** and tap to view the **Advanced Settings** options.
- Enable **Debugging over Bluetooth**. A status message appears under the option. It looks like this: 
``` 
Host: disconnected
Target: connected
```
At this point the development machine (the host) is not communicating with the watch (the target). You need to complete the link.

In this final step, you'll use everything: the debugger, the phone, and the watch.

- Connect the phone to your development machine with a USB cable.
- Run these two commands in the debugger/terminal:
```
adb forward tcp:4444 localabstract:/adb-hub
adb connect 127.0.0.1:4444
```
- After you type the connect command, look at the watch. It will ask you to confirm that you are allowing **ADB Debugging**.
- On the phone, check the status display in the Wear OS companion app. It should look like this:  
```
Host: connected
Target: connected
```
The watch is now connected to the debugger and you're ready to start debugging.


When you debug a watch using Bluetooth, adb always uses the IP address `127.0.0.1` plus the port that you assigned. In this example, the port is 4444. All adb commands use the following format:
```
adb -s 127.0.0.1:4444 <command> 
```

# ❤️ Support my work
<a href="https://www.buymeacoffee.com/stefandekraker" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/default-orange.png" alt="Buy Me A Coffee" height="41" width="174"></a>
