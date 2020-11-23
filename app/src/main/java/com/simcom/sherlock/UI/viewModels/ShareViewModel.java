package com.simcom.sherlock.UI.viewModels;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.simcom.sherlock.model.Repository;

public class ShareViewModel extends ViewModel {
    public Repository repository;
    public ShareViewModel(){
        repository = Repository.getInstance();
    }
    public Task<Void> startSharing(){
        return repository.startSharing();
    }
    public void stopSharing(){
        repository.startSharing();
    }
}
