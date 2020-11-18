package com.simcom.sherlock.model;

import com.google.firebase.auth.FirebaseAuth;

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

    public boolean registerUser(String email, String password){
        try {
            auth.createUserWithEmailAndPassword(email, password);
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
}
