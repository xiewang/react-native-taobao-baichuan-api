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
    
    NSString *itemID = item;
    id<AlibcTradePage> page = [AlibcTradePageFactory itemDetailPage: itemID];
    //淘客信息
    AlibcTradeTaokeParams *taoKeParams=[[AlibcTradeTaokeParams alloc] init];
    taoKeParams.pid= nil;
    //打开方式
    AlibcTradeShowParams* showParam = [[AlibcTradeShowParams alloc] init];
    showParam.openType = ALiOpenTypeAuto;
    
    
    // ALiTradeWebViewController类中,webview的delegate设置不能放在viewdidload里面,必须在init的时候,否则函数调用的时候还是nil
//    UINavigationController *rootViewController ;
//    UIViewController *rootViewController = RCTPresentedViewController();
    UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    while (root.presentedViewController != nil) {
        root = root.presentedViewController;
    }

    
    NSInteger ret = [[AlibcTradeSDK sharedInstance].tradeService
     show: root
     page: page
     showParams: showParam
     taoKeParams: taoKeParams
     trackParam:nil
     tradeProcessSuccessCallback:^(AlibcTradeResult * __nullable result) {
            NSLog(@"%@", result);
        }
    tradeProcessFailedCallback:^(NSError * __nullable error) {
            NSLog(@"%@", error);
        }
    ];
//    //返回1,说明h5打开,否则不应该展示页面
    if (ret == 1) {
         
//        [self.navigationController pushViewController:view animated:YES];
    }
    return ;
}


@end