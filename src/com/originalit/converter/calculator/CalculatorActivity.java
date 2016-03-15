package com.originalit.converter.calculator;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.originalit.converter.ConverterActivity;
import com.originalit.converter.R;
import com.originalit.converter.currencyconverter.CurrencyConverter;

public class CalculatorActivity extends ActionBarActivity {

	public String result = "";
	private String num1 = "0";
	private String num2 = "";
	private Double tmp;

	private boolean clickedPointN1 = false; // to prevent getting more than 1
											// dot
	private boolean clickedPointN2 = false;
	private boolean clickedRootN1 = false;
	private boolean clickedRootN2 = false;
	
	private boolean wantedN1empty = false;

	private Character operator = null;
	private Double resultIs = null;
	private TextView resultTxt;

	private Vibrator mVib;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title_main, null);

		TextView actionTitle = (TextView) v.findViewById(R.id.action_title);
		actionTitle.setText(R.string.title_activity_calculator);

		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);

		resultTxt = (TextView) this
				.findViewById(R.id.calculator_result_textview);
		// setting the textview to scroll while typing
		resultTxt.setMovementMethod(new ScrollingMovementMethod());
		
		mVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

	}

	public void openUnitConverter(View v) {
		Intent intent = new Intent(getApplicationContext(),
				ConverterActivity.class);
		startActivity(intent);
	}

	public void openCurrencyConverter(View v) {
		Intent intent = new Intent(getApplicationContext(),
				CurrencyConverter.class);
		startActivity(intent);
	}

	public void buttonNumberPressed(View v) {
		
		mVib.vibrate(50);
		
		String resourceNumber = "btn_";
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		if (v.getId() == R.id.btn_0) {
			if (resultIs != null) {
				newValue();
				resetResult();
				clickedRootN2 = false;
			}
			if (clickedRootN1)
				newValue();
			else if (clickedRootN2)
				newValueNumber2();

			if (!result.equals("0")) {
				if (!num1.equals("") || wantedN1empty) {
					num1 += 0;
					result += 0;
					resultTxt.setText(result);
					wantedN1empty = false;
				} else if (!num2.equals("0")) {
					num2 += 0;

					result += 0;
					resultTxt.setText(result);
				}
			}
		}

		for (int number : numbers) {
			if (v.getId() == getResources().getIdentifier(
					resourceNumber + number, "id", getPackageName())) {
				if (resultIs != null) {
					newValue();
					resetResult();
					clickedRootN2 = false;
				}
				if (clickedRootN1)
					newValue();
				else if (clickedRootN2)
					newValueNumber2();

				if (!num1.equals("") || wantedN1empty){
					if(!result.equals("0")){
					num1 += number;
					result += number;
					wantedN1empty = false;
					}
					else{
					num1 = ""+number;
					result = num1;
					}
					
				}
				else{
					if(!num2.equals("0")){
						num2 += number;
						result += number;
						}
						else{
						num2 = ""+number;
						result = result.substring(0, result.length()-1)+num2;
						}
				}
				resultTxt.setText(result);
			}

		}
	}

	/*
	 * switch (v.getId()) { case getResources().getIdentifier( resourceNumber +
	 * "mustsee", "id", getPackageName()): if (resultIs != null) { newValue();
	 * resetResult(); } if (!num1.equals("")) num1 += 0; else num2 += 0; result
	 * += 0; break; case R.id.btn_1: if (resultIs != null) { newValue();
	 * resetResult(); } if (!num1.equals("")) num1 += 1; else num2 += 1; result
	 * += 1; break; case R.id.btn_2: if (resultIs != null) { newValue();
	 * resetResult(); } if (!num1.equals("")) num1 += 2; else num2 += 2; result
	 * += 2; break; case R.id.btn_3: if (resultIs != null) if (resultIs != null)
	 * { newValue(); resetResult(); } if (!num1.equals("")) num1 += 3; else num2
	 * += 3; result += 3; break; case R.id.btn_4: if (resultIs != null) {
	 * newValue(); resetResult(); } result += 4; if (!num1.equals("")) num1 +=
	 * 4; else num2 += 4; break; case R.id.btn_5: if (resultIs != null) {
	 * newValue(); resetResult(); } if (!num1.equals("")) num1 += 5; else num2
	 * += 5; result += 5; break; case R.id.btn_6: if (resultIs != null) {
	 * newValue(); resetResult(); } if (!num1.equals("")) num1 += 6; else num2
	 * += 6; result += 6; break; case R.id.btn_7: if (resultIs != null) {
	 * newValue(); resetResult(); } if (!num1.equals("")) num1 += 7; else num2
	 * += 7; result += 7; break; case R.id.btn_8: if (resultIs != null) {
	 * newValue(); resetResult(); } if (!num1.equals("")) num1 += 8; else num2
	 * += 8; result += 8; break; case R.id.btn_9: if (resultIs != null) {
	 * newValue(); resetResult(); } if (!num1.equals("")) num1 += 9; else num2
	 * += 9; result += 9; break; }
	 */

	public void buttonPressed(View v) {
		
		mVib.vibrate(50);
		
		String exception = "First number is invalid";
		switch (v.getId()) {

		case R.id.btn_c:
			newValue();
			break;

		case R.id.btn_backspace:
			if (!result.equals("")) {
				if (!num1.equals("")|| wantedN1empty) {
					if(result.equals("-")){
						num1 = "";
						result = "";
						wantedN1empty = true;
					}else{
					num1 = num1.substring(0, num1.length() - 1);
					result = result.substring(0, result.length() - 1);
					}
				} else if (num2.equals("")) {
					result = result.substring(0, result.length() - 3);
					num1 = tmp.toString();
					operator = null;
				} else if (resultIs == null) {
					num2 = num2.substring(0, num2.length() - 1);
					result = result.substring(0, result.length() - 1);
				} else {
					newValue();
					resetResult();
				}

			}
			break;

		case R.id.btn_point:
			if (resultIs != null) {
				newValue();
				resetResult();
			}
			if (!num1.equals("")|| wantedN1empty) {
				if (clickedPointN1 == false) {
					num1 += ".";
					clickedPointN1 = true;
					result += ".";
					wantedN1empty = false;
				}
			} else {
				if (clickedPointN2 == false) {
					num2 += ".";
					clickedPointN2 = true;
					result += ".";
				}
			}

			break;

		case R.id.btn_negative:
			if (resultIs != null) {
				newValue();
				resetResult();
			}
			
			if(wantedN1empty){
					num1 = "-";
					result = num1;
					wantedN1empty = false;
			}else{
			
			if (!num1.equals("")){
				if(num1.equals("0"))
					num1="";
					
				if(result.equals("")){
					num1 = "-";
				}else if(result.equals("-")){
					num1 = "";
				wantedN1empty = true;
				}
				else if (num1.startsWith("-")){
					
					num1 = num1.substring(1);
				}
				else{
					if(num1.startsWith("0") && !result.startsWith("0"))
						num1 = "-" + num1.substring(1);
					else
					num1 = "-" + num1;
					}
			}
			else if(num2.equals(""))
				num2 = "-";
			else if(num2.equals("-"))
				num2 = "";
			else if (num2.startsWith("-"))
				num2 = num2.substring(1);
			else
				num2 = "-" + num2;

			if (operator == null)
				result = num1;
			else {
				int opIndex = result.indexOf(operator);
				if(operator == '-')
					opIndex = result.indexOf(operator, 1);
				if (result.substring(opIndex + 2).startsWith("-"))
					result = result.substring(0, opIndex + 2)
							+ result.substring(opIndex + 3);
				
				else
					result = result.substring(0, opIndex + 2) + "-"
							+ result.substring(opIndex + 2);
			}
			
			}

			break;

		case R.id.btn_root:
			if (resultIs != null) {
				newValue();
				resetResult();
			}
			if (!num1.equals("")) {
				Double value1 = Double.valueOf(num1).doubleValue();
				num1 = "" + Math.sqrt(value1);
				result = num1;
				clickedRootN1 = true;
				
			} else if (!num2.equals("")) {
				Double value2 = Double.valueOf(num2).doubleValue();
				num2 = "" + Math.sqrt(value2);
				result = result.substring(0, result.indexOf(operator) + 2)
						+ num2;
				clickedRootN2 = true;
			}
			break;

		case R.id.btn_plus:
			if (operator == null && !result.equals("")) {
				try {
					tmp = Double.valueOf(num1).doubleValue();
					num1 = "";
					result += "\n+\n";
					operator = '+';

					clickedRootN1 = false;
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(), exception,
							Toast.LENGTH_LONG).show();
				}
			}
			
			break;

		case R.id.btn_minus:
			if (operator == null && !result.equals("")) {
				try {
					tmp = Double.valueOf(num1).doubleValue();
					num1 = "";
					result += "\n-\n";
					operator = '-';

					clickedRootN1 = false;
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(), exception,
							Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.btn_mul:
			if (operator == null && !result.equals("")) {
				try {
					tmp = Double.valueOf(num1).doubleValue();
					num1 = "";
					result += "\n*\n";
					operator = '*';

					clickedRootN1 = false;
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(), exception,
							Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.btn_div:
			if (operator == null && !result.equals("")) {
				try {
					tmp = Double.valueOf(num1).doubleValue();
					num1 = "";
					result += "\n/\n";
					operator = '/';

					clickedRootN1 = false;
				} catch (NumberFormatException e) {
					Toast.makeText(getApplicationContext(), exception,
							Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.btn_equal:
			if (operator != null && !num2.equals("") && resultIs == null) {
				// Log.i("num1, num2, resultIs",
				// ""+tmp+", "+num2+", "+resultIs+" oper: "+operator);
				DecimalFormat format = new DecimalFormat();
				format.setMinimumFractionDigits(0);
				try {
					if (operator == '+')
						resultIs = tmp + Double.valueOf(num2).doubleValue();
					else if (operator == '-')
						resultIs = tmp - Double.valueOf(num2).doubleValue();
					else if (operator == '*')
						resultIs = tmp * Double.valueOf(num2).doubleValue();
					else if (operator == '/')
						resultIs = tmp / Double.valueOf(num2).doubleValue();

					if (resultIs.toString().endsWith(".0"))
						result += "\n=\n" + format.format(resultIs);
					else
						result += "\n=\n" + resultIs;

				} catch (NumberFormatException e) {
					String exception2 = "Second number is invalid";
					Toast.makeText(getApplicationContext(), exception2,
							Toast.LENGTH_LONG).show();
				}
				// newValue();
			}
			break;
		}
		resultTxt.setText(result);
	}

	public void newValue() {
		result = "";
		num1 = "0";
		num2 = "";
		operator = null;
		clickedPointN1 = false;
		clickedPointN2 = false;
		// resultIs = null;
		clickedRootN1 = false;
		clickedRootN2 = false;
		wantedN1empty = false;
	}

	public void newValueNumber1() {
		num1 = "0";
		result = "";
		clickedRootN1 = false;
		resetResult();
	}

	public void newValueNumber2() {
		num2 = "";
		result = result.substring(0, result.indexOf(operator) + 2);
		clickedRootN2 = false;
	}

	public void resetResult() {
		resultIs = null;
	}

}
