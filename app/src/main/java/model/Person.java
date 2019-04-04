package model;

import java.util.ArrayList;

public class Person {

    protected String name;
    protected int age;
    protected String type;

    static private ArrayList<Person> list = new ArrayList<Person>();

    public String getType() {
        return type;
    }

    public Person(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.list.add(this);
    }

    public static ArrayList<Person> getList() {
        return list;
    }

    public void removeObject() {
        //garbage collector
        list.remove(this);
    }

    @Override
    public String toString() {
        return name ;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

public Object getObject(){
        return null;
}

}
