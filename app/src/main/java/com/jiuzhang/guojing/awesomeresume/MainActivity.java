package com.jiuzhang.guojing.awesomeresume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private BasicInfo basicInfo;
    private List<Education> educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fakeData();
        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        setupBasicInfoUI();
        setupEducationsUI();
    }

    private void setupBasicInfoUI() {
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(basicInfo.email);
    }

    private  void  setupEducationsUI() {
        LinearLayout educationContainer = (LinearLayout) findViewById(R.id.educations);

        for(Education education : educations) {
            View view = getEducationView(education);
            educationContainer.addView(view);
        }
    }

    private View getEducationView(Education education) {
        // LayoutInflater: inflate a layout
        // inflate, read a layout xml file and transfer it into a tree
        View view = getLayoutInflater().inflate(R.layout.education_item, null);
        ((TextView) view.findViewById(R.id.education_school)).setText(education.school + "(" + DateUtils.dateToString(education.startDate) + " ~ " + DateUtils.dateToString(education.endDate) + ")");
        ((TextView) view.findViewById(R.id.education_courses)).setText(formatItems(education.courses));
        ((ImageButton) view.findViewById(R.id.edit_education_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start desired activity: standard procedure
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "Jing Guo";
        basicInfo.email = "guojing@jiuzhang.com";

        Education education = new Education();
        education.school = "THU";
        education.major = "Computer Science";
        education.startDate = DateUtils.stringToDate("09/2013");

        // TODO 1: Set the endDate
        // Follow the above example for startDate
        // DateUtils is a class written by ourselves, check out util/DateUtils
        education.endDate = DateUtils.stringToDate("12/2015");
        // TODO 2: Add some fake courses in education1.courses
        education.courses = new ArrayList<>();
        education.courses.add("Database");
        education.courses.add("Algorithm");
        education.courses.add("Networks");

        Education education2 = new Education();
        education2.school = "UCLA";
        education2.major = "Computer Science";
        education2.startDate = DateUtils.stringToDate("09/2013");

        // TODO 1: Set the endDate
        // Follow the above example for startDate
        // DateUtils is a class written by ourselves, check out util/DateUtils
        education2.endDate = DateUtils.stringToDate("12/2015");
        // TODO 2: Add some fake courses in education1.courses
        education2.courses = new ArrayList<>();
        education2.courses.add("Database");
        education2.courses.add("Algorithm");
        education2.courses.add("Networks");


        educations = new ArrayList<>();
        educations.add(education);
        educations.add(education2);
    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
