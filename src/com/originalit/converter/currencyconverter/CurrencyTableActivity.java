package com.originalit.converter.currencyconverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.format.DateFormat;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.originalit.converter.ConverterActivity;
import com.originalit.converter.R;

public class CurrencyTableActivity extends ActionBarActivity {

	private LinearLayout currencyTable;
	private TextView dateTxt;
	private String[] currenciesArr = { "eur", "usd", "chf", "gbp", "aud",
			"cad", "sek", "dkk", "nok", "jpy", "rub", "cny", "huf" };
	private String kupovni = "";
	private String srednji = "";
	private String prodajni = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency_table);
		
		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		TextView actionTitle = (TextView) v.findViewById(R.id.action_title_main);
		actionTitle.setText(R.string.title_activity_currency_converter);

		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);
		
		currencyTable = (LinearLayout) this.findViewById(R.id.currency_table_list);
		
		dateTxt = (TextView) this.findViewById(R.id.currency_table_date);
		dateTxt.setText("at date: "+CurrencyConverter.parseJSON(CurrencyConverter.getStringFromFile(), "date")+ "\nfrom National Bank of Serbia");
		if (currenciesArr != null) {
			for (String currency : currenciesArr) {
				
//				output = CurrencyConverter.parseJSON(CurrencyConverter.getStringFromFile(), currency);
				kupovni = CurrencyConverter.parseJSON(CurrencyConverter.getStringFromFile(), currency, "kup");
				srednji = CurrencyConverter.parseJSON(CurrencyConverter.getStringFromFile(), currency, "sre");
				prodajni = CurrencyConverter.parseJSON(CurrencyConverter.getStringFromFile(), currency, "pro");
				
				int dpValueTopBottom = 5; // margin Top and Bottom in dips
				
				
				float d = this.getResources().getDisplayMetrics().density; // Display Density
				
				int marginTopBottom = (int)(dpValueTopBottom * d); // padding Top and Bottom in pixels
				
				
				LinearLayout listItemLayout = new LinearLayout(this);
				TextView textviewCurrency = new TextView(this);
				TextView textviewKupovni = new TextView(this);
				TextView textviewSrednji = new TextView(this);
				TextView textviewProdajni = new TextView(this);
				
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				LinearLayout.LayoutParams textParamsCurrency = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.9f);
				LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
				
				textviewCurrency.setText(currency.toUpperCase());
				textviewKupovni.setText(kupovni);
				textviewSrednji.setText(srednji);
				textviewProdajni.setText(prodajni);
//				textviewCurrency.setGravity(Gravity.CENTER_HORIZONTAL);
				listItemLayout.setOrientation(LinearLayout.HORIZONTAL);
				
				textviewCurrency.setTextAppearance(this, R.style.currencyTable);
				textviewKupovni.setTextAppearance(this, R.style.currencyTable);
				textviewSrednji.setTextAppearance(this, R.style.currencyTable);
				textviewProdajni.setTextAppearance(this, R.style.currencyTable);
				layoutParams.setMargins(0, marginTopBottom, 0, marginTopBottom);
				
				listItemLayout.setLayoutParams(layoutParams);
				textviewCurrency.setLayoutParams(textParamsCurrency);
				textviewKupovni.setLayoutParams(textParams);
				textviewSrednji.setLayoutParams(textParams);
				textviewProdajni.setLayoutParams(textParams);
				listItemLayout.addView(textviewCurrency);
				listItemLayout.addView(textviewKupovni);
				listItemLayout.addView(textviewSrednji);
				listItemLayout.addView(textviewProdajni);
				currencyTable.addView(listItemLayout);
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
	
}
