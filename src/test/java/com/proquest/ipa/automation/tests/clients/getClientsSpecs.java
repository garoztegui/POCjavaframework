package com.proquest.ipa.automation.tests.clients;

import com.proquest.ipa.automation.framework.actions.UserActions;
import com.proquest.ipa.automation.framework.testData.ResponseFromApi;


import com.proquest.ipa.automation.tests.BaseTest;

import com.proquest.ipa.automation.framework.exceptions.RestException;
import com.proquest.testrails.listeners.annotations.AddToTestRail;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


@AddToTestRail
public class getClientsSpecs extends BaseTest {


    @Test
    public void getListOfActiveUsers() throws RestException {

        //Given:
        ResponseFromApi response;
        //When:
        response = UserActions.getListOfUsers(true);
        //Then:
        assertFalse(UserActions.foundInactiveUsers(response));


    }


    @Test
    public void getListOfActiveAndInactive() throws RestException {

        //Given:
        ResponseFromApi response;
        //When:
        response = UserActions.getListOfUsers(false);
        //Then:
        assertTrue(UserActions.foundInactiveUsers(response));


    }



}
