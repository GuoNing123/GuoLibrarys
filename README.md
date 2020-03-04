# GuoLibrarys
## versionupgradelibrary是一个app版本下载更新的插件；
        该插件提供了一个更新提示dailog，下载apk文件的service和展示下载进度的notification;下载完成后自动调用apk安装逻辑，完成app更新。
该流程虽然没有太多的技术难度，但是我在工作中每开发一个新项目都要重写一遍更新逻辑实在是很烦，于是干脆把这个流程直接封装起来打包成aar一劳永逸。
### 【集成方式】
#### 第一步：工程build.gradle文件里添加入代码
```java
allprojects {
    repositories {
        maven { url "https://raw.githubusercontent.com/GuoNing123/versionupgradelibrary/master" }
    }
}
```
#### 第二步：app的build.gradle文件中添加引用
        implementation 'com.GN.versionupgradelibrary:encrypt:1.0.1'

### 【使用方法】
```java
//最简单的使用方式（传四个参数：1、activity上下文 2、要提示的更新描述 3、更新apk的下载地址 4、工程图标）
 VersionUpgradeManager.showUpgradeDialog(this,
                "有新的版本，赶紧更新吧！"
                "url",
                R.mipmap.ic_launcher);
```
要想改变提示dialog主题颜色和样式可以用下面的调用方法
```java
VersionUpgradeManager manager=new VersionUpgradeManager();
        VersionUpgradeManager.Setings setings=manager.getSetings();
        setings.setTitle("版本更新");
        setings.setDialogTitleBackground(R.color.colorAccent);
        manager.setSetings(setings);
        manager.showUpgradeDialog(this,
                "有新的版本，赶紧更新吧！"
                "url",
                R.mipmap.ic_launcher);
```
