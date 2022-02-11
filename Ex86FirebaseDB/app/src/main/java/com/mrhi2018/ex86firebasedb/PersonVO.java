package com.mrhi2018.ex86firebasedb;

public class PersonVO {

    String name;
    int age;
    String address;

    public PersonVO(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    //firebaseDB에서 getValue()를 하려면...
    //빈 파라미터 생성자가 있어야 함.!!!(필수~!!!)
    public PersonVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
