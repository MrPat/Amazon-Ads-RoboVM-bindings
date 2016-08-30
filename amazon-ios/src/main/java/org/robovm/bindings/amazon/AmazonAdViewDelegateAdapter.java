package org.robovm.bindings.amazon;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.uikit.UIViewController;

public class AmazonAdViewDelegateAdapter extends NSObject implements AmazonAdView.AmazonAdViewDelegate {

	@Override
	public UIViewController getViewControllerForPresentingModalView() {
		return null;
	}

	@Override
	public void adViewWillExpand(AmazonAdView view) {
		
	}

	@Override
	public void adViewDidCollapse(AmazonAdView view) {
		
	}

	@Override
	public void adViewWillResize(AmazonAdView view, CGRect frame) {
		
	}

	@Override
	public boolean willHandleAdViewResize(AmazonAdView view, CGRect frame) {
		return false;
	}

	@Override
	public void adViewDidFailToLoad(AmazonAdView view, AmazonAdError error) {
		
	}

	@Override
	public void adViewDidLoad(AmazonAdView view) {
		
	}

}
