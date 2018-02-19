package com.proquest.ipa.automation.framework.tools.utils;


import com.proquest.ipa.automation.framework.authentication.AuthManager;
import com.proquest.ipa.automation.framework.tools.requests.APIRequest;
import com.proquest.ipa.automation.framework.exceptions.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;


public class HttpUtils {

    public static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static HttpUtils instance = null;

    public HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    public static void processExceptionResponse(HttpResponse response) throws RestException {
        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                return;
            case 400:
                throw new InvalidURL(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            case 403:
                throw new PermissionsException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            case 404:
                throw new NotFoundException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            case 409:
                throw new ConflictException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            case 412:
                throw new PreconditionException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            default:
                throw new RestException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
        }
    }

    public void addJsonBodyToRequest(HttpEntityEnclosingRequestBase request, String jsonInput) {
        try {
            StringEntity jsonBody = new StringEntity(jsonInput);
            jsonBody.setContentType("application/json");
            request.setEntity(jsonBody);
        } catch (UnsupportedEncodingException e) {
            LOG.error("Error adding json body to request", e);
        }
    }

    public HttpPost createUrlEncodedPostRequest(String url, List<NameValuePair> nameValueParams) {
        HttpPost request = new HttpPost(url);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValueParams);
            request.setEntity(entity);
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }

        return request;

    }

    public HttpResponse getHttpResponse(APIRequest apiRequest) {
        return getHttpResponse(apiRequest, false);
    }

    public HttpResponse getHttpResponse(APIRequest apiRequest, boolean authenticate) {
        LOG.info("Request: {} {}", apiRequest.getMethod(), apiRequest.getFullUrl());
        try {

            HttpRequestBase requestBase = createRequest(apiRequest, authenticate);
            HttpResponse response = getResponse(requestBase);
            LOG.info("Response: {}-{}.", response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase());
            return response;
        } catch (Exception e) {
            LOG.error("Error executing request");
        }
        return null;
    }

    public HttpRequestBase createRequest(APIRequest apiRequest, boolean authenticate) {

       // if (AuthManager.getInstance().mustIncludeAuth() && authenticate) {
        //    apiRequest.applyAuthentication();
        //}

        return getRequestWithHeaders(apiRequest);
    }

    public HttpRequestBase getRequestWithHeaders(APIRequest apiRequest) {

        HttpRequestBase request = getRequest(apiRequest);

        for (String key : apiRequest.getHeaders().keySet()) {
            request.addHeader(key, apiRequest.getHeaders().get(key));
        }

        return request;
    }

    private HttpRequestBase getRequest(APIRequest apiRequest) {
        switch (apiRequest.getMethod()) {
            case POST:
                HttpPost post = new HttpPost(apiRequest.getFullUrl());
                if (StringUtils.isNotBlank(apiRequest.getPayload())) {
                    addJsonBodyToRequest(post, apiRequest.getPayload());
                }
                return post;
            case PUT:
                HttpPut put = new HttpPut(apiRequest.getFullUrl());
                if (StringUtils.isNotBlank(apiRequest.getPayload())) {
                    addJsonBodyToRequest(put, apiRequest.getPayload());
                }
                return put;
            case DELETE:
                HttpDeleteWithBody delete = new HttpDeleteWithBody(apiRequest.getFullUrl());
                if (StringUtils.isNotBlank(apiRequest.getPayload())) {
                    addJsonBodyToRequest(delete, apiRequest.getPayload());
                }
                return delete;
            case GET:
                return new HttpGet(apiRequest.getFullUrl());
            default:
        }
        return null;
    }

    public HttpGet createUrlEncodedGetRequest(String url, List<NameValuePair> nameValueParams) {
        HttpGet request = new HttpGet(url);
        try {
            URI uri = new URIBuilder(request.getURI()).addParameters(nameValueParams).build();
            request.setURI(uri);
            request.addHeader("Content-Type", "application/json");
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

        return request;

    }

    public HttpResponse getResponse(HttpRequestBase request) {
        try {
            SSLContextBuilder sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslConnection = new SSLConnectionSocketFactory(sslContext.build(), new AllowAllHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnection).build();
            return httpClient.execute(request);
        } catch (Exception e) {
            LOG.error("Error obtaining response from server.", e);
        }

        return null;

    }

    public String encodeURIComponent(String text) {
        String result;

        try {
            result = URLEncoder.encode(text, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = text;
        }

        return result;
    }


}
