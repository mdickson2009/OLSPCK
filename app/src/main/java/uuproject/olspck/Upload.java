package uuproject.olspck;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Upload extends AppCompatActivity {

    private StorageReference mStorage;
    private DatabaseReference mRef;
    private ImageView mImageView;
    private EditText meditText;
    private Uri imageUri;


    public static final String STORAGE_PATH = "image/";
    public static final String DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mStorage = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        mImageView = findViewById(R.id.imageViewA);
        meditText = findViewById(R.id.TextInfo1);


    }


    //Button for browsing images
    public void btnBrowse_Click (View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null );
        imageUri = data.getData();

        try{
            Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            mImageView.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getImageExt (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    //Uploading Image
    public void btnUpload_Click (View v){
        if (imageUri!= null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading");
            dialog.show();

            //Get storage reference
            StorageReference ref = mStorage.child(STORAGE_PATH + System.currentTimeMillis() + "." +getImageExt(imageUri));

            //Listeners for success/failure and progress
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    Image_Upload imageUpload = new Image_Upload(meditText.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                    //Save Image to database
                    String uploadId = mRef.push().getKey();
                    mRef.child(uploadId).setValue(imageUpload);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialog.setMessage("Uploading" + (int)progress + "%");

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please Select an Image",Toast.LENGTH_SHORT).show();


        }
    }
}

