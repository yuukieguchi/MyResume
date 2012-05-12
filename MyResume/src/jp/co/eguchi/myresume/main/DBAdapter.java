package jp.co.eguchi.myresume.main;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBAdapter extends SQLiteOpenHelper {

	// File.separator == /
	protected static final String DB_FILE_NAME = MyResumeActivity.appFolder+File.separator+"myresume.db";
	protected static final String DB_TABLE_NAME = "T_myresume";
	protected static final String COL_ID = "_id";
	protected static final String COL_FAMILY_NAME = "family_name";
	protected static final String COL_GIVEN_NAME = "given_name";
	protected static final int    DB_VERSION = 1;

	/** 「myresume」テーブルの作成用SQL */
	protected static final String CREATE_TABLE_SQL = "create table "+ DB_TABLE_NAME +"(" +
			COL_ID+" integer primary key autoincrement, " +
			COL_FAMILY_NAME+" text, " +
			COL_GIVEN_NAME+" text " +
			")";

	/** 「myresume」テーブルの削除用SQL */
	protected static final String DROP_TABLE_SQL =
			"drop table if exists "+DB_TABLE_NAME;

	protected SQLiteDatabase db;

	/**
	 * コンストラクタ（必須）
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBAdapter( Context context ) {
		super(context, DB_FILE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	/**
	 * テーブルの再作成（必須）
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db,
			int oldVersion,int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			// バージョン1からバージョン2のテーブル定義変更のSQL
			db.execSQL(DROP_TABLE_SQL);
			db.execSQL(CREATE_TABLE_SQL);
		}
	}

}