package com.ninza.hrm.api.projectTest;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseClass.BaseApiClass;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;
import com.ninza.hrm.api.pojoClasses.ProjectPOJO;
import com.ninza.hrm.constants.endPoints.IEndPoint;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProjectTest extends BaseApiClass{
	
	
	ProjectPOJO pObj;
	
	@Test
	public void addSingleProjectWithCreatedTest() throws Throwable {

		String BaseUri = flib.getDataFromPropertiesFile("BaseUri");
		String expSuccessMsg = "Successfully Added";
		String projectName = "Pulse"+jlib.getRandonNumber();
		
		//Create an Object to Pojo class 
		pObj = new ProjectPOJO("Ryzen", projectName, "Created", 0);

		//Verify the projectName in API Layer 
		Response resp = given().spec(specReqObj)
								.body(pObj)
						.when().post(IEndPoint.AddProj);
					resp.then()
						.assertThat().statusCode(201)
						.assertThat().time(Matchers.lessThan(3000L))
						.spec(specRespObj)
						.log().all();
		
		String actSuccessMsg = resp.jsonPath().get("msg");
		Assert.assertEquals(expSuccessMsg, actSuccessMsg);
		
		//Verify the projectName in DB Layer 
		boolean flag = dblib.executeQueryVerifyAndGetData("select *from project", 4, projectName);
		Assert.assertTrue(flag,"Project in DB is not verified");
	}
	
	
	@Test(dependsOnMethods = "addSingleProjectWithCreatedTest")
	public void createAddDuplicateProject() throws Throwable {

		String BaseUri = flib.getDataFromPropertiesFile("BaseUri");
		//Verify the projectName in API Layer 
		Response resp = given().spec(specReqObj).body(pObj)
						.when().post(IEndPoint.AddProj);
					resp.then()
							.assertThat().statusCode(409)
							.spec(specRespObj)
							.log().all();
	}

}