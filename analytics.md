# Analytics: Track Custom Events

When the analytics module is started in our SDK, default events will be reported without you having to do anything. On the monitoring end, our platform will derive indicators such as : number of actions executed per user/type/date, action conversions, location analysis and dwell times.

This page indicates how to start the analytics component and how to report your own custom analytics events if you wish.

For a broader solution overview, [click here](http://insiteo.github.io/)


## Requirements

1. Successful SDK initialization and synchronization with `[INSAnalytics class]` module added (see [SDK Initialization](https://github.com/Insiteo/android-v4/blob/master/README.md#1-sdk-initialization)).
2. Auto-start or manually start (see [SDK Start](https://github.com/Insiteo/android-v4/blob/master/getting-ready.md#3-sdk-start))


## Track Custom Events

The SDK provides you the possibility to track custom analytics events. Your custom events will be treated like every other SDK events and will be sent to the server if the module is started.

To track custom events you must call the `trackCustomEvent(String metadata)`. This method will create an internal custom event with a name as a string and optional metadata as a dictionary of string keys/values that will be send to the server. The debug parameter is used to flag the message in debug mode, and the error, if not nil, will provide information if an error occured:

```java
...
	CustomEvents customEvent = new CustomEvents();
    customEvent.setMetadata("{ @\"user_id\": @\"1234\", @\"account_type\": @\"facebook\" }");
    Insiteo.getManager().getAnalyticsManager().trackCustomEvent(customEvent);
...
```
