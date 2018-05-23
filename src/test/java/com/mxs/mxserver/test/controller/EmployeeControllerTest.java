package com.mxs.mxserver.test.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.vo.EmployeeVo;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class EmployeeControllerTest {
	
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
//    	EmployeeVo employee = new EmployeeVo();
//    	employee.setEmployeeCode("mxs12004239");
//    	employee.setEmployeeName("李斯");
//    	employee.setPassword("342121");
//    	Set<String> set = new HashSet();
//		set.add("rr2329unvrnr2rm");
//		set.add("rr2329unvrnr343");
//    	employee.setVenderNames(set);
//    	ObjectMapper mapper = new ObjectMapper();
//    	MultiValueMap<String, String> map = new LinkedMultiValueMap();
//        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/employee/addEmployee")
//        		.contentType(MediaType.APPLICATION_JSON_VALUE)
//        		.content(mapper.writeValueAsString(employee))
//        		.param("employeeCode", "4gr3wr24re")
//        		.param("employeeName", "zhangsan")
//        		.param("password", "12335465"))
//        		.andReturn();
//        System.out.println(result.getResponse().getContentAsString());    
    }   

}
