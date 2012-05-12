package jp.co.eguchi.myresume.main;

import jp.co.eguchi.myresume.R;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MyResumeRegist extends Activity {
	
	DBAdapter dbadapter;
	SQLiteDatabase db;
	
	//メニューアイテムID
	private static final int
	MENU_ITEM0=0,
	MENU_ITEM1=1;
	
	private EditText family_name;
	private EditText given_name;
	// 性別
	private RadioGroup select_sex;
	private RadioButton select_man;
	private RadioButton select_woman;

	private DatePicker birthPicker;
	private int birth_year;
	private int birth_month;
	private int birthday;
	private EditText address;
	private EditText phone;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist);
		
		family_name    = (EditText)findViewById(R.id.edit_family_name);
		given_name     = (EditText)findViewById(R.id.edit_given_name);

		// 性別
		select_sex     = (RadioGroup)findViewById(R.id.select_sex);
		select_man     = (RadioButton)findViewById(R.id.select_man);
		select_woman   = (RadioButton)findViewById(R.id.select_woman);
		// 誕生日
		birthPicker    = (DatePicker)findViewById(R.id.picker_birth);
		// 住所
		address        = (EditText)findViewById(R.id.edit_address);
		// 電話番号
		phone          = (EditText)findViewById(R.id.edit_phone);
		
	}

	//オプションメニューの生成
	@Override
	public boolean onCreateOptionsMenu(Menu item) {
		super.onCreateOptionsMenu(item);

		// 保存
		MenuItem item0=item.add(0,MENU_ITEM0,0,R.string.save);
		item0.setIcon(android.R.drawable.ic_menu_save);

		// 編集のキャンセル
		MenuItem item1=item.add(0,MENU_ITEM1,0,R.string.back);
		item1.setIcon(android.R.drawable.ic_menu_revert);
		
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 姓
        family_name = (EditText)findViewById(R.id.edit_family_name);
        // 名
		given_name = (EditText)findViewById(R.id.edit_given_name);

		switch (item.getItemId()) {
	    // 保存処理
		case MENU_ITEM0:
			try {
				writeData();
				// 保存しました
				Toast.makeText(this, R.string.save_ok, Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// 保存失敗
//				Toast.makeText(this, R.string.save_ng, Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			// Activity終了
			finish();
			return true;
		// 編集キャンセル
		case MENU_ITEM1:
			// Activity終了
			finish();
			return true;
	    }
		return true;
	}
	
	public void writeData(){
		ContentValues values = new ContentValues();  
		values.put("_id", "1");  
        values.put("family_name", family_name.getText().toString() );  
        values.put("given_name", given_name.getText().toString());
		dbadapter = new DBAdapter(this);
		db = dbadapter.getReadableDatabase();  
		
		long ret;  
        try {  
            ret = db.insert(DBAdapter.DB_TABLE_NAME, null, values);  
        } finally {  
            db.close();  
        }  
        if (ret == -1) {  
            Toast.makeText(this, "Insert失敗", Toast.LENGTH_SHORT).show();  
        } else {   
            Toast.makeText(this, "Insert成功", Toast.LENGTH_SHORT).show();  
        }  
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
