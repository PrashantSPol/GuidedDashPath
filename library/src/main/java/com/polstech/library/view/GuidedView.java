package com.polstech.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashant.pol on 9/19/2017.
 */

public class GuidedView extends View {

    AppCompatActivity activity;
    List<GuidedPath> guidedPathList;
    Path mDashPath;
    Paint mPaint;
    int mPhase = 0;
    ComposePathEffect mComposePathEffect;
    boolean isDrawingEnabled;
    CornerPathEffect cornerPathEffect = new CornerPathEffect(5);

    public GuidedView(Context context) {
        super(context);

    }

    public GuidedView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GuidedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public GuidedView with(AppCompatActivity activity) {
        this.activity = activity;
        return this;
    }

    public void create() {
        if(guidedPathList != null && guidedPathList.size() != 0) {
            View view = null;

            for (GuidedPath guidedPath : guidedPathList) {
                if(guidedPath.source != null) {
                    view = guidedPath.source;
                } else if(guidedPath.dest != null) {
                    view = guidedPath.dest;
                }
            }

            if(view != null) {

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        int height = activity.getSupportActionBar()== null ? 0 : activity.getSupportActionBar().getHeight() + rect.top;

                        for (GuidedPath guidedPath : guidedPathList) {
                            if (guidedPath.source != null && guidedPath.dest != null) {
                                int[] location1 = new int[2];
                                int[] location2 = new int[2];

                                guidedPath.source.getLocationOnScreen(location1);
                                guidedPath.dest.getLocationOnScreen(location2);

                                guidedPath.sourceX = location1[0];
                                guidedPath.sourceY = location1[1] - height;
                                guidedPath.destX = location2[0];
                                guidedPath.destY = location2[1] - height;
                            }
                        }

                        init();
                        invalidate();
                    }
                });
            }

            init();
        }


    }

    /*
    source and destination point coordinates
     */
    public GuidedView addPointPairs(int sourceX, int sourceY, int destX, int destY) {
        if(guidedPathList == null) {
            guidedPathList = new ArrayList<>();
        }
        guidedPathList.add(new GuidedPath(sourceX, sourceY, destX, destY));
        return this;
    }

    public GuidedView addViewPair(View sourceView, View destView) {
        if(guidedPathList == null) {
            guidedPathList = new ArrayList<>();
        }

        guidedPathList.add(new GuidedPath(sourceView, destView));
        return this;
    }

    public void element(Path path) {
        if(path != null) {
            mDashPath = path;
        }
    }

    private void init() {

        if(guidedPathList == null || guidedPathList.size() == 0) {
            isDrawingEnabled = false;
            return;
        }

        for (GuidedPath guidedPath : guidedPathList) {
            generatePath(guidedPath);
        }

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

        if(mDashPath == null) {
            mDashPath = new Path();
            mDashPath.moveTo(0,0);
            mDashPath.lineTo(10,0);
            mDashPath.lineTo(10,5);
            mDashPath.lineTo(0, 5);
            mDashPath.close();
        }
        isDrawingEnabled = true;
    }

    private void generatePath(GuidedPath guidedPath) {
        guidedPath.mPath = new Path();
        int x1 = guidedPath.sourceX;
        int x2 = guidedPath.destX;
        int y1 = guidedPath.sourceY;
        int y2 = guidedPath.destY;
        guidedPath.mPath.moveTo(x1, y1);

        int increment = 10;
        int direction = 1;
        int newX = x1;
        int newY = y1;

        int xDiff = x2 - x1;
        int yDiff = y2 - y1;

        if(xDiff == 0 && yDiff == 0) {
            isDrawingEnabled = false;
            return;
        }

        double xStep = xDiff / increment;
        double yStep = yDiff / increment;

        for (int i = 1; i <= increment; i++) {
            newX = (int) (newX + xStep + direction * 5);
            newY =(int) (xDiff == 0 ? newY + yStep : newY + yStep)  + 10 * direction;
            direction = -direction;
            guidedPath.mPath.lineTo(newX, newY);
        }

        guidedPath.mPath.lineTo(x2, y2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isDrawingEnabled) {
            return;
        }

        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(mDashPath, 30, mPhase--, PathDashPathEffect.Style.ROTATE);
        mComposePathEffect = new ComposePathEffect(pathDashPathEffect, cornerPathEffect);

        mPaint.setPathEffect(mComposePathEffect);

        for (GuidedPath guidedPath : guidedPathList) {
            canvas.drawPath(guidedPath.mPath, mPaint);
        }

        invalidate();

    }


    /*
    Class to store location and path data
     */
    class GuidedPath {
        View source, dest;
        int sourceX, sourceY, destX, destY;
        Path mPath;

        GuidedPath(int x1, int y1, int x2, int y2) {
            this.sourceX = x1;
            this.sourceY = y1;
            this.destX = x2;
            this.destY = y2;
        }

        public GuidedPath(View source, View dest) {
            this.source = source;
            this.dest = dest;
        }

        @Override
        public String toString() {
            return "GuidedPath{" +
                    "source=" + source +
                    ", dest=" + dest +
                    ", sourceX=" + sourceX +
                    ", sourceY=" + sourceY +
                    ", destX=" + destX +
                    ", destY=" + destY +
                    ", mPath=" + mPath +
                    '}';
        }
    }
}
