package proj.zollo.robert.flickrapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String image = getIntent().getStringExtra("image");
        imageView = (ImageView) findViewById(R.id.SingleView);

        Picasso.with(this).load(image).into(imageView);


    }
}
