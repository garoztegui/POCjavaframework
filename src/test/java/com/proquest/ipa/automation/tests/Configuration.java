package com.proquest.ipa.automation.tests;


import com.proquest.ipa.automation.framework.BaseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * Configuration class to get information about the current suite executing
 */
public class Configuration implements ISuiteListener {

    public static final Logger LOG = LoggerFactory
            .getLogger(Configuration.class);

    @Override
    public void onStart(ISuite iSuite) {
        LOG.info("Starting suite {}.", iSuite.getName());
        BaseApi.getInstance().setCurrentSuiteName(iSuite.getName());

    }

    @Override
    public void onFinish(ISuite iSuite) {
        LOG.info("Terminating suite {}.", iSuite.getName());
    }
}
