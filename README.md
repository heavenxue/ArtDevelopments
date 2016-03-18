 
《Android开发艺术探索》
===================================
  Android进阶
  《Android开发艺术探索》侧重于Android知识的体系化和系统工作机制的分析，通过《Android开发艺术探索》的学习可以极大地提高开发者的Android技术水平，从而更加高效地成为高级开发者。而对于高级开发者来说，仍然可以从《Android开发艺术探索》的知识体系中获益。

  目录
----
### * 第1章 Activity的生命周期和启动模式 / 1
      1.1 Activity的生命周期全面分析 / 1
      1.1.1 典型情况下的生命周期分析 / 2
      1.1.2 异常情况下的生命周期分析 / 8
      1.2 Activity的启动模式 / 16
      1.2.1 Activity的LaunchMode / 16
      1.2.2 Activity的Flags / 27
      1.3 IntentFilter的匹配规则 / 28
### * 第2章 IPC机制 / 35
      2.1 Android IPC简介 / 35
      2.2 Android中的多进程模式 / 36
      2.2.1 开启多进程模式 / 36
      2.2.2 多进程模式的运行机制 / 39
      2.3 IPC基础概念介绍 / 42
      2.3.1 Serializable接口 / 42
      2.3.2 Parcelable接口 / 45
      2.3.3 Binder / 47
      2.4 Android中的IPC方式 / 61
      2.4.1 使用Bundle / 61
      2.4.2 使用文件共享 / 62
      2.4.3 使用Messenger / 65
      2.4.4 使用AIDL / 71
      2.4.5 使用ContentProvider / 91
      2.4.6 使用Socket / 103
      2.5 Binder连接池 / 112
      2.6 选用合适的IPC方式 / 121
### * 第3章 View的事件体系 / 122
      3.1 View基础知识 / 122
      3.1.1 什么是View / 123
      3.1.2 View的位置参数 / 123
      3.1.3 MotionEvent和TouchSlop / 125
      3.1.4 VelocityTracker、GestureDetector和Scroller / 126
      3.2 View的滑动 / 129
      3.2.1 使用scrollTo/scrollBy / 129
      3.2.2 使用动画 / 131
      3.2.3 改变布局参数 / 133
      3.2.4 各种滑动方式的对比 / 133
      3.3 弹性滑动 / 135
      3.3.1 使用Scroller / 136
      3.3.2 通过动画 / 138
      3.3.3 使用延时策略 / 139
      3.4 View的事件分发机制 / 140
      3.4.1 点击事件的传递规则 / 140
      3.4.2 事件分发的源码解析 / 144
      3.5 View的滑动冲突 / 154
      3.5.1 常见的滑动冲突场景 / 155
      3.5.2 滑动冲突的处理规则 / 156
      3.5.3 滑动冲突的解决方式 / 157
### * 第4章 View的工作原理 / 174
      4.1 初识ViewRoot和DecorView / 174
      4.2 理解MeasureSpec / 177
      4.2.1 MeasureSpec / 177
      4.2.2 MeasureSpec和LayoutParams的对应关系 / 178
      4.3 View的工作流程 / 183
      4.3.1 measure过程 / 183
      4.3.2 layout过程 / 193
      4.3.3 draw过程 / 197
      4.4 自定义View / 199
      4.4.1 自定义View的分类 / 200
      4.4.2 自定义View须知 / 201
      4.4.3 自定义View示例 / 202
      4.4.4 自定义View的思想 / 217
### * 第5章 理解RemoteViews / 218
      5.1 RemoteViews的应用 / 218
      5.1.1 RemoteViews在通知栏上的应用 / 219
      5.1.2 RemoteViews在桌面小部件上的应用 / 221
      5.1.3 PendingIntent概述 / 228
      5.2 RemoteViews的内部机制 / 230
      5.3 RemoteViews的意义 / 239
### * 第6章 Android的Drawable / 243
      6.1 Drawable简介 / 243
      6.2 Drawable的分类 / 244
      6.2.1 BitmapDrawable / 244
      6.2.2 ShapeDrawable / 247
      6.2.3 LayerDrawable / 251
      6.2.4 StateListDrawable / 253
      6.2.5 LevelListDrawable / 255
      6.2.6 TransitionDrawable / 256
      6.2.7 InsetDrawable / 257
      6.2.8 ScaleDrawable / 258
      6.2.9 ClipDrawable / 260
      6.3 自定义Drawable / 262
