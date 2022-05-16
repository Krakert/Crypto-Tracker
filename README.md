## Connect watch to debugger

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
