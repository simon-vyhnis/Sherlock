package com.simcom.sherlock.model;

import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class Repository {
    private static Repository instance = null;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private Repository(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    public static Repository getInstance(){
        if(instance==null){
            instance = new Repository();
        }
        return instance;

    }

    public Task<AuthResult> registerUser(String name, String email, String password){
            return auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
              if(task.isSuccessful()){
                  DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
                  userRef.set(new User(false, name));
              }
            });
    }

    public boolean login(String email, String password){
        try {
            auth.signInWithEmailAndPassword(email, password);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoggedIn(){
        return auth.getCurrentUser() != null;
    }

    public Task<Void> startSharing(){
        if(isLoggedIn()){
            DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
            return userRef.update("currentlySharing", true);
        }
        return null;
    }
    public void writeLocations(){


    }
    public void stopSharing(){
        if(isLoggedIn()){
            DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
            userRef.update("currentlySharing", false);
        }
    }

    public String getUid(){
        return auth.getCurrentUser().getUid();
    }
    public LiveData<Boolean> addFriend(String uid){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
        DocumentReference user2Ref = db.collection("users").document(uid);
        user2Ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    userRef.update("friends", FieldValue.arrayUnion(uid));
                    user2Ref.update("friends", FieldValue.arrayUnion(auth.getCurrentUser().getUid())).addOnCompleteListener(task1 -> {
                        result.postValue(task1.isSuccessful());
                    });
                }else {
                    result.postValue(false);
                }
            } else {
                result.postValue(false);
            }

        });
        return result;
    }
    public Query getActiveFriends(){
        System.out.println(auth.getCurrentUser().getUid());
        return db.collection("users")
                .whereArrayContains("friends",auth.getCurrentUser().getUid())
                .whereEqualTo("currentlySharing", false);
    }
}
