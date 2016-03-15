package com.originalit.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseCategoryActivity extends ActionBarActivity {
	
	static private final String TAG = "Category";
	
	
	private TextView textviewCategory;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_category);

		ActionBar ab = getSupportActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ab.setCustomView(v, layout);
		
		SharedPreferences prefs = this.getSharedPreferences(
				"com.originalit.converter", Context.MODE_PRIVATE);
		String mainCategoryKey = "com.originalit.converter.mainCategory";
		
		DatabaseHandler db = DatabaseHandler.getInstance(this);
		
		List<Unit> units = db.getAllUnitsDistinctCategory();
		
		LinearLayout listCategories = (LinearLayout) this.findViewById(R.id.list_categories);
		
		int dpValueTopBottom = 13; // padding Top and Bottom in dips
		int dpValueLeft = 23;
		int dpValueMarginRight = 8;
		int dpValueMarginLeft = 15; // margin for category name
		int dpValueBottomMarginLayout = 10;
		int dpValueIconWidth = 50;
		
		float d = this.getResources().getDisplayMetrics().density; // Display Density
		
		int paddingTopBottom = (int)(dpValueTopBottom * d); // padding Top and Bottom in pixels
		int paddingLeft = (int)(dpValueLeft * d);
		int marginRight = (int)(dpValueMarginRight * d);
		int marginLeft = (int)(dpValueMarginLeft * d);
		int iconWidth = (int)(dpValueIconWidth * d);
		int marginBottomLayout = (int)(dpValueBottomMarginLayout * d);
		
		Map<String, Integer> mapIcon = new HashMap<String, Integer>();
		mapIcon.put("WEIGHT/MASS", R.drawable.mass_icn_xml);
		mapIcon.put("LENGTH/DISTANCE", R.drawable.length_icn_xml);
		mapIcon.put("SPEED", R.drawable.speed_icn_xml);
		mapIcon.put("POWER", R.drawable.power_icn_xml);
		mapIcon.put("PRESSURE", R.drawable.pressure_icn_xml);
		mapIcon.put("TEMPERATURE", R.drawable.temperature_icn_xml);
		
		if (units != null && !units.isEmpty()) {
			for (Unit unit : units){
				final String categoryIs = unit.get_category();
				
				LinearLayout listItemLayout = new LinearLayout(this);
				ImageView imageviewIcon = new ImageView(this);
				textviewCategory = new TextView(this);
				
				
				LinearLayout.LayoutParams layoutCategoryParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
				LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

				layoutCategoryParams.setMargins(0, 0, marginRight, marginBottomLayout);
				listItemLayout.setGravity(Gravity.CENTER_VERTICAL);
				listItemLayout.setPadding(paddingLeft, paddingTopBottom, 0, paddingTopBottom);
				listItemLayout.setBackgroundResource(R.drawable.list_box_xml);
				listItemLayout.setOnClickListener(new OnClickListener() {
					
					@SuppressLint("NewApi")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						String unit = unitIs;
					/*	
					Intent intent = new Intent(getApplicationContext(), ConverterActivity.class);
						
				SharedPreferences prefs = getApplicationContext().getSharedPreferences(
							      "com.originalit.converter", Context.MODE_PRIVATE);
						String mainCategoryKey = "com.originalit.converter.mainCategory";
							Log.i("category ", categoryIs);	
						prefs.edit().putString(mainCategoryKey, categoryIs).apply();
						intent.putExtra("categoryChanged", 1);
						startActivity(intent);
						*/
						Intent intent = new Intent(ChooseCategoryActivity.this, ConverterActivity.class);
						Log.i("category ", categoryIs);	
						intent.putExtra(TAG, categoryIs);
						setResult(RESULT_OK, intent);
						finish();
						
					}
				});
				
				
				int icon = mapIcon.get(categoryIs);
				
				imageviewIcon.setImageResource(icon);
				textviewCategory.setText(categoryIs);
				
				textParams.setMargins(marginLeft, 0, 0, 0);
				textviewCategory.setTextAppearance(this, R.style.category_list);
				
				listItemLayout.setLayoutParams(layoutCategoryParams);
				imageviewIcon.setLayoutParams(iconParams);
				textviewCategory.setLayoutParams(textParams);
				
				listItemLayout.addView(imageviewIcon);
				listItemLayout.addView(textviewCategory);
				
				listCategories.addView(listItemLayout);
			}
		}
	}


	public void goBack(View view) {
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.action_bar_title, null);

		Button backBtn = (Button) v.findViewById(R.id.back_button);
		backBtn.setSelected(true);

		Intent intent = new Intent(ChooseCategoryActivity.this, ConverterActivity.class);
		setResult(RESULT_CANCELED, intent);
		
		this.onBackPressed();
	}

//	public void clickList(View view) {
//		int id = view.getId();
//
//		SharedPreferences prefs = getApplicationContext().getSharedPreferences(
//				"com.originalit.converter", Context.MODE_PRIVATE);
//		String categoryKey = "com.originalit.converter.category";
////		prefs.edit().putString(categoryKey, id).apply();
//
//		Intent intent = new Intent(this, ConverterActivity.class);
//		intent.putExtra("CategoryId", (int) id);
//		startActivity(intent);

		// if (id == R.id.list_item_temperature){
		// Toast.makeText(this, "List item temperature " +id + " clicked",
		// Toast.LENGTH_LONG).show();
		// } else {Toast.makeText(this, "List item " +id + " clicked",
		// Toast.LENGTH_LONG).show();}
//	}
}
