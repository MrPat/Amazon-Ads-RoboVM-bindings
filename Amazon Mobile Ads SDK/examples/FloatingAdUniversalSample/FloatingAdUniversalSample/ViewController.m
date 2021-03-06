// Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// Licensed under the Apache License, Version 2.0 (the "License").
// You may not use this file except in compliance with the License.
// A copy of the License is located at http://aws.amazon.com/apache2.0/
// or in the "license" file accompanying this file.
// This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// CONDITIONS OF ANY KIND, either express or implied. 
// See the License for the specific language governing permissions and
// limitations under the License.

#import <AmazonAd/AmazonAdView.h>
#import <AmazonAd/AmazonAdOptions.h>
#import <AmazonAd/AmazonAdError.h>
#import "ViewController.h"

@interface ViewController ()<AmazonAdViewDelegate, UIWebViewDelegate>
{
    AmazonAdView *_amazonAdView;
    UIInterfaceOrientation _lastOrientation;
}

- (void)loadAmazonAdWithUserInterfaceIdiom:(UIUserInterfaceIdiom)userInterfaceIdiom userInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation;
- (IBAction)loadAmazonAd:(UIButton *)sender;

@property (nonatomic, retain) AmazonAdView *amazonAdView;
@property (assign,nonatomic) UIInterfaceOrientation lastOrientation;

@end

@implementation ViewController
@synthesize loadAdButton;
@synthesize descLabel;
@synthesize lastOrientation = _lastOrientation;
@synthesize webView;
@synthesize amazonAdView = _amazonAdView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    // Load an amazon ad and create the AmazonAd view in the process
    [self loadAmazonAd:self.loadAdButton];
    
    // Register the ViewController with the delegate to receive callbacks.
    self.amazonAdView.delegate = self;
    
    [self.webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"http://www.amazon.com"]]];
}

- (BOOL) shouldAutorotate {
    return YES;
}

#pragma mark UIContentContainer protocol
- (void)viewWillTransitionToSize:(CGSize)size withTransitionCoordinator:(id<UIViewControllerTransitionCoordinator>)coordinator
{
    [super viewWillTransitionToSize:size withTransitionCoordinator:coordinator];
    [coordinator animateAlongsideTransition:nil completion:^(id<UIViewControllerTransitionCoordinatorContext> context){
        // Reload Amazon Ad upon rotation.
        // Important: Amazon expandable rich media ads target landscape and portrait mode separately.
        // If your app supports device rotation events, your app must reload the ad when rotating between portrait and landscape mode.
        [self loadAmazonAd:self.loadAdButton];
    }];
}

#pragma mark - IBAction
- (IBAction)loadAmazonAd:(UIButton *)sender
{
    // Load an amazon ad based on the current device and orientation
    [self loadAmazonAdWithUserInterfaceIdiom:[[UIDevice currentDevice] userInterfaceIdiom] userInterfaceOrientation:[[UIApplication sharedApplication] statusBarOrientation]];
}

