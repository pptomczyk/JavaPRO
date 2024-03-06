package java.pro;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class QuizActivity  extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner quizSpinner;
    private Button startQuizButton;
    private TextView questionTextView;
    private RadioGroup optionRadioGroup;
    private Button nextButton;
    private Button backButton;
    private TextView summaryTextView;
    private LinearLayout optionLinearLayout;

    private static final String[] quizArray = {"Test 1", "Test 2"};
    private int currentQuestionIndex = 0;
    private int correctAnswersCount = 0;
    private int currentAnswersCount = 0;
    private NodeList questionNodes;
    private CountDownTimer countDownTimer;
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_pick);
        View quizView = LayoutInflater.from(this).inflate(R.layout.activity_quiz, null);
        View summaryView = LayoutInflater.from(this).inflate(R.layout.activity_quiz_summary, null);

        quizSpinner = findViewById(R.id.spinner);
        startQuizButton = findViewById(R.id.startQuizButton);



        ArrayAdapter<String> adapter= new ArrayAdapter<String>(QuizActivity.this,
                android.R.layout.simple_spinner_item,quizArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quizSpinner.setAdapter(adapter);
        quizSpinner.setOnItemSelectedListener(this);

        Button button1=(Button)findViewById(R.id.Button);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);
            }
        });

        startQuizButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setContentView(R.layout.activity_quiz);
               questionTextView = findViewById(R.id.questionTextView);
               optionRadioGroup = findViewById(R.id.optionRadioGroup);
               optionLinearLayout = findViewById(R.id.optionLinearLayout);
               nextButton = findViewById(R.id.nextButton);
               startQuiz();
           }
       });




    }

    private void startQuiz() {
        displayQuestion(currentQuestionIndex);
        timer = findViewById(R.id.txtFile);
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Pozostały czas: " + millisUntilFinished / 1000 + " sekund");
            }

            public void onFinish() {
                finishQuiz();
            }
        }.start();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Element questionElement = (Element) questionNodes.item(currentQuestionIndex);
                NodeList optionNodes = questionElement.getElementsByTagName("option");
                for (int i = 0; i < optionNodes.getLength(); i++) {
                    Element optionElement = (Element) optionNodes.item(i);
                    boolean isCorrect = Boolean.parseBoolean(optionElement.getAttribute("correct"));
                    if(optionLinearLayout.getVisibility() == View.GONE){
                        if (isCorrect  ) {
                            RadioButton radioButton = (RadioButton) optionRadioGroup.getChildAt(i);
                            if (radioButton.isChecked()) {
                                correctAnswersCount++;
                            }
                            break;
                        }
                    }
                    else {
                        CheckBox checkBox = (CheckBox) optionLinearLayout.getChildAt(i);
                        if ((isCorrect && checkBox.isChecked()) ||((!isCorrect && !checkBox.isChecked()))) {
                            currentAnswersCount++;


                        if(currentAnswersCount == optionNodes.getLength()) {
                            currentAnswersCount = 0;
                            correctAnswersCount++;
                        }
                    }}

                }


                currentQuestionIndex++;
                if (currentQuestionIndex < questionNodes.getLength()) {
                    displayQuestion(currentQuestionIndex);
                }else{
                    if (currentQuestionIndex == questionNodes.getLength()) {
                        finishQuiz();


                    }

                }

            }
        });
    }

    private void finishQuiz() {
        setContentView(R.layout.activity_quiz_summary);
        summaryTextView = findViewById(R.id.summaryTextView);

        backButton = findViewById(R.id.Button);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(i);
            }
        });

        int totalQuestions = questionNodes.getLength();
        float percentage = (float) correctAnswersCount / totalQuestions * 100;
        int score = 0;
        if (percentage >= 90) {
            score = 5;
        } else if (percentage >= 75) {
            score = 4;
        } else if (percentage >= 50) {
            score = 3;
        } else {
            score = 2;
        }


        String summary = "Test Ukończony!\n" +
                "Poprawne Odpowiedzi: " + correctAnswersCount + "\n" +
                "Liczba pytań: " + totalQuestions + "\n" +
                "Procent poprawnych odpowiedzi: " + percentage + "%\n" +
                "Ocena: " + score;
        summaryTextView.setText(summary);
    }

    private void loadXML(Integer file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(getResources().openRawResource(file));
            doc.getDocumentElement().normalize();
            questionNodes = doc.getElementsByTagName("question");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion(int index) {
        Element questionElement = (Element) questionNodes.item(index);
        String questionText = questionElement.getAttribute("text");
        questionTextView.setText(questionText);

        optionRadioGroup.removeAllViews();

        NodeList optionNodes = questionElement.getElementsByTagName("option");
        for (int i = 0; i < optionNodes.getLength(); i++) {
            Element optionElement = (Element) optionNodes.item(i);
            String optionText = optionElement.getAttribute("text");
            if (!optionElement.hasAttribute("multi")) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(optionText);
                optionRadioGroup.addView(radioButton);
                optionLinearLayout.setVisibility(View.GONE);
                optionRadioGroup.setVisibility(View.VISIBLE);
            }else{
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(optionText);
                optionLinearLayout.addView(checkBox);
                optionRadioGroup.setVisibility(View.GONE);
                optionLinearLayout.setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                loadXML(R.raw.quiz1);
                break;
            case 1:
                loadXML(R.raw.quiz2);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
