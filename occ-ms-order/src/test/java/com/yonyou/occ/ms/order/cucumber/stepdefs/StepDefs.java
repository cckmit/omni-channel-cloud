package com.yonyou.occ.ms.order.cucumber.stepdefs;

import com.yonyou.occ.ms.order.OccMsOrderApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = OccMsOrderApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
