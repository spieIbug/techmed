package com.technosofteam.techmed.cucumber.stepdefs;

import com.technosofteam.techmed.TechmedApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TechmedApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
