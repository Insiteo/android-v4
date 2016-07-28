<p align="center" >
  <img src="https://raw.github.com/Insiteo/ios-v4/assets/insiteo-logo.png" alt="Insiteo" title="Insiteo" style="width:80%;">
</p>

# Insiteo Android SDK

Insiteo Proximity Interaction platform allows you to easily enable your Apps with:

- user Notifications, even when the app is not running,
- content delivery (webviews, media content...),
- contextualization: use in-app deep linking to bring your user right to the valuable content,
- IoT and local interactions: control in-building devices or systems,
- any other value-added action you may be thinking of.

To get started with our SDK, follow the steps below.

For a broader solution overview, [click here](https://github.com/Insiteo/android-v4)


## Requirements

Insiteo Android SDK is available for Android 5.0 Lollipop higher.

## Insiteo Android SDK Class Reference

Insiteo Android SDK class latest reference is available [here](http://insiteo.github.io/sdk/Android/latest/index.html).


## Features

Insiteo Android SDK has been designed to work has a modular framework to make the installation easier. Each framework brings its own features as a submodule of the Core framework which includes API usage, analytics feature and basic location services detection.

- **Core** - The Core framework provides common SDK features such as Insiteo API usage, analytics tracking and location services detection (iBeacon and geofencing).
- **Push** - The Push framework brings interactivity to your app. According to user location and proximity detection, you will be able to trigger actions and notify your user with notifications, web views or other custom stuff 

## Installation

### Project Setup
1. Insiteo's API is provided as a single Android ARchive (.aar) file. Get it [here](https://github.com/Insiteo/android-v4)
2. In Android Studio you can easily import this archive by right clicking on your project -> new -> module 
3. Select 'Import JAR or AAR Package'. 
4. Make sure to add the module dependency afterward.

### Dependecies

If you haven't already, add these dependencies in build.graddle:

```bash
    compile 'com.squareup.retrofit:retrofit:2.0.0-beta2'
    compile 'com.squareup.retrofit:adapter-rxjava:2.0.0-beta2'
    compile 'com.squareup.retrofit:converter-gson:2.0.0-beta2'
```

### Permissions on Android M

**Since Android M**, Google requires you to ask user permission to use their location. You need to specify in your application *Info.plist* which location authorization is needed. Without any key, the SDK (and your application) will not have any access to location services.

- Just add onRequestPermissionResult to you AppCompatActivity, library will handle the rest

```java
public class MainActivityTab extends AppCompatActivity 
```

```java
private boolean isPermissionGranted = true;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                isPermissionGranted = false;
                Log.d(TAG, permissions[i] + " refused");
            } else {

                Log.w(TAG, permissions[i] + " granted");
            }
        }
        if (isPermissionGranted) {
            //initializeSdk
        }
        else {
            //whatever you want
        }
    }
```

## Code Snippets

- Launch the Api

```java
    Insiteo.initWithApiKey(ActivityContext, ApiKey);
```

- Stop the Api

```java
    Insiteo.stop();
```

- Update settings

```java
    if(Insiteo.getManager() != null) {
                    Insiteo.getManager().updateSettings();
    }
```

## Where To Go From Here?

- Read the [Getting Ready guide](https://github.com/Insiteo/android-v4/blob/master/getting_ready.md).