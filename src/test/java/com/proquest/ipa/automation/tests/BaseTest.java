package com.proquest.ipa.automation.tests;


import com.proquest.ipa.automation.framework.BaseApi;


import com.proquest.ipa.automation.framework.authentication.AuthManager;

import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;

import com.proquest.ipa.automation.framework.exceptions.RestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


@Listeners({com.proquest.ipa.automation.tests.Configuration.class})
@ContextConfiguration("classpath:application-context.xml")
public class BaseTest extends AbstractTestNGSpringContextTests {

    public static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    public static ResourceBundle testRun;

    private static Random rnd = new Random();
    private static String numbers = "0123456789";
    private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static {
        testRun = ResourceBundle.getBundle("testrun");
    }

    private BaseApi baseApi;




    private void requestAuthToken() {

        if (!TestEnvironment.isMustAuthenticate()) {
            AuthManager.getInstance().setDummyAuthentication();
            return;
        }

        if (!AuthManager.getInstance().getAuthInfo().isAuthenticated()) {
            AuthManager.getInstance().doAuthenticate();
            AuthManager.getInstance().heartBeat();
        } else {
            AuthManager.getInstance().heartBeat();
            if (AuthManager.getInstance().getAuthInfo().getLastActivity() == null
                    || AuthManager.getInstance().getAuthInfo().getLastActivity().getTs() == null) {
                AuthManager.getInstance().doAuthenticate();
            }
        }

    }

    @BeforeClass
    public void initializeClass() {
//        AuthManager.getInstance().doAuthenticate();
    }

    @BeforeSuite(alwaysRun = true)
    public void initialize() throws URISyntaxException, InterruptedException, IOException, RestException {

        //request token
        /*requestAuthToken();
        if (AuthManager.getInstance().getAuthInfo().isAuthenticated()) {
            LOG.info("User authenticated. Token: {}"
                    , AuthManager.getInstance().getAuthInfo().getLastActivity().getId());
            this.baseApi = BaseApi.getInstance();
        }*/
    }

    @AfterSuite(alwaysRun = true)
    public void fullCleanup() throws RestException, URISyntaxException {


    }

    protected BaseApi baseApi() throws URISyntaxException {
        return BaseApi.getInstance();
    }

}