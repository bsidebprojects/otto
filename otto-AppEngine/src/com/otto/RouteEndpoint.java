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
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

@Api(name = "routeendpoint", namespace = @ApiNamespace(ownerDomain = "otto.com", ownerName = "otto.com", packagePath = ""))
public class RouteEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRoute")
	public CollectionResponse<Route> listRoute(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Route> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Route.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Route>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Route obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Route> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getRoute")
	public Route getRoute(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Route route = null;
		try {
			route = mgr.getObjectById(Route.class, id);
		} finally {
			mgr.close();
		}
		return route;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param route the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRoute")
	public Route insertRoute(Route route) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (route.getId() != null && containsRoute(route)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(route);
		} finally {
			mgr.close();
		}
		return route;
	}

	@ApiMethod(name = "addStop", path="addStop", httpMethod = HttpMethod.POST)
	public Route addStop(@Named("id") Long id, @Named("stop") String stop) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Route route = mgr.getObjectById(Route.class, id);
			route.addStop(stop);
			mgr.makePersistent(route);
			
			return route;
		} finally {
			mgr.close();
		}
	}
	
	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param route the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRoute")
	public Route updateRoute(Route route) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsRoute(route)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(route);
		} finally {
			mgr.close();
		}
		return route;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRoute")
	public void removeRoute(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Route route = mgr.getObjectById(Route.class, id);
			mgr.deletePersistent(route);
		} finally {
			mgr.close();
		}
	}

	private boolean containsRoute(Route route) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Route.class, route.getId());
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
