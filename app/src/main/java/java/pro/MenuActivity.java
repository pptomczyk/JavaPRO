package java.pro;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button1=(Button)findViewById(R.id.Button01);
        button1.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        SlideViewerActivity.class);
                startActivity(intent);
            }
        });
        Button button2=(Button)findViewById(R.id.Button02);
        button2.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CodeViewerActivity.class);
                startActivity(intent);
            }
        });

        Button button3=(Button)findViewById(R.id.Button03);
        button3.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        QuizActivity.class);
                startActivity(intent);
            }
        });

    }
}