package scot.wildcamping.wildscotland;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Chris on 03-Mar-16.
 */
public class SelectFeatures extends Activity implements View.OnClickListener {

    RelativeLayout feature1box;
    RelativeLayout feature2box;
    RelativeLayout feature3box;
    RelativeLayout feature4box;
    RelativeLayout feature5box;
    RelativeLayout feature6box;
    RelativeLayout feature7box;
    RelativeLayout feature8box;
    RelativeLayout feature9box;
    RelativeLayout feature10box;
    Boolean feature1 = false;
    Boolean feature2 = false;
    Boolean feature3 = false;
    Boolean feature4 = false;
    Boolean feature5 = false;
    Boolean feature6 = false;
    Boolean feature7 = false;
    Boolean feature8 = false;
    Boolean feature9 = false;
    Boolean feature10 = false;

    Button confirmFeatures;
    Button cancelFeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_features);

        final int green = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        final int gray = ContextCompat.getColor(getApplicationContext(), R.color.counter_text_color);

        feature1box = (RelativeLayout)findViewById(R.id.feature1box);
        feature2box = (RelativeLayout)findViewById(R.id.feature2box);
        feature3box = (RelativeLayout)findViewById(R.id.feature3box);
        feature4box = (RelativeLayout)findViewById(R.id.feature4box);
        feature5box = (RelativeLayout)findViewById(R.id.feature5box);
        feature6box = (RelativeLayout)findViewById(R.id.feature6box);
        feature7box = (RelativeLayout)findViewById(R.id.feature7box);
        feature8box = (RelativeLayout)findViewById(R.id.feature8box);
        feature9box = (RelativeLayout)findViewById(R.id.feature9box);
        feature10box = (RelativeLayout)findViewById(R.id.feature10box);

        confirmFeatures = (Button)findViewById(R.id.confirmFeatures);
        cancelFeatures = (Button)findViewById(R.id.cancelFeatures);

        confirmFeatures.setOnClickListener(this);
        cancelFeatures.setOnClickListener(this);

        feature1box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature1){
                    //turn colour grey
                    feature1box.setBackgroundColor(gray);
                    feature1 = false;
                } else {
                    //turn colour green
                    feature1box.setBackgroundColor(green);
                    feature1 = true;
                }
            }
        });

        feature2box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature2){
                    //turn colour grey
                    feature2box.setBackgroundColor(gray);
                    feature2 = false;
                } else {
                    //turn colour green
                    feature2box.setBackgroundColor(green);
                    feature2 = true;
                }
            }
        });

        feature3box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature3){
                    //turn colour grey
                    feature3box.setBackgroundColor(gray);
                    feature3 = false;
                } else {
                    //turn colour green
                    feature3box.setBackgroundColor(green);
                    feature3 = true;
                }
            }
        });

        feature4box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature4){
                    //turn colour grey
                    feature4box.setBackgroundColor(gray);
                    feature4 = false;
                } else {
                    //turn colour green
                    feature4box.setBackgroundColor(green);
                    feature4 = true;
                }
            }
        });

        feature5box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature5){
                    //turn colour grey
                    feature5box.setBackgroundColor(gray);
                    feature5 = false;
                } else {
                    //turn colour green
                    feature5box.setBackgroundColor(green);
                    feature5 = true;
                }
            }
        });

        feature6box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature6){
                    //turn colour grey
                    feature6box.setBackgroundColor(gray);
                    feature6 = false;
                } else {
                    //turn colour green
                    feature6box.setBackgroundColor(green);
                    feature6 = true;
                }
            }
        });

        feature7box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature7){
                    //turn colour grey
                    feature7box.setBackgroundColor(gray);
                    feature7 = false;
                } else {
                    //turn colour green
                    feature7box.setBackgroundColor(green);
                    feature7 = true;
                }
            }
        });

        feature8box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature8){
                    //turn colour grey
                    feature8box.setBackgroundColor(gray);
                    feature8 = false;
                } else {
                    //turn colour green
                    feature8box.setBackgroundColor(green);
                    feature8 = true;
                }
            }
        });

        feature9box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature9){
                    //turn colour grey
                    feature9box.setBackgroundColor(gray);
                    feature9 = false;
                } else {
                    //turn colour green
                    feature9box.setBackgroundColor(green);
                    feature9 = true;
                }
            }
        });

        feature10box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feature10){
                    //turn colour grey
                    feature10box.setBackgroundColor(gray);
                    feature10 = false;
                } else {
                    //turn colour green
                    feature10box.setBackgroundColor(green);
                    feature10 = true;
                }
            }
        });



    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.cancelFeatures:
                Intent intent = new Intent(getApplicationContext(), AddSite.class);
                startActivity(intent);
                //bundle all data back EXCEPT for feature data
                finish();
                break;

            case R.id.confirmFeatures:
                Intent i = new Intent(getApplicationContext(), AddSite.class);
                //bundle additional data
                startActivity(i);
                finish();
                break;
        }

    }

}