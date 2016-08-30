package org.robovm.bindings.amazon;

import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.objc.annotation.Property;
import org.robovm.rt.bro.ValuedEnum;
import org.robovm.rt.bro.annotation.Library;

@Library("Foundation")
@NativeClass
public class AmazonAdError extends NSObject {
	//
//  AmazonAdError.h
//  AmazonMobileAdsSDK
//
//  Copyright (c) 2012-2015 Amazon.com. All rights reserved.
//

/*typedef enum {
    AmazonAdErrorRequest,           // Invalid Request. Example : "An invalid request was sent".
    AmazonAdErrorNoFill,            // No ad returned from the server. Example : "Ad not found".
    AmazonAdErrorInternalServer,    // Internal server error. Example : "Failed to load configuration".
    AmazonAdErrorNetworkConnection, // Network Connection error
    AmazonAdErrorReserved
} AmazonAdErrorCode;*/

public enum AmazonAdErrorCode implements ValuedEnum {
	AmazonAdErrorRequest(0L),           // Invalid Request. Example : "An invalid request was sent".
    AmazonAdErrorNoFill(1L),            // No ad returned from the server. Example : "Ad not found".
    AmazonAdErrorInternalServer(2L),    // Internal server error. Example : "Failed to load configuration".
    AmazonAdErrorNetworkConnection(3L), // Network Connection error
    AmazonAdErrorReserved(4L)
	;

	private final long n;

	private AmazonAdErrorCode(long n) { this.n = n; }

	@Override
	public long value() { return n; }

	public static AmazonAdErrorCode valueOf(long n) {
		for (AmazonAdErrorCode v : values()) {
			if (v.n == n) {
				return v;
			}
		}
		throw new IllegalArgumentException("No constant with value " + n + " found in " 
				+ AmazonAdErrorCode.class.getName());
	}
};

//@property (nonatomic, readonly) AmazonAdErrorCode errorCode;
@Property(selector = "errorCode")
public native AmazonAdErrorCode getErrorCode();

//@property (nonatomic, strong, readonly) NSString *errorDescription; 
@Property(selector = "errorDescription", strongRef = true)
public native String getErrorDescription();

}
