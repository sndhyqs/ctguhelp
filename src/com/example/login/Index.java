/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.example.login;



import com.ctgu.base.BaseActivity;
import android.os.Bundle;
import android.content.Intent;

import com.ctgu.model.Course1;
import com.ctgu.serve.MyServe;
import com.ctgu.util.IO;

public class Index extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent my_serve_intent = new Intent(this, MyServe.class);
		startService(my_serve_intent);
		forward(My_Menu.class);
	}
}
