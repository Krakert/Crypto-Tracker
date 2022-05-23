# About the capstone
For the capstone, I'm going to create an app that can display crypto statistics. During the IoT theme semester I made a similar application, only this one was not for mobile. So now I'm going to recreate this for Android. Furthermore, the app will be a complete stand-alone app for Wear OS to challenge myself.

## Make
The app will consist of 2 parts, a tile, and the overlay. Via the tile, the user can retrieve the latest statistics of his favorite currency in an easy and simple way. The overlay provides more information about different coins and here users can get more details about a coin.

### overlay
To realize this app I will make a simple layout where the user will see a simple graph per coin, in which the lowest, highest and current price of the coin is displayed. Navigation will be within the overlay with swipes. For example, the user can retrieve additional information about a coin by swiping right on the coin of his choice. this screen will become a small list about the coins and will contain additional stats, this will display things like price and 'market cap'. At the bottom of the list in the overlay is the option to add additional coins to the list.

### Tile
Tiles provide easy access to the information and actions users need to get things done in seconds. With a simple swipe from the watch face, a user can see how their favorite coin is doing. This tile does not offer too much information so that the user can see at a glance what it is all about.

### User stories
**Epic: As a user I want an app that allows me to keep track of my favorite crypto coins on my watch. Per coin I have a graph showing the price of the market. With a few swipes I want to know everything about the coin and add or remove coins from my list.**
- Objective
    - As a user I want to be able to see the current price of a coin so that I can see whether it is rising or falling
    - As a user I want to see more stats about a coin like
    - As a developer, you want to be able to make an API call via Wear OS
    - As a developer likes to use different elements and gain basic knowledge
- Optional
    - As a user I would like to be able to quickly retrieve extra information about my favorite coin
    - As a developer I would like to make a graph of current data of the coin

# Connect watch to debugger

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


When you debug a watch using Bluetooth, adb alway uses the IP address `127.0.0.1` plus the port that you assigned. In this example, the port is 4444. All adb commands use the following format:
```
adb -s 127.0.0.1:4444 <command> 
```
