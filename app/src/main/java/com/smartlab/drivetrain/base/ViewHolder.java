package com.smartlab.drivetrain.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by smartlab on 15/12/26.
 * holder
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    public ImageLoader imageLoader;
    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position)
    {
        this.mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mPosition = position;
        imageLoader = ImageLoader.getInstance();
        this.mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context Context
     * @param convertView 当前视=图
     * @param parent 父容器
     * @param layoutId 布局id
     * @param position 位置
     * @return ViewHolder
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position)
    {
        if (convertView == null)
        {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public ViewHolder setVisibility(int viewId,int tag){
        getView(viewId).setVisibility(tag);
        return this;
    }
    /**
     *
     * @param resId 通过id获取String
     * @return String
     */
    public String getResString(int resId){
        return mContext.getResources().getString(resId);
    }
    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     *
     * @param viewID 视图ID
     * @param formatResId 格式化
     * @param resText String
     * @return ViewHolder
     */
    public ViewHolder setTextResource(int viewID,int formatResId,int resText){
        TextView view = getView(viewID);
        view.setText(mContext.getResources()
                .getString(formatResId, getResString(resText)));
        return this;
    }

    /**
     *
     * @param format 格式
     * @param resText String
     * @return String
     */
    public String formatByRes(int format,int resText){
        return mContext.getResources().getString(format,getResString(resText));
    }

    /**
     *
     * @param format 格式
     * @param text String
     * @return String
     */
    public String formatByText(int format,String text){
        return mContext.getResources().getString(format,text);
    }
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId ViewId
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     *
     * @param viewId 视图Id
     * @param textRes 资源id
     * @param text String
     * @return ViewHolder 对象
     */
    public ViewHolder setTextByResource(int viewId, int textRes, String text){
        TextView view = getView(viewId);
        view.setText(mContext.getResources().getString(textRes, text));
        return this;
    }
    /**
     * 为TextView设置字符串
     *
     * @param viewId ViewId
     * @param text text
     * @return holder
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId imageView id
     * @param drawableId 背景图resid
     * @return ViewHolder
     */
    public ViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId viewId
     * @return View holder
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageWithUrl(int viewId, String url)
    {
        imageLoader.displayImage(url, (ImageView) getView(viewId));
        return this;
    }

    public int getPosition()
    {
        return mPosition;
    }

}
