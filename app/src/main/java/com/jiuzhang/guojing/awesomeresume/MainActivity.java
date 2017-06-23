package com.jiuzhang.guojing.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.reflect.TypeToken;
import com.jiuzhang.guojing.awesomeresume.model.BasicInfo;
import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;
import com.jiuzhang.guojing.awesomeresume.util.ModelUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private static final String MODEL_EDUCATIONS = "educations";


    // request code start from 100
    private static final int REQ_CODE_EDUCATION_EDIT = 100;

    // set up data structure for BasicInfo, Education, Experience and Project
    private BasicInfo basicInfo;
    private List<Education> educations;



    // override onCreate

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup UI needed in main activity
        loadData();
        setupUI();
    }

    // override for result from other activities


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDUCATION_EDIT:
                    String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationID != null) {
                        deleteEducation(educationID);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
            }
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        // image button for education, on click, jump to EducationEditActivity
        ImageButton addEducationBtn = (ImageButton)findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        //other UI setup including Basic Info, Education, Experiences

        setupEducations();
    }


    // function to setup educations in main activity
    private void setupEducations() {
        LinearLayout educationLayout = (LinearLayout) findViewById(R.id.educations);

        //first remove all View inside the linear layout, for update purpose.
        educationLayout.removeAllViews();

        //add all education info from educations list
        for(Education education : educations) {
            //get layout from xml file and transfer it to a tree
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);
            setupEducation(educationView, education);
        }

    }

    // help function to setup a single education view
    private void setupEducation(View educationView, final Education education) {
        String dateString = DateUtils.dateToString(education.startDate) + " ~ " + DateUtils.dateToString(education.endDate);
        ((TextView)educationView.findViewById(R.id.education_school)).setText(education.school + " " +
                                                        education.major + " (" + dateString + ")");
        ((TextView)educationView.findViewById(R.id.education_courses)).setText(formatItems(education.courses));

        // setup image button for edit education
        ImageButton editEducationbtn = (ImageButton) educationView.findViewById(R.id.edit_education_btn);
        editEducationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                //putExtra can be treated as key value pair as hashmap, it can only contain
                // parcel of education, thus education must be modified to be parcelable (serilization)
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
    }

    // help function to setup course in formate, can be static, can be used anywhere
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


    // update educations after editing
    private void updateEducation(Education education) {
        boolean found = false;
        for(int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if(TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }
        if(!found) {
            educations.add(education);
        }
        // after update Education, show educations on UI
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
        // save the result to

    }
    // delete education in educations if delete btn in EducationEditActivity is triggered
    private void deleteEducation(@NonNull String educationId) {
        for(int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if(TextUtils.equals(educationId, e.id)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    // initially load data to memory from hard drive
    private void loadData() {

        List<Education> savedEducations = ModelUtils.read(this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savedEducations == null ? new ArrayList<Education>() : savedEducations;
    }



}
