package com.mx.hotbook.android.ui.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

public class Register extends AbstractUI {
	
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.register, container, false);
	return view;
  }

}
