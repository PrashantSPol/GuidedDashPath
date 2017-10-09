package com.polstech.library.guideddashpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import com.polstech.library.view.GuidedView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView image1 = (TextView) findViewById(R.id.img1);
        TextView image2 = (TextView) findViewById(R.id.img2);
        TextView image3 = (TextView) findViewById(R.id.img3);
        TextView image4 = (TextView) findViewById(R.id.img4);
        TextView image5 = (TextView) findViewById(R.id.img5);

        final GuidedView guidedView = (GuidedView) findViewById(R.id.gv);
        guidedView.with(this)
                .addViewPair(image1, image2)
                .addViewPair(image2, image3)
                .addViewPair(image3, image4)
                .addViewPair(image4, image5)
                .create();
    }
}
