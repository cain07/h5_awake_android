# h5_awake_android

介绍

##需求

1.h5 页面 可以启动 app 跳转到指定 页面a

2.页面a 返回 到首页

3.a页面 里 有按钮 再点击 要判断是否已经登录 未登录 跳到登陆页


首先做成HTML的页面，页面内容格式如下：
```
<a href="[scheme]://[host]/[path]?[query]">启动应用程序</a>
```
首先在AndroidManifest.xml的MAIN Activity下追加以下内容。

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
    
    
    # contribute 
如果本 工具 对你有较大的帮助，欢迎捐赠 


![image](https://github.com/cain07/atools/blob/master/WX20181029110830.png)

# 欢迎交流

wx zhenai907

qq 309657191
