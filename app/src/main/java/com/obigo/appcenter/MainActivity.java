package com.obigo.appcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.obigo.appcenter.fragment.MainFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String MAIN_TAG = "main";

    ImageView backView;
    TextView titleView;
    int updatePosition = -1;
    int newPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backView = (ImageView)findViewById(R.id.btn_back);
        titleView = (TextView)findViewById(R.id.text_title);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(), MAIN_TAG)
                    .commit();
        }

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popFragment();
            }
        });


    }

    public void changeFragment(Fragment f, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, f, tag).addToBackStack(tag)
                .commit();
    }

    private void popFragment(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void setTitle(String title){
        titleView.setText(title);
    }

    public int getSelectUpdatePosition(){
        return this.updatePosition;
    }

    public int getSelectNewPosition(){
        return this.newPosition;
    }

    public void setSelectUpdatePosition(int updatePosition){
        this.updatePosition = updatePosition;
    }

    public void setSelectNewPosiition(int newPosition){
        this.newPosition = newPosition;
    }

    public void go(){
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"index.jpg"));
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(uri, "image/*");
        startActivity(viewIntent);
    }
}
