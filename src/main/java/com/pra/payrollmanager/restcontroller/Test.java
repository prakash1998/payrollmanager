package com.pra.payrollmanager.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RestController
@RequestMapping("api/test")
public class Test {
	
    @GetMapping(path="/", produces = "application/json")
    public Employee getEmployees() 
    {
        return new Employee("Hello world");
    }
     
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) 
    {
         System.out.println(employee);
        return ResponseEntity.ok().build();
    }
}

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Employee{
	private String name;
}
