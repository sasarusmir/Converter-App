package com.originalit.converter;

import java.util.List;

import com.originalit.converter.calculator.CalculatorActivity;
import com.originalit.converter.currencyconverter.CurrencyConverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChooseUnitActivity extends ActionBarActivity {

	static private final String TAG = "Unit";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_unit);
		
		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);
		TextView title = (TextView) v.findViewById(R.id.action_title_main);
		String titleText = "CHOOSE UNIT";
		title.setText(titleText);
		
		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);
		
		// Database
		DatabaseHandler db = DatabaseHandler.getInstance(this);
//		SQLiteDatabase sdb = db.getWritableDatabase();
//		sdb.execSQL("DROP TABLE IF EXISTS units");
//		db.onCreate(sdb);
//		
		Log.d("Insert: ", "Inserting ..");
//		db.addUnit(new Unit("Length", "mm", "Millimeters",0.001));
//		db.addUnit(new Unit("Length", "cm", "Centimeters",0.01));
//		db.addUnit(new Unit("Length", "m", "Meters",1));
//		db.addUnit(new Unit("Length", "km", "Kilometers",1000));
		
//		Log.d("Reading: ", "Reading all texts ..");
		List<Unit> units = db.getAllUnitsFromCategory(ConverterActivity.categoryIs);
		
		
		
		// Initialize display
		
		LinearLayout listUnits = (LinearLayout) this.findViewById(R.id.list_units);
		
		String idIs = "";
		String categoryIs = "";
//		String unitIs = "";
		String nameIs = "";
		String valueIs = "";
		
		int dpValueTopBottom = 10; // padding Top and Bottom in dips
		int dpValueLeft = 20;
		int dpValueMarginLeft = 12; // dip value margin for left taxtViewValue
		int dpValueBottomMarginLayout = 10;
		int dpValueUnitWidth = 80;
		
		float d = this.getResources().getDisplayMetrics().density; // Display Density
		
		int paddingTopBottom = (int)(dpValueTopBottom * d); // padding Top and Bottom in pixels
		int paddingLeft = (int)(dpValueLeft * d);
		int marginLeft = (int)(dpValueMarginLeft * d);
		int marginBottomLayout = (int)(dpValueBottomMarginLayout * d);
		int unitWidth = (int)(dpValueUnitWidth * d);
		
		
		if (units != null && !units.isEmpty()) {
		for (Unit unit : units){
			idIs = Integer.toString(unit.get_id());
			categoryIs = unit.get_category();
		    final String unitIs = unit.get_unit();
			nameIs = unit.get_name();
			valueIs = Double.toString(unit.get_value());
		
			String log = "Id: "+unit.get_id()+" ,Category: " + unit.get_category() + " , Unit: " + unit.get_unit()+", Name: " + unit.get_name()+"  ,Value: " + unit.get_value();
			Log.d("Unit: ", log);
			
		LinearLayout listItemLayout = new LinearLayout(this);
		TextView textviewUnit = new TextView(this);
		TextView textviewValue = new TextView(this);
		
		LinearLayout.LayoutParams layoutUnitParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(unitWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams textParamsValue = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
//		textviewId.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//		textviewCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//		textviewUnit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//		textviewValue.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		listItemLayout.setPadding(paddingLeft, paddingTopBottom, 0, paddingTopBottom);
		listItemLayout.setBackgroundResource(R.drawable.list_box_xml);
		layoutUnitParams.setMargins(0, 0, 0, marginBottomLayout);
		listItemLayout.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				String unit = unitIs;
				/*
				Intent intent = new Intent(getApplicationContext(), ConverterActivity.class);
				
				SharedPreferences prefs = getApplicationContext().getSharedPreferences(
					      "com.originalit.converter", Context.MODE_PRIVATE);
				String mainUnitKey = "com.originalit.converter.mainUnit";
				String resultUnitKey = "com.originalit.converter.resultUnit";
				
				if (mainUnit == 1)
				{
					prefs.edit().putString(mainUnitKey, unitIs).apply();
				}else{
					prefs.edit().putString(resultUnitKey, unitIs).apply();
				}
				
				
				intent.putExtra("MainUnitString", unitIs);
				intent.putExtra("MainUnit", mainUnit);
				
				startActivity(intent);*/
				
				Intent intent = new Intent(ChooseUnitActivity.this, ConverterActivity.class);
				Log.i("Unit is in Choose", unitIs);
				intent.putExtra(TAG, unitIs);
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
		textviewUnit.setText(unitIs);
		textviewValue.setText(nameIs);
		
		
//		textviewUnit.setGravity(Gravity.CENTER_HORIZONTAL);
		textviewUnit.setTextAppearance(this, R.style.list_unit);
//		textviewUnit.setTextColor(getResources().getColor(R.color.gray));
		
		textParamsValue.setMargins(marginLeft, 0, 0, 0);
		textviewValue.setMaxLines(2);
		textviewValue.setTextAppearance(this, R.style.unit_long);
//		textviewValue.setTextColor(R.drawable.list_text);
		
		listItemLayout.setLayoutParams(layoutUnitParams);
		textviewUnit.setLayoutParams(textParams);
		textviewValue.setLayoutParams(textParamsValue);
		
		listItemLayout.addView(textviewUnit);
		listItemLayout.addView(textviewValue);
		
		listUnits.addView(listItemLayout);
		
//		listUnits.addView(textviewId);
//		listUnits.addView(textviewCategory);
//		listUnits.addView(textviewUnit);
//		listUnits.addView(textviewValue);
		}
		}
		
		
		
		
	}
	
	public void goBack(View view) {
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		Button backBtn = (Button) v.findViewById(R.id.back_button);
		backBtn.setSelected(true);

		this.onBackPressed();
	}
	
	public void clickUnit(View view) {
		int id = view.getId();
		
		
		Intent intent = new Intent(this, ConverterActivity.class);
		intent.putExtra("MainUnitId", (int)id);
		startActivity(intent);
		
	}
	
	public void openCurrencyConverter(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CurrencyConverter.class);
		startActivity(intent);
	}
	
	public void openCalculator(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CalculatorActivity.class);
		startActivity(intent);
	}
}
