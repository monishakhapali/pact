package com.qaautomated.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qaautomated.controller.AllToysData;

@Repository
public interface ToysRepository extends JpaRepository<AllToysData, String>{

}
