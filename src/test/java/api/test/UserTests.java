package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayLoad;

	@BeforeClass
	public void setUpData()
	{
		faker =new Faker();
		userPayLoad=new User();
		 userPayLoad.setId(faker.idNumber().hashCode());
		 userPayLoad.setFirstName(faker.name().firstName());
		 userPayLoad.setLastName(faker.name().lastName());
		 userPayLoad.setUsername(faker.name().username());
		 userPayLoad.setEmail(faker.internet().emailAddress());
		 userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		 userPayLoad.setPassword(faker.internet().password());
	}
	@Test(priority=1)
	public void testPostUser()
	{
      Response response= UserEndPoints.createUser(userPayLoad);
      response.then().log().all();
      Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority=2)
	public void testGetUserByName()
	{
	   Response response=UserEndPoints.readUser(this.userPayLoad.getUsername());
	   response.then().log().all();
	   Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority=3)
	public void testUpdateUser()
	{
		 //updata data using payload
		userPayLoad.setFirstName(faker.name().firstName());
		 userPayLoad.setLastName(faker.name().lastName());
		 userPayLoad.setEmail(faker.internet().emailAddress());
		Response response=UserEndPoints.updateUser(userPayLoad,this.userPayLoad.getUsername());
		 response.then().log().body().statusCode(200);
		   //Assert.assertEquals(response.getStatusCode(), 200);

		 //Checking data after updata
		 Response responseAfterUpdate=UserEndPoints.readUser(userPayLoad.getUsername());
		 responseAfterUpdate.then().log().all();
		   Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

	}

	@Test(priority=4)
	public void testDeleteUser()
	{
		Response response=UserEndPoints.deleteUser(this.userPayLoad.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
