package com.example.sakshamdubey.chatbot_v20;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.textservice.SuggestionsInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.v2beta1.QueryInput;
import com.google.cloud.dialogflow.v2beta1.SessionName;
import com.google.cloud.dialogflow.v2beta1.SessionsClient;
import com.google.cloud.dialogflow.v2beta1.SessionsSettings;
import com.google.cloud.dialogflow.v2beta1.TextInput;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;


    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    private SessionsClient sessionsClient;
    private SessionName session;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ScrollView scrollview = findViewById(R.id.chatScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));

        chatLayout = findViewById(R.id.chatLayout);

        ImageView sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this::sendMessage);

        queryEditText = findViewById(R.id.queryEditText);
        queryEditText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage(sendBtn);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });


        initChatbot();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Changes made by SakshamDubey


        showTextView("Hello There! My name is CollegeGyanBot. I am here to help you with your college admissions. I am very smart and " +
                "can help you with Top college names, Exams and Predict your chances to get selected in top Universities and Colleges.", BOT);

        Chip actionChip1 = findViewById(R.id.action_chip1);
        Chip actionChip2 = findViewById(R.id.action_chip2);
        Chip actionChip3 = findViewById(R.id.action_chip3);
        Chip actionChip4 = findViewById(R.id.action_chip4);
        Chip actionChip5 = findViewById(R.id.action_chip5);
        Chip actionChip6 = findViewById(R.id.action_chip6);

        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.add("Hi");
        suggestions.add("I want to get into a college");
        suggestions.add("I want to get into college");
        suggestions.add("Admissions");
        suggestions.add("Hello");
        suggestions.add("Hey");

        ArrayList<String >Streams = new ArrayList<>();
        Streams.add("science");
        Streams.add("arts");
        Streams.add("commerce");
        Streams.add("Open School");
        Streams.add("Music");
        Streams.add("Didn't qualify class 12");

        ArrayList<String>Branches = new ArrayList<>();
        Branches.add("B.E");
        Branches.add("B.Arch");
        Branches.add("BCA");
        Branches.add("BSc");
        Branches.add("MBBS");
        Branches.add("BA");

        ArrayList<String>Courses = new ArrayList<>();
        Courses.add("CSE");
        Courses.add("IT");
        Courses.add("Mechanical");
        Courses.add("Civil");
        Courses.add("Biotechnology");
        Courses.add("ECE");



        Collections.shuffle(suggestions);
        Collections.shuffle(Streams);
        Collections.shuffle(Branches);
        Collections.shuffle(Courses);




        actionChip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = actionChip1.getChipText().toString();

                showTextView(message, USER);

                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();



                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }


            }
        });





        actionChip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = actionChip2.getChipText().toString();

                showTextView(message, USER);


                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();

                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));
                }


            }
        });



        actionChip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = actionChip3.getChipText().toString();

                showTextView(message, USER);


                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();

                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }




            }


        });






        actionChip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = actionChip4.getChipText().toString();

                showTextView(message, USER);


                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();

                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }


            }
        });

        actionChip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = actionChip5.getChipText().toString();

                showTextView(message, USER);


                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();

                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));
                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }


            }
        });


        actionChip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = actionChip6.getChipText().toString();

                showTextView(message, USER);


                aiRequest.setQuery(message);
                RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
                requestTask.execute(aiRequest);


                QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
                new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();

                if(message.equals(suggestions.get(0))|| message.equals(suggestions.get(1))|| message.equals(suggestions.get(2))
                        || message.equals(suggestions.get(3))|| message.equals(suggestions.get(4))|| message.equals(suggestions.get(5))) {
                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(a));
                    actionChip2.setText(suggestions.get(b));
                    actionChip3.setText(suggestions.get(c));
                    actionChip4.setText(Streams.get(0));
                    actionChip5.setText(Branches.get(0));
                    actionChip6.setText(Courses.get(0));

                }

                else if(message.equals(Streams.get(0))|| message.equals(Streams.get(1))|| message.equals(Streams.get(2))||message.equals(Streams.get(3))||message.equals(Streams.get(4))||message.equals(Streams.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }

                else if(message.equals(Branches.get(0))||message.equals(Branches.get(1))|| message.equals(Branches.get(2))|| message.equals(Branches.get(3))|| message.equals(Branches.get(4))|| message.equals(Branches.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);
                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));


                }
                else if(message.equals(Courses.get(0))||message.equals(Courses.get(1))|| message.equals(Courses.get(2))|| message.equals(Courses.get(3))|| message.equals(Courses.get(3))|| message.equals(Courses.get(4))|| message.equals(Courses.get(5))){

                    int maximum = 5;
                    int minimum = 0;

                    int a =  (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int  b = (int) (Math.random()*((maximum-minimum) + 1) + minimum);


                    int c=  (int) (Math.random()*((maximum-minimum) + 1) + minimum);

                    actionChip1.setText(suggestions.get(0));
                    actionChip2.setText(suggestions.get(1));
                    actionChip3.setText(suggestions.get(2));
                    actionChip4.setText(Streams.get(a));
                    actionChip5.setText(Branches.get(b));
                    actionChip6.setText(Courses.get(c));

                }


            }
        });









        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        initV2Chatbot();
    }

    private void initChatbot() {
        final AIConfiguration config = new AIConfiguration("8c9665a53fba45d9a3015e0ba7330417",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(this, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);
        aiRequest = new AIRequest();
    }

    private void initV2Chatbot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.version_config);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(View view) {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");

            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);


            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
            new RequestJavaV2Task(MainActivity.this, session, sessionsClient, queryInput).execute();
        }
    }

    public void callback(AIResponse aiResponse) {
        if (aiResponse != null) {

            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            Log.d(TAG, "Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } //else {
        //Log.d(TAG, "Bot Reply: Null");
        // showTextView("There was some communication issue. Please Try again!", BOT);
        //}
    }

    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {

            String botReply = response.getQueryResult().getFulfillmentText();
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            Log.d(TAG, "Bot Reply: Null");
            //showTextView("There was some communication issue. Please Try again!", BOT);
        }
    }

    private void showTextView(String message, int type) {
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout);
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        queryEditText.requestFocus();
    }

    FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        return (FrameLayout) inflater.inflate(R.layout.user_message_layout, null);
    }

    FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_message_layout, null);
    }
}