#pragma mark - private
- (void)loadAmazonAdWithUserInterfaceIdiom:(UIUserInterfaceIdiom)userInterfaceIdiom userInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Set the ad options and load the ad
    AmazonAdOptions *options = [AmazonAdOptions options];
    // Test Requests disable metric tracking and typically have a 100% fill rate
    options.isTestRequest = YES;
    
    // Amazon Ad center Y offset from bottom. 
    // The value is based on the device and orientation, and it will be used for sliding off the floating ad.
    float amazonAdCenterYOffsetFromBottom = 0.0;
    
    // IMPORTANT: Create the AmazonAd view for requesting an ad of appropriate size based on the current device and orientation if necessary
    if (userInterfaceIdiom == UIUserInterfaceIdiomPhone) {
        amazonAdCenterYOffsetFromBottom = 25.0;
        
        if (self.amazonAdView == nil) {
            // Create the Amazon Ad view of size 320x50 for iPhone if we have not already
            self.amazonAdView = [AmazonAdView amazonAdViewWithAdSize:AmazonAdSize_320x50];
            self.amazonAdView.autoresizingMask = UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleBottomMargin |  UIViewAutoresizingFlexibleTopMargin;
        }
    } else {
        // Remove the Amazon Ad view since we may request an ad of different size
        [self.amazonAdView removeFromSuperview];
        
        if (UIInterfaceOrientationIsPortrait(interfaceOrientation)) {
            amazonAdCenterYOffsetFromBottom = 45.0;
            
            // Create the Amazon Ad view of size 728x90 for iPad while in portrait mode
            self.amazonAdView = [AmazonAdView amazonAdViewWithAdSize:AmazonAdSize_728x90];
            
            // Reposition and resize the Amazon Ad view to center at bottom
            [self.amazonAdView setCenter:CGPointMake(self.view.bounds.size.width / 2.0, self.view.bounds.size.height - 45.0)];
        } else {
            amazonAdCenterYOffsetFromBottom = 25.0;
            
            // Create the Amazon Ad view of size 1024x50 for iPad while in landscape mode
            self.amazonAdView = [AmazonAdView amazonAdViewWithAdSize:AmazonAdSize_1024x50];
           
            // Reposition and resize the Amazon Ad view to center at bottom
            [self.amazonAdView setCenter:CGPointMake(self.view.bounds.size.width / 2.0, self.view.bounds.size.height - 25.0)];
        }
        
        // Add the newly created Amazon Ad view to our view.
        [self.view addSubview:self.amazonAdView];
        
        // Register the ViewController with the delegate to receive callbacks.
        self.amazonAdView.delegate = self;
    }
    
    // Animate sliding Amazon Ad view off the scree with a 500 ms duration
    [UIView animateWithDuration:0.6 
                     animations:^{
                         self.amazonAdView.center = CGPointMake(self.view.bounds.size.width / 2.0, self.view.bounds.size.height + amazonAdCenterYOffsetFromBottom);
                     }
     ];
    
    // Load an amazon ad with the given options
    [self.amazonAdView loadAd:options];
}

#pragma mark AmazonAdViewDelegate
- (UIViewController *)viewControllerForPresentingModalView
{
    return self;
}

- (void)adViewDidLoad:(AmazonAdView *)view 
{
    [self.view addSubview:self.amazonAdView];
    
    // Amazon Ad center Y offset from bottom. 
    // The value is based on the device and orientation, and it will be used for sliding in the floating ad.
    float amazonAdCenterYOffsetFromBottom = 0.0;
    
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        amazonAdCenterYOffsetFromBottom = 25.0;
    } else {
        if (UIInterfaceOrientationIsPortrait([[UIApplication sharedApplication] statusBarOrientation])) {
            amazonAdCenterYOffsetFromBottom = 45.0;
        } else {
            amazonAdCenterYOffsetFromBottom = 25.0;
        }    
    }
    
    // Animate sliding Amazon Ad view into the scree from bottom with a 500 ms duration
    [UIView animateWithDuration:0.6 
                     animations:^{
                         self.amazonAdView.center = CGPointMake(self.view.bounds.size.width / 2.0, self.view.bounds.size.height - amazonAdCenterYOffsetFromBottom);
                     }
     ];
    
    NSLog(@"Ad loaded");
}

- (void)adViewDidFailToLoad:(AmazonAdView *)view withError:(AmazonAdError *)error
{
    NSLog(@"Ad Failed to load. Error code %d: %@", error.errorCode, error.errorDescription);
}

- (void)adViewWillExpand:(AmazonAdView *)view
{
    NSLog(@"Ad will expand");
    // Save orientation so when our ad collapses we can reload an ad
    // Also useful if you need to programmatically rearrange view on orientation change
    self.lastOrientation = [[UIApplication sharedApplication] statusBarOrientation];
}

- (void)adViewDidCollapse:(AmazonAdView *)view
{
    NSLog(@"Ad has collapsed");
    // Check for if the orientation has changed while the view disappeared.
    if (self.lastOrientation != [[UIApplication sharedApplication] statusBarOrientation]) {
        [self loadAmazonAd:self.loadAdButton];
    }
}

@end
