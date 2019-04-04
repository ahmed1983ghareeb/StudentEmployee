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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import model.Employee;
import model.FileManager;
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
        textViewTitle = findViewById(R.id.textViewTitle);
        allBtn =findViewById(R.id.allBtn);
        employeeBtn = findViewById(R.id.employeBtn);
        studentBtn = findViewById(R.id.studentBtn);
        saveBtn =findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        studentBtn.setOnClickListener(this);
        employeeBtn.setOnClickListener(this);
        allBtn.setOnClickListener(this);

        personArrayList = FileManager.readFile(this,"person.txt");

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
                    Student s = new Student("S",editTextName.getText().toString(), Integer.valueOf(editTextAge.getText().toString()),
                            editTextId.getText().toString(), editTextProgram.getText().toString());
                    personArrayList.add(s);
                    personArrayAdapter.notifyDataSetChanged();

                }else if(flag.equals("e")){
                    Employee e = new Employee("E",editTextName.getText().toString(),Integer.valueOf(editTextAge.getText().toString()),
                            editTextId.getText().toString(),editTextJob.getText().toString(),Double.valueOf(editTextSalary.getText().toString()));
                    personArrayList.add(e);
                    personArrayAdapter.notifyDataSetChanged();

            }
                break;
            case R.id.studentBtn:
                textViewTitle.setText("List of Students");
                clearText();
                editTextJob.setVisibility(EditText.INVISIBLE);
                editTextSalary.setVisibility(EditText.INVISIBLE);
                editTextProgram.setVisibility(EditText.VISIBLE);

                personArrayList = filterPerson("S");
                personArrayAdapter.clear();
                personArrayAdapter.addAll(personArrayList);
                Toast.makeText(this,String.valueOf(personArrayAdapter.getCount()),Toast.LENGTH_SHORT).show();
                personArrayAdapter.notifyDataSetChanged();
                flag = "s";
                break;

            case R.id.employeBtn:
                textViewTitle.setText("List of Employees");
                clearText();
                editTextJob.setVisibility(EditText.INVISIBLE);
                editTextSalary.setVisibility(EditText.INVISIBLE);
                editTextProgram.setVisibility(EditText.VISIBLE);

                personArrayList = filterPerson("E");
                personArrayAdapter.clear();
                personArrayAdapter.addAll(personArrayList);
                Toast.makeText(this,String.valueOf(personArrayAdapter.getCount()),Toast.LENGTH_SHORT).show();
                personArrayAdapter.notifyDataSetChanged();

                flag = "e";
                break;
            case R.id.allBtn:
                textViewTitle.setText("List of All");
                clearText();
                editTextJob.setVisibility(EditText.VISIBLE);
                editTextSalary.setVisibility(EditText.VISIBLE);
                editTextProgram.setVisibility(EditText.VISIBLE);

                personArrayList = getPersonArrayList();
                personArrayAdapter.clear();
                personArrayAdapter.addAll(personArrayList);
                Toast.makeText(this,String.valueOf(personArrayAdapter.getCount()),Toast.LENGTH_SHORT).show();
                personArrayAdapter.notifyDataSetChanged();
                flag = "a";
                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (flag)
        {
            case "a":
                if (personArrayList.get(position).getType().equals("S")){
                    clearText();
                    Student s = (Student)personArrayList.get(position).getObject();
                    setStudent(s);
                }else if (personArrayList.get(position).getType().equals("E")){
                    clearText();
                    Employee e = (Employee)personArrayList.get(position).getObject();
                    setEmployee(e);
                }break;
            case "s":
                clearText();
                Student s = (Student)personArrayList.get(position).getObject();
                setStudent(s);
                break;
            case "e":
                clearText();
                Employee e = (Employee)personArrayList.get(position).getObject();
                setEmployee(e);
                break;
        }

    }

    public ArrayList<Person> getPersonArrayList(){
        ArrayList<Person> list = new ArrayList<>();
        for(Person person: Person.getList())
        list.add(person);
        return list;


    }
    public ArrayList<Person> filterPerson(String type){

       ArrayList<Person> filteredList = new ArrayList<>();
        for (Person person: Person.getList()) {
            if (person.getType().equals(type)) {
                filteredList.add(person);
            }
        }

       return filteredList;
    }

    private void setEmployee(Employee e) {
        String name = e.getName();
        String salary = String.valueOf(e.getSalary());
        String employeeId = e.getEmployyId();
        int age = e.getAge();
        String jobTitle = e.getTitle();
        editTextName.setText(name);
        editTextId.setText(employeeId);
        editTextAge.setText(String.valueOf(age));
        editTextSalary.setText(salary);
        editTextJob.setText(jobTitle);
    }

    public void setStudent(Student s){
        String name = s.getName();
        String program = s.getProgram();
        String studentId = s.getStudentId();
        int age = s.getAge();
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

                personArrayList.get(currentPosition).removeObject();
                personArrayList = getPersonArrayList();
                personArrayAdapter.notifyDataSetChanged();
                personArrayAdapter.clear();
                personArrayAdapter.addAll(personArrayList);
                Toast.makeText(this,String.valueOf(personArrayAdapter.getCount()),Toast.LENGTH_SHORT).show();
                clearText();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }

    }


    public void clearText(){
        editTextId.setText("");
        editTextName.setText("");
        editTextAge.setText("");
        editTextSalary.setText("");
        editTextJob.setText("");
        editTextProgram.setText("");
    }

}
