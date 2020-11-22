package com.simcom.sherlock.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.simcom.sherlock.model.Repository;

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public boolean isLoggedIn(){
        return repository.isLoggedIn();
    }

    public Task<AuthResult> registerUser(String name, String email, String password){
        return repository.registerUser(name, email, password);
    }

    public boolean login(String email, String password){
        return repository.login(email, password);
    }

}
