package com.gamerole.common.model.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gamerole.common.model.bean.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by lv on 18-5-7.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();


    @Insert(onConflict = REPLACE)
    void save(User info);

    @Query("SELECT * FROM user WHERE uid = :id")
    LiveData<User> load(String id);

    @Update
    void update(User... users);

    @Insert
    void insertAll(List<User> list);

    @Delete
    void delete(User... list);
}
