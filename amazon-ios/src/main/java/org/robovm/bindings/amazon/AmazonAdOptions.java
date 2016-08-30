package org.robovm.bindings.amazon;

import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.annotation.Method;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.Property;
import org.robovm.rt.bro.ValuedEnum;
import org.robovm.rt.bro.annotation.GlobalValue;
import org.robovm.rt.bro.annotation.Library;

@Library("Foundation")
@NativeClass
public class AmazonAdOptions extends NSObject {
	//
	//  AmazonAdOptions.h
	//  AmazonMobileAdsSDK
	//
	//  Copyright (c) 2012-2015 Amazon.com. All rights reserved.
	//

	/**
	 * Vertical alignment setting options.
	 */
	/*typedef NS_ENUM(NSInteger, AmazonAdVerticalAlignment)
{
    AmazonAdVerticalAlignmentTop,
    AmazonAdVerticalAlignmentCenter,
    AmazonAdVerticalAlignmentBottom,
    AmazonAdVerticalAlignmentFitToContent
};*/

	public enum AmazonAdVerticalAlignment implements ValuedEnum {
		AmazonAdVerticalAlignmentTop(0L),
		AmazonAdVerticalAlignmentCenter(1L),
		AmazonAdVerticalAlignmentBottom(2L),
		AmazonAdVerticalAlignmentFitToContent(3L)
		;

		private final long n;

		private AmazonAdVerticalAlignment(long n) { this.n = n; }

		@Override
		public long value() { return n; }

		public static AmazonAdVerticalAlignment valueOf(long n) {
			for (AmazonAdVerticalAlignment v : values()) {
				if (v.n == n) {
					return v;
				}
			}
			throw new IllegalArgumentException("No constant with value " + n + " found in " 
					+ AmazonAdVerticalAlignment.class.getName());
		}
	};

	/**
	 * Horizontal alignment setting options.
	 */
	/*typedef NS_ENUM(NSInteger, AmazonAdHorizontalAlignment)
	{
		AmazonAdHorizontalAlignmentLeft,
		AmazonAdHorizontalAlignmentCenter,
		AmazonAdHorizontalAlignmentRight
	};*/
	
	public enum AmazonAdHorizontalAlignment implements ValuedEnum {
		AmazonAdHorizontalAlignmentLeft(0L),
		AmazonAdHorizontalAlignmentCenter(1L),
		AmazonAdHorizontalAlignmentRight(2L)
		;

		private final long n;

		private AmazonAdHorizontalAlignment(long n) { this.n = n; }

		@Override
		public long value() { return n; }

		public static AmazonAdHorizontalAlignment valueOf(long n) {
			for (AmazonAdHorizontalAlignment v : values()) {
				if (v.n == n) {
					return v;
				}
			}
			throw new IllegalArgumentException("No constant with value " + n + " found in " 
					+ AmazonAdHorizontalAlignment.class.getName());
		}
	};

	/**
	 * Standard 320x50 Amazon Ad Size for phones.
	 */
	//const CGSize AmazonAdSize_320x50;
	@GlobalValue(symbol = "AmazonAdSize_320x50", optional=true)
	public static native CGSize AmazonAdSize_320x50();

	/**
	 * Standard 300x250 Amazon Ad Size.
	 */
	//const CGSize AmazonAdSize_300x250;
	@GlobalValue(symbol = "AmazonAdSize_300x250", optional=true)
	public static native CGSize AmazonAdSize_300x250();

	/**
	 * Standard 600x90 Amazon Ad Size for tablets.
	 */
	//const CGSize AmazonAdSize_600x90;
	@GlobalValue(symbol = "AmazonAdSize_600x90", optional=true)
	public static native CGSize AmazonAdSize_600x90();

	/** 
	 * Standard 728x90 Amazon Ad Size for tablets.
	 */
	//const CGSize AmazonAdSize_728x90;
	@GlobalValue(symbol = "AmazonAdSize_728x90", optional=true)
	public static native CGSize AmazonAdSize_728x90();

	/**
	 * Standard 1024x50 Amazon Ad Size for tablets.
	 */
	//const CGSize AmazonAdSize_1024x50;
	@GlobalValue(symbol = "AmazonAdSize_1024x50", optional=true)
	public static native CGSize AmazonAdSize_1024x50();

	/**
	 * Set the isTestRequest to YES, during development/integration only. 
	 * This option is turned off by default.
	 */
	//@property (nonatomic) BOOL isTestRequest;
	@Property(selector = "isTestRequest")
	public native boolean isTestRequest();
	
	@Property(selector = "setIsTestRequest:")
	public native void setTestRequest(boolean testRequest);

	/**
	 * If your application is enabled to read lat/long, you can configure 
	 * this option to receive geo targetted ads.
	 * This option is turned off by default.
	 */
	//@property (nonatomic) BOOL usesGeoLocation;
	@Property(selector = "usesGeoLocation")
	public native boolean usesGeoLocation();
	
	@Property(selector = "setUsesGeoLocation:")
	public native void setUsesGeoLocation(boolean usesGeoLocation);

	/**
	 * This will set the timeout of the request for the ad
	 *
	 * The minimum value is 5 seconds and the maximum value is 60 seconds
	 * The default value is 10 seconds
	 */
	//@property (nonatomic) NSTimeInterval timeout;
	// Apparently NSTimeInterval doesn't exist in RoboVM? Use double instead. Note this is measured in seconds
	@Property(selector = "timeout")
	public native double timeout();
	
	@Property(selector = "setTimeout:")
	public native void setTimeout(double timeout);

	/**
	 * Gets an instance of options to use.
	 */
	//+ (instancetype)options;
	@Method(selector = "options")
	public static native AmazonAdOptions getOptionsInstance();

	//- (void)setAdvancedOption:(NSString *)value forKey:(NSString *)key;
	@Method(selector = "setAdvancedOption:")
	public native void setAdvancedOption(String key, String value);
	
	//- (NSDictionary *)advancedOptions;
	@Method(selector = "advancedOptions")
	public native NSDictionary getAdvancedOptions();
}
