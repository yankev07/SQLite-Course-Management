package com.kevappsgaming.coursemanagementsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kevappsgaming.coursemanagementsqlite.adapters.CourseAdapter;
import com.kevappsgaming.coursemanagementsqlite.database.CourseHelper;
import com.kevappsgaming.coursemanagementsqlite.utils.CourseInformation;
import com.kevappsgaming.coursemanagementsqlite.utils.InputValidation;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CourseHelper myDB;
    public RecyclerView recyclerView;
    private InputValidation inputValidation;
    private CourseAdapter adapter;
    private List<CourseInformation> coursesList;
    private List<CourseInformation> queryList;

    private TextInputLayout TextInputLayoutCourseNumber;
    private TextInputLayout TextInputLayoutCourseName;
    private TextInputLayout TextInputLayoutCourseCredits;

    private TextInputEditText TextInputEditTextCourseNumber;
    private TextInputEditText TextInputEditTextCourseName;
    private TextInputEditText TextInputEditTextCourseCredits;

    private AppCompatButton ButtonInsert;
    private AppCompatButton ButtonDelete;
    private AppCompatButton ButtonUpdate;
    private AppCompatButton ButtonQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.courses_recyclerView);
        myDB = new CourseHelper(MainActivity.this);

        TextInputLayoutCourseNumber = (TextInputLayout) findViewById(R.id.textInputLayoutCourseNumber);
        TextInputLayoutCourseName = (TextInputLayout) findViewById(R.id.textInputLayoutCourseName);
        TextInputLayoutCourseCredits = (TextInputLayout) findViewById(R.id.textInputLayoutCourseCredits);

        TextInputEditTextCourseNumber = (TextInputEditText) findViewById(R.id.textInputEditTextCourseNumber);
        TextInputEditTextCourseName = (TextInputEditText) findViewById(R.id.textInputEditTextCourseName);
        TextInputEditTextCourseCredits = (TextInputEditText) findViewById(R.id.textInputEditTextCourseCredits);

        ButtonInsert = (AppCompatButton) findViewById(R.id.ButtonInsert);
        ButtonDelete = (AppCompatButton) findViewById(R.id.ButtonDelete);
        ButtonUpdate = (AppCompatButton) findViewById(R.id.ButtonUpdate);
        ButtonQuery = (AppCompatButton) findViewById(R.id.ButtonQuery);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        coursesList = myDB.fetchCourseInfo();
        adapter = new CourseAdapter(this, coursesList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.relativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        ButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputValidity()){

                    if(!myDB.searchCourse(TextInputEditTextCourseNumber.getText().toString())){
                        myDB.saveCourseInfo(TextInputEditTextCourseNumber.getText().toString(), TextInputEditTextCourseName.getText().toString(), TextInputEditTextCourseCredits.getText().toString());
                        coursesList.add(new CourseInformation(TextInputEditTextCourseNumber.getText().toString(), TextInputEditTextCourseName.getText().toString(), TextInputEditTextCourseCredits.getText().toString()));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "course added...", Toast.LENGTH_LONG).show();
                        clearInputFields();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "course number is unique", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputValidity()){
                    if(!myDB.searchCourse(TextInputEditTextCourseNumber.getText().toString())){
                        Toast.makeText(MainActivity.this, "course number not found", Toast.LENGTH_LONG).show();
                    }
                    else{
                        myDB.deleteCourse(TextInputEditTextCourseNumber.getText().toString());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "course deleted...", Toast.LENGTH_LONG).show();
                        clearInputFields();
                    }
                }
            }
        });

        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputValidity()){
                    if(!myDB.searchCourse(TextInputEditTextCourseNumber.getText().toString())){
                        Toast.makeText(MainActivity.this, "course number is not editable", Toast.LENGTH_LONG).show();
                    }
                    else{
                        myDB.updateCourseInfo(TextInputEditTextCourseNumber.getText().toString(), TextInputEditTextCourseName.getText().toString(), TextInputEditTextCourseCredits.getText().toString());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "course updated...", Toast.LENGTH_LONG).show();
                        clearInputFields();
                    }
                }
            }
        });

        ButtonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInputValidity()){
                    try{
                        coursesList = myDB.queryCourseInfo(TextInputEditTextCourseNumber.getText().toString());
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    public boolean checkInputValidity(){
        inputValidation = new InputValidation(MainActivity.this);

        if(!inputValidation.isInputEditTextFilled(TextInputEditTextCourseNumber, TextInputLayoutCourseNumber, getString(R.string.error_message_courseNumber))){
            return false;
        }
        if(!inputValidation.isInputEditTextFilled(TextInputEditTextCourseName, TextInputLayoutCourseName, getString(R.string.error_message_courseName))){
            return false;
        }
        if(!inputValidation.isInputEditTextFilled(TextInputEditTextCourseCredits, TextInputLayoutCourseCredits, getString(R.string.error_message_courseCredits))){
            return false;
        }
        if(!inputValidation.isInputEditTextInteger(TextInputEditTextCourseCredits, TextInputLayoutCourseCredits, getString(R.string.error_message_courseCredits))){
            return false;
        }
        return true;
    }


    public void clearInputFields(){
        TextInputEditTextCourseNumber.getText().clear();
        TextInputEditTextCourseName.getText().clear();
        TextInputEditTextCourseCredits.getText().clear();
    }
}
