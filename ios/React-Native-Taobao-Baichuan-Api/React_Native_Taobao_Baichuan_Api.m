//
//  React_Native_Taobao_Baichuan_Api.m
//  React-Native-Taobao-Baichuan-Api
//
//  Created by Xie, Wang on 10/28/16.
//  Copyright © 2016 Xie, Wang. All rights reserved.
//

#import "React_Native_Taobao_Baichuan_Api.h"

@implementation React_Native_Taobao_Baichuan_Api


RCT_EXPORT_MODULE(React_Native_Taobao_Baichuan_Api);


RCT_EXPORT_METHOD(jump:(NSString *)itemId)
{
    
    [self itemDetailPage: itemId];
    
}

-(void)itemDetailPage: (NSString *) item{
    UIViewController *rootViewController = RCTPresentedViewController();
    
    // create page
    NSString *itemID = item;//itemId可以传入真实的或者混淆的商品id
    
    NSDictionary *params = @{@"_viewType" : @"taobaoH5", @"isv_code" : @"tag1"};
    //iemDetailPage的params有如下参数可以配置:
    // isv_code :开发者自己传入，可以在订单中跟踪此参数
    //_viewType : taobaoH5 （淘宝H5）
    ALBBTradePage *page = [ALBBTradePage itemDetailPage:itemID params:params];
    ALBBTradeTaokeParams *taoKeParams=[[ALBBTradeTaokeParams alloc] init];
    taoKeParams.pid = @"xxx";
    
    // show
    id <ALBBTradeService> tradeService = [[ALBBSDK sharedInstance] getService:@protocol(ALBBTradeService)];
    [tradeService       show:rootViewController
                  isNeedPush:NO
           webViewUISettings:nil
                        page:page
                 taoKeParams:taoKeParams
 tradeProcessSuccessCallback:^(ALBBTradeResult * __nullable result) {
     NSLog(@"%@", result);
 }
  tradeProcessFailedCallback:^(NSError * __nullable error) {
      NSLog(@"%@", error);
  }
     ];
    return;
}


@end