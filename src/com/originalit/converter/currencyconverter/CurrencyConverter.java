package com.originalit.converter.currencyconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.originalit.converter.ConverterActivity;
import com.originalit.converter.R;
import com.originalit.converter.calculator.CalculatorActivity;

public class CurrencyConverter extends ActionBarActivity {

	static private final int GET_CURRENCY_REQUEST_CODE = 1;
	static private final int GET_CURRENCY1_REQUEST_CODE = 2;
	static private final String TAG = "Currency";
	
	static Context context = null;
	TextView currencyJsonTxtView;
	
	private EditText mainValueTxt;
	private TextView mainCurrencyTxt;
	private TextView resultValueTxt;
	private TextView resultCurrencyTxt;
	private String mainCurrency;
	private String resultCurrency;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency_converter);

		context = getApplicationContext();

		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title_main, null);

		TextView actionTitle = (TextView) v.findViewById(R.id.action_title);
		actionTitle.setText(R.string.title_activity_currency_converter);

		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);

//		Log.i("File is older ", "" + fileIsOlder());
		
		  if (isOnline() && fileIsOlder()) { final DownloaderTask
		  downloaderTask = new DownloaderTask( CurrencyConverter.this);
		  downloaderTask.execute(); Toast.makeText(getApplicationContext(),
		  "New Currency table is downloading", Toast.LENGTH_LONG).show(); }
		  
		  
		  
		 mainValueTxt = (EditText) this.findViewById(R.id.edit_text_main_value_currency_converter);
		 mainCurrencyTxt = (TextView) this.findViewById(R.id.main_currency_text_currency_converter);
		 resultCurrencyTxt = (TextView) this.findViewById(R.id.result_currency_text_currency_converter);
		 resultValueTxt = (TextView) this.findViewById(R.id.result_value_currency_currency_converter);
		 
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
		 
		 if(mainCurrency == null)
			 mainCurrency = "EUR";
		 if(resultCurrency == null)
			 resultCurrency = "USD";

	}
	
	@Override
	public void onResume() {

		super.onResume(); // Always call the superclass method first

		updateCurrenciesView();
		
	}

	public void openUnitConverter(View v) {
		Intent intent = new Intent(getApplicationContext(),
				ConverterActivity.class);
		startActivity(intent);
	}
	
	public void openCalculator(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CalculatorActivity.class);
		startActivity(intent);
	}

	public void openCurrencyTable(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CurrencyTableActivity.class);
		startActivity(intent);
		mainValueTxt.setText("");
		resultValueTxt.setText("");
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

