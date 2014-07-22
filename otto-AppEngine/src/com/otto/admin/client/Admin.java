package com.otto.admin.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.Size;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerImage;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.otto.admin.shared.ServiceDTO;

public class Admin implements EntryPoint {

	private AdminServiceAsync adminService = GWT.create(AdminService.class);

	private HorizontalPanel baseLayout = new HorizontalPanel();
	private MapWidget mapWidget;

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(baseLayout);

		loadMapApi();
	}

	private void loadMapApi() {
		boolean sensor = true;

		// load all the libs for use in the maps
		ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
		// loadLibraries.add(LoadLibrary.ADSENSE);
		loadLibraries.add(LoadLibrary.DRAWING);
		loadLibraries.add(LoadLibrary.GEOMETRY);
		loadLibraries.add(LoadLibrary.PANORAMIO);
		loadLibraries.add(LoadLibrary.PLACES);
		loadLibraries.add(LoadLibrary.WEATHER);

		Runnable onLoad = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};

		LoadApi.go(onLoad, loadLibraries, sensor);
	}

	private void draw() {
		MapOptions mapOptions = MapOptions.newInstance();
		mapOptions.setZoom(17);
		mapOptions.setCenter(LatLng.newInstance(42.6034719, 0.5238266));

		mapWidget = new MapWidget(mapOptions);
		mapWidget.setSize(String.valueOf(Window.getClientWidth())+"px", String.valueOf(Window.getClientHeight())+"px");

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				mapWidget.setSize(String.valueOf(Window.getClientWidth())+"px", String.valueOf(Window.getClientHeight())+"px");
			}
		});
		baseLayout.add(mapWidget);
		
		adminService.getServices(new AsyncCallback<ServiceDTO[]>() {
			
			@Override
			public void onSuccess(ServiceDTO[] result) {
				for(int i=0; i<result.length; i++) {
					try {
						final ServiceDTO service = result[i];
						MarkerOptions markerOptions = MarkerOptions.newInstance();
						markerOptions.setPosition(LatLng.newInstance(service.getLat(), service.getLng()));
						markerOptions.setTitle(service.getName());
						Size size = Size.newInstance(20, 20);
						MarkerImage icon = MarkerImage.newInstance("http://ottoextremeandroid.appspot.com/img/bus.png", null, null, null, size);
						markerOptions.setIcon(icon);
						final Marker marker = Marker.newInstance(markerOptions);
						marker.setMap(mapWidget);
						marker.setClickable(true);
						marker.addClickHandler(new ClickMapHandler() {
							@Override
							public void onEvent(ClickMapEvent event) {
								Window.alert("Ruta: "+service.getName());
							}
						});
					} catch(Exception e) {
					}
				}

			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}
		});
	}
}