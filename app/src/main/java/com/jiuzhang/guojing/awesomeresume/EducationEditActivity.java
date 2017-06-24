package com.jiuzhang.guojing.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiuzhang.guojing.awesomeresume.model.Education;
import com.jiuzhang.guojing.awesomeresume.util.DateUtils;

import java.util.Arrays;

/**
 * Created by linyujie on 6/21/17.
 */

@SuppressWarnings("ConstantConditions")
public class EducationEditActivity extends EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";
    @Override
    protected int getlayoutID() {
        return R.layout.activity_education_edit;
    }


    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Education data) {
        ((EditText) findViewById(R.id.education_edit_school)).setText(data.school);
        ((EditText) findViewById(R.id.education_edit_major)).setText(data.major);
        ((EditText) findViewById(R.id.education_edit_start_date)).setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.education_edit_end_date)).setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.education_edit_courses)).setText(TextUtils.join("\n", data.courses));

        //TODO add delete btn
        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    @Override
    protected void saveAndExit(@Nullable Education data) {
        if(data == null) {
            data = new Education();
        }
        data.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        data.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        data.startDate = DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(((TextView) findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    //    private void saveAndExit() {
//
//        Education education = new Education();
//        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
//        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
//        education.startDate = DateUtils.stringToDate(((TextView) findViewById(R.id.education_edit_start_date)).getText().toString());
//        education.endDate = DateUtils.stringToDate(((TextView) findViewById(R.id.education_edit_end_date)).getText().toString());
//        education.courses = Arrays.asList(TextUtils.split(((EditText)findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));
//
//        Intent resultIntent = new Intent(); // HashMap key-value pair
//        resultIntent.putExtra(KEY_EDUCATION, education); //serialize and de-serialize do it in Education class
//        setResult(Activity.RESULT_OK, resultIntent);
//        finish();
//
//    }
}
