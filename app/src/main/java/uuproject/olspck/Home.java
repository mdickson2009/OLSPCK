package uuproject.olspck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Home extends AppCompatActivity {


    private  ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mImageView = (ImageView) findViewById(R.id.imageView);

    }
}
