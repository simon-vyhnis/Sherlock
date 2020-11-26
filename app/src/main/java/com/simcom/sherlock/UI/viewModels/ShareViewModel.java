package com.simcom.sherlock.UI.viewModels;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.simcom.sherlock.model.Friend;
import com.simcom.sherlock.model.Repository;

import java.util.List;

public class ShareViewModel extends ViewModel {
    private final Repository repository;
    private Friend friend;
    public ShareViewModel(){
        repository = Repository.getInstance();
    }
    public Task<Void> startSharing(){
        return repository.startSharing();
    }
    public void stopSharing(){
        repository.startSharing();
    }
    public Query getActiveFriends(){
        return repository.getActiveFriends();
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriendUid(Friend friend) {
        this.friend = friend;
    }

    public DocumentReference getFriendsLocations(){
        return repository.getFriendsLocations(friend.getUid());
    }
}
