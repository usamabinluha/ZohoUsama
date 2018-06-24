package in.usamabinluha.www.zohousama;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.kosalgeek.android.caching.FileCacher;
import java.io.IOException;
import java.util.ArrayList;

public class UserLoader extends AsyncTaskLoader<ArrayList<User>> {

    private static final String LOG_TAG = UserLoader.class.getName();
    private String mUrl;

    public UserLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<User> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        ArrayList<User> users = QueryUtils.fetchUserData(mUrl);
        FileCacher<ArrayList<User>> fc = new FileCacher<>(getContext(), "usama.srl");
        try {
            fc.writeCache(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
