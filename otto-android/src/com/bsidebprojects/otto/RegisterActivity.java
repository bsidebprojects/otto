package com.bsidebprojects.otto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otto.messageEndpoint.MessageEndpoint;
import com.otto.messageEndpoint.model.CollectionResponseMessageData;
import com.otto.serviceendpoint.Serviceendpoint;
import com.otto.serviceendpoint.model.Service;

/**
 * An activity that communicates with your App Engine backend via Cloud
 * Endpoints.
 * 
 * When the user hits the "Register" button, a message is sent to the backend
 * (over endpoints) indicating that the device would like to receive broadcast
 * messages from it. Clicking "Register" also has the effect of registering this
 * device for Google Cloud Messaging (GCM). Whenever the backend wants to
 * broadcast a message, it does it via GCM, so that the device does not need to
 * keep polling the backend for messages.
 * 
 * If you've generated an App Engine backend for an existing Android project,
 * this activity will not be hooked in to your main activity as yet. You can
 * easily do so by adding the following lines to your main activity:
 * 
 * Intent intent = new Intent(this, RegisterActivity.class);
 * startActivity(intent);
 * 
 * To make the sample run, you need to set your PROJECT_NUMBER in
 * GCMIntentService.java. If you're going to be running a local version of the
 * App Engine backend (using the DevAppServer), you'll need to toggle the
 * LOCAL_ANDROID_RUN flag in CloudEndpointUtils.java. See the javadoc in these
 * classes for more details.
 * 
 * For a comprehensive walkthrough, check out the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

	enum State {
		REGISTERED, REGISTERING, UNREGISTERED, UNREGISTERING
	}

	private static String preferences_file_user = "user_file";
	private static String preferences_name = "user_set";
	
	private State curState = State.UNREGISTERED;
	private OnTouchListener registerListener = null;
	private OnTouchListener unregisterListener = null;
	private MessageEndpoint messageEndpoint = null;
	private Serviceendpoint serviceendpoint = null;
	
	private String code;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		Button regButton = (Button) findViewById(R.id.regButton);
		regButton.setOnClickListener(this);

		registerListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return startRegistration();
			}
		};

		unregisterListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					updateState(State.UNREGISTERING);
					GCMIntentService.unregister(getApplicationContext());
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				default:
					return false;
				}
			}
		};

		MessageEndpoint.Builder endpointBuilder = new MessageEndpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});

		messageEndpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();

		Serviceendpoint.Builder serviceEndpointBuilder = new Serviceendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});

		serviceendpoint = CloudEndpointUtils.updateBuilder(serviceEndpointBuilder).build();
		
		loadFavs();
	}

	private void loadFavs() {
		LinearLayout favs = (LinearLayout) findViewById(R.id.favs);
		
		Set<String> preferences = getPreferences(this);
		
		for(final String fav : preferences) {
			Button trackFav = new Button(this);
			trackFav.setText(fav);
			trackFav.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new Thread(){
						@Override
						public void run() {
							try {
								code = fav;
								final Service service = serviceendpoint.getServiceByCode(code).execute();

								runOnUiThread(new Runnable() {
									public void run() {
								
										if(service != null) {
											startRegistration();
										} else {
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_service_active_error)+" ["+ code +"]", Toast.LENGTH_LONG).show();
										}
									}
								});
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();
				}
			});
			
			favs.addView(trackFav);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		/*
		 * If we are dealing with an intent generated by the GCMIntentService
		 * class, then display the provided message.
		 */
		if (intent.getBooleanExtra("gcmIntentServiceMessage", false)) {

//			showDialog(intent.getStringExtra("message"));

			if (intent.getBooleanExtra("registrationMessage", false)) {

				if (intent.getBooleanExtra("error", false)) {
					/*
					 * If we get a registration/unregistration-related error,
					 * and we're in the process of registering, then we move
					 * back to the unregistered state. If we're in the process
					 * of unregistering, then we move back to the registered
					 * state.
					 */
					if (curState == State.REGISTERING) {
						updateState(State.UNREGISTERED);
					} else {
						updateState(State.REGISTERED);

						Intent parentIntent = new Intent(getApplicationContext(), ParentsActivity.class);
						parentIntent.putExtra("securityCode", code);
						
						startActivity(parentIntent);
					}
				} else {
					/*
					 * If we get a registration/unregistration-related success,
					 * and we're in the process of registering, then we move to
					 * the registered state. If we're in the process of
					 * unregistering, the we move back to the unregistered
					 * state.
					 */
					if (curState == State.REGISTERING) {
						updateState(State.REGISTERED);
						
						Intent parentIntent = new Intent(getApplicationContext(), ParentsActivity.class);
						parentIntent.putExtra("securityCode", code);
						
						addFavourite();
						
						startActivity(parentIntent);
					} else {
						updateState(State.UNREGISTERED);
					}
				}
			} else {
				/*
				 * if we didn't get a registration/unregistration message then
				 * go get the last 5 messages from app-engine
				 */
				new QueryMessagesTask(this, messageEndpoint).execute();
			}
		}
	}

	private void addFavourite() {
		
	}

	private void updateState(State newState) {
		Button registerButton = (Button) findViewById(R.id.regButton);
		switch (newState) {
		case REGISTERED:
//			registerButton.setText("Unregister");
//			registerButton.setOnTouchListener(unregisterListener);
			registerButton.setEnabled(true);
			break;

		case REGISTERING:
//			registerButton.setText("Registering...");
			registerButton.setEnabled(false);
			break;

		case UNREGISTERED:
//			registerButton.setText("Register");
//			registerButton.setOnTouchListener(registerListener);
			registerButton.setEnabled(true);
			break;

		case UNREGISTERING:
//			registerButton.setText("Unregistering...");
			registerButton.setEnabled(false);
			break;
		}
		curState = newState;
	}

	private void showDialog(String message) {
		new AlertDialog.Builder(this)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		}).show();
	}

	/*
	 * Need to run this in background so we don't hold up the UI thread, this
	 * task will ask the App Engine backend for the last 5 messages sent to it
	 */
	private class QueryMessagesTask extends
	AsyncTask<Void, Void, CollectionResponseMessageData> {
		Exception exceptionThrown = null;
		MessageEndpoint messageEndpoint;

		public QueryMessagesTask(Activity activity,
				MessageEndpoint messageEndpoint) {
			this.messageEndpoint = messageEndpoint;
		}

		@Override
		protected CollectionResponseMessageData doInBackground(Void... params) {
			try {
				CollectionResponseMessageData messages = messageEndpoint
						.listMessages().setLimit(5).execute();
				return messages;
			} catch (IOException e) {
				exceptionThrown = e;
				return null;
				// Handle exception in PostExecute
			}
		}

		protected void onPostExecute(CollectionResponseMessageData messages) {
			// Check if exception was thrown
			if (exceptionThrown != null) {
				Log.e(RegisterActivity.class.getName(),
						"Exception when listing Messages", exceptionThrown);
				showDialog("Failed to retrieve the last 5 messages from "
						+ "the endpoint at " + messageEndpoint.getBaseUrl()
						+ ", check log for details");
			} else {
				//				TextView messageView = (TextView) findViewById(R.id.msgView);
				//				messageView.setText("Last 5 Messages read from "
				//						+ messageEndpoint.getBaseUrl() + ":\n");
				//				for (MessageData message : messages.getItems()) {
				//					messageView.append(message.getMessage() + "\n");
				//				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regButton:

			new Thread(){
				@Override
				public void run() {
					try {
						EditText codeInserted = (EditText) findViewById(R.id.serviceCode);
						code = codeInserted.getText().toString();
						
						if("".equals(code) || code.length() == 0 ) {
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_empty_error), Toast.LENGTH_LONG).show();
									return;
								}
							});
						}
						
						final Service service = serviceendpoint.getServiceByCode(code).execute();

						runOnUiThread(new Runnable() {
							public void run() {
						
								if(service != null) {
									setPreferences(getApplicationContext());
									startRegistration();
								} else {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_service_active_error)+" ["+ code +"]", Toast.LENGTH_LONG).show();
								}
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

			break;

		default:
			break;
		}

	}

	private boolean startRegistration() {
		if (GCMIntentService.PROJECT_NUMBER == null || GCMIntentService.PROJECT_NUMBER.length() == 0) {
			showDialog("Unable to register for Google Cloud Messaging. "
					+ "Your application's PROJECT_NUMBER field is unset! You can change "
					+ "it in GCMIntentService.java");
		} else {
			updateState(State.REGISTERING);
			try {
				GCMIntentService.register(getApplicationContext());
			} catch (Exception e) {
				Log.e(RegisterActivity.class.getName(),
						"Exception received when attempting to register for Google Cloud "
								+ "Messaging. Perhaps you need to set your virtual device's "
								+ " target to Google APIs? "
								+ "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
								+ " for more information.", e);
				showDialog("There was a problem when attempting to register for "
						+ "Google Cloud Messaging. If you're running in the emulator, "
						+ "is the target of your virtual device set to 'Google APIs?' "
						+ "See the Android log for more details.");
				updateState(State.UNREGISTERED);
			}
		}
		return true;
	}
	
	private Set<String> getPreferences(Context context) {
		Set<String>  codeSet = new HashSet<String>();
		SharedPreferences prefs = context.getSharedPreferences(preferences_file_user, Context.MODE_PRIVATE);
		String toLoad = prefs.getString(preferences_name, "");
		
		if(toLoad != "") {
			Gson gson = new Gson();
			Type type = new TypeToken<Set<String>>(){}.getType();
			codeSet = gson.fromJson(toLoad, type);
		}
		
		return codeSet;
	}
	
	private void setPreferences(Context context) {
		Set<String> codeSet = getPreferences(context);
		codeSet.add(code);
		Gson gson = new Gson();
		String toSave = gson.toJson(codeSet);
		
		SharedPreferences.Editor editor = context.getSharedPreferences(preferences_file_user, Context.MODE_PRIVATE).edit();
		editor.putString(preferences_name, toSave);
		editor.commit();
	}
}
