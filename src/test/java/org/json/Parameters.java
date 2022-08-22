package org.json;

public class Parameters {
    private String str;
    JSONObject obj;

    Parameters(String str){
        this.str = str;
        obj = new JSONObject(this.str);
    }

    public String getName(){
       return obj.getString("name");
    }
    public String getEMAIL(){
        return obj.getString("email");
    }
    public String getHobby(){
        return obj.getString("hobby");
    }
    public int getPhone(){
        return obj.getInt("phone");
    }


    public Object getParam(String name){
        return obj.getJSONObject("name");
    }


}
