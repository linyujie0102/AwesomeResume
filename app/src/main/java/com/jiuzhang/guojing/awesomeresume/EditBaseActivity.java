package com.jiuzhang.guojing.awesomeresume;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by linyujie on 6/23/17.
 */

// this is BaseActivity for Educations, Experiences and Projects, including UI setup and else

public abstract class EditBaseActivity<T> extends AppCompatActivity {

    private T data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //setup content view
        setContentView(getlayoutID());
        //setup for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initialize data
        data = initializeData();

        if(data != null) {
            setupUIForEdit(data);
        } else {
            setupUIForCreate();
        }
    }

    // action bar back to main menu setup
    // back to main manue

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_save){
            saveAndExit(data);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //action bar for save btn setup

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_education_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }


   //abstract for function to extract layout id
    protected abstract int getlayoutID();
    protected abstract void setupUIForEdit(@NonNull T data);
    protected abstract void saveAndExit(@Nullable T data);
    protected abstract void setupUIForCreate();
    protected abstract T initializeData();

}
