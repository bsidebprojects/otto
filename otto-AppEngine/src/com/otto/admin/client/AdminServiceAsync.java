package com.otto.admin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otto.admin.shared.ServiceDTO;

public interface AdminServiceAsync {

	void getServices(AsyncCallback<ServiceDTO[]> callback);

}
