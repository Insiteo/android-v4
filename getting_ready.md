# Getting Started with Insiteo iOS SDK

This is a guide to help developers get up to speed with Insiteo SDK. It will present all basic steps to initialize and interact properly with the SDK.

> This guide suppose that you have followed the framework installation process described [here](https://github.com/Insiteo/android-v4/blob/master/README.md).


## 1. SDK Initialization

> **Important:** We highly recommend to initialize and start the SDK into the `onCreate()` heritated method from your `Fragment` or `Activity` class in order to catch every events as soon as possible.

The first step to use Insiteo services is to initialize the SDK using your client `API-KEY` (available in your account in the Insiteo web interface) and define which modules will be used.

This will be done using `Insiteo` class which provides a couple of methods to interact with the SDK. The `initializeWithAPIKey(Context context, String apiKey)` method which returns `true` if the initialization succeed using last synchronized data, otherwise `NO` need your client API key and an array of class module. 

```java
    boolean success = Insiteo.initializeWithApiKey(context, apiKey);
    if (success) {
		// Success
    } else {
        // Error
    }
    ...
```

Insiteo SDK modules are all proposing three methods which defines usage methods such as start, stop and clean methods. The current available modules are:

- **INSAnalytics** which provides SDK analytics tracking and let you the possibility to track custom events.
- **INSPush** which provides an interactivity system based on location services detection (beacon and geofencing).

## 2. SDK Server Synchronization

In order to start Insiteo services, the SDK needs to retrieve the latest configuration from our server.  You therefore need to force a server synchronization at least once by calling the `synchronize()` method. The following example shows you how to catch if the SDK has never been synchronized:

```java
...
	boolean success = Insiteo.initializeWithApiKey(context, apiKey);
    if (success) {
		boolean synchoSuccess = Insiteo.getManager().updateSettings();
		if(synchroSuccess) {
		    // You can Start now
		} else {
		    //Error
		}
    } else {
        // Error
    }
...

```

### Keep SDK up-to-date with your server client configuration

In order to keep the SDK up-to-date with the latest server configuration, server synchronizations need to be done on a regular basis. You can do a synchronization on application start up (in your `onCreate` for example) or in the background, which is important to make sure the application is always up-to-date, event if it has not been started by the user for a while.

## 3. Start SDK

After your successful initialization and server synchronization, you are now able to start SDK common services and modules. Calling `start` method from `Insiteo` class will start automatically your available modules (if module *auto-start* is checked in your configuration). 

```objective-c
    ...
    // Init SDK
    boolean success = Insiteo.initializeWithApiKey(context, apiKey);
    if (success) {
        // Already synchronized
        if (Insiteo.isInitialized()) {
            // Start
            startSuccess = Insiteo.start()
            if (!startSuccess) {
                //Error
            }
        }
    }
    ...
}
```

### Force Module Start

If you have disabled *auto-start* for a specific module, you are able to force start by calling its own `start()` method. The following example force the Analytics module to start:

```java
// Check availability
if (Insiteo.isInitialized()) {
	// Start
    Insiteo.getManager().getAnalyticsManager().start();
}
```

> **Note:** 

> 1. You must have call `Insiteo.synchronize()` method at least once before trying to start anything or it will failed.
> 2. Even you have disable the *auto-start* and want to start manually your module, you MUST call `Insiteo.start()` method at first (to start location services and common features).

## 4. Stop SDK

To stop the Insiteo SDK, you can call the `stop()` method which will stop all running services (location services and modules).

```java
// Stop all your services
Insiteo.stop();
```
> **Warning**: Stopping the SDK will stop all location services and your application will not be waken in background by beacon proximity, unless you call the `Insiteo.start()` method again.


### Reset SDK

You are also able to reset everything from Insiteo SDK by calling the `reset()` method which will reset everything (stop everything + clean modules and reset your configuration). You will need to call `updateSettings()` method again to retrieve your data.

### Module Specific Stop & Clean

As the `start()` method, each module can be stopped or clean individually by calling its own `clean()` or `stop()` method.

## Where To Go From Here?

- [Analytics: Track Custom Events](https://github.com/Insiteo/android-v4/blob/master/analytics.md).
- [Push: Add Location Based Interactions To Your App](https://github.com/Insiteo/android-v4/blob/master/push.md).

## Where To Go From Here?

- Read the [Getting Ready guide](https://github.com/Insiteo/android-v4/blob/master/getting-ready.md).