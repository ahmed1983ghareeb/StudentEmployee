package com.lasalle.studentemployee;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import model.Employee;
import model.Person;
import model.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener{

    EditText editTextId, editTextName, editTextAge, editTextSalary, editTextJob, editTextProgram;
    Button studentBtn, employeeBtn, allBtn, saveBtn;
    TextView textViewTitle;

    ArrayAdapter<Student> studentArrayAdapter;
    ArrayList<Student> studentsList = new ArrayList<Student>();

String flag = "a";
    ArrayList<Employee> employeesList = new ArrayList<Employee>();
    ArrayAdapter<Employee> employeeArrayAdapter;

    ArrayList<Person> personArrayList = new ArrayList<>();
    ArrayAdapter<Person> personArrayAdapter;
    ListView listView;
    AlertDialog.Builder alertDialog;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextSalary = findViewById(R.id.editTextSalary);
        editTextJob = findViewById(R.id.editTextTitle);
        editTextProgram = findViewById(R.id.editTextProgram);

        allBtn =findViewById(R.id.allBtn);
        employeeBtn = findViewById(R.id.employeBtn);
        studentBtn = findViewById(R.id.studentBtn);
        saveBtn =findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        studentBtn.setOnClickListener(this);
        employeeBtn.setOnClickListener(this);
        allBtn.setOnClickListener(this);

        Student s = new Student("Student1", 34,
                "1",  "Mobile science");
        Employee e = new Employee("Employee1",40,"1","Scrum master",100000.00);
        addStudent(s);
        addEmployee(e);

        listView = findViewById(R.id.list);

        personArrayAdapter = new ArrayAdapter<>(this,R.layout.one_item,personArrayList);
        listView.setAdapter(personArrayAdapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you wish to delete this? ( Y / N )");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.saveBtn:
                if(flag.equals("s")) {
                    Student s = new Student(editTextName.getText().toString(), Integer.valueOf(editTextAge.getText().toString()),
                            editTextId.getText().toString(), editTextProgram.getText().toString());
                    addStudent(s);
                    personArrayAdapter.notifyDataSetChanged();
                    studentArrayAdapter.notifyDataSetChanged();
                }else if(flag.equals("e")){
                    Employee e = new Employee(editTextName.getText().toString(),Integer.valueOf(editTextAge.getText().toString()),
                            editTextId.getText().toString(),editTextJob.getText().toString(),Double.valueOf(editTextSalary.getText().toString()));
                    addEmployee(e);
                    personArrayAdapter.notifyDataSetChanged();
                    employeeArrayAdapter.notifyDataSetChanged();
            }
                break;
            case R.id.studentBtn:
                editTextJob.setVisibility(EditText.INVISIBLE);
                editTextSalary.setVisibility(EditText.INVISIBLE);
                editTextProgram.setVisibility(EditText.VISIBLE);
                studentArrayAdapter = new ArrayAdapter<>(this,R.layout.one_item,studentsList);
                listView.setAdapter(studentArrayAdapter);
                flag = "s";
                break;
            case R.id.employeBtn:
               editTextProgram.setVisibility(EditText.INVISIBLE);
                editTextJob.setVisibility(EditText.VISIBLE);
                editTextSalary.setVisibility(EditText.VISIBLE);
                employeeArrayAdapter = new ArrayAdapter<>(this,R.layout.one_item,employeesList);
                listView.setAdapter(employeeArrayAdapter);
                flag = "e";
                break;
            case R.id.allBtn:
                editTextJob.setVisibility(EditText.VISIBLE);
                editTextSalary.setVisibility(EditText.VISIBLE);
                editTextProgram.setVisibility(EditText.VISIBLE);
                //personArrayAdapter = new ArrayAdapter<>(this,R.layout.one_item,studentsList);
                listView.setAdapter(personArrayAdapter);
                flag = "a";
                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int index = position;

        if (flag.equals("a") && personArrayList.get(position).getClass().equals(Student.class)){
            for(int i = 0;i<studentsList.size();i++){
                if (studentsList.get(i).getName().equals(personArrayList.get(position).getName()))
                    index = i;
            }
            getStudent(index);
        }else if (flag.equals("a") && personArrayList.get(position).getClass().equals(Employee.class)){
            for(int i = 0;i<employeesList.size();i++){
                if ( employeesList.get(i).getName().equals(personArrayList.get(position).getName()))
                    index = i;
            }
            getEmployee(index);
        }else if (flag.equals("s"))
            getStudent(position);
        else if (flag.equals("e"))
            getEmployee(position);


    }

    private void getEmployee(int index) {
        String name = employeesList.get(index).getName();
        String salary = String.valueOf(employeesList.get(index).getSalary());
        String employyId = employeesList.get(index).getEmployyId();
        int age = employeesList.get(index).getAge();
        String jobTitle = employeesList.get(index).getTitle();
        editTextName.setText(name);
        editTextId.setText(employyId);
        editTextAge.setText(String.valueOf(age));
        editTextSalary.setText(salary);
        editTextJob.setText(jobTitle);
    }

    public void getStudent(int index){
        String name = studentsList.get(index).getName();
        String program = studentsList.get(index).getProgram();
        String studentId = studentsList.get(index).getStudentId();
        int age = studentsList.get(index).getAge();
        editTextName.setText(name);
        editTextId.setText(studentId);
        editTextAge.setText(String.valueOf(age));
        editTextProgram.setText(program);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        currentPosition = position;
        AlertDialog dialogBox = alertDialog.create();
        dialogBox.show();

        //true : manages a long click + a click, false : manages a long click
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which){
            case Dialog.BUTTON_POSITIVE:
                personArrayList.remove(currentPosition);
                personArrayAdapter.notifyDataSetChanged();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }

    }

    public void setStudentsList(ArrayList<Person> personList){

//        String name;
//        String program;
//        String studentId;
//        int age;
//        for(int i = 0; i < personList.size();i++)
//        {
//            name = personList.get(i).getName();
//            program = personList.get(i).getProgram();
//            studentId = personList.get(i).getStudentId();
//            age = personList.get(i).getAge();
//            studentsList.add(new Student(name,age,studentId,program));
//        }
//
//        listView.setAdapter(studentArrayAdapter);
      }

      public void addStudent(Student s)
      {
          personArrayList.add(s);
          studentsList.add(s);

      }
    public void addEmployee(Employee e)
    {
        personArrayList.add(e);
        employeesList.add(e);

    }
}
