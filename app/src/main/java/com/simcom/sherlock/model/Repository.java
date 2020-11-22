package com.simcom.sherlock.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
                  UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                          .setDisplayName(name)
                          .build();
                  if (auth.getCurrentUser() != null) {
                      auth.getCurrentUser().updateProfile(request);
                  }
                  DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
                  userRef.set(new User(false));
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
            boolean result;
            return userRef.update("currentlySharing", true);
        }
        return null;
    }
    public void writeLocations(){


    }
}
