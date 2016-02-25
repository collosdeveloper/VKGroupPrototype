package groups.vk.test;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import groups.vk.test.adapters.RecyclerWallListAdapter;
import groups.vk.test.api.APIConstants;
import groups.vk.test.api.model.response.GroupList;
import groups.vk.test.api.model.response.GroupWallList;

public class GroupDetailActivity extends AppCompatActivity {
    private static final String TAG = GroupDetailActivity.class.getSimpleName();

    public static final String EXTRA_VK_GROUP_OBJECT = "vk_group_object";

    private GroupList.Response.GroupOneItem mGroupOneItem = null;

    // View
    private RecyclerView mRecyclerView = null;
    // Manager
    private RecyclerView.LayoutManager mLayoutManager = null;
    // Adapter
    private RecyclerWallListAdapter mAdapter = null;

    private ActionBar mActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_detail);

        try {
            mActionBar = getSupportActionBar();
        } catch (NullPointerException e){
            Log.e(TAG, "Exeption : " + e.getMessage());
        }

        Intent intent = getIntent();

        if(intent != null) {
            // Get VK Group Object
            mGroupOneItem = (GroupList.Response.GroupOneItem) intent.getSerializableExtra(EXTRA_VK_GROUP_OBJECT);

            if(mActionBar != null){
                mActionBar.setTitle(mGroupOneItem.getName());
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        initUI();
    }

    private void initUI(){
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(GroupDetailActivity.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerWallListAdapter(getApplicationContext(), new ArrayList<GroupWallList.Response.WallOneItem>());
        mRecyclerView.setAdapter(mAdapter);

        /**
         * TODO
         *
         * Link :  https://vk.com/dev/wall.get
         * Link :  https://vk.com/dev/post
         * Link :  https://vk.com/dev/wall.getById
         */

        Uri.Builder urlBuilder = new Uri.Builder();

        urlBuilder.scheme(APIConstants.SCHEME)
                .authority(APIConstants.BASIC_URL)
                .appendPath(APIConstants.PATH)
                .appendPath(APIConstants.METHOD_WALL_GET);

        urlBuilder.appendQueryParameter(APIConstants.GROUP_TYPE_OWNER_ID, "-" + String.valueOf(mGroupOneItem.getId()));
        urlBuilder.appendQueryParameter(APIConstants.VK_VERSION_PARAM, "5.45");
        urlBuilder.appendQueryParameter(APIConstants.ACCESS_TOKEN_PARAM, mGroupOneItem.getAccessToken());
        urlBuilder.appendQueryParameter(APIConstants.GROUP_TYPE_COUNT, "20");
        urlBuilder.appendQueryParameter(APIConstants.GROUP_TYPE_FILTER, "owner");

        // Init Request and result
        Future<Response<GroupWallList>> result = Ion.with(getApplicationContext())
                .load(urlBuilder.build().toString())
                .setLogging(TAG, Log.DEBUG)
                .as(new TypeToken<GroupWallList>() {
                })
                .withResponse()
                .setCallback(new FutureCallback<Response<GroupWallList>>() {

                    @Override
                    public void onCompleted(Exception e, Response<GroupWallList> response) {
                        Log.e(TAG, response.getResult() + " : Exception : " + e);
                    }
                });

        GroupWallList groupWallList = null;

        try {
            groupWallList = result.get().getResult();

            Log.i(TAG, groupWallList + " : GroupWallList : " + groupWallList.toString());

            if(mAdapter!=null){
                mAdapter.updateData(groupWallList.getResponse().getItems());
                mAdapter.setGroupDomain(mGroupOneItem.getScreenName());
            }
        } catch (NullPointerException | InterruptedException | ExecutionException e) {
            Log.e(TAG, groupWallList + " : Exeption : " + e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
