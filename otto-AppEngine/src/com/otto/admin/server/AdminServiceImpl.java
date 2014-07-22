package com.otto.admin.server;

import java.util.List;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.otto.Service;
import com.otto.ServiceEndpoint;
import com.otto.admin.client.AdminService;
import com.otto.admin.shared.ServiceDTO;

public class AdminServiceImpl extends RemoteServiceServlet implements AdminService {

	private static final long serialVersionUID = -4893823709963425676L;

	@Override
	public ServiceDTO[] getServices() {
		ServiceEndpoint endpoint = new ServiceEndpoint();
		CollectionResponse<Service> listService = endpoint.listService(null, null);
		ServiceDTO[] services = new ServiceDTO[listService.getItems().size()];
		int i=0;
		for(Service service : listService.getItems()) {
			ServiceDTO serviceDTO = new ServiceDTO();
			try {
				serviceDTO.setName(service.getRouteName());
				List<String> track = service.getTrack();
				String last = track.get(track.size()-1);
				String[] fields = last.split("@");
				serviceDTO.setLat(Double.parseDouble(fields[0]));
				serviceDTO.setLng(Double.parseDouble(fields[1]));
			} catch(Exception e) {
			}
			services[i] = serviceDTO;
			i++;
		}
		return services;
	}

}
