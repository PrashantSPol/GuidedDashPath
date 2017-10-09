# GuidedDashPath
Android library to draw animated guided dash line between two views.

![](https://github.com/PrashantSPol/GuidedDashPath/blob/master/movie.gif)

In java code
    
    ImageView image1 = (ImageView) findViewById(R.id.img1);
    ImageView image2 = (ImageView) findViewById(R.id.img2);
    ImageView image3 = (ImageView) findViewById(R.id.img3);
    ImageView image4 = (ImageView) findViewById(R.id.img4);

    final GuidedView guidedView = (GuidedView) findViewById(R.id.gv);
    guidedView.with(this)
            .addViewPair(image1, image2)
            .addViewPair(image2, image3)
            .addViewPair(image3, image4)
            .create();
            
 In Xml
 
     <FrameLayout>
        <com.polstech.library.view.GuidedView/>
        
        ...... 
        other views
        ...... 
     </FrameLayout>
