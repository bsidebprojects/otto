package com.bsidebprojects.otto;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.driver).setOnClickListener(this);
		findViewById(R.id.parents).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.driver:
			Intent newServiceIntent = new Intent(this, NewServiceActivity.class);
			startActivity(newServiceIntent);
			break;

		case R.id.parents:
			Intent registerIntent = new Intent(this, RegisterActivity.class);
			startActivity(registerIntent);
			break;
		}
	}
}
