
package in.usamabinluha.www.zohousama;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class UserActivity extends AppCompatActivity
        implements LoaderCallbacks<List<User>> {

    private static final String USER_REQUEST_URL =
            "https://reqres.in/api/users?page=";
    private static boolean firstBoot = true;
    private static final int USER_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private TextView mEmptyStateTextView;
    private static UserRoomDb roomDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        intializeUi();

        SharedPreferences prefs = getSharedPreferences("test", MODE_PRIVATE);
        firstBoot = prefs.getBoolean("firstBoot", true);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && firstBoot) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(USER_LOADER_ID, null, this);
            SharedPreferences.Editor editor = getSharedPreferences("test", MODE_PRIVATE).edit();
            editor.putBoolean("firstBoot", false);
            editor.apply();
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            List<User> offlineList;
            roomDb = Room.databaseBuilder(getApplicationContext(), UserRoomDb.class, "userdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
            offlineList =roomDb.userDao().getUsers();
            mEmptyStateTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (offlineList != null && !offlineList.isEmpty()) {
                myAdapter = new MyAdapter(offlineList, UserActivity.this);
                recyclerView.setAdapter(myAdapter);
            }
        }
    }

    private void intializeUi() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
        return new UserLoader(this, Uri.parse(USER_REQUEST_URL).toString());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (users != null && !users.isEmpty()) {
            myAdapter = new MyAdapter(users, this);
            recyclerView.setAdapter(myAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
    }

}