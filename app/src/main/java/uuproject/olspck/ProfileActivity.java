package uuproject.olspck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAddress;
    private Button buttonSaveInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() == null){
                startActivity(new Intent(this, Login.class));
            }
            FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        buttonSaveInfo = (Button) findViewById(R.id.buttonSaveInfo);

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome "+user.getEmail());

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonSaveInfo.setOnClickListener(this);
    }

    private void saveUserInfo(){
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        UserInfo userInfo = new UserInfo(name,address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInfo);

        Toast.makeText(this, "Information has been saved", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
        }

        if(view == buttonSaveInfo){
            saveUserInfo();
        }

    }
}
