package groups.vk.test.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.util.List;

import groups.vk.test.R;
import groups.vk.test.api.APIConstants;
import groups.vk.test.api.model.response.GroupWallList;

public class RecyclerWallListAdapter extends RecyclerView.Adapter<RecyclerWallListAdapter.ViewHolder> {
    private static final String TAG = RecyclerGroupListAdapter.class.getSimpleName();

    private Context mContext = null;

    private ArrayList<GroupWallList.Response.WallOneItem> mWallOneItemList = null;

    private String mGroupDomain = null;

    public RecyclerWallListAdapter(Context context, ArrayList<GroupWallList.Response.WallOneItem> wallOneItems) {
        super();

        this.mContext = context;
        this.mWallOneItemList = wallOneItems;
    }

    public void setGroupDomain(String domain){
        this.mGroupDomain = domain;
    }

    public void updateData(final List<GroupWallList.Response.WallOneItem> wallOneItems) {
        this.mWallOneItemList = new ArrayList<GroupWallList.Response.WallOneItem>(wallOneItems);

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        GroupWallList.Response.WallOneItem wallOneItem = mWallOneItemList.get(i);

        viewHolder.categoryName.setText(wallOneItem.getText());

        if(wallOneItem.getAttachments() != null && wallOneItem.getAttachments().size() > 0){
            GroupWallList.Response.WallOneItem.Attachments attachments = wallOneItem.getAttachments().get(0);

            if(attachments.getType().equals("photo")){
                Picasso.with(mContext)
                        .load(attachments.getPhoto().getPhoto())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(viewHolder.categoryImg);
            } else if(attachments.getType().equals("link")){
                viewHolder.categoryName.setText(attachments.getLink().getTitle());
            } else if(attachments.getType().equals("video")){
                viewHolder.categoryName.setText(attachments.getVideo().getTitle());

                Picasso.with(mContext)
                        .load(attachments.getVideo().getPhoto())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(viewHolder.categoryImg);
            }
        }

        // Listen for ListView Item Click
        viewHolder.categoryContentLayout.setTag(wallOneItem);
        viewHolder.categoryContentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GroupWallList.Response.WallOneItem wallOneItemClick = (GroupWallList.Response.WallOneItem) arg0.getTag();

                Uri.Builder urlBuilder = new Uri.Builder();

                urlBuilder.scheme(APIConstants.SCHEME)
                        .authority("vk.com")
                        .encodedPath(mGroupDomain);

                urlBuilder.appendQueryParameter(APIConstants.GROUP_TYPE_WALL, "wall" + String.valueOf(wallOneItemClick.getFromId()) + "_" + wallOneItemClick.getId());

                Log.i(TAG, urlBuilder.build().toString() + " : WallOneItem : " + wallOneItemClick.toString());

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBuilder.build().toString()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWallOneItemList.size();
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