//	public void showFile() {
//		InputStream input = null;
//		String filename = "currencyTable.json";
//
//		try {
//			// input = getApplicationContext().openFileInput(filename);
//			input = new BufferedInputStream(getApplicationContext()
//					.openFileInput(filename));
//			// currencyJsonTxtView.setText(input.read());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (input != null)
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//	}

	public static String fileToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static String getStringFromFile() {
		String filepath = context.getFilesDir() + "/currencyTable.json";
		String ret = "";
		File fl = new File(filepath);
		if(fl.exists()){
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(fl);

			ret = fileToString(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String date = parseCurrencyTable(ret,query);
		// Make sure you close all streams.
		finally {
			if (fin != null)
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		}else{
			InputStream in = null;
			try {
				 in = context.getResources().openRawResource(R.raw.currencytable);
				ret = fileToString(in);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}

		return ret;
	}

	public static String parseJSON(String data, String currency) {
		String output = "";
		JSONObject jObject;
		try {
			jObject = new JSONObject(data);
			String status = jObject.getString("status");
			if (status.equals("ok") && status != null) {
				String result = jObject.getString("result");
				JSONObject jObject1 = new JSONObject(result);
				if (currency.equals("date"))
					output = jObject1.getString(currency);
				else {
					String currencyString = jObject1.getString(currency);
					JSONObject jObjectCurrency = new JSONObject(currencyString);
					output = jObjectCurrency.getString("sre");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public static String parseJSON(String data, String currency, String type) {
		String output = "";
		JSONObject jObject;
		try {
			jObject = new JSONObject(data);
			String status = jObject.getString("status");
			if (status.equals("ok") && status != null) {
				String result = jObject.getString("result");
				JSONObject jObject1 = new JSONObject(result);
				if (currency.equals("date"))
					output = jObject1.getString(currency);
				else {
					String currencyString = jObject1.getString(currency);
					JSONObject jObjectCurrency = new JSONObject(currencyString);
					output = "" + jObjectCurrency.getString(type);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public boolean fileIsOlder() {
		boolean isOlder = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.ENGLISH);
		try {
			Date dateFile = sdf.parse(parseJSON(getStringFromFile(), "date"));
			Date date = new Date();
			String date1 = sdf.format(date);
			Date dateNow = sdf.parse(date1);
			if (dateNow.compareTo(dateFile) > 0) {
				if (((daysBetween(dateFile, dateNow) == 1) && (Calendar
						.getInstance().get(Calendar.HOUR_OF_DAY) < 8))
						|| ((daysBetween(dateFile, dateNow) <= 2) && ((Calendar
								.getInstance().get(Calendar.DAY_OF_WEEK) == 7) || (Calendar
								.getInstance().get(Calendar.DAY_OF_WEEK) == 1)))
						|| ((daysBetween(dateFile, dateNow) == 3)
								&& (Calendar.getInstance().get(
										Calendar.DAY_OF_WEEK) == 2) && (Calendar
								.getInstance().get(Calendar.HOUR_OF_DAY) < 8))) {
					isOlder = false;
				} else {
					isOlder = true;
				}

			} else if (dateNow.compareTo(dateFile) < 0) {
				isOlder = false;
			} else if (dateNow.compareTo(dateFile) == 0) {
				isOlder = false;
			}
			Log.i("Date1: ", dateFile.toString());
			Log.i("Date now: ", dateNow.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return isOlder;
	}

	public long daysBetween(Date startDate, Date endDate) {
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(startDate);
		Calendar eDate = Calendar.getInstance();
		eDate.setTime(endDate);

		long daysBetween = 0;
		while (sDate.before(eDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
	
	public void switchCurrency(View v) {

		String temp;
		temp = mainCurrency;
		mainCurrency = resultCurrency;
		resultCurrency = temp;
		updateCurrenciesView();
		convert(v);
	}
	
	public void convert(View view) {
		String mainValue;
		Double resultValue;
		String result;
		Double mainUnitValue = 0d;
		Double resultUnitValue = 0d;
		Double value;

		mainValue = mainValueTxt.getText().toString();
		// if a value is entered
		if (!mainValue.equals("")) {
			Integer mainValueI = Integer.valueOf(mainValue);
			if(mainCurrency.equals("rsd")){
				mainUnitValue = 1d;
				resultUnitValue = Double.parseDouble(parseJSON(getStringFromFile(), resultCurrency.toLowerCase(Locale.ENGLISH)));
			}else if(resultCurrency.equals("rsd")){
				mainUnitValue = Double.parseDouble(parseJSON(getStringFromFile(), mainCurrency.toLowerCase(Locale.ENGLISH)));
				resultUnitValue = 1d;
			}else if(mainCurrency.equals("rsd")||resultCurrency.equals("rsd")){
				mainUnitValue = 1d;
				resultUnitValue = 1d;
			}else{
			mainUnitValue = Double.parseDouble(parseJSON(getStringFromFile(), mainCurrency.toLowerCase(Locale.ENGLISH)));
			resultUnitValue = Double.parseDouble(parseJSON(getStringFromFile(), resultCurrency.toLowerCase(Locale.ENGLISH)));
			}
			value = mainUnitValue / resultUnitValue;
			resultValue = mainValueI * value;
			
			String log = "Main unit value is: " + mainValue + " main unit v: "
					+ mainUnitValue + " result unit v: " + resultUnitValue
					+ " value: " + value + " result value: " + resultValue;
			Log.d("Values:", log);
			
			DecimalFormat format = new DecimalFormat();
			format.setGroupingSize(3);
			format.setGroupingUsed(true);
			format.setMaximumIntegerDigits(350);
			format.setMaximumFractionDigits(2);
			result = format.format(resultValue);
			
			Log.i("ResultValue: ", result);
			resultValueTxt.setText(result);
		}
	}

	public void chooseCurrency(View view) {
		Intent intent = new Intent(CurrencyConverter.this,
				ChooseCurrencyActivity.class);
		startActivityForResult(intent, GET_CURRENCY_REQUEST_CODE);
	}

	public void chooseCurrencyResult(View view) {
		Intent intent = new Intent(CurrencyConverter.this,
				ChooseCurrencyActivity.class);
		startActivityForResult(intent, GET_CURRENCY1_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GET_CURRENCY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String RESULT = data.getStringExtra(TAG);
				if (!(RESULT == null)) {
					mainCurrency = RESULT;
					resultValueTxt.setText("");
					Log.i("Currency is ", RESULT);
				}
			}

		} else if (requestCode == GET_CURRENCY1_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String RESULT = data.getStringExtra(TAG);
				if (!(RESULT == null)) {
					resultCurrency = RESULT;
					resultValueTxt.setText("");
					Log.i("Currency1 is ", RESULT);
				}
			}

		}

	}
	
	public void openKeyboard(View v) {
//		mainValueTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		resultValueTxt.setText("");
	}
	
	public void updateCurrenciesView(){
		mainCurrencyTxt.setText(mainCurrency.toUpperCase(Locale.ENGLISH)+" ");
		resultCurrencyTxt.setText(resultCurrency.toUpperCase(Locale.ENGLISH)+" ");
	}
	
	// public static String parseCurrencyTable(String data, String query) {
	// String date = "";
	// String chf = "";
	// try {
	// JSONObject object = (JSONObject) new JSONTokener(data).nextValue();
	// date = object.getString("result");
	// /*
	// * JSONObject jObject = new JSONObject(data); date =
	// * jObject.getString("result");
	// */
	// JSONObject jObject1 = new JSONObject(date);
	// chf = jObject1.getString(query);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return chf;
	// }

}
