package com.jssz.festec.versionupgradelibrary;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.DrawableRes;

import com.jssz.festec.versionupgradelibrary.view.UpgradeDialog;


public class VersionUpgradeManager {
    private static UpgradeDialog dialog;
    private static Setings setings;

    public Setings getSetings(){
        return new Setings();
    }
    public void setSetings(Setings setings){
        this.setings=setings;
    }
    public class Setings{
        String title;
        @DrawableRes int titleColor=-1;
        @DrawableRes int titleBackground=-1;
        public void setDialogTitleBackground(@DrawableRes int titleBackground){
            this.titleBackground=titleBackground;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public void setTitleColor(@DrawableRes int titleColor) {
            this.titleColor = titleColor;
        }
    }
    public static void showUpgradeDialog(final Context context, String msg, final String url, @DrawableRes final int id){
        UpgradeDialog.Builder dialogBuilder = new UpgradeDialog.Builder()
                .setContext(context)
                .setContentMsg(msg)
                .setAction(new UpgradeDialog.OptionAction() {
                    @Override
                    public void onNoUpgrade() {
                        dialog.dismiss();
                        dialog=null;
                    }

                    @Override
                    public void onLaterUpgrade() {
                        dialog.dismiss();
                        dialog=null;
                    }

                    @Override
                    public void onUpgrade() {
                        //立即升级(启动下载service)
                        Intent intent =new Intent(context,VersionUpgradeService.class);
                        intent.putExtra("drawable",id);
                        intent.putExtra("url", url);
                        context.startService(intent);
                        dialog.dismiss();
                    }
                });
        if(setings!=null){
            if(!TextUtils.isEmpty(setings.title))
                dialogBuilder.setTitle(setings.title);
             if(setings.titleColor!=-1)
                 dialogBuilder.setTitleColor(setings.titleColor);
            if(setings.titleBackground!=-1)
                dialogBuilder.setTitleBackground(setings.titleBackground);
        }
        dialog=dialogBuilder.bundle();
        dialog.show();
    }
}
