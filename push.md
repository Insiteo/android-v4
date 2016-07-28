# Push : Customize push interactions

When the push  module is started in our SDK, default events will be reported without you having to do anything. Our platform will trigger events such as : notification or webview.

This page indicates how to start the push component and how to customize the push events if you wish.

For a broader solution overview, [click here](http://insiteo.github.io/)


## Requirements

1. Successful SDK initialization and synchronization with `[INSPush class]` module added (see [SDK Initialization](https://github.com/Insiteo/android-v4/blob/master/README.md#1-sdk-initialization)).
2. Auto-start or manually start (see [SDK Start](https://github.com/Insiteo/android-v4/blob/master/getting-ready.md#3-sdk-start))


## Customize push Events

The SDK provides you the possibility to custom the push event behavior. Your custom events will be treated like every other SDK events.

First, you have to implement your activity with ActionLauncher.Callbacks

```java
...
	public class MainActivity extends Activity implements ActionLauncher.Callbacks {
}
...
```

Then, provide the Action Callback to `PushManager`


```java
...
        Insiteo.getManager().getPushManager().setActionCallbacks(this);
...
```


Once finished, just implement the callbacks methods like this in your activity:


```java
...

	@Override
    public void onActionNotification() {
		//Do what you want
    }

    @Override
    public void onActionCustom(String s) {
		//Do what you want
    }

    @Override
    public void onActionWebview(String s) {
        //Do what you want
    }
...
```
