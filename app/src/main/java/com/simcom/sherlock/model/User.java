package com.simcom.sherlock.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private boolean currentlySharing;
    private List<String> friends;

    public User(boolean currentlySharing) {
        this.currentlySharing = currentlySharing;
        friends = new ArrayList<>();
    }

    public User(boolean currentlySharing, List<String> friends) {
        this.currentlySharing = currentlySharing;
        this.friends = friends;
    }

    public boolean isCurrentlySharing() {
        return currentlySharing;
    }

    public void setCurrentlySharing(boolean currentlySharing) {
        this.currentlySharing = currentlySharing;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
    public void addFriend(String uid){
        friends.add(uid);
    }
}
