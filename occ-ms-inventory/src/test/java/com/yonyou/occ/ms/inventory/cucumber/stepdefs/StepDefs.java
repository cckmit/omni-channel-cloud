package com.yonyou.occ.ms.inventory.cucumber.stepdefs;

import com.yonyou.occ.ms.inventory.OccMsInventoryApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = OccMsInventoryApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
