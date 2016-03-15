package com.originalit.converter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.originalit.converter.calculator.CalculatorActivity;
import com.originalit.converter.currencyconverter.CurrencyConverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ConverterActivity extends ActionBarActivity {

	static private final int GET_CATEGORY_REQUEST_CODE = 1;
	static private final int GET_UNIT_REQUEST_CODE = 2;
	static private final int GET_UNIT1_REQUEST_CODE = 3;
	static private final String TAGCATEGORY = "Category";
	static private final String TAGUNIT = "Unit";

	// private boolean categoryChanged = false;
	private int iconResource;

	public static String categoryIs;
	// private String categoryIs1;
	
	EditText mainValueTxt;
	TextView mainUnitTxt;
	TextView resultValueTxt;
	TextView resultUnitTxt;
//	int mainUnitIs;
	String mainUnit;
	String resultUnit;
//	String result;
//	String resultPref;
//	String resultKey = "com.originalit.converter.result";

	private ImageView categoryImg;
	private TextView categoryTxt;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converter);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title_main, null);

		TextView actionTitle = (TextView) v.findViewById(R.id.action_title);
		actionTitle.setText(R.string.unit_converter);

		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);

		categoryIs = "WEIGHT/MASS";

		Log.i("in converter categoryIs", categoryIs);

		categoryImg = (ImageView) this
				.findViewById(R.id.convert_active_category_image);
		categoryTxt = (TextView) this
				.findViewById(R.id.convert_active_category_text);
		mainValueTxt = (EditText) this.findViewById(R.id.edit_text_main_value);
		mainUnitTxt = (TextView) this.findViewById(R.id.main_unit_text);
		resultUnitTxt = (TextView) this.findViewById(R.id.result_unit_text);
		resultValueTxt = (TextView) this.findViewById(R.id.result_value_unit);

		mainValueTxt.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event != null && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

					in.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});

		 // Get Unit 1 and 2 from category
		
		 DatabaseHandler db = DatabaseHandler.getInstance(this);
		 List<Unit> units = db.getAllUnitsFromCategory(categoryIs);
		 Unit unit1 = units.get(0);
		 Unit unit2 = units.get(1);
		 if(mainUnit == null)
		 mainUnit = unit1.get_unit();
		
		 if(resultUnit == null)
		 resultUnit = unit2.get_unit();

	}

	@Override
	public void onResume() {

		super.onResume(); // Always call the superclass method first

		Log.i("onResume ", categoryIs);

		Map<String, Integer> mapIcon = new HashMap<String, Integer>();
		mapIcon.put("WEIGHT/MASS", R.drawable.a_mass_icn_active);
		mapIcon.put("LENGTH/DISTANCE", R.drawable.b_length_icn_active);
		mapIcon.put("SPEED", R.drawable.c_speed_icn_active);
		mapIcon.put("POWER", R.drawable.d_power_icn_active);
		mapIcon.put("PRESSURE", R.drawable.e_pressure_icn_active);
		mapIcon.put("TEMPERATURE", R.drawable.f_temperature_icn_active);

		iconResource = mapIcon.get(categoryIs);
		categoryImg.setImageResource(iconResource);
		categoryTxt.setText(categoryIs);

		updateUnitsView();
		
	}

	public void goBack(View view) {

		finish();
	}

	public void chooseCategory(View view) {
		Intent intent = new Intent(ConverterActivity.this,
				ChooseCategoryActivity.class);
		startActivityForResult(intent, GET_CATEGORY_REQUEST_CODE);
	}

	public void chooseUnit(View view) {
		Intent intent = new Intent(ConverterActivity.this,
				ChooseUnitActivity.class);
		startActivityForResult(intent, GET_UNIT_REQUEST_CODE);
	}

	public void chooseUnitResult(View view) {
		Intent intent = new Intent(ConverterActivity.this,
				ChooseUnitActivity.class);
		startActivityForResult(intent, GET_UNIT1_REQUEST_CODE);
	}

	@SuppressLint("NewApi")
	public void convert(View view) {
		String mainValue;
		Double resultValue;
		String result;
		Double mainUnitValue;
		Double resultUnitValue;
		Double value;

		DatabaseHandler db = DatabaseHandler.getInstance(this);

		mainValue = mainValueTxt.getText().toString();
		// if a value is entered
		if (!mainValue.equals("")) {
			Double mainValueD = Double.parseDouble(mainValue);
			mainUnitValue = db.getUnitValue(mainUnit);
			resultUnitValue = db.getUnitValue(resultUnit);
		if(categoryIs.equals("TEMPERATURE")){
				resultValue = convertTemperature(mainUnit, resultUnit, mainValueD);
			}else{
			value = mainUnitValue / resultUnitValue;
			resultValue = mainValueD * value;
			
			String log = "Main unit value is: " + mainValue + " main unit v: "
					+ mainUnitValue + " result unit v: " + resultUnitValue
					+ " value: " + value + " result value: " + resultValue;
			Log.d("Values:", log);
			}
			
			
//			DecimalFormat format = new DecimalFormat("0.###");
//			DecimalFormat format = new DecimalFormat("@##");
//			DecimalFormat format = new DecimalFormat("@@###");
			DecimalFormat format = new DecimalFormat();
			format.setGroupingSize(3);
			format.setGroupingUsed(true);
			format.setMaximumIntegerDigits(350);
			format.setMaximumFractionDigits(10);
			
//			String newResult = Double.valueOf(resultValue).toString();
//			NumberFormat format2 = NumberFormat.getInstance();
//			format2.setMinimumFractionDigits(0);
//			format2.setMaximumFractionDigits(3);
			result = format.format(resultValue);
//			result = resultValue.toString();
//			newResult = format2.format(resultValue);
			
			Log.i("ResultValue: ", result);
			resultValueTxt.setText(result);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GET_CATEGORY_REQUEST_CODE) {
//			Log.i(TAGCATEGORY, "Entered onActivityResult() Category");
//			Log.i("onResult",
//					"requestCode: " + requestCode + " resultCode: "
//							+ resultCode + " data Category: "
//							+ data.getStringExtra(TAGCATEGORY));
			if (resultCode == RESULT_OK && data != null) {
				String RESULT = data.getStringExtra(TAGCATEGORY);
				if (!(RESULT == null)) {
					categoryIs = RESULT;
					
					DatabaseHandler db = DatabaseHandler.getInstance(this);
					 List<Unit> units = db.getAllUnitsFromCategory(categoryIs);
					 Unit unit1 = units.get(0);
					 Unit unit2 = units.get(1);
					 mainUnit = unit1.get_unit();
					 resultUnit = unit2.get_unit();
					 mainValueTxt.setText("");
					 resultValueTxt.setText("");
					
					// categoryChanged = true;
					Log.i("in result code ", RESULT);
				}
			} else if(resultCode == RESULT_CANCELED){
				
			}

		} else if (requestCode == GET_UNIT_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String RESULT = data.getStringExtra(TAGUNIT);
				if (!(RESULT == null)) {
					mainUnit = RESULT;
					resultValueTxt.setText("");
					Log.i("Unit is ", RESULT);
				}
			}

		} else if (requestCode == GET_UNIT1_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String RESULT = data.getStringExtra(TAGUNIT);
				if (!(RESULT == null)) {
					resultUnit = RESULT;
					resultValueTxt.setText("");
					Log.i("Unit1 is ", RESULT);
				}
			}

		}

	}

	public void openKeyboard(View v) {
//		mainValueTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		resultValueTxt.setText("");
	}
	
	public void switchUnit(View v) {

		String temp;
		temp = mainUnit;
		mainUnit = resultUnit;
		resultUnit = temp;
		updateUnitsView();
		convert(v);
	}
	
	public void updateUnitsView(){
		mainUnitTxt.setText(mainUnit+" ");
		resultUnitTxt.setText(resultUnit+" ");
	}
	
	public void openCurrencyConverter(View v){
		Intent intent = new Intent(getApplicationContext(), CurrencyConverter.class);
		startActivity(intent);
	}
	
	public void openCalculator(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CalculatorActivity.class);
		startActivity(intent);
	}
	
	private double convertTemperature(String mainU, String resultU, double value){
		double result = 0;
		
		if(mainUnit.equals("°C")&&resultUnit.equals("°F"))
			result = (value*9/5)+32;
		else if (mainUnit.equals("°F")&&resultUnit.equals("°C"))
			result = (value-32)*5/9;
		else if (mainUnit.equals("°C")&&resultUnit.equals("K"))
			result = value+273.15;
		else if (mainUnit.equals("K")&&resultUnit.equals("°C"))
			result = value-273.15;
		else if (mainUnit.equals("°C")&&resultUnit.equals("°R"))
			result = (value+273.15)*9/5;
		else if (mainUnit.equals("°R")&&resultUnit.equals("°C"))
			result = (value-491.67)*5/9;
		else if (mainUnit.equals("°C")&&resultUnit.equals("°Ré"))
			result = value*4/5;
		else if (mainUnit.equals("°Ré")&&resultUnit.equals("°C"))
			result = value*5/4;
		
		else if (mainUnit.equals("°F")&&resultUnit.equals("K"))
			result = (value+459.67)*5/9;
		else if (mainUnit.equals("K")&&resultUnit.equals("°F"))
			result = (value*9/5)-459.67;
		else if (mainUnit.equals("°F")&&resultUnit.equals("°R"))
			result = value+459.67;
		else if (mainUnit.equals("°R")&&resultUnit.equals("°F"))
			result = value-459.67;
		else if (mainUnit.equals("°F")&&resultUnit.equals("°Ré"))
			result = (value-32)*4/9;
		else if (mainUnit.equals("°Ré")&&resultUnit.equals("°F"))
			result = (value*9/4)+32;
		
		else if (mainUnit.equals("K")&&resultUnit.equals("°R"))
			result = value*9/5;
		else if (mainUnit.equals("°R")&&resultUnit.equals("K"))
			result = value*5/9;
		else if (mainUnit.equals("K")&&resultUnit.equals("°Ré"))
			result = (value-237.15)*4/5;
		else if (mainUnit.equals("°Ré")&&resultUnit.equals("K"))
			result = (value*5/4)+237.15;
		
		else if (mainUnit.equals("°R")&&resultUnit.equals("°Ré"))
			result = (value-491.67)*4/9;
		else if (mainUnit.equals("°Ré")&&resultUnit.equals("°R"))
			result = (value*9/4)+491.67;
		
		return result;
	}
}
