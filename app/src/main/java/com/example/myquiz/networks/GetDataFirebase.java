package com.example.myquiz.networks;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myquiz.models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetDataFirebase {

    public static FirebaseFirestore firebaseFirestore;

    public static FirebaseFirestore getFirebaseFirestoreInstance() {
        if (firebaseFirestore == null) {
            firebaseFirestore = FirebaseFirestore.getInstance();

        }
        return firebaseFirestore;

    }


}
