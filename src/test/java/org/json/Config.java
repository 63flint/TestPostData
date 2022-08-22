package org.json;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class Config {

    @BeforeMethod
    public void setup(){
//        RestAssured.config = RestAssured.config(config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    }
}
