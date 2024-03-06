package java.pro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;


public class CodeViewerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private TextView txt;
    private Button btn;

    private Spinner spinner;
    private Spinner spinner2;
    private static final String[] paths = {"Laboratorium 1","Laboratorium 2","Laboratorium 3"};
    private static final String[] lab1 = {"1Program.java" , "DrugiProgram.java"};
    private static final String[] lab2 = {"GridBagLayout", "SpringLayout"};
    private static final String[] lab3 = {"JavaFXQuiz.java", "JavaFXTextViewer.java"};

    String filename = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_viewer);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CodeViewerActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        Button button1 = (Button) findViewById(R.id.Button01);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MenuActivity.class);
                startActivity(intent);
            }
        });
        btn = (Button)findViewById(R.id.btnReadTxtFile);
        txt = (TextView)findViewById(R.id.txtFile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                try{
                    InputStream inputStream = getAssets().open(filename);
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    text = new String(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                txt.setText(text);
                Toast.makeText(getApplicationContext(),"Read successfully !",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner1:
                switch (position) {
                    case 0:
                        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CodeViewerActivity.this,
                                android.R.layout.simple_spinner_item,lab1);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter);
                        break;
                    case 1:
                        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(CodeViewerActivity.this,
                                android.R.layout.simple_spinner_item,lab2);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter1);
                        break;
                    case 2:
                        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(CodeViewerActivity.this,
                                android.R.layout.simple_spinner_item,lab3);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        break;

                }
                break;

            case R.id.spinner2:
                filename = (String) parent.getItemAtPosition(position) + ".txt";
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}