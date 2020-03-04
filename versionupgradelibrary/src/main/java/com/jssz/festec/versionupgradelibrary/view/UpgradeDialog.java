package com.jssz.festec.versionupgradelibrary.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.jssz.festec.versionupgradelibrary.R;

public class UpgradeDialog extends Dialog implements View.OnClickListener {
    private OptionAction mAction;
    private boolean is;
    @SuppressLint("ResourceType")
    private UpgradeDialog(Builder builder) {
        super(builder.context);
        mAction=builder.action;
        View root=this.getLayoutInflater().inflate(R.layout.dialog_upgrade_layout,null,false);
        TextView title_tv=root.findViewById(R.id.title_tv);
        TextView content=root.findViewById(R.id.content_txt);
        TextView noUpgrade=root.findViewById(R.id.option_txt_1);//忽略本次升级
        TextView laterUpgrade=root.findViewById(R.id.option_txt_2);//下次字说
        TextView upgrade=root.findViewById(R.id.option_txt_3);//立刻升级
        if(!TextUtils.isEmpty(builder.msg)){
            content.setText(builder.msg);
        }
        if(!TextUtils.isEmpty(builder.title)){
            title_tv.setText(builder.title);
        }
        if(builder.titleBackground!=-1){
            title_tv.setBackgroundResource(builder.titleBackground);
        }
        if(builder.titleColor!=-1){
            title_tv.setTextColor(builder.context.getResources().getColor(builder.titleColor));
        }
        noUpgrade.setOnClickListener(this);
        laterUpgrade.setOnClickListener(this);
        upgrade.setOnClickListener(this);
        setContentView(root);
//        this.setCanceledOnTouchOutside(false);
//        this.setCancelable(false);
    }

//    public void show(){
//        super.show();
//    }

    @Override
    public void onClick(View v) {
        if(mAction!=null){
            if (v.getId() == R.id.option_txt_1) {
                mAction.onNoUpgrade();
            }else if(v.getId() == R.id.option_txt_2){
                mAction.onLaterUpgrade();
            }else if(v.getId() == R.id.option_txt_3){
                mAction.onUpgrade();
            }
        }
    }

    public static class Builder{
        Context context;
        String msg;
        OptionAction action;
        @DrawableRes int titleBackground=-1;
        @DrawableRes int titleColor=-1;
        String title;

        public Builder setContext(Context context){
          this.context=context;
          return this;
        }
        public Builder setContentMsg(String msg){
          this.msg=msg;
          return this;
        }
        public Builder setAction(OptionAction action){
          this.action=action;
          return this;
        }
        public Builder setTitleBackground( @DrawableRes int titleBackground){
          this.titleBackground=titleBackground;
          return this;
        }
        public Builder setTitleColor(@DrawableRes int titleColor){
          this.titleColor=titleColor;
          return this;
        }
        public Builder setTitle(String title){
          this.title=title;
          return this;
        }
        public UpgradeDialog bundle(){
            return new UpgradeDialog(this);
        }
    }
    public interface OptionAction{
        void onNoUpgrade();
        void onLaterUpgrade();
        void onUpgrade();
    }
}
