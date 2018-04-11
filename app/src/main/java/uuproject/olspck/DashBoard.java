package uuproject.olspck;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    private CardView register, download, home, upload;
    private LinearLayout department, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        register =  (CardView) findViewById(R.id.registerId);
        download =  (CardView) findViewById(R.id.downloadId);
        home = (CardView) findViewById(R.id.homeId);
        upload =  (CardView) findViewById(R.id.uploadId);
        department = (LinearLayout) findViewById(R.id.departmentid);
        logout = (LinearLayout) findViewById(R.id.logoutId);


        //Listeners
        register.setOnClickListener(this);
        download.setOnClickListener(this);
        home.setOnClickListener(this);
        upload.setOnClickListener(this);
        department.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.registerId: i = new Intent(this, MainActivity.class); startActivity(i);break;
            case R.id.downloadId: i = new Intent(this, Download.class); startActivity(i);break;
            case R.id.homeId:     i = new Intent(this, Home.class); startActivity(i);break;
            case R.id.uploadId: i = new Intent(this, Upload.class); startActivity(i);break;
            case R.id.departmentid: i = new Intent(this, Department.class); startActivity(i);break;
            case R.id.logoutId: i = new Intent(this, ProfileActivity.class); startActivity(i);break;
            default:break;
        }
    }
}