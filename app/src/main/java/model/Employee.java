package model;

public class Employee extends Person {
    private String employyId;
    private String title;
    private double salary;

    public Employee(String name, int age, String employyId, String title, double salary) {
        super(name, age);
        this.employyId = employyId;
        this.title = title;
        this.salary = salary;
    }

    public String getEmployyId() {
        return employyId;
    }

    public String getTitle() {
        return title;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object getObject() {
        return this;
    }
}
