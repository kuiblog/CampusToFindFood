package com.example.FindFood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		TextView title = (TextView)findViewById(R.id.titledetail);
		title.setText(bundle.getString("titleItem"));
		TextView phone = (TextView)findViewById(R.id.phonedetail);
		phone.setText(bundle.getString("phone"));
		TextView distance = (TextView)findViewById(R.id.distancedetail);
		distance.setText(bundle.getString("distance"));
		TextView address = (TextView)findViewById(R.id.addressdetail);
		address.setText(bundle.getString("address"));
		
		
		
	}
}