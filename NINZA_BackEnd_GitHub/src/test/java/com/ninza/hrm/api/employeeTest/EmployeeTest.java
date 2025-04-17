package com.ninza.hrm.api.employeeTest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseClass.BaseApiClass;
import com.ninza.hrm.api.pojoClasses.EmployeePOJO;
import com.ninza.hrm.api.pojoClasses.ProjectPOJO;
import com.ninza.hrm.constants.endPoints.IEndPoint;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmployeeTest extends BaseApiClass{

	ProjectPOJO pObj;
	
	@Test
	public void addEmployee() throws Throwable {

		//String BaseUri = flib.getDataFromPropertiesFile("BaseUri");
		String projectName = "Pulse"+jlib.getRandonNumber();
		String empName = "Modi"+jlib.getRandonNumber();
		String userName = "Trumps"+jlib.getRandonNumber();
		
		//API-1 ==> add Project
		pObj = new ProjectPOJO("Ryzen", projectName, "Created", 0);

		//Verify the projectName in API Layer 
		given().spec(specReqObj).body(pObj)
		.when().post(IEndPoint.AddProj)
		.then().spec(specRespObj).log().all();
		
		
		//API-2 ==> add an Employee to same Project
		EmployeePOJO empObj = new EmployeePOJO("Architect", "21/05/1985", "Architect@gmail.com",  empName, 12, "9087654321", projectName, "ROLE_ADMIN", userName);
		
		//Verify the projectName in API Layer 
		Response resp = given().spec(specReqObj).body(empObj)
		.when().post(IEndPoint.AddEmp);
		resp.then()
			.assertThat().statusCode(201)
			//.time(Matchers.lessThan(3000L))
			.spec(specRespObj)
			.log().all();
		
		
		//Verify the Employee Name in DB Layer 
		boolean flag = dblib.executeQueryVerifyAndGetData("select * from employee", 11, userName);
		Assert.assertTrue(flag,"Project in DB is not verified");
		Assert.assertTrue(flag,"Employee's UserName in DB is not verified");
	}
	
	@Test
	public void addEmployeeWithOutEmail() throws Throwable {

		//String BaseUri = flib.getDataFromPropertiesFile("BaseUri");
		String empName = "Modi"+jlib.getRandonNumber();
		String projectName = "Pulse"+jlib.getRandonNumber();
		String userName = "Trumps"+jlib.getRandonNumber();
		
		//API-1 ==> add Project
		pObj = new ProjectPOJO("Ryzen", projectName, "Created", 0);

		//Verify the projectName in API Layer 
		given().spec(specReqObj).body(pObj)
		.when().post(IEndPoint.AddProj)
		.then().spec(specRespObj).log().all();
		
		
		//API-2 ==> add an Employee to same Project
		EmployeePOJO empObj = new EmployeePOJO("Architect", "21/05/1985", "",  empName, 12, "9087654321", projectName, "ROLE_ADMIN", userName);
		
		//Verify the projectName in API Layer 
		Response resp = given().spec(specReqObj).body(empObj)
		.when().post(IEndPoint.AddEmp);
		resp.then()
			.assertThat().statusCode(500)
			.spec(specRespObj)
			.log().all();
		
	}
	
}
