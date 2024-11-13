package org.github.test;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.github.requestPOJO.RepoUpdateRequest;
import org.github.requestPOJO.RepoCreateRequest;
import org.github.utils.EnvironmentDetails;
import com.github.base.BaseTest;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GitHubAPITest extends BaseTest {

    @Test
    public void testGetSingleRepository() {
        String owner = EnvironmentDetails.getProperty("repoowner");
        String repo = EnvironmentDetails.getProperty("reponame");
              
        Response response = given()
                .pathParam("owner", owner)
                .pathParam("repo", repo)
        .when()
                .get("/repos/{owner}/{repo}")
        .then()
                .statusCode(200)
                .body("full_name", equalTo(owner + "/" + repo))
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("full_name"), owner + "/" + repo);
    }

    @Test
    public void testGetSingleRepositoryWithNonExistingRepo() {
        String owner = EnvironmentDetails.getProperty("repoowner");
        String repo = EnvironmentDetails.getProperty("nonexistreponame");
   
        Response response = given()       
                .pathParam("owner", owner)
                .pathParam("repo", repo)
        .when()
                .get("/repos/{owner}/{repo}")
        .then()
                .statusCode(404)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("message"), "Not Found");
    }

    @Test
    public void testGetAllRepositories() {
        String token = EnvironmentDetails.getProperty("token");
        String owner = EnvironmentDetails.getProperty("repoowner");

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .pathParam("owner", owner)
        .when()
                .get("/users/{owner}/repos")
        .then()
                .statusCode(200)
                .extract().response();

        List<String> allRepos = response.jsonPath().getList("name");
        System.out.println("Total repositories: " + allRepos.size());
    }

    @Test
    public void testCreateRepository() {
        RepoCreateRequest requestBody = new RepoCreateRequest();
        requestBody.setName("Hello-World7");
        requestBody.setDescription("This is your first repo!");
        requestBody.setHomepage("https://github.com");
        requestBody.setPrivateRepo(false);
      
        Response response = given()
               
        .body(requestBody)
        .when()
        .post("/user/repos")
        .then()
        .statusCode(201)
        .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), requestBody.getName());
    }

    @Test
    public void testCreateRepositoryWithExistingName() {
        RepoCreateRequest requestBody = new RepoCreateRequest();
        requestBody.setName("Hello-World5");
        requestBody.setDescription("This is your first repo!");
        requestBody.setHomepage("https://github.com");
        requestBody.setPrivateRepo(false);

        String token = EnvironmentDetails.getProperty("token");

        Response response = given()
                .body(requestBody)
        .when()
                .post("/user/repos")
        .then()
                .statusCode(422)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("message"), "Repository creation failed.");
    }

    @Test
    public void testUpdateRepositoryName() {
        RepoUpdateRequest requestBody = new RepoUpdateRequest();
        requestBody.setName("NewTestRepo6");
        requestBody.setDescription("Updated repo");
        requestBody.setPrivateRepo(false);

        String owner = EnvironmentDetails.getProperty("repoowner");
        String repoName = EnvironmentDetails.getProperty("reponame");

        Response response = given()
                .body(requestBody)
        .when()
                .patch("/repos/{owner}/{repo}", owner, repoName)
        .then()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(response.jsonPath().getString("name"), requestBody.getName());
    }

    @Test
    public void testDeleteRepository() {
 
        String owner = EnvironmentDetails.getProperty("repoowner");
        String repoName = EnvironmentDetails.getProperty("reponame");
        
        Response response = given()
                .pathParam("owner", owner)
                .pathParam("repo", repoName)
        .when()
                .delete("/repos/{owner}/{repo}")
        .then()
                .statusCode(204)
                .log().all()
                
                .extract().response();

        Assert.assertNull(response.getBody().asString(), "Expected no content for DELETE request");
    }

    @Test
    public void testDeleteNonExistingRepository() {
        String owner = "owner_name";
        String repo = "non_existing_repo";
        String token = EnvironmentDetails.getProperty("token");

        Response response = given()
                .auth().oauth2(token)
                .pathParam("owner", owner)
                .pathParam("repo", repo)
        .when()
                .delete("/repos/{owner}/{repo}")
        .then()
                .statusCode(404)
                .extract().response();

        Assert.assertTrue(response.getBody().asString().contains("Not Found"));
    }
}
