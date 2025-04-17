package com.ninza.hrm.api.baseClass;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApiClass {
	
	public JavaUtility jlib = new JavaUtility();
	public FileUtility flib = new FileUtility();
	public DataBaseUtility dblib = new DataBaseUtility();
	public static RequestSpecification specReqObj;
	public static ResponseSpecification specRespObj;

	@BeforeSuite
	public void configBS() throws Throwable {
		dblib.connectionToDB();
		System.out.println("=============Connect to DB=============");
		RequestSpecBuilder ReqBuilder = new RequestSpecBuilder();
		ReqBuilder.setContentType(ContentType.JSON);
		//reqSpecBuildObj.setAuth(basic("username", "password"));
		//reqSpecBuildObj.addHeader("", "");
		ReqBuilder.setBaseUri(flib.getDataFromPropertiesFile("BaseUri"));
		specReqObj = ReqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
		specRespObj = resBuilder.build();
		
	}
	
	@AfterSuite
	public void configAS() throws SQLException {
		dblib.closeDBConnection();
		System.out.println("=============Close DB Connection=============");
	}
}
