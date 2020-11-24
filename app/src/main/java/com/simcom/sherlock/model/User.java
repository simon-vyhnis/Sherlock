package com.simcom.sherlock.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private boolean currentlySharing;
    private List<String> friends;
    private String displayName;

    public User(boolean currentlySharing, String displayName) {
        this.currentlySharing = currentlySharing;
        this.displayName = displayName;
        friends = new ArrayList<>();
    }

    public User(boolean currentlySharing, List<String> friends, String displayName) {
        this.currentlySharing = currentlySharing;
        this.friends = friends;
        this.displayName = displayName;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