### * 第7章 Android动画深入分析 / 265
      7.1 View动画 / 265
      7.1.1 View动画的种类 / 265
      7.1.2 自定义View动画 / 270
      7.1.3 帧动画 / 272
      7.2 View动画的特殊使用场景 / 273
      7.2.1 LayoutAnimation / 273
      7.2.2 Activity的切换效果 / 275
      7.3 属性动画 / 276
      7.3.1 使用属性动画 / 276
      7.3.2 理解插值器和估值器 / 280
      7.3.3 属性动画的监听器 / 282
      7.3.4 对任意属性做动画 / 282
      7.3.5 属性动画的工作原理 / 288
      7.4 使用动画的注意事项 / 292
### * 第8章 理解Window和WindowManager / 294
      8.1 Window和WindowManager / 294
      8.2 Window的内部机制 / 297
      8.2.1 Window的添加过程 / 298
      8.2.2 Window的删除过程 / 301
      8.2.3 Window的更新过程 / 303
      8.3 Window的创建过程 / 304
      8.3.1 Activity的Window创建过程 / 304
      8.3.2 Dialog的Window创建过程 / 308
      8.3.3 Toast的Window创建过程 / 311
### * 第9章 四大组件的工作过程 / 316
      9.1 四大组件的运行状态 / 316
      9.2 Activity的工作过程 / 318
      9.3 Service的工作过程 / 336
      9.3.1 Service的启动过程 / 336
      9.3.2 Service的绑定过程 / 344
      9.4 BroadcastReceiver的工作过程 / 352
      9.4.1 广播的注册过程 / 353
      9.4.2 广播的发送和接收过程 / 356
      9.5 ContentProvider的工作过程 / 362
### * 第10章 Android的消息机制 / 372
      10.1 Android的消息机制概述 / 373
      10.2 Android的消息机制分析 / 375
      10.2.1 ThreadLocal的工作原理 / 375
      10.2.2 消息队列的工作原理 / 380
      10.2.3 Looper的工作原理 / 383
      10.2.4 Handler的工作原理 / 385
      10.3 主线程的消息循环 / 389
### * 第11章 Android的线程和线程池 / 391
      11.1 主线程和子线程 / 392
      11.2 Android中的线程形态 / 392
      11.2.1 AsyncTask / 392
      11.2.2 AsyncTask的工作原理 / 395
      11.2.3 HandlerThread / 402
      11.2.4 IntentService / 403
      11.3 Android中的线程池 / 406
      11.3.1 ThreadPoolExecutor / 407
      11.3.2 线程池的分类 / 410
### * 第12章 Bitmap的加载和Cache / 413
      12.1 Bitmap的高效加载 / 414
      12.2 Android中的缓存策略 / 417
      12.2.1 LruCache / 418
      12.2.2 DiskLruCache / 419
      12.2.3 ImageLoader的实现 / 424
      12.3 ImageLoader的使用 / 441
      12.3.1 照片墙效果 / 441
      12.3.2 优化列表的卡顿现象 / 446
### * 第13章 综合技术 / 448
      13.1 使用CrashHandler来获取应用的crash信息 / 449
      13.2 使用multidex来解决方法数越界 / 455
      13.3 Android的动态加载技术 / 463
      13.4 反编译初步 / 469
      13.4.1 使用dex2jar和jd-gui反编译apk / 470
      13.4.2 使用apktool对apk进行二次打包 / 470
### * 第14章 JNI和NDK编程 / 473
      14.1 JNI的开发流程 / 474
      14.2 NDK的开发流程 / 478
      14.3 JNI的数据类型和类型签名 / 484
      14.4 JNI调用Java方法的流程 / 486
      第15章 Android性能优化 / 489
      15.1 Android的性能优化方法 / 490
      15.1.1 布局优化 / 490
      15.1.2 绘制优化 / 493
      15.1.3 内存泄露优化 / 493
      15.1.4 响应速度优化和ANR日志分析 / 496
      15.1.5 ListView和Bitmap优化 / 501
      15.1.6 线程优化 / 501
      15.1.7 一些性能优化建议 / 501
      15.2 内存泄露分析之MAT工具 / 502
      15.3 提高程序的可维护性 / 506[1] 
    
