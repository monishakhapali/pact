package com.rahulshettyacademy.Courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//We provide the provider which is same as the provider name in contract
@Provider("CoursesCatalogue")
//Path for the contract file
@PactFolder("C:\\projects\\Code+Projects\\Code Projects\\Courses\\src\\main\\java\\pacts")
public class PactProviderTestSecond {
	
	@LocalServerPort
	public int port;
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	//PactVerificationContext is the junit5 pact test runner
	public void pactVerificationTest(PactVerificationContext context )
	{
		//For each interactions this code will run
		//if there are 2 interactions in the interaction array in
		//the interation array this test will run twice.
		//We need to let the context know on which port the server is running
		
		context.verifyInteraction();
	}
	
	@BeforeEach
	public void setup(PactVerificationContext context)
	{
		context.setTarget(new HttpTestTarget("localhost",port));
	}
	
	//If you get a course that does not exist at all then it will return the course does not exixst
	//if its there it will provide uou the details
	// /getCourseName/Appium () => { name - apium, id ..}
	// /getCourseName/Appium () => {course does not exists}
	//To avoid this for every interaction there will be setup and tear down
	//Setup - we will check if appium is present
	//if its not present insert a record in db, all this is done before the test runs
	//its not good to insert data in db, in teardown state it will delete this record.
	//We are making sure test does not fail if data does not exist
	
	//For which interaction this setup should execute
	@State(value ="Courses exist",action=StateChangeAction.SETUP)
	public void coursesExist()
	{
		
	}
	
	
	@State(value ="Courses exist",action=StateChangeAction.TEARDOWN)
	public void coursesExistTearDown()
	{
		
	}
	
	
	
	

}
