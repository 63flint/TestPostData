package org.json;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.io.*;

import java.util.*;

public class APITests extends JSONObject{
    String urlPost = "http://users.bugred.ru/tasks/rest/createuser";


    @Test(dataProvider = "data" )
    public void test1(Object key, Object data){

        Random random = new Random();
        int rnd = random.nextInt(9999);

        String ter ="";
        ter = data.toString().replace("xxxx", Integer.toString(rnd));

        Parameters er = new Parameters(ter);


        // Отправить Post запрос
        Response response = RestAssured.given().contentType(ContentType.JSON).body(ter).post(urlPost);
        Parameters ar = new Parameters(response.asString());

        System.out.println( "Send: " + ter );
        System.out.println("Response:" + response.asString());
        System.out.println("status code: " + response.getStatusCode());
        System.out.println("Body:" + response.getBody().asString());
        System.out.println("____");

        Assert.assertEquals(ar.getName(), er.getName());
        Assert.assertEquals(ar.getEMAIL(), er.getEMAIL());
        Assert.assertEquals(ar.getHobby(), er.getHobby());
//        Assert.assertEquals(ar.getPhone(), er.getPhone());

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
