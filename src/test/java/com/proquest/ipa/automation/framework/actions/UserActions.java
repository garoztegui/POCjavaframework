package com.proquest.ipa.automation.framework.actions;


import com.fasterxml.jackson.core.type.TypeReference;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.proquest.ipa.automation.framework.exceptions.RestException;
import com.proquest.ipa.automation.framework.testData.ResponseFromApi;
import com.proquest.ipa.automation.framework.tools.requests.APIRequest;
import com.proquest.ipa.automation.framework.tools.utils.EntityMapper;
import com.proquest.ipa.automation.framework.tools.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class UserActions extends BaseAction {

    public static final Logger LOG = LoggerFactory.getLogger(UserActions.class);

    private static HttpResponse getAndProcessResponse(String url) throws RestException {
        return getAndProcessResponse(HttpMethod.GET, url);
    }

    private static HttpResponse getAndProcessResponse(HttpMethod method, String url) throws RestException {
        HttpResponse response = httpUtils().getHttpResponse(APIRequest.with(method, url)
                .addHeader("cookieString", "id=5afc345fe4b095c9bd81dcf9; user_token=38d7103d77f3307769abb85a89571ca3")
                .addHeader("apiversion", "1")
                .addHeader("appid", "me"));
        HttpUtils.getInstance().processExceptionResponse(response);
        return response;
    }

    public static ResponseFromApi getListOfUsers(boolean activeOnly) throws RestException {
        String url = ipaAPI("/user/institutionUsers?start=0&length=1000&activeonly=" + activeOnly);
        HttpResponse response = getAndProcessResponse(url);
        return EntityMapper.mapJasonEntity(response.getEntity(), new TypeReference<ResponseFromApi>() {
        });
    }


    public static boolean foundInactiveUsers(ResponseFromApi response) {

        boolean foundInactiveUser = false;
        for (ResponseFromApi.User user : response.getUsers() ) {
            if ("inactive".equals(user.getStatus())) {
                foundInactiveUser = true;
            }
        }
        return foundInactiveUser;
    }
}
