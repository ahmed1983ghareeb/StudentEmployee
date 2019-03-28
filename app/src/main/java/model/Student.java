package model;

public class Student extends Person {
    private String studentId;
    private String program;

    public Student(String name, int age, String studentId, String program) {
        super(name, age);
        this.studentId = studentId;
        this.program = program;
    }

    @Override
    public String toString() {
        return  name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getProgram() {
        return program;
    }


}
