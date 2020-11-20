package com.simcom.sherlock.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Repository {
    private static Repository instance = null;
    private FirebaseAuth auth;
    private Repository(){
        auth = FirebaseAuth.getInstance();
    }
    public static Repository getInstance(){
        if(instance==null){
            instance = new Repository();
        }
        return instance;

    }

    public boolean registerUser(String name, String email, String password){
        try {
            auth.createUserWithEmailAndPassword(email, password);
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            auth.getCurrentUser().updateProfile(request);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
}
