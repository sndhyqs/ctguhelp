/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.ctgu.util;

import com.ctgu.base.BaseHandler;
import com.ctgu.base.BaseActivity;
import android.os.Message;

public class IndexHander extends BaseHandler {
    BaseActivity activity;
    
    public IndexHander(BaseActivity activity) {
        super(activity);
       this. activity = activity;
    }
    
    public void handleMessage(Message msg) {
        switch(msg.what) {
            case 0:
            {
                activity.finish();
                break;
            }
        }
    }
}