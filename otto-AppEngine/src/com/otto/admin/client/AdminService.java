package com.otto.admin.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.otto.admin.shared.ServiceDTO;

@RemoteServiceRelativePath("admin")
public interface AdminService extends RemoteService {
	ServiceDTO[] getServices();
}
