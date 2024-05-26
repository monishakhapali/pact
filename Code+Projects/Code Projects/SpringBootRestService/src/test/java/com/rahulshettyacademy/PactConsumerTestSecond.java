package com.rahulshettyacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulshettyacademy.controller.LibraryController;
import com.rahulshettyacademy.controller.ProductsPrices;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest(classes = SpringBootRestServiceApplication.class)
//This class has all the features of Pact consumer
@ExtendWith(PactConsumerTestExt.class)
//Provide a provider name , so that the consumer knows on which provider the test has to run
@PactTestFor(providerName = "CoursesCatalogue")
public class PactConsumerTestSecond {
	
	@Autowired
	private LibraryController libraryController;
	
//	@Pact(consumer="BooksCatalogue")
//	public V4Pact PactallCoursesDetailsConfig(PactDslWithProvider builder)
//	{
//		return builder.given("Courses exist")
//		.uponReceiving("getting all courses details")
//		.path("/allCourseDetails")
//		.willRespondWith()
//		.status(200)
//		.body(PactDslJsonArray.arrayMinLike(3)
//				.stringType("course_name")
//				.stringType("id")
//				.integerType("price",10)
//				.stringType("category").closeObject()).toPact(V4Pact.class);
//	}
	//Write rules that we need, here we only need price
	@Pact(consumer="BooksCatalogue")
	public V4Pact PactallCoursesDetailsPriceCheck(PactDslWithProvider builder)
	{
		return builder.given("Courses exist")
		.uponReceiving("getting all courses details")
		.path("/allCourseDetails")
		.willRespondWith()
		.status(200)
		.body(PactDslJsonArray.arrayMinLike(3)
				.integerType("price",10)
				.closeObject()).toPact(V4Pact.class);
	}
	
	@Test
	@PactTestFor(pactMethod = "PactallCoursesDetailsPriceCheck",port="9999")
	public void testAllProductsSum(MockServer server )
	{
		String expectedJson ="{\"booksPrice\":250,\"coursesPrice\":30}";
		libraryController.setBaseUrl(server.getUrl());
		
		ProductsPrices productPrices = null;
		try {
			productPrices = libraryController.getProductPrices();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObjectMapper obj = new ObjectMapper();
		String jsonActual = null;
		try {
			jsonActual = obj.writeValueAsString(productPrices);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assertions.assertEquals(expectedJson, jsonActual);
	}
}
