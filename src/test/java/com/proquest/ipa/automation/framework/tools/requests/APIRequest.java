package com.proquest.ipa.automation.framework.tools.requests;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.proquest.ipa.automation.framework.authentication.AuthInfo;
import com.proquest.ipa.automation.framework.authentication.AuthManager;
import com.proquest.ipa.automation.framework.tools.utils.HttpUtils;
import com.proquest.ipa.automation.framework.tools.utils.JsonUtils;
import com.proquest.ipa.automation.framework.exceptions.RestException;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;



/**
 * Use this class to encapsulate requests calls to the API.
 * (Instead of having to create a new method each time)
 */
public class APIRequest {


    public static final Logger LOG = LoggerFactory.getLogger(APIRequest.class);
    public String payload;
    protected final Map<String, String> headers = new HashMap();
    protected final Map<String, String> queryString = new HashMap();
    protected AuthenticationType authentication = AuthenticationType.HEADERS;
    private String url;
    private HttpMethod method;

    public APIRequest() {
        this.setAuthentication(AuthManager.getDefaultAuthType());
        headers.put("Accept", ContentType.APPLICATION_JSON.getMimeType());
        headers.put("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
    }

    public static APIRequest with(HttpMethod method, String url) {
        return new APIRequest()
                .setMethod(method).setUrl(url);
    }



    public static APIRequest with(HttpMethod method, String url, Object payload) {
        return new APIRequest()
                .setMethod(method).setUrl(url)
                .setPayload(JsonUtils.getInstance().serialize(payload));
    }

    public static APIRequest with(HttpMethod method, String url, String payload) {
        return new APIRequest()
                .setMethod(method).setUrl(url)
                .setPayload(payload);
    }

    public static APIRequest with(String url) {
        return new APIRequest()
                .setMethod(HttpMethod.GET).setUrl(url);
    }

    public void setAuthentication(AuthenticationType authentication) {
        this.authentication = authentication;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public String getUrl() {
        return url;
    }

    public APIRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public APIRequest setPayload(String jsonPayload) {
        this.payload = jsonPayload;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public APIRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public APIRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public String getFullUrl() {

        StringBuilder fullUrl = new StringBuilder(this.getUrl());
        boolean first = true;
        for (String key : this.queryString.keySet()) {
            if (first) {
                fullUrl.append("?");
                first = false;
            } else {
                fullUrl.append("&");
            }
            fullUrl.append(String.format("%s=%s", key, this.queryString.get(key)));
        }
        return fullUrl.toString();
    }

    public APIRequest addParam(String key, String value) {
        if (!queryString.containsKey(key) && !value.isEmpty()) {
            try {
                queryString.put(key, URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOG.error("Error encoding value {}", value, e);
            }
        }
        return this;
    }

    public void execute() throws RestException {

        HttpResponse response = HttpUtils.getInstance().getHttpResponse(this);

        HttpUtils.getInstance().processExceptionResponse(response);
    }

    public void applyAuthentication() {
        applyAuthentication(AuthManager.getInstance().getAuthInfo());
    }

    public void applyAuthentication(AuthInfo authInfo) {
        switch (authentication) {
            case HEADERS:
                //headers.put("libraryCode", authInfo.getLibraryCode());
                //headers.put("apiKey", authInfo.getApikey());
                headers.put("authToken", authInfo.getLastActivity().getId());
                break;
            case QUERYSTRING:
                //queryString.put("libraryCode", authInfo.getLibraryCode());
                //queryString.put("apiKey", authInfo.getApikey());
                queryString.put("authToken", authInfo.getLastActivity().getId());
                break;
            default:
        }
    }

    public enum AuthenticationType {
        NONE,
        HEADERS,
        QUERYSTRING
    }
}
