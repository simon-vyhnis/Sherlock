package com.simcom.sherlock.model;

public class Friend {
    private String displayName;
    private String uid;

    public Friend(String uid, String displayName) {
        this.uid = uid;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Friend() {
    }
}
