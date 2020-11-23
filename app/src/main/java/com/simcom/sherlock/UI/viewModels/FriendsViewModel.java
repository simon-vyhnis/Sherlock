package com.simcom.sherlock.UI.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.simcom.sherlock.model.Repository;

public class FriendsViewModel extends ViewModel {
    Repository repository;
    public FriendsViewModel(){
        repository = Repository.getInstance();
    }
    public String getUid(){
        return repository.getUid();
    }
    public LiveData<Boolean> addFriend(String uid){
        return repository.addFriend(uid);
    }
}

