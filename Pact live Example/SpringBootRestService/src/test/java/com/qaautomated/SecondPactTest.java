package com.qaautomated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaautomated.controller.LibraryController;
import com.qaautomated.controller.ProductsPrices;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
public class SecondPactTest {
	
	@Autowired
	private LibraryController lib;
	
	//Configure Mock Pact Server
	//Since there are a lot consumers we need to specify which consumer we are building the mock pact server
	//Video says books we are using productdetails
	@Pact(consumer="ProductsDetails")
	public RequestResponsePact pactAllToysDetails(PactDslWithProvider builder) 
	{
		return builder.given("toys details")
		.uponReceiving("Getting all toys details")
		.path("/allToysDetails")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(2)
				.stringType("toys_name")
				.stringType("id")
				.integerType("price",100)
				.stringType("category").closeObject()).toPact();
	}
	
	@Test
	@PactTestFor(pactMethod="pactAllToysDetails")
	//Specifying the mock server port
	@MockServerConfig(port="9191")
	//Instead of using actual end point of the toy microservice we need to use the URL and port provided by Pact server
	public void testPrice(MockServer mockServer) throws JsonMappingException, JsonProcessingException
	{
		//This will get the Pact server url
		//expectedjson we can have it in a file
		String expectedjson = "{\"booksPrice\":100,\"toysPrice\":200}";
		 lib.setBaseUrl(mockServer.getUrl());
	
			 
			ProductsPrices pp = lib.getProductPrices();
			ObjectMapper obj = new ObjectMapper();
			String actualJson = obj.writeValueAsString(pp);
			Assertions.assertEquals(expectedjson,actualJson);
			
		 
		 
		
	}

}
