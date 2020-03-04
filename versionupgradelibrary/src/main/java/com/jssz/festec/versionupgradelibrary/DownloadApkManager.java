package com.jssz.festec.versionupgradelibrary;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadApkManager {
    /**
     * 从服务器下载文件
     * @param path 下载文件的地址
     * @param FilePath 文件名字
     */
    public static void downLoad(final String path, final String FilePath,final downLoadListner listner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(30000);
                    con.setConnectTimeout(30000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");//设置请求方式为get
                    con.connect();//连接
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            double filesize =  con.getContentLength();//获取文件总大小;
                            if(filesize<=0){
                                listner.onError(null);
                                return;
                            }
                            File file=createFile(FilePath);
                            fileOutputStream = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            double readedsize=0;
                            int ch;
                            int i=0;
                            while ((ch = is.read(buf)) != -1) {
                                i+=1;
                                readedsize+=ch;
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                                if(i%30==0){
                                    listner.onProgress((int)(readedsize/filesize*100));
                                }
                            }
                            //回调下载完成
                            listner.onComplete(file);
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    listner.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface downLoadListner{
        void onComplete(File file);
        void onProgress(int p);
        void onError(Exception e);
    }

    private static File createFile(String FilePath) throws IOException {
        File file=new File(FilePath.substring(0,FilePath.lastIndexOf("/")));
        if(!file.exists()||!file.isDirectory()){
            file.mkdirs();
        }
        File file2=new File(FilePath);
        if(file2.exists()){
            file2.delete();
            file2.createNewFile();
        }
        return file2;
    }
}
