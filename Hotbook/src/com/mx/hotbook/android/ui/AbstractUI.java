package com.mx.hotbook.android.ui;


import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.util.calligraphy.CalligraphyConfig;
import com.mx.hotbook.android.util.calligraphy.CalligraphyContextWrapper;
import com.mx.hotbook.android.vo.core.User;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class AbstractUI extends Activity {
 
  protected static final String TAG = "LorantAsegura";
  protected DrawerLayout drawerLayout = null;
  protected RelativeLayout rootView = null;
  protected ActionBar actionBar = null;
  protected Context ctx = null;
  protected View loader = null;
  
  protected static User user = null;
  
  public abstract View getLayout(LayoutInflater inflater, ViewGroup container);
	
  static{
	     CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
	                          .setDefaultFontPath("fonts/SohoStd-MediumCondensed.otf")
	                          .setFontAttrId(R.attr.fontPath)
	                          .build()
	     );
  }
  
  public AbstractUI(){
	 super();
	 ctx = this;
  }
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.container);
	loader = findViewById(R.id.loader);
	ContentManager contentManager = new ContentManager();
	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	getFragmentManager().beginTransaction().replace(R.id.content_frame
			                               , contentManager).commit();
	getFragmentManager().executePendingTransactions();
	drawerLayout = (DrawerLayout) findViewById(R.id.container);
    if(isActiveMenu()){
       createMenu();
       setActionBar();
    } else {
    	drawerLayout.removeView(drawerLayout.findViewById(R.id.drawer));
    }
  }
  
  protected void setActionButtonBackground(int resId){
	ImageButton actionButton = null;
	if(actionBar != null){
	  actionButton = (ImageButton) actionBar.getCustomView().findViewById(R.id
			                                                    .imgBnAction);
	  actionButton.setBackgroundResource(resId);
	}
  }
  
  public View getLoader(){
	return loader;
  }
  
  protected void setActionBarTitle(Integer titleResId){
	TextView tv = null;
	if(actionBar != null){
	  tv = (TextView) actionBar.getCustomView().findViewById(R.id.tvTitle);
	  tv.setText(titleResId);
	}
  }
  
  public void onToggleButtonClickEvent(View v) {
    if(drawerLayout.isDrawerOpen(Gravity.START)){
      drawerLayout.closeDrawer(Gravity.START);
    } else {
    	drawerLayout.openDrawer(Gravity.START);
    }
  }
  
  public void onActionButtonClickEvent(View v) {
  }
  
  private void setActionBar(){
	actionBar = getActionBar();
  	actionBar.setDisplayShowHomeEnabled(false); 
  	actionBar.setDisplayShowCustomEnabled(true); 
  	actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setCustomView(R.layout.common_action_bar);
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
  }
  
  private void createMenu(){
  }
  
  private Boolean isActiveMenu(){
	Boolean isActiveMenu = false;
    ActivityInfo ai = null;
	try {
		ai = getPackageManager().getActivityInfo(getComponentName()
				  , PackageManager.GET_ACTIVITIES|PackageManager.GET_META_DATA);
		if(ai.metaData != null){
		  isActiveMenu = ai.metaData.getBoolean(Config.DRAWER_META_DATA_NAME);
		}
	} catch(NameNotFoundException ignored){}
	return isActiveMenu;
  }
  
  private class ContentManager extends Fragment {
	 public ContentManager(){
		 
	 }
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                                           Bundle savedInstanceState) {
	    return getLayout(inflater, container);
	 }
	 
	 @Override
	 public void onViewCreated(View view, Bundle savedInstanceState){
		 rootView = (RelativeLayout) view;
	 }
	 
  }
  
  private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    }
  }
  
  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
  
  protected void setSession(Integer id, String mail){
	user = new User();
	user.setId(id);
	user.setMail(mail);
  }

}
