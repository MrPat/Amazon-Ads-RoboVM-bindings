package org.robovm.bindings.amazon;


import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.amazon.AmazonAdOptions.AmazonAdHorizontalAlignment;
import org.robovm.bindings.amazon.AmazonAdOptions.AmazonAdVerticalAlignment;

public class Amazon extends UIApplicationDelegateAdapter {
	public static Amazon instance;
	public final String AMAZON_KEY = "YOUR_APP_KEY";
	public AmazonAdView amazonBanner;

	@Override
	public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
		// Keep a static reference to this class
		instance = this;
		return true;
	}

	public static void main(String[] args) {
		try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
			UIApplication.main(args, null, Amazon.class);
		}
	}

	public void initAmazonBanner() {
		System.out.println("Trying to instantiate Amazon banner");
		
		// Set the app key
		AmazonAdRegistration.getSharedRegistration().setAppKey(AMAZON_KEY);
		AmazonAdRegistration.getSharedRegistration().setLoggingEnabled(true);
		
		// Make the ad options
		AmazonAdOptions options = AmazonAdOptions.getOptionsInstance();
		options.setTestRequest(true); // Want to see test ads while testing
		
		// Instantiate the ad view. The intention is to use the native constants here but they give errors when referenced.
		// I tried the following lines with no luck
		//amazonBanner = new AmazonAdView(new CGRect(0, 0, UIScreen.getMainScreen().getBounds().getSize().getWidth(), 60));
		//amazonBanner = AmazonAdView.amazonAdViewWithAdSize(new CGSize(320, 50));
		//amazonBanner = new AmazonAdView(AmazonAdOptions.AmazonAdSize_320x50());
		amazonBanner = new AmazonAdView(new CGSize(320, 50));
		amazonBanner.setHorizontalAlignment(AmazonAdHorizontalAlignment.AmazonAdHorizontalAlignmentCenter);
		amazonBanner.setVerticalAlignment(AmazonAdVerticalAlignment.AmazonAdVerticalAlignmentFitToContent);
		
		// Set the delegate and implement the basic needs
		amazonBanner.setDelegate(new AmazonAdViewDelegateAdapter() {

			@Override
			public void adViewDidFailToLoad(AmazonAdView view, AmazonAdError error) {
				super.adViewDidFailToLoad(view, error);
				System.out.println("Amazon banner failed to load");
			}

			@Override
			public void adViewDidLoad(AmazonAdView view) {
				// Show the banner when it loads. I've yet to see this method be called
				super.adViewDidLoad(view);
				showAmazon();
				System.out.println("Amazon banner loaded");
			}

			@Override
			public UIViewController getViewControllerForPresentingModalView() {
				super.getViewControllerForPresentingModalView();
				return MyViewController.instance;
			}
		});

		// Add it to the screen
		MyViewController.instance.getView().addSubview(amazonBanner);

		amazonBanner.loadAd(options);
	}

	public void showAmazon() {
		try {
			CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
			double screenWidth = screenSize.getWidth();
			double screenHeight = screenSize.getHeight();

			CGSize adSize = amazonBanner.getBounds().getSize();
			double adHeight = adSize.getHeight();
			double adWidth = adSize.getWidth();
			
			if (adHeight <= 0 || adWidth <= 0) System.out.println("Invalid banner dimensions (" + adWidth + ", " + adHeight + ")");

			float bannerWidth = (float) screenWidth;
			float bannerHeight = (float) (bannerWidth / adWidth * adHeight);
			CGRect frame = new CGRect(screenWidth / 2 - adWidth / 2, 0, bannerWidth, bannerHeight);
			amazonBanner.setFrame(frame);
			
			System.out.println("Banner frame is now (" + frame.getX() + ", " + frame.getY() + ", " + frame.getWidth() + ", " + frame.getHeight() + ")");

			System.out.println("Showing Amazon banner");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
