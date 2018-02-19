package com.proquest.ipa.automation.framework.authentication;


import com.fasterxml.jackson.core.type.TypeReference;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.proquest.ipa.automation.framework.actions.BaseAction;
import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;
import com.proquest.ipa.automation.framework.tools.requests.APIRequest;
import com.proquest.ipa.automation.framework.tools.utils.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AuthManager extends BaseAction {


    public static final Logger LOG = LoggerFactory.getLogger(AuthManager.class);

    private static APIRequest.AuthenticationType defaultAuthType = APIRequest.AuthenticationType.QUERYSTRING;
    private static AuthManager instance = null;

    private final AuthInfo authInfo = new AuthInfo();

    private final String creationEndpoint
            = TestEnvironment.getEnvProperty("auth.url") + "authToken/create";

    private final String deleteTokenEndpoint
            = TestEnvironment.getEnvProperty("auth.url") + "authToken/";

    private final String userActivityEndpoint
            = TestEnvironment.getEnvProperty("auth.url") + "authToken/userActivity";

    private final String dummyToken = TestEnvironment.getEnvProperty("auth.dummyToken");


    private AuthManager() {

    }

    public static AuthManager getInstance() {
        if (instance == null) instance = new AuthManager();
        return instance;
    }

    public static APIRequest.AuthenticationType getDefaultAuthType() {
        return defaultAuthType;
    }

    public static void setDefaultAuthType(APIRequest.AuthenticationType defaultAuthType) {
        AuthManager.defaultAuthType = defaultAuthType;
    }

    public boolean mustIncludeAuth() {
        return authInfo.isAuthenticated();
    }

    private AuthAPILoginInfo getLoginInfo() {
        return new AuthAPILoginInfo();
    }

    public void setDummyAuthentication() {
        authInfo.setAuthenticated(true);
        authInfo.setLastActivity(AuthActivity.with(dummyToken));
        LOG.info("Setting Dummy Authentication.");
    }

    private AuthAPILoginInfo getLoginInfo(String email, String password) {

        AuthAPILoginInfo authAPILoginInfo = getLoginInfo();

       // authAPILoginInfo.setEmail(email);
     //   authAPILoginInfo.setPassword(password);

        return authAPILoginInfo;
    }

    public void doAuthenticate() {

        LOG.info("Request: POST {}", creationEndpoint);
        try {

            HttpRequestBase request = httpUtils().createRequest(APIRequest.with(HttpMethod.POST,
                    creationEndpoint, getLoginInfo()), true);
            request.addHeader("Authorization", String.format("Token %s", TestEnvironment.getEnvProperty("auth.apikey")));

            HttpResponse response = httpUtils().getResponse(request);
            if (response == null) {
                return;
            }
            String json = EntityUtils.toString(response.getEntity());
            AuthAPIResponse apiResponse = JsonUtils.getInstance().deserialize(json, new TypeReference<AuthAPIResponse>() {
            });
            if (apiResponse != null
                    && apiResponse.getStatus() != null
                    && apiResponse.getStatus().isSuccess()) {
                authInfo.setAuthenticated(true);
                authInfo.setLastActivity(AuthActivity.with(apiResponse.getToken().getId()));
            }

        } catch (Exception e) {
            LOG.error("Error authenticating against IAuth", e);
        }
    }

    public void deleteToken(String token) {

        LOG.info("Request: DELETE {}", deleteTokenEndpoint + token);
        try {

            HttpRequestBase request = httpUtils().createRequest(APIRequest.with(HttpMethod.DELETE,
                    deleteTokenEndpoint + token, getLoginInfo()), false);
            request.addHeader("Authorization", String.format("Token %s", TestEnvironment.getEnvProperty("auth.apikey")));

            HttpResponse response = httpUtils().getResponse(request);
            if (response == null) {
                return;
            }
        } catch (Exception e) {
            LOG.error("Error deleting token " + token, e);
        }
    }

    public void heartBeat() {

        LOG.info("Request: PUT {}", userActivityEndpoint);
        try {


            HttpPut request = new HttpPut(userActivityEndpoint);
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", String.format("Token %s", TestEnvironment.getEnvProperty("auth.apikey")));
            httpUtils().addJsonBodyToRequest(request, JsonUtils.getInstance().serialize(AuthAPIHeartbeatInfo
                    .with(authInfo.getLastActivity())));

            HttpResponse response = httpUtils().getResponse(request);
            if (response == null) {
                return;
            }

            String json = EntityUtils.toString(response.getEntity());
            AuthAPIHeartbeatInfo apiResponse = JsonUtils.getInstance().deserialize(json, new TypeReference<AuthAPIHeartbeatInfo>() {
            });

            if (apiResponse != null) {
                authInfo.setLastActivity(apiResponse.getLastActivity().get(0));
            }

        } catch (Exception e) {
            LOG.error("Error on HeartBeat", e);
        }
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public AuthInfo getAuthInfoForUser(String email, String password) {

        LOG.info("Request: POST {}", creationEndpoint);
        try {

            AuthInfo authInfo = new AuthInfo();

            HttpRequestBase request =
                    httpUtils().getRequestWithHeaders(APIRequest.with(HttpMethod.POST, creationEndpoint, getLoginInfo(email, password)));

            request.addHeader("Authorization", String.format("Token %s", TestEnvironment.getEnvProperty("auth.apikey")));

            HttpResponse response = httpUtils().getResponse(request);
            if (response == null) {
                return null;
            }
            String json = EntityUtils.toString(response.getEntity());
            AuthAPIResponse apiResponse = JsonUtils.getInstance().deserialize(json, new TypeReference<AuthAPIResponse>() {
            });
            if (apiResponse != null
                    && apiResponse.getStatus() != null
                    && apiResponse.getStatus().isSuccess()) {
                authInfo.setAuthenticated(true);
                authInfo.setLastActivity(AuthActivity.with(apiResponse.getToken().getId()));
            }
            return authInfo;
        } catch (Exception e) {
            LOG.error("Error authenticating against IAuth", e);
            return null;
        }
    }


}
