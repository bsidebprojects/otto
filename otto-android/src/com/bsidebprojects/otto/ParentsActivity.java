package com.bsidebprojects.otto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import com.otto.routeendpoint.Routeendpoint;
import com.otto.routeendpoint.model.Route;
import com.otto.serviceendpoint.Serviceendpoint;
import com.otto.serviceendpoint.model.Service;

public class ParentsActivity extends Activity {

	private static final int MAX_ZOOM = 17;
	private static final int INIT_ZOOM = 9;
	private GoogleMap map;
	private Marker busMarker;
	private BitmapDescriptor busResource;
	private BitmapDescriptor stopResource;
	private BitmapDescriptor breadcrumbResource;
	private BitmapDescriptor messageResource;
	private String securityCode;
	private Serviceendpoint serviceendpoint = null;
	private Routeendpoint routeendpoint = null;
	private BroadcastReceiver myReceiver;
	private Service service;
	private Route route;
	private List<Marker> stops = new ArrayList<Marker>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parents);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		busResource = BitmapDescriptorFactory.fromResource(R.drawable.bus);
		stopResource = BitmapDescriptorFactory.fromResource(R.drawable.stop);
		breadcrumbResource = BitmapDescriptorFactory.fromResource(R.drawable.breadcrumb);
		messageResource = BitmapDescriptorFactory.fromResource(R.drawable.message);
		
		myReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle extras = intent.getExtras();
				if (extras != null){
					String service = extras.getString("service");

					if(service.equals(securityCode)) {
						String message = extras.getString("message");
						if(message != null && busMarker != null) {
							showMessage(message);
							
							Notification.Builder builder = new Notification.Builder(getApplicationContext());
							builder.setSmallIcon(R.drawable.message_white);
//							builder.setContentTitle("Mensaje de la ruta "+securityCode);
							builder.setContentTitle(getResources().getString(R.string.message_on_route) +" "+securityCode);
							builder.setContentText(message);
							Notification notification = builder.getNotification();
							NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
							notificationManager.notify(1, notification);
						} else {
							double latitude = extras.getDouble("latitude");
							double longitude = extras.getDouble("longitude");
							onNewLocation(new LatLng(latitude, longitude));
						}
					}
				}
			}
		};
		IntentFilter filter = new IntentFilter("com.otto.android.action.broadcast");
		registerReceiver(myReceiver, filter);
		
		securityCode = getIntent().getExtras().getString("securityCode");
		
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
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(getDefatultLocation(), INIT_ZOOM));
		
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

	private void loadService() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					service = serviceendpoint.getServiceByCode(securityCode).execute();
					route = routeendpoint.getRoute(service.getRouteId()).execute();
					
					if(busMarker != null) {
						busMarker.setTitle(service.getRouteName());
					}
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							LatLng last = null;
							if(service.getTrack() != null) {
								for(String track : service.getTrack()) {
									String[] position = track.split("@");
									last = new LatLng(Double.parseDouble(position[0]), Double.parseDouble(position[1]));
									addBreadcrumb(last);
								}
							}
							
							if(service.getMessages() != null) {
								for(String message : service.getMessages()) {
									showMessage(message);
								}
							}
							
							
							if(last != null) {
								createBus(last);
								// Move the camera instantly to hamburg with a zoom of .
								map.moveCamera(CameraUpdateFactory.newLatLngZoom(last, INIT_ZOOM));
								// Zoom in, animating the camera.
								map.animateCamera(CameraUpdateFactory.zoomTo(MAX_ZOOM), 2000, null);
							}
							
							if(route.getStops() !=  null) {
								for(String stop : route.getStops()) {
									String[] stopInfo = stop.split("@");
									LatLng position = new LatLng(Double.valueOf(stopInfo[0]), Double.valueOf(stopInfo[1]));
									Marker stopMarker = map.addMarker(new MarkerOptions()
									.position(position)
									.title(stopInfo[2])
									.snippet(estimation(last, position))
									.icon(stopResource)
									);
									stops.add(stopMarker);
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

	private String estimation(LatLng position1, LatLng position2) {
		if(position1==null || position2==null) {
			return "";
		}
		double earthRadius = 3958.75;
		double latDiff = Math.toRadians(position2.latitude-position1.latitude);
		double lngDiff = Math.toRadians(position2.longitude-position1.longitude);
		double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
				Math.cos(Math.toRadians(position1.latitude)) * Math.cos(Math.toRadians(position2.latitude)) *
				Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = earthRadius * c;

		int meterConversion = 1609;

		return getResources().getString(R.string.in)+" "+((int)(distance * meterConversion / 200))+" "+getResources().getString(R.string.minutes);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parents, menu);
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
	
	public void onNewLocation(LatLng location) {
		if(busMarker == null) {
			createBus(location);
		}
		
		addBreadcrumb(busMarker.getPosition());
		busMarker.setPosition(location);
		map.animateCamera(CameraUpdateFactory.newLatLng(location));
		
		for(Marker stop : stops) {
			stop.setSnippet(estimation(location, stop.getPosition()));
		}
	}

	private void addBreadcrumb(LatLng latLng) {
		map.addMarker(new MarkerOptions()
			.position(latLng)
			.icon(breadcrumbResource)
		);
	}
	
	private void createBus(LatLng last) {
		busMarker = map.addMarker(new MarkerOptions()
			.position(last)
			.title("route")
			.icon(busResource)
				);
		if(service != null) {
			busMarker.setTitle(service.getRouteName());
		}
	}
	
	private void showMessage(String message) {
		Marker marker = map.addMarker(new MarkerOptions()
		.position(busMarker.getPosition())
		.title(message)
		.snippet(SimpleDateFormat.getDateTimeInstance().format(new Date()))
		.icon(messageResource)
			);
		marker.showInfoWindow();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
	}
}
