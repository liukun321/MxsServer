package com.mxs.mxserver.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mxs.mxsserver.MxsServerApplication;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class CoffeeMachineControllerTest {
	
    @Test  
    public void contextLoads() {  
    }  
  
    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。    
        
    @Autowired    
    private WebApplicationContext wac; // 注入WebApplicationContext    
    
//	    @Autowired    
//	    private MockHttpSession session;// 注入模拟的http session    
//	        
//	    @Autowired    
//	    private MockHttpServletRequest request;// 注入模拟的http request\    
    
    @Before // 在测试开始前初始化工作    
    public void setup() {    
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    
    }    
    
    @Test    
    public void testQ1() throws Exception {    
        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.get("/order/sumprice?status=1&payMethod=1"))
//        		.andExpect(Mock.status().isOk())
        		.andReturn();
        System.out.println(result.getResponse().getContentAsString());    
    }   

}
