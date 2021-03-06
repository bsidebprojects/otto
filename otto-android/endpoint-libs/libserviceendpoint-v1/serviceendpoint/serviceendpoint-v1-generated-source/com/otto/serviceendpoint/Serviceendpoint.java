/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-09 17:08:39 UTC)
 * on 2014-07-20 at 04:01:25 UTC 
 * Modify at your own risk.
 */

package com.otto.serviceendpoint;

/**
 * Service definition for Serviceendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link ServiceendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Serviceendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the serviceendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://ottoextremeandroid.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "serviceendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Serviceendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Serviceendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "addMessage".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link AddMessage#execute()} method to invoke the remote operation.
   *
   * @param code
   * @param message
   * @return the request
   */
  public AddMessage addMessage(java.lang.String code, java.lang.String message) throws java.io.IOException {
    AddMessage result = new AddMessage(code, message);
    initialize(result);
    return result;
  }

  public class AddMessage extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "addMessage";

    /**
     * Create a request for the method "addMessage".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link AddMessage#execute()} method to invoke the remote
     * operation. <p> {@link
     * AddMessage#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param code
     * @param message
     * @since 1.13
     */
    protected AddMessage(java.lang.String code, java.lang.String message) {
      super(Serviceendpoint.this, "POST", REST_PATH, null, com.otto.serviceendpoint.model.Service.class);
      this.code = com.google.api.client.util.Preconditions.checkNotNull(code, "Required parameter code must be specified.");
      this.message = com.google.api.client.util.Preconditions.checkNotNull(message, "Required parameter message must be specified.");
    }

    @Override
    public AddMessage setAlt(java.lang.String alt) {
      return (AddMessage) super.setAlt(alt);
    }

    @Override
    public AddMessage setFields(java.lang.String fields) {
      return (AddMessage) super.setFields(fields);
    }

    @Override
    public AddMessage setKey(java.lang.String key) {
      return (AddMessage) super.setKey(key);
    }

    @Override
    public AddMessage setOauthToken(java.lang.String oauthToken) {
      return (AddMessage) super.setOauthToken(oauthToken);
    }

    @Override
    public AddMessage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddMessage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddMessage setQuotaUser(java.lang.String quotaUser) {
      return (AddMessage) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddMessage setUserIp(java.lang.String userIp) {
      return (AddMessage) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String code;

    /**

     */
    public java.lang.String getCode() {
      return code;
    }

    public AddMessage setCode(java.lang.String code) {
      this.code = code;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String message;

    /**

     */
    public java.lang.String getMessage() {
      return message;
    }

    public AddMessage setMessage(java.lang.String message) {
      this.message = message;
      return this;
    }

    @Override
    public AddMessage set(String parameterName, Object value) {
      return (AddMessage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "addTrack".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link AddTrack#execute()} method to invoke the remote operation.
   *
   * @param code
   * @param track
   * @return the request
   */
  public AddTrack addTrack(java.lang.String code, java.lang.String track) throws java.io.IOException {
    AddTrack result = new AddTrack(code, track);
    initialize(result);
    return result;
  }

  public class AddTrack extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "addTrack";

    /**
     * Create a request for the method "addTrack".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link AddTrack#execute()} method to invoke the remote operation.
     * <p> {@link
     * AddTrack#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param code
     * @param track
     * @since 1.13
     */
    protected AddTrack(java.lang.String code, java.lang.String track) {
      super(Serviceendpoint.this, "POST", REST_PATH, null, com.otto.serviceendpoint.model.Service.class);
      this.code = com.google.api.client.util.Preconditions.checkNotNull(code, "Required parameter code must be specified.");
      this.track = com.google.api.client.util.Preconditions.checkNotNull(track, "Required parameter track must be specified.");
    }

    @Override
    public AddTrack setAlt(java.lang.String alt) {
      return (AddTrack) super.setAlt(alt);
    }

    @Override
    public AddTrack setFields(java.lang.String fields) {
      return (AddTrack) super.setFields(fields);
    }

    @Override
    public AddTrack setKey(java.lang.String key) {
      return (AddTrack) super.setKey(key);
    }

    @Override
    public AddTrack setOauthToken(java.lang.String oauthToken) {
      return (AddTrack) super.setOauthToken(oauthToken);
    }

    @Override
    public AddTrack setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddTrack) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddTrack setQuotaUser(java.lang.String quotaUser) {
      return (AddTrack) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddTrack setUserIp(java.lang.String userIp) {
      return (AddTrack) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String code;

    /**

     */
    public java.lang.String getCode() {
      return code;
    }

    public AddTrack setCode(java.lang.String code) {
      this.code = code;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String track;

    /**

     */
    public java.lang.String getTrack() {
      return track;
    }

    public AddTrack setTrack(java.lang.String track) {
      this.track = track;
      return this;
    }

    @Override
    public AddTrack set(String parameterName, Object value) {
      return (AddTrack) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getService".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link GetService#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetService getService(java.lang.Long id) throws java.io.IOException {
    GetService result = new GetService(id);
    initialize(result);
    return result;
  }

  public class GetService extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "service/{id}";

    /**
     * Create a request for the method "getService".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link GetService#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetService#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetService(java.lang.Long id) {
      super(Serviceendpoint.this, "GET", REST_PATH, null, com.otto.serviceendpoint.model.Service.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetService setAlt(java.lang.String alt) {
      return (GetService) super.setAlt(alt);
    }

    @Override
    public GetService setFields(java.lang.String fields) {
      return (GetService) super.setFields(fields);
    }

    @Override
    public GetService setKey(java.lang.String key) {
      return (GetService) super.setKey(key);
    }

    @Override
    public GetService setOauthToken(java.lang.String oauthToken) {
      return (GetService) super.setOauthToken(oauthToken);
    }

    @Override
    public GetService setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetService) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetService setQuotaUser(java.lang.String quotaUser) {
      return (GetService) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetService setUserIp(java.lang.String userIp) {
      return (GetService) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetService setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetService set(String parameterName, Object value) {
      return (GetService) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getServiceByCode".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link GetServiceByCode#execute()} method to invoke the remote
   * operation.
   *
   * @param code
   * @return the request
   */
  public GetServiceByCode getServiceByCode(java.lang.String code) throws java.io.IOException {
    GetServiceByCode result = new GetServiceByCode(code);
    initialize(result);
    return result;
  }

  public class GetServiceByCode extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "serviceByCode";

    /**
     * Create a request for the method "getServiceByCode".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link GetServiceByCode#execute()} method to invoke the remote
     * operation. <p> {@link GetServiceByCode#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param code
     * @since 1.13
     */
    protected GetServiceByCode(java.lang.String code) {
      super(Serviceendpoint.this, "GET", REST_PATH, null, com.otto.serviceendpoint.model.Service.class);
      this.code = com.google.api.client.util.Preconditions.checkNotNull(code, "Required parameter code must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetServiceByCode setAlt(java.lang.String alt) {
      return (GetServiceByCode) super.setAlt(alt);
    }

    @Override
    public GetServiceByCode setFields(java.lang.String fields) {
      return (GetServiceByCode) super.setFields(fields);
    }

    @Override
    public GetServiceByCode setKey(java.lang.String key) {
      return (GetServiceByCode) super.setKey(key);
    }

    @Override
    public GetServiceByCode setOauthToken(java.lang.String oauthToken) {
      return (GetServiceByCode) super.setOauthToken(oauthToken);
    }

    @Override
    public GetServiceByCode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetServiceByCode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetServiceByCode setQuotaUser(java.lang.String quotaUser) {
      return (GetServiceByCode) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetServiceByCode setUserIp(java.lang.String userIp) {
      return (GetServiceByCode) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String code;

    /**

     */
    public java.lang.String getCode() {
      return code;
    }

    public GetServiceByCode setCode(java.lang.String code) {
      this.code = code;
      return this;
    }

    @Override
    public GetServiceByCode set(String parameterName, Object value) {
      return (GetServiceByCode) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertService".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link InsertService#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.otto.serviceendpoint.model.Service}
   * @return the request
   */
  public InsertService insertService(com.otto.serviceendpoint.model.Service content) throws java.io.IOException {
    InsertService result = new InsertService(content);
    initialize(result);
    return result;
  }

  public class InsertService extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "service";

    /**
     * Create a request for the method "insertService".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link InsertService#execute()} method to invoke the remote
     * operation. <p> {@link InsertService#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.otto.serviceendpoint.model.Service}
     * @since 1.13
     */
    protected InsertService(com.otto.serviceendpoint.model.Service content) {
      super(Serviceendpoint.this, "POST", REST_PATH, content, com.otto.serviceendpoint.model.Service.class);
    }

    @Override
    public InsertService setAlt(java.lang.String alt) {
      return (InsertService) super.setAlt(alt);
    }

    @Override
    public InsertService setFields(java.lang.String fields) {
      return (InsertService) super.setFields(fields);
    }

    @Override
    public InsertService setKey(java.lang.String key) {
      return (InsertService) super.setKey(key);
    }

    @Override
    public InsertService setOauthToken(java.lang.String oauthToken) {
      return (InsertService) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertService setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertService) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertService setQuotaUser(java.lang.String quotaUser) {
      return (InsertService) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertService setUserIp(java.lang.String userIp) {
      return (InsertService) super.setUserIp(userIp);
    }

    @Override
    public InsertService set(String parameterName, Object value) {
      return (InsertService) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listService".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link ListService#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListService listService() throws java.io.IOException {
    ListService result = new ListService();
    initialize(result);
    return result;
  }

  public class ListService extends ServiceendpointRequest<com.otto.serviceendpoint.model.CollectionResponseService> {

    private static final String REST_PATH = "service";

    /**
     * Create a request for the method "listService".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link ListService#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListService#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListService() {
      super(Serviceendpoint.this, "GET", REST_PATH, null, com.otto.serviceendpoint.model.CollectionResponseService.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListService setAlt(java.lang.String alt) {
      return (ListService) super.setAlt(alt);
    }

    @Override
    public ListService setFields(java.lang.String fields) {
      return (ListService) super.setFields(fields);
    }

    @Override
    public ListService setKey(java.lang.String key) {
      return (ListService) super.setKey(key);
    }

    @Override
    public ListService setOauthToken(java.lang.String oauthToken) {
      return (ListService) super.setOauthToken(oauthToken);
    }

    @Override
    public ListService setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListService) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListService setQuotaUser(java.lang.String quotaUser) {
      return (ListService) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListService setUserIp(java.lang.String userIp) {
      return (ListService) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListService setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListService setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListService set(String parameterName, Object value) {
      return (ListService) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeService".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link RemoveService#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveService removeService(java.lang.Long id) throws java.io.IOException {
    RemoveService result = new RemoveService(id);
    initialize(result);
    return result;
  }

  public class RemoveService extends ServiceendpointRequest<Void> {

    private static final String REST_PATH = "service/{id}";

    /**
     * Create a request for the method "removeService".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link RemoveService#execute()} method to invoke the remote
     * operation. <p> {@link RemoveService#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveService(java.lang.Long id) {
      super(Serviceendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveService setAlt(java.lang.String alt) {
      return (RemoveService) super.setAlt(alt);
    }

    @Override
    public RemoveService setFields(java.lang.String fields) {
      return (RemoveService) super.setFields(fields);
    }

    @Override
    public RemoveService setKey(java.lang.String key) {
      return (RemoveService) super.setKey(key);
    }

    @Override
    public RemoveService setOauthToken(java.lang.String oauthToken) {
      return (RemoveService) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveService setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveService) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveService setQuotaUser(java.lang.String quotaUser) {
      return (RemoveService) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveService setUserIp(java.lang.String userIp) {
      return (RemoveService) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveService setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveService set(String parameterName, Object value) {
      return (RemoveService) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateService".
   *
   * This request holds the parameters needed by the serviceendpoint server.  After setting any
   * optional parameters, call the {@link UpdateService#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.otto.serviceendpoint.model.Service}
   * @return the request
   */
  public UpdateService updateService(com.otto.serviceendpoint.model.Service content) throws java.io.IOException {
    UpdateService result = new UpdateService(content);
    initialize(result);
    return result;
  }

  public class UpdateService extends ServiceendpointRequest<com.otto.serviceendpoint.model.Service> {

    private static final String REST_PATH = "service";

    /**
     * Create a request for the method "updateService".
     *
     * This request holds the parameters needed by the the serviceendpoint server.  After setting any
     * optional parameters, call the {@link UpdateService#execute()} method to invoke the remote
     * operation. <p> {@link UpdateService#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.otto.serviceendpoint.model.Service}
     * @since 1.13
     */
    protected UpdateService(com.otto.serviceendpoint.model.Service content) {
      super(Serviceendpoint.this, "PUT", REST_PATH, content, com.otto.serviceendpoint.model.Service.class);
    }

    @Override
    public UpdateService setAlt(java.lang.String alt) {
      return (UpdateService) super.setAlt(alt);
    }

    @Override
    public UpdateService setFields(java.lang.String fields) {
      return (UpdateService) super.setFields(fields);
    }

    @Override
    public UpdateService setKey(java.lang.String key) {
      return (UpdateService) super.setKey(key);
    }

    @Override
    public UpdateService setOauthToken(java.lang.String oauthToken) {
      return (UpdateService) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateService setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateService) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateService setQuotaUser(java.lang.String quotaUser) {
      return (UpdateService) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateService setUserIp(java.lang.String userIp) {
      return (UpdateService) super.setUserIp(userIp);
    }

    @Override
    public UpdateService set(String parameterName, Object value) {
      return (UpdateService) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Serviceendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Serviceendpoint}. */
    @Override
    public Serviceendpoint build() {
      return new Serviceendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link ServiceendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setServiceendpointRequestInitializer(
        ServiceendpointRequestInitializer serviceendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(serviceendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
