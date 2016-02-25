package groups.vk.test.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import groups.vk.test.GroupDetailActivity;
import groups.vk.test.R;
import groups.vk.test.api.model.request.VKUserObject;
import groups.vk.test.api.model.response.GroupList;

public class RecyclerGroupListAdapter extends RecyclerView.Adapter<RecyclerGroupListAdapter.ViewHolder> {
    private static final String TAG = RecyclerGroupListAdapter.class.getSimpleName();

    private Context mContext = null;

    private ArrayList<GroupList.Response.GroupOneItem> mMainGroupList = null;

    private VKUserObject mVKUserObject = null;

    public RecyclerGroupListAdapter(Context context, ArrayList<GroupList.Response.GroupOneItem> groupLists, VKUserObject vkUserObject) {
        super();

        this.mContext = context;
        this.mMainGroupList = groupLists;
        this.mVKUserObject = vkUserObject;
    }

    public void updateData(final ArrayList<GroupList.Response.GroupOneItem> groupLists) {
        this.mMainGroupList = groupLists;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        GroupList.Response.GroupOneItem groupOneItem = mMainGroupList.get(i);

        viewHolder.categoryName.setText(groupOneItem.getName());

        Picasso.with(mContext)
                .load(groupOneItem.getPhoto_50())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.categoryImg);

        // Listen for ListView Item Click
        viewHolder.categoryContentLayout.setTag(groupOneItem);
        viewHolder.categoryContentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GroupList.Response.GroupOneItem groupOneItemClick = (GroupList.Response.GroupOneItem) arg0.getTag();
                groupOneItemClick.setAccessToken(mVKUserObject.getAccessToken());

                Log.i(TAG, "GroupOneItem : " + groupOneItemClick.toString());

                Intent intent = new Intent(mContext, GroupDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Put Category object data
                intent.putExtra(GroupDetailActivity.EXTRA_VK_GROUP_OBJECT, groupOneItemClick);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMainGroupList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView categoryImg;
        public TextView categoryName;

        public RelativeLayout categoryContentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryImg = (ImageView) itemView.findViewById(R.id.img_group);
            categoryName = (TextView) itemView.findViewById(R.id.txt_group_name);

            categoryContentLayout = (RelativeLayout) itemView.findViewById(R.id.content);
        }
    }
}
