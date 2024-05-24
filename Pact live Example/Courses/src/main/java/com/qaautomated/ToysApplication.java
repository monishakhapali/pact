package com.qaautomated;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.qaautomated.controller.AllToysData;
import com.qaautomated.repository.ToysRepository;


@SpringBootApplication
public class ToysApplication implements CommandLineRunner{

	@Autowired
	ToysRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ToysApplication.class, args);
	}
	
	@Override

	public void run(String[] args)
	{
		List<AllToysData> lib= repository.findAll();
		System.out.println(lib.get(0).getToys_name()+"is this");
		

}
}
