package com.polstech.library.guideddashpath;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;

import com.polstech.library.view.GuidedView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image1 = (ImageView) findViewById(R.id.img1);
        final ImageView image2 = (ImageView) findViewById(R.id.img2);
        final ImageView image3 = (ImageView) findViewById(R.id.img3);
        final ImageView image4 = (ImageView) findViewById(R.id.img4);

        final GuidedView guidedView = (GuidedView) findViewById(R.id.gv);
        guidedView.with(this)
                .addViewPair(image1, image2)
                .addViewPair(image2, image3)
                .addViewPair(image3, image4)
                .create();
    }
}
