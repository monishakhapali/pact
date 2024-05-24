package com.qaautomated;

import java.util.HashMap;
import java.util.Map;

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
import com.qaautomated.controller.SpecificProduct;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
public class FirstPactTest {
	
     @Autowired
     private LibraryController libraryC;
     
    
	
	@Pact(provider="toysDetails",consumer="BooksDetails")
	public RequestResponsePact pactAllToysDetails(PactDslWithProvider builderP)
	{
		Map<String, String> headers = new HashMap<String, String>();
        headers.put("testreqheader", "testreqheadervalue");
        
		return builderP
		.given("toys details")
		.uponReceiving("Getting all toys details")
		.path("/allToysDetails")
		.willRespondWith()
		.status(200)
		.headers(headers)
		.body(PactDslJsonArray.arrayMinLike(5)
				.stringType("toys_name")
				.stringType("id")
				.integerType("price",100)
				.stringType("category").closeObject()).toPact();
		        
        
        
	}
	
	@Pact(provider="toysDetails",consumer="BooksDetails")
	public RequestResponsePact pactAllToysIdDetails(PactDslWithProvider builderP)
	{
		Map<String, String> headers = new HashMap<String, String>();
        headers.put("testreqheader", "testreqheadervalue");
        
		return builderP
		.given("toys details")
		.uponReceiving("Getting all toys details")
		.path("/allToysDetails")
		.willRespondWith()
		.status(200)
		.headers(headers)
		.body(PactDslJsonArray.arrayMinLike(1)
				.stringType("id").closeObject()).toPact();
		        
        
        
	}
	@Pact(provider="toysDetails",consumer="BooksDetails")
	public RequestResponsePact pactAllToysDetailsByName(PactDslWithProvider builderP)
	{
		//Map<String, String> headers = new HashMap<String, String>();
       // headers.put("testreqheader", "testreqheadervalue");
        
		return builderP
		.given("toy barbie exists")
		.uponReceiving("Getting all toys details")
		.path("/getToyByName/barbie")
		.willRespondWith()
		.status(200)
//		.body(PactDslJsonArray.arrayMinLike(1)
//				.integerType("price",1000)
//				.stringType("category","pretend play").closeObject()).toPact();  
		.body(new PactDslJsonBody()
				.integerType("price",1000)
				.stringType("category","pretend play")).toPact();
        
        
	}
	
	@Test
	@PactTestFor(pactMethod="pactAllToysDetails", port="9191")
	public void testPrice(MockServer mocserver) throws JsonMappingException, JsonProcessingException
	{
		String expectedjson="{\"booksPrice\":100,\"toysPrice\":500}";
		libraryC.setBaseUrl(mocserver.getUrl());
		ProductsPrices pp= libraryC.getProductPrices();
		ObjectMapper obj=new ObjectMapper();
		String actualjson=obj.writeValueAsString(pp);
		Assertions.assertEquals(expectedjson, actualjson);
		
		
	}
	
	@Test
	@PactTestFor(pactMethod="pactAllToysIdDetails", port="9191")
	public void testId(MockServer mocserver) throws JsonMappingException, JsonProcessingException
	{
		//String expectedjson="{\"booksPrice\":100,\"toysPrice\":500}";
		libraryC.setBaseUrl(mocserver.getUrl());
		ProductsPrices pp= libraryC.getProductPrices();
		ObjectMapper obj=new ObjectMapper();
		String actualjson=obj.writeValueAsString(pp);
		//Assertions.assertEquals(expectedjson, actualjson);
		
		
	}
	
	@Test
	@PactTestFor(pactMethod="pactAllToysDetailsByName", port="9191")
	public void testGetAllProducts(MockServer mocserver) throws JsonMappingException, JsonProcessingException
	{
		String expectedjson="{"
				+ "\"product\":\"101 bed time stories\","
				+ "\"price\":1000,"
				+ "\"category\":\"pretend play\""
				+ "}";
		libraryC.setBaseUrl(mocserver.getUrl());
		SpecificProduct sp=libraryC.getProductFullDetails("barbie");
		
		ObjectMapper obj=new ObjectMapper();
		String actualjson=obj.writeValueAsString(sp);
		Assertions.assertEquals(expectedjson, actualjson);
	}
	
	
	@Pact(provider="toysDetails",consumer="BooksDetails")
	public RequestResponsePact pactAllToysDetailsByNameNotExists(PactDslWithProvider builderP)
	{
		//Map<String, String> headers = new HashMap<String, String>();
       // headers.put("testreqheader", "testreqheadervalue");
        
		return builderP
		.given("toy gun does not exists")
		.uponReceiving("no details for toy gun")
		.path("/getToyByName/gun")
		.willRespondWith()
		.status(404).
         toPact();
        
        
	}
	@Test
	@PactTestFor(pactMethod="pactAllToysDetailsByNameNotExists", port="9191")
	public void testToydByNameNotExists(MockServer mocserver) throws JsonMappingException, JsonProcessingException
	{
		String expectedjson="{"
				+ "\"product\":\"101 bed time stories\","
				+ "\"msg\":\"gunCategory and price details are not available at this time\""
				+ "}";
		libraryC.setBaseUrl(mocserver.getUrl());
		SpecificProduct sp=libraryC.getProductFullDetails("gun");
		
		ObjectMapper obj=new ObjectMapper();
		String actualjson=obj.writeValueAsString(sp);
		Assertions.assertEquals(expectedjson, actualjson);
	}

}
