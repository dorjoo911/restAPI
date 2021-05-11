package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TC_VideoGameAPI {

    // GET all Video Games
    @Test(priority = 1)
    public void test_GET_allVideoGames() {
        given()
                .when()
                .get("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200);
    }

    // POST request
    @Test(priority = 2)
    public void test_POST_addNewVideoGames() {
        HashMap data = new HashMap();
        data.put("id", "11");
        data.put("name", "Batman");
        data.put("releaseDate", "2021-05-11T15:31:05.231Z");
        data.put("reviewScore", "5");
        data.put("category", "Action");
        data.put("rating", "String");


        Response res =
                given()
                        .contentType("application/json")
                        .body(data)
                        .when()
                        .post("http://localhost:8080/app/videogames")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().response();
        String strJson = res.asString();
        Assert.assertEquals(strJson.contains("Record Added Successfully"), true);
    }

    // GET specified data
    @Test(priority = 3)
    public void test_getVideoGame(){

        given()
                .when()
                .get("http://localhost:8080/app/videogames/11")
                .then()
                .statusCode(200)
                .log().body()
                .body("videoGame.id", equalTo("11"))
                .body("videoGame.name", equalTo("Batman"));

    }

    // PUT specified data updated
    @Test(priority = 4)
    public void test_UpdateVideoGame(){
        HashMap data = new HashMap();
        data.put("id", "11");
        data.put("name", "The Batman");
        data.put("releaseDate", "2021-05-11T15:31:05.231Z");
        data.put("reviewScore", "5");
        data.put("category", "Action");
        data.put("rating", "4.8");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("http://localhost:8080/app/videogames/11")
                .then()
                .statusCode(200)
                .log().body()
                .body("videoGame.id", equalTo("11"))
                .body("videoGame.name", equalTo("The Batman"));
    }

    // Delete specified data
    @Test(priority = 5)
    public void test_DeleteVideoGame() throws InterruptedException {
          Response res =
        given()
                   .when()
                .delete("http://localhost:8080/app/videogames/11")
                .then()
                .statusCode(200)
                  .log().body()
                  .extract().response();

          Thread.sleep(3000);
          String strJson = res.asString();
          Assert.assertEquals(strJson.contains("Record Deleted Successfully"), true);



    }

}
