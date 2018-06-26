package in.usamabinluha.www.zohousama;

import android.arch.persistence.room.Room;
import android.content.AsyncTaskLoader;
import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLoader extends AsyncTaskLoader<List<User>> {

    private static final String LOG_TAG = UserLoader.class.getName();
    private String mUrl;
    public static UserRoomDb roomDb;

    public UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        roomDb = Room.databaseBuilder(getContext(), UserRoomDb.class, "userdb").build();
        List<User> users = QueryUtils.fetchUserData(mUrl);
        return users;
    }
}
