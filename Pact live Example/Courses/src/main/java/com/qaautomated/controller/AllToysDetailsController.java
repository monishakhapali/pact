package com.qaautomated.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.qaautomated.repository.ToysRepository;


@RestController
public class AllToysDetailsController {


@Autowired
ToysRepository repository;



@CrossOrigin
	@GetMapping("/allToysDetails")
	public List<AllToysData> GetCourses()
	{

	return repository.findAll();
		
		
	}


@CrossOrigin
@RequestMapping("/getToyByName/{name}")
public AllToysData getBookById(@PathVariable(value="name")String name)
{
	try {
		AllToysData lib =repository.findById(name).get();
	return lib;
	}
	catch(Exception e)
	{
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
}
	
	
	
	
	
}
