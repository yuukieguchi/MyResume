package jp.co.eguchi.myresume.main;

import java.io.File;
import java.util.Calendar;

import jp.co.eguchi.myresume.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MyResumeActivity extends TabActivity{

	//メニューアイテムID
	protected static final int
	MENU_ITEM0=0,
	MENU_ITEM1=1,
	MENU_ITEM2=2;

	protected static final String appFolder = "MyResume";
	protected DBAdapter dbadapter;

	// プロフィール表示用変数
	protected TextView txt_family_name;
	protected TextView txt_given_name;
	protected TextView txt_sex;
	protected TextView txt_birth_year; 
	protected TextView txt_birth_month; 
	protected TextView txt_birth_day; 
	protected Calendar calendar;
	protected TextView txt_entry_year; 
	protected TextView txt_entry_month; 
	protected TextView txt_entry_day; 
	protected int now_year;
	protected int now_month;
	protected int now_day;
	protected TextView age; 
	protected TextView address;
	protected TextView phone;
	int TabNum;

		@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Resource object to get Drawables  
	    Resources res = getResources();  
	    
	    // The activity tabs  
	    TabHost tabs = getTabHost();    
	  
	    // Resusable TabSpec for each tab  
	    TabHost.TabSpec spec;  
	  
	    // Reusable Intent for each tab  

	    // tabsのインスタンスを取得
		LayoutInflater.from(this).inflate(R.layout.main, tabs.getTabContentView(), true);

		txt_family_name = (TextView)findViewById(R.id.txt_family_name);
		txt_given_name  = (TextView)findViewById(R.id.txt_given_name);
		txt_sex     = (TextView)findViewById(R.id.txt_sex);

		calendar    = Calendar.getInstance();
		now_year    = calendar.get(Calendar.YEAR);
		now_month   = calendar.get(Calendar.MONTH)+1;
		now_day     = calendar.get(Calendar.DAY_OF_MONTH);

		// 本日の日付をセットします
		txt_entry_year    = (TextView)findViewById(R.id.txt_entry_year);
		txt_entry_year.setText(String.valueOf(now_year));
		txt_entry_month = (TextView)findViewById(R.id.txt_entry_month);
		txt_entry_month.setText(String.valueOf(now_month));
		txt_entry_day = (TextView)findViewById(R.id.txt_entry_day);
		txt_entry_day.setText(String.valueOf(now_day));

		// 生年月日をセットします。
		txt_birth_year    = (TextView)findViewById(R.id.txt_birth_year);
		txt_birth_month      = (TextView)findViewById(R.id.txt_birth_month);
		txt_birth_day      = (TextView)findViewById(R.id.txt_birth_day);
		age         = (TextView)findViewById(R.id.txt_age);
		address     = (TextView)findViewById(R.id.txt_address);
		phone       = (TextView)findViewById(R.id.txt_phone);

		
		// タブシートの設定
		TabSpec tab01 = tabs.newTabSpec(getText(R.string.basic_info).toString());
		tab01.setIndicator(getText(R.string.basic_info).toString());
		tab01.setContent(R.id.txt_family_name);
		tabs.addTab(tab01);
		TabSpec tab02 = tabs.newTabSpec("TabSheet2");
		tab02.setIndicator(getText(R.string.school_background).toString());
		tab02.setContent(R.id.txt_family_name);
		tabs.addTab(tab02);
		TabSpec tab03 = tabs.newTabSpec("TabSheet3");
		tab03.setIndicator(getText(R.string.work_experience).toString());
		tab03.setContent(R.id.txt_family_name);
		tabs.addTab(tab03);
		TabSpec tab04 = tabs.newTabSpec("TabSheet4");
		tab04.setIndicator(getText(R.string.basic_info).toString());
		tab04.setContent(R.id.txt_family_name);
		tabs.addTab(tab04);
		
		// 初期表示のタブ設定
		tabs.setCurrentTab(0);

		tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			// タブがクリックされた時のハンドラ
			public void onTabChanged(String tabId) {
				// クリックされた時の処理を記述
				if(tabId == "TabSheet1") {
					TabNum = 1;
				}
				else if(tabId == "TabSheet2") {
					TabNum = 2;
				}
				else if(tabId == "TabSheet3") {
					TabNum = 3;
				}
			}
			
		});
		
		Intent intent = new Intent();
		switch(TabNum){
		case 1:
			intent.setClass(this, MyResumeRegist.class);
			break;
		}

//		if(intent != null){
//			startActivity(intent);
//		}
		   // Create an Intent to launch an Activity   
	    // for the tab (to be reused)  
/*	    intent = new Intent().setClass(this, MyResumeActivity.class);  
	  
	    // Initialize a TabSpec for each tab and   
	    // add it to the tabs  
	    spec = tabs.newTabSpec("tab1")  
	                  .setIndicator("Home",   
	                   res.getDrawable(R.drawable.ic_menu_edit))  
	                  .setContent(intent);  
	    tabs.addTab(spec);  
	  
	    // Do the same for the other tabs  
	    intent = new Intent().setClass(this, MyResumeActivity.class);  
	    spec = tabs.newTabSpec("tab2")  
	                  .setIndicator("Camera",   
	                   res.getDrawable(R.drawable.ic_menu_edit))  
	                  .setContent(intent);  
	    tabs.addTab(spec);  
	  
	    intent = new Intent().setClass(this, MyResumeActivity.class);  
	    spec = tabs.newTabSpec("tab3")  
	                  .setIndicator("Star",   
	                   res.getDrawable(R.drawable.ic_menu_edit))  
	                  .setContent(intent);  
	    tabs.addTab(spec);  
	  
	    tabs.setCurrentTab(2); 
	    */
		
		// sdcardにフォルダ作成（最初の1回のみ）
		sdCardMkDir();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu item) {
		super.onCreateOptionsMenu(item);

		MenuItem item0=item.add(0,MENU_ITEM0,0,R.string.edit);
		item0.setIcon(R.drawable.ic_menu_edit);

		MenuItem item1=item.add(0,MENU_ITEM1,0,R.string.setup);
		item1.setIcon(android.R.drawable.ic_menu_manage);

		MenuItem item2=item.add(0,MENU_ITEM2,0,R.string.exit);
		item2.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		// 編集画面へ移行
		case MENU_ITEM0:
			intent.setClass(this, MyResumeRegist.class);
			if(intent != null){
				startActivity(intent);
			}
			return true;
		case MENU_ITEM1:
			return true;
		case MENU_ITEM2:
			finish();
			return true;
		}
		return true;
	}



	/** sdcard/ に MyResumeフォルダを作成します */
	public void sdCardMkDir(){
		File outDir01 = new File( Environment.getExternalStorageDirectory(), appFolder );  
		if (outDir01.exists() == false) {  
			outDir01.mkdir();  
		}
		Log.i( "outDir01：", ""+outDir01 );

		// sdcard/MyResume/ にmyprofileフォルダを作成します
/*		File outDir02 = new File( outDir01, "myprofile" );  
		if ( outDir02.exists() == false ) {  
			outDir02.mkdir();  
		}  
		Log.i("outDir02：",""+outDir02);
*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}