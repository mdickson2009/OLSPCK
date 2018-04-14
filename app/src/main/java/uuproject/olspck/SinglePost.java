package uuproject.olspck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SinglePost extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private ImageView mSinglePostImage;
    private TextView mSingleTitle;
    private TextView mSingleDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Information");

        mSingleDesc = findViewById(R.id.postTextSingle);
        mSingleTitle = findViewById(R.id.postTileSingle);
        mSinglePostImage = findViewById(R.id.postImageSingle);




        mPost_key = getIntent().getExtras().getString("post_id");

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("Title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();

                mSingleTitle.setText(post_title);
                mSingleDesc.setText(post_desc);

                Picasso.with(SinglePost.this).load(post_image).into(mSinglePostImage);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
