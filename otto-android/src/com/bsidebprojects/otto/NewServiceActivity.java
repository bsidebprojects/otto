package com.bsidebprojects.otto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.otto.routeendpoint.Routeendpoint;
import com.otto.routeendpoint.model.CollectionResponseRoute;
import com.otto.routeendpoint.model.Route;
import com.otto.serviceendpoint.Serviceendpoint;
import com.otto.serviceendpoint.model.Service;

public class NewServiceActivity extends Activity implements View.OnClickListener{

	private List<Route> routes;
	private Routeendpoint ruteEndpoint = null;
	private Serviceendpoint serviceendpoint = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_service);
		
		findViewById(R.id.startServiceButton).setOnClickListener(this);
		findViewById(R.id.cancelButton).setOnClickListener(this);
		
		Routeendpoint.Builder ruteEndpointBuilder = new Routeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});

		ruteEndpoint = CloudEndpointUtils.updateBuilder(ruteEndpointBuilder).build();
		
		Serviceendpoint.Builder serviceEndpointBuilder = new Serviceendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		
		serviceendpoint = CloudEndpointUtils.updateBuilder(serviceEndpointBuilder).build();
		
		retrieveAvailableRoutes();
	}

	private void retrieveAvailableRoutes() {
		new Thread(){
			@Override
			public void run() {
				try {
					CollectionResponseRoute collectionResponseRute = ruteEndpoint.listRoute().execute();
					routes = collectionResponseRute.getItems();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Spinner routeSpinner = (Spinner) findViewById(R.id.selectRouteSpinner);
							
							List<String> list = new ArrayList<String>();
							list.add(getResources().getString(R.string.no_route_selected));
							for (Route route : routes) {
								list.add(route.getName());
							}
							
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
							routeSpinner.setAdapter(dataAdapter);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startServiceButton:
			addNewService();
			break;

		case R.id.cancelButton:
			finish();
			break;
		default:
			break;
		}
		
	}

	private void addNewService() {
		Spinner routeSpinner = (Spinner) findViewById(R.id.selectRouteSpinner);
		String routeSelected = (String) routeSpinner.getSelectedItem();
		Route route = getRouteByName(routeSelected);

		EditText serviceCode = (EditText) findViewById(R.id.serviceCode);
		String serviceCodeText = serviceCode.getText().toString();
		
		if(route == null || "".equals(serviceCodeText) || serviceCodeText.length() == 0) {
			Toast.makeText(this, getResources().getString(R.string.fields_empty_error), Toast.LENGTH_LONG).show();
			return;
		}
		
		Long code = Long.parseLong(serviceCodeText);
		
		final Service service = new Service();
		service.setRouteId(route.getId());
		service.setRouteName(route.getName());
		service.setSecurityCode(code);
		
		DateTime date = new DateTime(new Date());
		service.setStart(date);
		service.setEnd(date);
		
		new Thread(){
			@Override
			public void run() {
				try {
					serviceendpoint.insertService(service).execute();
					
					Intent driverIntent = new Intent(getApplicationContext(), DriverActivity.class);
					driverIntent.putExtra("securityCode", String.valueOf(service.getSecurityCode()));
					startActivity(driverIntent);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	
	}

	private Route getRouteByName(String routeSelected) {
		for(Route route : routes) {
			if(route.getName().equalsIgnoreCase(routeSelected)) {
				return route;
			}
		}
		return null;
	}
}
