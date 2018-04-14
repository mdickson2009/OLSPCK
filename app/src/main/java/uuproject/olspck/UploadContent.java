package uuproject.olspck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UploadContent extends AppCompatActivity {

    private ImageButton mImageButton;
    private EditText mTitle;
    private EditText mDesc;
    private Button mupload;
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private DatabaseReference mDataRef;
    private ProgressDialog mProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_content);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Information");

        mImageButton = findViewById(R.id.imageButton1);


        mTitle = findViewById(R.id.textTitle);
        mDesc = findViewById(R.id.textDesc);

        mupload = findViewById(R.id.uploadButton);

        mProgress = new ProgressDialog(this);


        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });



        mupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();

            }
        });


    }

    private void startPosting() {



        final String title_value = mTitle.getText().toString().trim();
        final String desc_value = mDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(title_value) && !TextUtils.isEmpty(desc_value) && mImageUri !=null){

            mProgress.setMessage("Uploading...");
            mProgress.show();

            StorageReference filepath = mStorage.child("Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    mProgress.dismiss();

                    DatabaseReference newPost = mDataRef.push();

                    newPost.child("Title").setValue(title_value);
                    newPost.child("desc").setValue(desc_value);
                    newPost.child("image").setValue(downloadUrl.toString());


                    startActivity(new Intent(UploadContent.this, DashBoard.class));

                }
            });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();
            mImageButton.setImageURI(mImageUri);
        }
    }
}
