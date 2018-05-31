package com.gamerole.common.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gamerole.common.model.bean.User;


/**
 * Created by lv on 18-5-7.
 */
@Database(entities = {User.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDataBase INSTANCE;
    private static final Object sLock = new Object();

    public static UserDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserDataBase.class, "user")
                        .build();
            }
            return INSTANCE;
        }
    }
}
