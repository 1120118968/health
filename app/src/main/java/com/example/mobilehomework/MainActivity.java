package com.example.mobilehomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;

public class MainActivity extends AppCompatActivity {


    private int[] numberIds = new int []{R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six
            ,R.id.seven,R.id.eight,R.id.nine,R.id.dot};
    private int[] opreations = new int []{R.id.plus,R.id.minus,R.id.mul,R.id.div,R.id.mode,R.id.equal
            ,R.id.ce,R.id.c};
    private Button[] numberBtns = new Button[numberIds.length];
    private Button[] operationBtns = new Button[opreations.length];


    private EditText showInfo;
    private String str = "0";
    private double num1;
    private double num2;
    private String operationStr = "";
    private String result = "";
    private boolean isFirstClicked = true;
    private boolean isLastNum = true;
    private boolean isOperationFirstClicked = true;
    private Button knownKcal;
    private Button planeKcal;
    private Button knownSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //D:\czq\personFile\C_studying\MobileHomeWork\app\build\outputs\apk\debug

        knownKcal = findViewById(R.id.known_kcal);
        planeKcal = findViewById(R.id.kcalplane);
        knownSport = findViewById(R.id.known_sport);
        knownSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SportStyleActivity.class);
                startActivity(i);
            }
        });

        planeKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,KcalPlaneActivity.class);
                startActivity(i);
            }
        });

        knownKcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,GalleryActivity.class);
                startActivity(i);
            }
        });

        NumberBtnListener numberBtnListener = new NumberBtnListener();

        for (int i = 0;i < numberIds.length; i++){
            numberBtns [i] = (Button)findViewById(numberIds[i]);
            numberBtns[i].setOnClickListener(numberBtnListener);

        }
        OperationBtnListener operationBtnListener = new OperationBtnListener();

        for( int i = 0 ; i<operationBtns.length; i++){
            operationBtns[i] = findViewById(opreations[i]);
            operationBtns[i].setOnClickListener(operationBtnListener);
        }

        showInfo = findViewById(R.id.showInfo);

    }
    class NumberBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!isFirstClicked && isLastNum) {
                if (v.getId() == R.id.dot) {
                    if (str.indexOf(".") >= 0) {
                        return;
                    }
                }
                str += ((Button) v).getText().toString();
                str = operationZero(str);
            }else{

                str = ((Button)v).getText().toString();
                isFirstClicked = false;
                isLastNum = true;
                if(v.getId() == R.id.dot){
                    str = "0.";
                }
            }
            showInfo.setText(str);
        }
    }

    private String operationZero(String zero) {

        if (str.indexOf(".")<0){
            while (str.startsWith("0")&&str.length()>1){
                str = str.substring(1);
            }
        }
        return str;
    }

    class OperationBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (isFirstClicked) {
                showInfo.setText(str);
                return;
            }
            if (v.getId() == R.id.ce) {
                clear();
                showInfo.setText(str);
                return;
            }
            if (v.getId() == R.id.c) {
                str = str.substring(0, str.length() - 1);
                if (str.length() == 0) {
                    str = "0";
                }
                showInfo.setText(str);
                return;
            }
            if (isLastNum) {
                if (isOperationFirstClicked) {
                    if (!"=".equals(((Button) v).getText().toString())) {
                        num1 = Double.parseDouble(str);
                        operationStr = ((Button) v).getText().toString();
                        isOperationFirstClicked = false;
                        isLastNum = false;
                    }
                } else {
                    num2 = Double.parseDouble(str);
                    if ("=".equals(((Button) v).getText().toString())) {

                        double calResult = getReslt(operationStr);
                        showInfo.setText(result + "=" + calResult + "");
                        clear();

                    } else {
                        num1 = getReslt(operationStr);
                        showInfo.setText(result + "=" + num1 + "");
                        operationStr = ((Button) v).getText().toString();
                        isOperationFirstClicked = false;
                        isLastNum = false;
                    }
                }
            }else {
                operationStr = ((Button)v).getText().toString();
            }
        }

        private double getReslt(String operationStr) {
            if ("-".equals(operationStr)){
                result = num1+"-"+num2;
                return num1-num2;
            }  else if ("+".equals(operationStr)){
                result = num1+"+"+num2;
                return num1+num2;
            } else if ("*".equals(operationStr)){
                result = num1+"*"+num2;
                return num1*num2;
            } else if ("+".equals(operationStr)){
                result = num1+"/"+num2;
                return num1/num2;
            } else if ("%".equals(operationStr)){
                result = num1+"%"+num2;
                return num1%num2;
            }
            return num2;
        }

        private void clear() {
            isFirstClicked =true;
            isOperationFirstClicked = true;
            num1 = 0;
            num2 = 0;
            operationStr ="";
            str = "0";

        }
    }

}
