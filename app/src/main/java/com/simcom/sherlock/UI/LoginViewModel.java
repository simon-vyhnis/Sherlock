package com.simcom.sherlock.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.simcom.sherlock.model.Repository;

public class LoginViewModel extends AndroidViewModel {
    Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public boolean loadUsernameAndPassword(){
        return false;
    }

    public boolean registerUser(String email, String password){
        return repository.registerUser(email, password);
    }

    public boolean login(String email, String password){
        return repository.login(email, password);
    }

}
