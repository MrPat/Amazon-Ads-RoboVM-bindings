package org.robovm.bindings.amazon;


import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBAction;
import org.robovm.objc.annotation.IBOutlet;

@CustomClass("MyViewController")
public class MyViewController extends UIViewController {
	public static UIViewController instance;
	
    @IBOutlet
    private UILabel label;
    private int clickCount;

    @IBAction
    private void clicked() {
		Amazon.instance.initAmazonBanner();
    }

	@Override
	public void viewWillAppear(boolean animated) {
		super.viewWillAppear(animated);
		// Save a ref to the controller and try to load the ad. It should appear at the top of the screen if it works.
		instance = this;
	}
    
    
}
