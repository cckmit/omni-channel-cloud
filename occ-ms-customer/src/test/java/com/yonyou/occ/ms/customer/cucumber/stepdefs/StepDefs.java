package com.yonyou.occ.ms.customer.cucumber.stepdefs;

import com.yonyou.occ.ms.customer.OccMsCustomerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = OccMsCustomerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
