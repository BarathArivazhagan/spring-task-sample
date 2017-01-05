package com.barath.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=TestController.class)
public class IssueRequestBinding415UnsupportederrorApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper mapper=new ObjectMapper();

	//@Test
	public void testfails() throws Exception {
		Object request=mapper.readValue(new File("//Users//barath//test.json"), Object.class);
		String requestString=mapper.writeValueAsString(request);
		System.out.println("request "+requestString);
		mockMvc.perform(post("http://localhost:8080/addCompany").content(requestString)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testPasses() throws Exception {
		Object request=mapper.readValue(new File("/Users/barath/Eclipseworkspace/NoSQLWS/issue-requestBinding-415-unsupportederror/src/main/java/com/barath/app/test1.json"), Object.class);
		String requestString=mapper.writeValueAsString(request);
		System.out.println("request "+requestString);
		mockMvc.perform(post("http://localhost:8080/addCompany").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestString)).andExpect(status().isOk()).andDo(print());
	}

}
