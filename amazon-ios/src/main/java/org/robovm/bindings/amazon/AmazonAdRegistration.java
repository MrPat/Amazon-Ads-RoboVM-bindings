package org.robovm.bindings.amazon;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Library;

@Library("Foundation")
@NativeClass
public class AmazonAdRegistration extends NSObject {
	//
	//  AmazonAdRegistration.h
	//  AmazonMobileAdsSDK
	//
	//  Copyright (c) 2012-2015 Amazon.com. All rights reserved.
	//

	//+ (instancetype)sharedRegistration;
	@Method(selector = "sharedRegistration")
	public static native AmazonAdRegistration getSharedRegistration();

	// Set the applicationId provided by the Amazon Appstore.
	//- (void)setAppKey:(NSString *)appKey;
	@Method(selector = "setAppKey:")
	public native void setAppKey(String appKey);

	// Enable/Disable logging. Logging is turned off by default.
	//- (void)setLogging:(BOOL)isEnabled;
	@Method(selector = "setLogging:")
	public native void setLoggingEnabled(boolean isEnabled);

	// The current Ad SDK version.
	//- (NSString *)sdkVersion;
	@Method(selector = "sdkVersion")
	public native String getSdkVersion();

	// Register your app to track app downloads from mobile ad campaigns. 
	// To add app download conversion tracking to your app, invoke the 
	// setApplicationId and the registerApp from your applicationDelegate's
	// applicationDidBecomeActive protocol method.
	// 
	// Note: Calling loadAd automatically registers your app. 
	// Only call this method if you are not using the Amazon Ads SDK to 
	// display Ads, but to track app downloads from mobile ads campaigns.
	//- (void)registerApp;
	@Method(selector = "registerApp")
	public native void registerApp();

}
