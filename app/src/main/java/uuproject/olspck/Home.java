package uuproject.olspck;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Home extends AppCompatActivity {


    private RecyclerView mRList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Information");

        mRList = findViewById(R.id.recyclerView1);
        mRList.setHasFixedSize(true);
        mRList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Content, ContentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Content, ContentViewHolder>(

                Content.class,
                R.layout.home_row,
                ContentViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ContentViewHolder viewHolder, Content model, int position) {

            final String post_key = getRef(position).getKey();

            viewHolder.setTitle(model.getTitle());
            viewHolder.setDesc(model.getDesc());
            viewHolder.setImage(getApplicationContext(), model.getImage());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(Home.this, "Selected Article", Toast.LENGTH_SHORT).show();

                    Intent singPost = new Intent(Home.this, SinglePost.class);
                    singPost.putExtra("post_id", post_key);
                    startActivity(singPost);
                }
            });


            }
        };

        mRList.setAdapter(firebaseRecyclerAdapter);


        }





    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public ContentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String Title){

            TextView post_title = mView.findViewById(R.id.postTtile);
            post_title.setText(Title);
        }

        public void setDesc(String desc){

            TextView post_desc = mView.findViewById(R.id.postText);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image){

            ImageView post_image = mView.findViewById(R.id.postImage);
            Picasso.with(ctx).load(image).into(post_image);


        }

    }



}

