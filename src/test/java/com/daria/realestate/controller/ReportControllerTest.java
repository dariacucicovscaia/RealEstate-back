package com.daria.realestate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ReportControllerTest.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void downloadFromDrive() {

    }

    @Test
    public void generateLocalReport() {
    }

    @Test
    public void uploadToDrive() {
    }
}