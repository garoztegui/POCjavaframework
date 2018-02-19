package com.proquest.ipa.automation.framework.tools.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class EntityMapper {


    public static final Logger LOG = LoggerFactory.getLogger(EntityMapper.class);

    public static <T> T mapJasonEntity(HttpEntity responseEntity, TypeReference<T> type) {
        try {
            String responseBody = EntityUtils.toString(responseEntity);
            return JsonUtils.getInstance().deserialize(responseBody, type);

        } catch (IOException e) {
            LOG.error("Error while converting entity response to type.", e);
        }

        return null;
    }


}
