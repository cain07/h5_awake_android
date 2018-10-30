# h5_awake_android

介绍

##需求

1.h5 页面 可以启动 app 跳转到指定 页面a

2.如果没有 启动过 页面a 返回 到首页

3.如果 启动过app 页面a 返回 到上一页

4.a页面 里 有按钮 再点击 要判断是否已经登录 未登录 跳到登陆页


首先做成HTML的页面，页面内容格式如下：
```
<a href="[scheme]://[host]/[path]?[query]">启动应用程序</a>
```
manifest中配置能接受Scheme方式启动的activity

为了 能点击 返回到 首页 所以要创建 空白的 启动 跳转页面 AwakenActivity

```
<activity
    android:name=".AwakenActivity"
    android:launchMode="singleTask"
    android:screenOrientation="portrait" >
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.BROWSABLE" />
        <category android:name="android.intent.category.DEFAULT" />

        <data
            android:host="cain"
            android:pathPrefix="/october"
            android:scheme="monday" />
    </intent-filter>
</activity>

如果要配置JS可调用，需要添加这句：

<category android:name="android.intent.category.BROWSABLE"></category>

```
webview 加载页 配置 跳转栈
```
<activity android:name=".WebActivity"
    android:launchMode="standard"
    android:parentActivityName=".MainActivity"
    android:screenOrientation="portrait">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity"
        />

</activity>

```
如果 首页 在栈里 就直接 跳转到a
如果 没有在 栈里  就启动 栈
```
  if (AppManager.getAppManager().isOpenActivity(MainActivity.class)){
        startActivity(intent);
    } else {
        TaskStackBuilder.create(this)
                .addParentStack(intent.getComponent())
                .addNextIntent(intent)
                .startActivities();
    }
    
```
    
   
 使用URL调起Activity

Uri uri=Uri.parse("monday://cain/october?userId=11");
Intent intent=new Intent(Intent.ACTION_VIEW,uri);
startActivity(intent);

### 实际使用中的一些小细节

当自定义的URL配置在LAUNCHER对应的Activity上时，上述配置就足够了。

但是当自定义的URL配置在非LAUNCHER对应的Activity时，还需要增加额外几步操作。



#### 问题一：使用自定义的URL启动Activity时，默认是已FLAG_ACTIVITY_NEW_TASK的方式启动的，所以可能存在URL启动的Activity跟应用已启动的Activity不再同一个堆栈的现象。

解决方式：这种情况下，需要在manifest中将Activity多配置一个taskAffinity属性，约束URL启动的Activity与应用自身的启动的Activity在同一个堆栈中。

android:launchMode="singleTask"



#### 问题二：应用A使用url的方式唤起应用B的Activity时，可能存在应用B的Activity启动了，但是堆栈仍然在后台的现象，即应用B的Activity没有聚焦的问题。

解决方式：这种情况下，应用B的Activity收到启动的请求后，可以主动将Activity对应的堆栈移动到最前端。

ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
activityManager.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);

使用这种方式需要注意的是该api是在Android 3.0（api 11）以后才提供的，现在基本上手机rom版本都是Android4.4以上了，就不太需要关注3.0一下怎么处理了，且使用这个需要在manifest中申请android.permission.REORDER_TASKS权限。


# contribute 
如果本 工具 对你有较大的帮助，欢迎捐赠 


![image](https://github.com/cain07/atools/blob/master/WX20181029110830.png)

# 欢迎交流

wx zhenai907

qq 309657191
