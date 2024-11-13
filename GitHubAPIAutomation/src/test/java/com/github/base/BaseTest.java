package com.github.base;

import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.github.utils.EnvironmentDetails;
import org.github.utils.TestDataUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void beforeSuite() {
        // Load properties from external files (environment and test data)
        EnvironmentDetails.loadProperties();
        TestDataUtils.loadProperties();
    }

    @BeforeClass
    public void setup() {
    	 
        // Set the base URI for the GitHub API
        RestAssured.baseURI = EnvironmentDetails.getProperty("baseurl");
        
     // Create RedirectConfig to configure redirects
        RedirectConfig redirectConfig = new RedirectConfig()
                .followRedirects(true)  // Enable following redirects
                .maxRedirects(5);  // Set maximum redirects to 5
        // Set RestAssured configuration with the RedirectConfig
        RestAssured.config().redirect(redirectConfig);
        
        // Add Authentication (GitHub token for authentication)
        String githubToken = EnvironmentDetails.getProperty("token"); // This Github PAT token
        RestAssured.authentication = RestAssured.oauth2(githubToken);

        // Set common headers for all requests
        RestAssured.given()
            .header("Accept", "application/vnd.github.v3+json") // API versioning header
            .header("Content-Type", "application/json") // Common content type
            .header("Authorization", "Bearer " + githubToken);  // Authorization header with token

        log.info("Base setup completed for all tests.");
    }
}
