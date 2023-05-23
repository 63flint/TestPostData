package org.json;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringEscapeUtils;
import org.testng.Assert;
import org.testng.annotations.*;


import java.io.*;

import java.util.*;

public class APITests extends JSONObject{
    static private final String urlPost = "http://users.bugred.ru/tasks/rest/createuser";
    private RequestSpecification requestSpecification;


    @BeforeClass
    public void setUp(){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(urlPost)
                .setContentType(ContentType.JSON)
                .build();

         RestAssured.config = RestAssured.config().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
    }



    @Test(dataProvider = "data" )
    public void test1(Object key, Object data){

        Random random = new Random();
        int rnd = random.nextInt(9999);

        String ter ="";
        ter = data.toString().replace("xxxx", Integer.toString(rnd));

        Parameters er = new Parameters(ter);


        // Отправить Post запрос
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ter)
                .post(urlPost);

        Parameters ar = new Parameters(response.asString());

        System.out.println("Send: " + ter );
        System.out.println("Response:" + response.asString());
        System.out.println("status code: " + response.getStatusCode());
        System.out.println("Body:" + response.getBody().asString());
        System.out.println("____");

        Assert.assertEquals(ar.getName(), er.getName());
        Assert.assertEquals(ar.getEMAIL(), er.getEMAIL());
        Assert.assertEquals(ar.getHobby(), er.getHobby());
        Assert.assertEquals(ar.getPhone(), er.getPhone());
    }

    @Test
    @Step
    public void AllureTest(){
        // Отправить Post запрос
        try {
            File jsonData = new File("src/test/resources/TestData.json");
            Response response = RestAssured.given()
                    .spec(requestSpecification)
                    .body(jsonData)
                    .when()
                    .post()
                    .then()
                    .log()
                    .all()
                    .statusCode(200)
                    .extract()
                    .response();


            System.out.println(response.body().asString());
//            Allure.getLifecycle().updateTestCase((t) -> {
//                t.setStatusDetails(t.getStatusDetails().setMessage(response.body().asString()));
//            });

//            String str = response.body().asString();
//            System.out.println(StringEscapeUtils.unescapeJava(str));



        }
        catch (AssertionError e){
            e.printStackTrace();
//            Allure.description("Все норм");
//            Allure.getLifecycle().updateTestCase((t) -> {
//                t.setStatusDetails( t.getStatusDetails().setMessage("blablablabl"));
//            });
        }



    }


    @DataProvider( name = "data" )
    public Object[][] getData() {
        Map<String, JSONObject> dataMap = new HashMap<String, JSONObject>();
        Iterator entriesIterator = null;
        try {
            // Получаем строку из файла
            String str = getJSONData();

            JSONObject obj = new JSONObject(str);
            JSONObject pp = (JSONObject) obj.get("TestData");
            System.out.println(pp);

            pp.keySet().forEach(key -> {
                dataMap.put(String.valueOf(key), (JSONObject) pp.get(key));
            });

            Set entries = pp.entrySet();
            entriesIterator = entries.iterator();

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] arr = new Object[dataMap.size()][2];
        int i = 0;
            while(entriesIterator.hasNext()){

        Map.Entry mapping = (Map.Entry) entriesIterator.next();

        arr[i][0] = mapping.getKey();
        arr[i][1] = mapping.getValue();

        i++;
    }
        return arr;

    }


    public static String getJSONData(){
        StringBuffer content = new StringBuffer();
        String Path = "./src/test/resources/Data.json";
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(Path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        }
        catch (Exception e){
            System.out.println("Ошибка файла");
        }
        return content.toString();
    }


}
