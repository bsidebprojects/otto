package com.otto;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@Api(name = "serviceendpoint", namespace = @ApiNamespace(ownerDomain = "otto.com", ownerName = "otto.com", packagePath = ""))
public class ServiceEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listService")
	public CollectionResponse<Service> listService(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Service> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Service.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Service>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Service obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Service> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getService")
	public Service getService(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Service service = null;
		try {
			service = mgr.getObjectById(Service.class, id);
		} finally {
			mgr.close();
		}
		return service;
	}
	
	@ApiMethod(name = "getServiceByCode", path="serviceByCode", httpMethod = HttpMethod.GET)
	public Service getServiceByCode(@Named("code") String code) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Query query = mgr.newQuery(Service.class);
			query.setFilter(" securityCode == "+ code);
			query.setOrdering("start desc");
			
			List<Service> result = (List<Service>) query.execute();
			
			if(result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} finally {
			mgr.close();
		}
	}
	
	@ApiMethod(name = "addTrack", path="addTrack", httpMethod = HttpMethod.POST)
	public Service addTrack(@Named("code") String code, @Named("track") String track) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Query query = mgr.newQuery(Service.class);
			query.setFilter(" securityCode == "+ code);
//			query.setOrdering("start desc");
			
			List<Service> result = (List<Service>) query.execute();
			
			if(result.size() > 0) {
				result.get(0).addTrack(track);
				
				mgr.makePersistent(result.get(0));
				
				return result.get(0);
			} else {
				return null;
			}
		} finally {
			mgr.close();
		}
	}

	
	@ApiMethod(name = "addMessage", path="addMessage", httpMethod = HttpMethod.POST)
	public Service addMessage(@Named("code") String code, @Named("message") String message) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Query query = mgr.newQuery(Service.class);
			query.setFilter(" securityCode == "+ code);
//			query.setOrdering("start desc");
			
			List<Service> result = (List<Service>) query.execute();
			
			if(result.size() > 0) {
				result.get(0).addMessage(message);
				
				mgr.makePersistent(result.get(0));
				
				return result.get(0);
			} else {
				return null;
			}
		} finally {
			mgr.close();
		}
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param service the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertService")
	public Service insertService(Service service) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (service.getId() != null && containsService(service)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(service);
		} finally {
			mgr.close();
		}
		return service;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param service the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateService")
	public Service updateService(Service service) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsService(service)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(service);
		} finally {
			mgr.close();
		}
		return service;
	}
	

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeService")
	public void removeService(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Service service = mgr.getObjectById(Service.class, id);
			mgr.deletePersistent(service);
		} finally {
			mgr.close();
		}
	}

	private boolean containsService(Service service) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Service.class, service.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
