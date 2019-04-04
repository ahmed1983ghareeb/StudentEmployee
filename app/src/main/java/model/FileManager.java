package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManager {

    public static ArrayList<Person> readFile(Context context, String fileName){

        ArrayList<Person> personArrayList = new ArrayList<>();

        AssetManager assetManager = context.getResources().getAssets();

        try{
        InputStreamReader isr = new InputStreamReader(assetManager.open(fileName));
        BufferedReader br = new BufferedReader(isr);

        String oneLine = br.readLine();
        while(oneLine != null){
            StringTokenizer st = new StringTokenizer(oneLine,",");
            String type = st.nextToken();
            switch (type){
                case "E":
                    String eName = st.nextToken();
                    int eAge = Integer.valueOf(st.nextToken());
                    String employyId = st.nextToken();
                    String title = st.nextToken();
                    double salary = Double.valueOf(st.nextToken());
                    //personArrayList.add(new Employee( type,eName,  eAge,  employyId,  title,  salary));
                    new Employee( type,eName,  eAge,  employyId,  title,  salary);
                    break;
                case "S":
                    String sName= st.nextToken();
                    int sAge= Integer.valueOf(st.nextToken());
                    String studentId= st.nextToken();
                    String program= st.nextToken();
                    //personArrayList.add(new Student( type, sName,  sAge,  studentId,  program));
                    new Student( type, sName,  sAge,  studentId,  program);
                    break;
            }
            oneLine = br.readLine();
        }
        br.close();
        isr.close();
    } catch (Exception e) {
        Toast.makeText(context, e.getMessage(),Toast.LENGTH_LONG).show();
    }

    for(Person person: Person.getList())
    {
        personArrayList.add(person);
    }
    return personArrayList;

    }

}
