package com.originalit.converter.currencyconverter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.originalit.converter.ChooseUnitActivity;
import com.originalit.converter.ConverterActivity;
import com.originalit.converter.R;
import com.originalit.converter.R.id;
import com.originalit.converter.R.layout;
import com.originalit.converter.R.menu;
import com.originalit.converter.calculator.CalculatorActivity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.LayoutParams;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseCurrencyActivity extends ActionBarActivity {

	private LinearLayout listCurrencies;
	private String[] currenciesArr = {"eur", "usd", "chf", "gbp","rsd", "aud", "cad", "sek", "dkk", "nok","jpy", "rub", "cny", "huf"};
	
	static private final String TAG = "Currency";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_currency);
		
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
		
		Map<String, String> mapCurrencyName = new HashMap<String, String>();
		mapCurrencyName.put("eur", "Euro");
		mapCurrencyName.put("usd", "U.S. dollar");
		mapCurrencyName.put("chf", "Swiss franc");
		mapCurrencyName.put("gbp", "British pond");
		mapCurrencyName.put("rsd", "Serbian dinar");
		mapCurrencyName.put("aud", "Australian dollar");
		mapCurrencyName.put("cad", "Canadian dollar");
		mapCurrencyName.put("sek", "Swedish krona");
		mapCurrencyName.put("dkk", "Danish krone");
		mapCurrencyName.put("nok", "Norwegian krone");
		mapCurrencyName.put("jpy", "Japanese yen");
		mapCurrencyName.put("rub", "Russian ruble");
		mapCurrencyName.put("cny", "Chinese yuan renminbi");
		mapCurrencyName.put("huf", "Hungarian forint");
		
		listCurrencies = (LinearLayout) this.findViewById(R.id.list_units_choose_currency);
		
//		String currencyIs = "";
		String currencyNameIs = "";
	
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
		
		if(currenciesArr != null){
			for(String currency : currenciesArr){
				final String currencyIs = currency;
				currencyNameIs = mapCurrencyName.get(currency);
			
				LinearLayout listItemLayout = new LinearLayout(this);
				TextView textviewCurrency = new TextView(this);
				TextView textviewName = new TextView(this);
				
				LinearLayout.LayoutParams layoutUnitParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(unitWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
				LinearLayout.LayoutParams textParamsValue = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				
				listItemLayout.setPadding(paddingLeft, paddingTopBottom, 0, paddingTopBottom);
				listItemLayout.setBackgroundResource(R.drawable.list_box_xml);
				listItemLayout.setGravity(Gravity.CENTER_VERTICAL);
				layoutUnitParams.setMargins(0, 0, 0, marginBottomLayout);
				listItemLayout.setOnClickListener(new OnClickListener() {
					
					@SuppressLint("NewApi")
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ChooseCurrencyActivity.this, CurrencyConverter.class);
						Log.i("Currency is in Choose", currencyIs);
						intent.putExtra(TAG, currencyIs);
						setResult(RESULT_OK, intent);
						finish();
						
					}
				});
				
				textviewCurrency.setText(currencyIs.toUpperCase(Locale.ENGLISH));
				textviewName.setText(currencyNameIs);
				
				textviewCurrency.setTextAppearance(this, R.style.list_currency);
				textParamsValue.setMargins(marginLeft, 0, 0, 0);
				textviewName.setMaxLines(2);
				textviewName.setTextAppearance(this, R.style.unit_long);
				
				listItemLayout.setLayoutParams(layoutUnitParams);
				textviewCurrency.setLayoutParams(textParams);
				textviewName.setLayoutParams(textParamsValue);
				
				listItemLayout.addView(textviewCurrency);
				listItemLayout.addView(textviewName);
				
				listCurrencies.addView(listItemLayout);
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
	
}
