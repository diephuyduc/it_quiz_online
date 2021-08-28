package com.example.myquiz.views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myquiz.R;
import com.example.myquiz.adapters.SetAdapter;
import com.example.myquiz.models.Category;
import com.example.myquiz.models.SetClass;
import com.example.myquiz.networks.GetDataFirebase;
import com.example.myquiz.ultis.ISetOnClick;
import com.example.myquiz.ultis.SetGenerate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity implements ISetOnClick {
    RecyclerView recyclerView;
    SetAdapter setAdapter;
    List<SetClass> setClasses = new ArrayList<>();
    Category category;
    ImageView imageView;
    TextView textView;
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        init();
        loadSets();
    }
    void init(){

        recyclerView = findViewById(R.id.rv_sets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        setAdapter = new SetAdapter(this);
        category = (Category)getIntent().getBundleExtra("cat_id").get("cat_id");
        imageView = findViewById(R.id.cat_img_set);
        textView = findViewById(R.id.cat_title_set);
        Glide.with(this).load(category.img).into(imageView);
        textView.setText(category.name);


        recyclerView.setAdapter(setAdapter);
    }
    private void loadSets(){

        GetDataFirebase.getFirebaseFirestoreInstance().collection("QUIZ").document(category.id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                        number = Integer.valueOf(String.valueOf(documentSnapshot.getData().get("count")));
                    }
                    SetGenerate setGenerate = new SetGenerate(number);
                    setClasses = setGenerate.generateName();
                    setAdapter.setData(setClasses);
                }

            }
        });
    }

    @Override
    public void setOnclick(SetClass setClass) {
        Intent intent = new Intent(SetActivity.this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item_set", setClass);
        bundle.putSerializable("cat_id", category);
        intent.putExtra("item_set", bundle);
        startActivity(intent);

    }

}