package API.testing;

import API.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;

public class MyStepdefs extends BaseTest {
    @Given("user have an API to access {string}")
    public void userHaveAnAPIToAccess(String url) {
        apiServer=url;
    }

    @And("user input to get page third \\({int}) with per_page \\({int})")
    public void userInputToGetPageThirdWithPer_page(int pg, int perpg) {
        page=pg;
        perpage=perpg;
    }

    @When("user send a GET respond per page")
    public void userSendAGETRespondPerPage() {
        response= RestAssured
                .given().when()
                .get(apiServer+"?page="+page+"&per_page="+perpage)
                .then().log().body()
        ;
    }

    @Then("respond code should be \\({int})")
    public void respondCodeShouldBe(int statusCode) {
        Assert.assertEquals(response.statusCode(statusCode), response);
    }

    @And("user GET API respond page \\({int}) with per_page \\({int})")
    public void userGETAPIRespondPageWithPer_page(int arg0, int arg1) {
        Assert.assertEquals(response.body("page", Matchers.equalTo(page)),response);
        Assert.assertEquals(response.body("per_page", Matchers.equalTo(perpage)),response);
    }

    @And("user give name as {string}")
    public void userGiveNameAsJob(String nm) {
        name=nm;
    }

    @When("user send a post respond")
    public void userSendAPostRespond() {
        int UserId=2;
        String fName = RestAssured.given().when().get("https://reqres.in/api/users/"+UserId).getBody().jsonPath().get("data.first_name");
        String avatar = RestAssured.given().when().get("https://reqres.in/api/users/"+UserId).getBody().jsonPath().get("data.avatar");
        String lName = RestAssured.given().when().get("https://reqres.in/api/users/"+UserId).getBody().jsonPath().get("data.last_name");
        String email = RestAssured.given().when().get("https://reqres.in/api/users/"+UserId).getBody().jsonPath().get("data.email");

        HashMap<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("id",UserId);
        bodyMap.put("email",email);
        bodyMap.put("first_name",name);
        bodyMap.put("last_name",lName);
        bodyMap.put("avatar",avatar);
        JSONObject jsonObject=new JSONObject(bodyMap);

        response= RestAssured.given().log().all()
                .header("Content-type","application/json")
                .header("Accept","application/json")
                .body(jsonObject.toString())
                .put(apiServer+"/"+UserId)
                .then().log().all();
    }

    @And("respond should be name {string}")
    public void respondShouldBeName(String nm) {
        Assert.assertEquals(response.body("first_name", Matchers.equalTo(nm)),response);
    }

    @And("user get the id number \\({int})")
    public void userGetTheIdNumber(int id) {
        number=id;
    }

    @When("user send a DELETE respond")
    public void userSendADELETERespond() {
       response= RestAssured.given().log().all()
                .when().delete(apiServer+"/"+number)
                .then().log().all();
    }
}
