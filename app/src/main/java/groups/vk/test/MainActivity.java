package groups.vk.test;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import groups.vk.test.adapters.RecyclerGroupListAdapter;
import groups.vk.test.api.APIConstants;
import groups.vk.test.api.model.request.VKUserObject;
import groups.vk.test.api.model.response.GroupList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_VK_USER_OBJECT = "vk_user_object";

    private VKUserObject mVKUserObject = null;

    // View
    private RecyclerView mRecyclerView = null;
    // Manager
    private RecyclerView.LayoutManager mLayoutManager = null;
    // Adapter
    private RecyclerGroupListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if(intent != null) {
            // Get VK User Object
            mVKUserObject = (VKUserObject) intent.getSerializableExtra(EXTRA_VK_USER_OBJECT);
        }

        initUI();
    }

    private void initUI(){
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerGroupListAdapter(getApplicationContext(), new ArrayList<GroupList.Response.GroupOneItem>(), mVKUserObject);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    /**
                     * TODO
                     *
                     * Link :  https://vk.com/dev/groups.search?params[q]=Music&params[future]=0&params[market]=0&params[offset]=3&params[count]=3&params[v]=5.45
                     */

                    Uri.Builder urlBuilder = new Uri.Builder();

                    urlBuilder.scheme(APIConstants.SCHEME)
                            .authority(APIConstants.BASIC_URL)
                            .appendPath(APIConstants.PATH)
                            .appendPath(APIConstants.METHOD_GROUP_SEARCH);

                    urlBuilder.appendQueryParameter(APIConstants.SEARCH_TEXT_PARAM, query);
                    urlBuilder.appendQueryParameter(APIConstants.VK_VERSION_PARAM, "5.45");
                    urlBuilder.appendQueryParameter(APIConstants.ACCESS_TOKEN_PARAM, mVKUserObject.getAccessToken());
                    urlBuilder.appendQueryParameter(APIConstants.GROUP_TYPE_PARAM, "page");

                    // Init Request and result
                    Future<Response<GroupList>> result = Ion.with(getApplicationContext())
                            .load(urlBuilder.build().toString())
                            .setLogging(TAG, Log.DEBUG)
                            .as(new TypeToken<GroupList>() {
                            })
                            .withResponse()
                            .setCallback(new FutureCallback<Response<GroupList>>() {

                                @Override
                                public void onCompleted(Exception e, Response<GroupList> response) {
                                    Log.e(TAG, "Exception : " + e);
                                }
                            });

                    GroupList groupList = null;

                    try {
                        groupList = result.get().getResult();

                        Log.i(TAG, "GroupList : " + groupList.toString());

                        if(mAdapter!=null){
                            mAdapter.updateData(groupList.getResponse().getItems());
                        }
                    } catch (NullPointerException | InterruptedException | ExecutionException e) {
                        Log.e(TAG, "Exeption : " + e.getMessage());
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return true;
                }
            });
        }

        return true;
    }
}
