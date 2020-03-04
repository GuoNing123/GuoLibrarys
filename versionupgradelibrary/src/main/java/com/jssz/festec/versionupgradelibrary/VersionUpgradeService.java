package com.jssz.festec.versionupgradelibrary;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class VersionUpgradeService extends Service {
//    public static void start(Context context,String msg, String url, @DrawableRes int id){
//
//    }
    public static final int NOTIFICATION_ID=0x12;
    NotificationManager notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            int drawableid=intent.getIntExtra("drawable",0);
            String url=intent.getStringExtra("url");
            String path=this.getCacheDir().toString()+"/apk/";
//            VersionUpgradeManager.showUpgradeDialog(this,msg,url,path,drawableid);
            Upgrade(this,url,path,drawableid);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private  int outProgress=0;
    private  void Upgrade(final Context context, String url, String path, @DrawableRes final int id){
//        InstallApp( new File(path+"upgrade.apk"),context);
        notificationUser(context,id,0);
        DownloadApkManager.downLoad(url, path+"upgrade.apk", new DownloadApkManager.downLoadListner() {
            @Override
            public void onComplete(File file) {
                notificationUser(context,id,100);
                InstallApp(file,context);
            }

            @Override
            public void onProgress(int p) {
                Log.d("VersionUpgradeManager",p+"");
                if(outProgress!=p){
                    Log.d("VersionUpgradeManager",p+"打印***");
                    notificationUser(context,id,p);
                    outProgress=p;
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                notificationUser(context,id,-1);
            }
        });
    }
    private void notificationUser(Context context,@DrawableRes int id,int progress){

//        RemoteViews rv=createRemoteViews(context,id);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(id);  //设置小图标
        if(progress>100||progress<0){
            builder.setContentTitle("下载失败");  //设置标题
            builder .setProgress(0,0,false);
        }else{
            builder.setContentTitle("下载"+"("+progress+"%)");  //设置标题
            builder .setProgress(100,progress,false);
        }
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
    private static RemoteViews createRemoteViews(Context context, @DrawableRes int id){
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.notification_layout);
        remoteViews.setImageViewResource(R.id.icon,id);
        remoteViews.setProgressBar(R.id.progress,100,0,false);
        remoteViews.setTextViewText(R.id.progress_txt,"0%");
        return remoteViews;
    }

    public void InstallApp(File apkFile, Context context){
        Log.e("tag", "getContentIntent()");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri uri=FileProvider.getUriForFile(context,context.getPackageName()+".versionupgradelibrary.fileprovider",apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.parse("file://"+apkFile.getAbsolutePath()),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
    public interface UpgradeListner{

    }
}
