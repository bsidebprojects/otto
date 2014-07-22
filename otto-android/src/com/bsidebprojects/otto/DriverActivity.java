package com.bsidebprojects.otto;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.otto.messageEndpoint.MessageEndpoint;
import com.otto.routeendpoint.Routeendpoint;
import com.otto.routeendpoint.model.Route;
import com.otto.serviceendpoint.Serviceendpoint;
import com.otto.serviceendpoint.model.Service;

public class DriverActivity extends Activity implements LocationListener, View.OnClickListener {

	private static final int MAX_ZOOM = 17;
	private static final int INIT_ZOOM = 9;
	private static final int REQUEST_CODE = 99;
	private LocationManager locationManager;
	private String provider;
	private MessageEndpoint messageEndpoint = null;
	private Serviceendpoint serviceendpoint = null;
	private Routeendpoint routeendpoint = null;
	private String securityCode;
	private GoogleMap map;
	private BitmapDescriptor busResource;
	private BitmapDescriptor stopResource;
	private BitmapDescriptor breadcrumbResource;
	private Marker busMarker;
	private EditText text;
	private Button sendMsg;
	private Button finish;
	
	private Service service;
	private Route route;
	private Button voice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.driver);

		securityCode = getIntent().getExtras().getString("securityCode");
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		MessageEndpoint.Builder messageEndpointBuilder = new MessageEndpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		
		messageEndpoint = CloudEndpointUtils.updateBuilder(messageEndpointBuilder).build();
		
		Serviceendpoint.Builder serviceEndpointBuilder = new Serviceendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		
		serviceendpoint = CloudEndpointUtils.updateBuilder(serviceEndpointBuilder).build();
		
		Routeendpoint.Builder routeEndpointBuilder = new Routeendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});

		routeendpoint = CloudEndpointUtils.updateBuilder(routeEndpointBuilder).build();
		
		busResource = BitmapDescriptorFactory.fromResource(R.drawable.bus);
		stopResource = BitmapDescriptorFactory.fromResource(R.drawable.stop);
		breadcrumbResource = BitmapDescriptorFactory.fromResource(R.drawable.breadcrumb);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(getDefatultLocation(), INIT_ZOOM));

		text = (EditText) findViewById(R.id.text);
		sendMsg = (Button) findViewById(R.id.send);
		finish = (Button) findViewById(R.id.finish);
		voice = (Button) findViewById(R.id.voice);
		text.setOnClickListener(this);
		sendMsg.setOnClickListener(this);
		finish.setOnClickListener(this);
		voice.setOnClickListener(this);
		
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
		}
		
		loadService();
	}
	
	private LatLng getDefatultLocation() {
		
		try {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, false);
			Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
			return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new LatLng(42.603471, 0.52382);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.driver, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		final double lat = location.getLatitude();
		final double lng = location.getLongitude();
		if(busMarker == null) {
			createBus(new LatLng(lat, lng));
		} else {
			addBreadcrumb(busMarker.getPosition());
			busMarker.setPosition(new LatLng(lat, lng));
		}
		
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(MAX_ZOOM), 2000, null);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					messageEndpoint.sendMessage("move@"+ securityCode +"@"+ lat +"@"+ lng).execute();
					serviceendpoint.addTrack(securityCode, lat +"@"+ lng).execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	private void createBus(LatLng last) {
		busMarker = map.addMarker(new MarkerOptions()
			.position(last)
			.icon(busResource));
		
		if(service != null) {
			busMarker.setTitle(service.getRouteName());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.voice:
			startVoiceRecognitionActivity();
			break;
		case R.id.send:
			final String textToSend = text.getText().toString();
			if(textToSend.length()>0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							messageEndpoint.sendMessage("msg@"+ securityCode +"@"+ textToSend).execute();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			break;
		case R.id.finish:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						messageEndpoint.sendMessage("msg@"+ securityCode +"@Finalizado").execute();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.route_ended_text), Toast.LENGTH_LONG).show();
							}
						});
						finish();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;

		default:
			break;
		}
	}
	
	private void loadService() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					service = serviceendpoint.getServiceByCode(securityCode).execute();
					route = routeendpoint.getRoute(service.getRouteId()).execute();
				
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(busMarker != null) {
								busMarker.setTitle(service.getRouteName());
							}
							
							if(route.getStops() !=  null) {
								for(String stop : route.getStops()) {
									String[] stopInfo = stop.split("@");
									map.addMarker(new MarkerOptions()
									.position(new LatLng(Double.valueOf(stopInfo[0]), Double.valueOf(stopInfo[1])))
									.title(stopInfo[2])
									.icon(stopResource)
									);
								}
							}
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void addBreadcrumb(LatLng latLng) {
		map.addMarker(new MarkerOptions()
			.position(latLng)
			.icon(breadcrumbResource)
		);
	}
	
	/* Voice */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.insert_message));
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * Handle the results from the voice recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if(matches.size()>0) {
				text.setText(matches.get(0));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
