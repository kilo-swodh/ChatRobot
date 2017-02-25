package com.example.a45556.chatrobot.net;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a45556.chatrobot.R;

import java.util.List;

/**
 * Created by 45556 on 2017-2-24.
 */

public class ChatAdapter extends BaseAdapter {

    private List<ChatBean> lists;
    private Context context;
    private int resourceId;
    private RelativeLayout layout;

    public ChatAdapter(Context context,int resourceId,List<ChatBean> lists){
        this.lists = lists;
        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatBean msg = lists.get(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.leftLayout = (RelativeLayout) view.findViewById(R.id.layout_left);
            holder.rightLayout = (RelativeLayout) view.findViewById(R.id.layout_right);
            holder.leftMsg = (TextView) view.findViewById(R.id.tvl_text);
            holder.rightMsg = (TextView) view.findViewById(R.id.tvr_text);
            holder.leftAvatar = (ImageView) view.findViewById(R.id.ivl_avatar);
            holder.rightAvatar = (ImageView) view.findViewById(R.id.ivr_avatar);
            holder.leftTime = (TextView) view.findViewById(R.id.tvl_time);
            holder.rightTime = (TextView) view.findViewById(R.id.tvr_time);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        String time = lists.get(position).getTime();
        if (msg.getFlag() == ChatBean.TYPE_RECEIVER){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getText());
            if (time.equals("")){
                holder.leftTime.setVisibility(View.GONE);
            }else {
                holder.leftTime.setText(time);
            }
        }else {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getText());
            if (time.equals("")){
                holder.rightTime.setVisibility(View.GONE);
            }else {
                holder.rightTime.setText(time);
            }
        }
        return view;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView leftAvatar;
        ImageView rightAvatar;
        TextView leftTime;
        TextView rightTime;
    }
}
