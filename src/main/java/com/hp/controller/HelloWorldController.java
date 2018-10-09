package com.hp.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hp.model.Account;
import com.hp.service.UserService;
import com.mongodb.MongoClient; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
public class HelloWorldController {
	MongoClient mongo = new MongoClient( "localhost" , 27017 ); 
	@Resource(name="userService")
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes= {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Account showMessage(@RequestBody Account account) {
		System.out.println("Credentials ::" + account.getUserName());

		Account acc = userService.getLoggedInUser(account);
		//List<Account> lists= userService.getAll();
		// System.out.println("Credentials ::" + lists); 


		return acc;
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST,consumes= {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public boolean getFormDetails(@RequestBody Account account) {
		System.out.println("Credentials ::" + account.getUserName());

		boolean outputFlag = userService.add(account);
		//List<Account> lists= userService.getAll();
		// System.out.println("Credentials ::" + lists); 


		return outputFlag;
	}
	/*@RequestMapping(value = "/accounts", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	    public List<Account> getAccounts() {

		 List<Account> studentList = new ArrayList<Account>();
		 studentList.add(new Account("345", "Peter J") );  
				  studentList.add(new Account("123", "John R"));  

	        return studentList;
	    }*/
}

