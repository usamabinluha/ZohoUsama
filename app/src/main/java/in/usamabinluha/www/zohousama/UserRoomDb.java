package in.usamabinluha.www.zohousama;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class},version = 2)
public abstract class UserRoomDb extends RoomDatabase{
    public abstract UserDao userDao();
}
