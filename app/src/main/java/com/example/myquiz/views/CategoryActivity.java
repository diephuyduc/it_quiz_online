package com.example.myquiz.views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.airbnb.lottie.LottieAnimationView;
import com.example.myquiz.R;
import com.example.myquiz.adapters.CatAdapter;
import com.example.myquiz.models.Category;
import com.example.myquiz.networks.GetDataFirebase;
import com.example.myquiz.ultis.ICatOnclick;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryActivity extends AppCompatActivity implements ICatOnclick {
    private RecyclerView recyclerViewCat;
    LottieAnimationView lottieAnimationView;
    List<Category> categoryList = new ArrayList<>();
    CatAdapter catAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        init();

        getCategory();

    }

    private void init() {
        recyclerViewCat = findViewById(R.id.rv_cat);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        recyclerViewCat.setLayoutManager(layoutManager);
        catAdapter = new CatAdapter(this, this);
        recyclerViewCat.setAdapter(catAdapter);


    }
    private void getCategory(){
        GetDataFirebase.getFirebaseFirestoreInstance().collection("QUIZ").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                         Log.d(TAG,  String.valueOf(document.getData().get("name")));
                        String name = String.valueOf(document.getData().get("name"));
                        Category category = new Category(document.getId(), name, String.valueOf(document.getData().get("img")) );
                        categoryList.add(category);
                    }
                    lottieAnimationView.setVisibility(View.GONE);
                    catAdapter.setData(categoryList);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }}
        );;


    }

    @Override
    public void catItemOnlcik(Category category) {
        Intent intent = new Intent(CategoryActivity.this, SetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cat_id", category);
        intent.putExtra("cat_id", bundle);
        startActivity(intent);
    }
}