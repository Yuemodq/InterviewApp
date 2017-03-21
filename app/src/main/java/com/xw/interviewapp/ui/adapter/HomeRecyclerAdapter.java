package com.xw.interviewapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xw.interviewapp.R;
import com.xw.interviewapp.bean.HomeRecyclerBean;

import java.util.List;
import java.util.Map;

/**
 * <br/>
 * 作者：XW <br/>
 * 邮箱：xw_appdev@163.com <br/>
 * 时间：2017-03-15 22:40
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerHolder> {
    
    private Context mContext;
    
    private List<HomeRecyclerBean> mDatas;
    
    private OnItemClickListener mListener;
    
    private HomeRecyclerHolder mHolder;
    
    public HomeRecyclerAdapter(Context ctx, List<HomeRecyclerBean> beanList) {
        mContext = ctx;
        mDatas = beanList;
    }
    
    @Override
    public HomeRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mHolder = new HomeRecyclerHolder(inflater.inflate(R.layout.item_home, parent, false));
        return mHolder;
    }
    
    @Override
    public void onBindViewHolder(final HomeRecyclerHolder holder, final int position) {
        Glide.with(mContext)
                .load(mDatas.get(position).getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.iv_img);
        holder.tv_name.setText(mDatas.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    
    public class HomeRecyclerHolder extends RecyclerView.ViewHolder {
        
        public ImageView iv_img;
        
        TextView tv_name;
    
        public HomeRecyclerHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ActivityCompat.setEnterSharedElementCallback((Activity) mContext, new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put("iv_img", iv_img);
                }
            });
//            ActivityCompat.setEnterSharedElementCallback(new SharedElementCallback() {
//                @Override
//                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                    sharedElements.put("iv_img", iv_img);
//                }
//            });
//            int width = ((Activity) iv_img.getContext()).getWindowManager().getDefaultDisplay().getWidth();
//            ViewGroup.LayoutParams params = iv_img.getLayoutParams();
//            //设置图片的相对于屏幕的宽高比
//            params.width = width/3;
//            params.height =  (int) (200 + Math.random() * 400) ;
//            iv_img.setLayoutParams(params);
        }
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    
    public ImageView getImageView() {
        return mHolder.iv_img;
    }
    
}


























