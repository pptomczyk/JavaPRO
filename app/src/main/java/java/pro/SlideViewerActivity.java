package java.pro;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.VideoView;


public class SlideViewerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Spinner spinner1;
    private ImageView iv_display;
    private VideoView videoView;
    private int current_image_index;
    private int[] images = {};
    private static final String[] paths = {"Wykład 1","Wykład 2","Wykład 3"};
    private static final String[] w1 = {"1","2","3"};
    private static final String[] w2 = {"1","2"};
    private static final String[] w3 = {"1","2","3","4"};
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_slide_viewer);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(SlideViewerActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);

        Button button1=(Button)findViewById(R.id.Button01);
        button1.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);
            }
        });

        DisplayImage();
        SwitchButton();



        }
    void ChangeDisplay(){
        if (getResources().getResourceName(images[current_image_index]).contains("img")) {
            iv_display.setVisibility(View.VISIBLE);
            iv_display.setImageResource(images[current_image_index]);
            videoView.setVisibility(View.GONE);
        } else {
            iv_display.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + images[current_image_index]));
            videoView.start();
        }
    }
    void DisplayImage(){
        iv_display = findViewById(R.id.iv_display);
        videoView = findViewById(R.id.videoView);
        videoView.setVisibility(View.GONE);
    }
    void SwitchButton(){
        Button btn_right = this.findViewById(R.id.btn_right);
        Button btn_left = findViewById(R.id.btn_left);
        btn_right.setOnClickListener(v -> {
            current_image_index++;
            current_image_index = current_image_index % images.length;
            ChangeDisplay();
            spinner1.setSelection(current_image_index);
        });
        btn_left.setOnClickListener(v -> {
            current_image_index--;
            if(current_image_index < 0){
                current_image_index = images.length - 1;
            }
            ChangeDisplay();
            spinner1.setSelection(current_image_index);
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner:
                switch (position) {
                    case 0:
                        images = new int[]{R.drawable.img1_1, R.drawable.img1_2, R.drawable.img1_3};
                        iv_display.setImageResource(images[0]);
                        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(SlideViewerActivity.this,
                                android.R.layout.simple_spinner_item,w1);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter1);
                        break;
                    case 1:
                        images = new int[]{R.drawable.img1_1, R.drawable.img1_2};
                        iv_display.setImageResource(images[0]);
                        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(SlideViewerActivity.this,
                                android.R.layout.simple_spinner_item,w2);

                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter2);
                        break;
                    case 2:
                        images = new int[]{R.drawable.img1_1, R.drawable.img1_2, R.drawable.img1_3, R.raw.video1};
                        iv_display.setImageResource(images[0]);
                        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(SlideViewerActivity.this,
                                android.R.layout.simple_spinner_item,w3);

                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter3);
                        break;
                }
                break;
            case R.id.spinner1:
                current_image_index = position;
                ChangeDisplay();
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
