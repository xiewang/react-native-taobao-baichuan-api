//
//  React_Native_Taobao_Baichuan_Api.m
//  React-Native-Taobao-Baichuan-Api
//
//  Created by Xie, Wang on 10/28/16.
//  Copyright © 2016 Xie, Wang. All rights reserved.
//

#import "React_Native_Taobao_Baichuan_Api.h"

@implementation React_Native_Taobao_Baichuan_Api

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
  
    [[ALBBSDK sharedInstance] setDebugLogOpen:YES]; // 打开debug日志
    [[ALBBSDK sharedInstance] setUseTaobaoNativeDetail:NO]; // 优先使用手淘APP打开商品详情页面，如果没有安装手机淘宝，SDK会使用H5打开
    [[ALBBSDK sharedInstance] setViewType:ALBB_ITEM_VIEWTYPE_TAOBAO];// 使用淘宝H5页面打开商品详情
    [[ALBBSDK sharedInstance] setISVCode:@"my_isv_code"]; //设置全局的app标识，在电商模块里等同于isv_code
    [[ALBBSDK sharedInstance] asyncInit:^{ // 基础SDK初始化
        NSLog(@"init success");
    } failure:^(NSError *error) {
        NSLog(@"init failure, %@", error);
    }];
    
    return YES;
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation{
    BOOL isHandled = [[ALBBSDK sharedInstance] handleOpenURL:url]; // 如果百川处理过会返回YES
    if (!isHandled) {
        // 其他处理逻辑
    }
    return YES;
}

RCT_EXPORT_MODULE(React_Native_Taobao_Baichuan_Api);

RCT_EXPORT_METHOD(jump:(NSString *)itemId)
{
    
    [self itemDetailPage];
   
}

-(void)itemDetailPage{
    // create page
    NSString *itemID = @"AAF6OHtzACXqtpfJSgJWv2Jc";//itemId可以传入真实的或者混淆的商品id
    
    NSDictionary *params = @{@"_viewType" : @"taobaoH5", @"isv_code" : @"tag1"};
    //iemDetailPage的params有如下参数可以配置:
    // isv_code :开发者自己传入，可以在订单中跟踪此参数
    //_viewType : taobaoH5 （淘宝H5）
    ALBBPage *page = [ALBBPage itemDetailPage:itemID params:params];
    
    // show
    id <ALBBTradeService> tradeService = [[ALBBSDK sharedInstance] getService:@protocol(ALBBTradeService)];
    [tradeService       show:null
                  isNeedPush:NO
           webViewUISettings:nil
                        page:page
 tradeProcessSuccessCallback:^(TaeTradeProcessResult * __nullable result) { /* handle result */ }
  tradeProcessFailedCallback:^(NSError * __nullable error) { /* handle error */ }
     ];

}


@end
