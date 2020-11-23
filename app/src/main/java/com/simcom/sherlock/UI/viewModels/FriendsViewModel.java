package com.simcom.sherlock.UI.viewModels;

import androidx.lifecycle.ViewModel;

import com.simcom.sherlock.model.Repository;

public class FriendsViewModel extends ViewModel {
    Repository repository;
    public FriendsViewModel(){
        repository = Repository.getInstance();
    }
    public String getUid(){
        return repository.getUid();
    }
}
