package com.simcom.sherlock.model;

public class Friend {
    private String displayName;
    private String uid;

    public Friend(String displayName, String uid) {
        this.displayName = displayName;
        this.uid = uid;
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
}
