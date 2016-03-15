package com.originalit.converter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static DatabaseHandler sInstance;

	// Database Version
	private static final int DATABASE_VERSION = 19;

	// Database Name
	private static final String DATABASE_NAME = "unitsManager";

	// Texts table name
	private static final String TABLE_UNITS = "units";

	protected Context context;

	// Texts Table Colums names
	private static final String KEY_ID = "id";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_UNIT = "unit";
	private static final String KEY_NAME = "name";
	private static final String KEY_VALUE = "value";

	public static DatabaseHandler getInstance(Context context) {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (sInstance == null) {
			sInstance = new DatabaseHandler(context.getApplicationContext());
		}
		return sInstance;
	}

	private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context; // added
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_UNITS;

		String ADD_ROWS = "INSERT INTO "
				+ TABLE_UNITS
				+ " (category, unit, name, value) VALUES('Length', 'mm', 'Millimeters', 0.001),"
				+ "('Length', 'cm', 'Centimeters', 0.01), ('Length', 'm', 'Meters', 1), ('Length', 'km', 'Kilometers', 1000)";

		String CREATE_TEXTS_TABLE = "CREATE TABLE " + TABLE_UNITS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
				+ KEY_UNIT + " TEXT," + KEY_NAME + " TEXT," + KEY_VALUE
				+ " DOUBLE" + ")";
		// db.execSQL(DROP_TABLE);

		String s;
		try {
			Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
			InputStream in = context.getResources().openRawResource(R.raw.sql);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(in, null);
			NodeList statements = doc.getElementsByTagName("statement");
			for (int i = 0; i < statements.getLength(); i++) {
				s = statements.item(i).getChildNodes().item(0).getNodeValue();
				db.execSQL(s);
			}
		} catch (Throwable t) {
			Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
		}

		// db.execSQL(CREATE_TEXTS_TABLE);
		// db.execSQL(ADD_ROWS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);

		onCreate(db);
	}

	public void addUnit(Unit unit) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, unit.get_category());
		values.put(KEY_UNIT, unit.get_unit());
		values.put(KEY_NAME, unit.get_name());
		values.put(KEY_VALUE, unit.get_value());

		db.insert(TABLE_UNITS, null, values);
		db.close();
	}

	public Unit getUnit(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_UNITS, new String[] { KEY_ID,
				KEY_CATEGORY, KEY_UNIT, KEY_NAME, KEY_VALUE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Unit unit = new Unit(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				Double.parseDouble(cursor.getString(4)));

		return unit;
	}

	public Double getUnitValue(String unit) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_UNITS, new String[] { KEY_VALUE }, KEY_UNIT + "=?",
				new String[] { String.valueOf(unit) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Double value = Double.parseDouble(cursor.getString(0));

		return value;
	}
	
//	public Double getUnitName(String unit) {
//
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_UNITS, new String[] { KEY_VALUE }, KEY_UNIT + "=?",
//				new String[] { String.valueOf(unit) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		Double value = Double.parseDouble(cursor.getString(0));
//
//		return value;
//	}

	public List<Unit> getAllUnits() {

		List<Unit> unitList = new ArrayList<Unit>();

		String selectQuery = "SELECT * FROM " + TABLE_UNITS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Unit unit = new Unit();
				unit.set_id(Integer.parseInt(cursor.getString(0)));
				unit.set_category(cursor.getString(1));
				unit.set_unit(cursor.getString(2));
				unit.set_name(cursor.getString(3));
				unit.set_value(Double.parseDouble(cursor.getString(4)));
				unitList.add(unit);
			} while (cursor.moveToNext());
		}

		return unitList;
	}

	public List<Unit> getAllUnitsFromCategory(String category) {

		List<Unit> unitList = new ArrayList<Unit>();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_UNITS, new String[] { KEY_ID,
				KEY_CATEGORY, KEY_UNIT, KEY_NAME, KEY_VALUE }, KEY_CATEGORY
				+ "=?", new String[] { String.valueOf(category) }, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				Unit unit = new Unit();
				unit.set_id(Integer.parseInt(cursor.getString(0)));
				unit.set_category(cursor.getString(1));
				unit.set_unit(cursor.getString(2));
				unit.set_name(cursor.getString(3));
				unit.set_value(Double.parseDouble(cursor.getString(4)));
				unitList.add(unit);
			} while (cursor.moveToNext());
		}
		return unitList;
	}

	public List<Unit> getAllUnitsDistinctCategory() {

		List<Unit> unitList = new ArrayList<Unit>();

		String selectQuery = "SELECT DISTINCT category FROM " + TABLE_UNITS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Unit unit = new Unit();
				unit.set_category(cursor.getString(0));
				unitList.add(unit);
			} while (cursor.moveToNext());
		}

		return unitList;
	}

	public int getTextsCount() {

		String countQuery = "SELECT * FROM " + TABLE_UNITS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

	public int updateText(Unit unit) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, unit.get_category());
		values.put(KEY_UNIT, unit.get_unit());
		values.put(KEY_NAME, unit.get_name());
		values.put(KEY_VALUE, unit.get_value());

		return db.update(TABLE_UNITS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(unit.get_id()) });
	}

	public void deleteText(Unit unit) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_UNITS, KEY_ID + " = ?",
				new String[] { String.valueOf(unit.get_id()) });
		db.close();
	}

}
