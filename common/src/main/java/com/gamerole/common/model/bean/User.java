package com.gamerole.common.model.bean;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by lv on 18-5-7.
 */
@Entity(primaryKeys = {"uid"})
public class User {
    @NonNull
    private String token;
    private String invitation;
    @NonNull
    private String uid;
    private String qrcode;

    public String getUid() {
        return uid;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;

    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

}
