package pojo;

public class TestPojo {
    public static void main(String[] args) {

        House house = new House();
        house.setSize("18");
        house.setAddress("Пятая просека");

        City city = new City();
        city.setName("Samara");
        city.setAge("1790");
        city.setHouse(house);

        MyPojo myPojo = new MyPojo();
        myPojo.setCountry("Russia");
        myPojo.setCity(city);

        System.out.println(myPojo.getCountry());


    }
}
