# GuoLibrarys
##versionupgradelibrary是一个app版本下载更新的插件；
        该插件提供了一个更新提示dailog，下载apk文件的service和展示下载进度的notification;下载完成后自动调用apk安装逻辑，完成app更新。
该流程虽然没有太多的技术难度，但是我在工作中每开发一个新项目都要重写一遍更新逻辑实在是很烦，于是干脆把这个流程直接封装起来打包成aar一劳永逸。
        ###【集成方式】
        ####第一步：工程build.gradle文件里添加入代码
```java
allprojects {
    repositories {
        maven { url "https://raw.githubusercontent.com/GuoNing123/versionupgradelibrary/master" }
    }
}
```
####第二步：app的build.gradle文件中添加引用
        implementation 'com.GN.versionupgradelibrary:encrypt:1.0.1'

