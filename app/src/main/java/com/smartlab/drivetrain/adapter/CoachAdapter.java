package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.CoachBrief;
import com.smartlab.drivetrain.model.Discuss;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.util.ImageLoaders;
import com.smartlab.drivetrain.view.HandyTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by smartlab on 15/9/27.
 *
 */
public class CoachAdapter extends BaseAdapter {
    private List<CoachBrief> coach;
    private Context context;
    private ImageLoader imageLoader;
    public CoachAdapter(List<CoachBrief> coach,Context context){
        this.coach = coach;
        this.context = context;
        imageLoader = MainApplication.getImageLoader();
    }
    @Override
    public int getCount() {
        return coach.size();
    }

    @Override
    public Object getItem(int i) {
        return coach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder;
        if (view == null){
            viewholder=new Viewholder();
            view = LayoutInflater.from(context).inflate(R.layout.coach,viewGroup,false);
            viewholder.icon = BaseViewHolder.get(view, R.id.driving_image);
            viewholder.title = BaseViewHolder.get(view,R.id.driving_title);
            viewholder.address = BaseViewHolder.get(view,R.id.driving_description);
            view.setTag(viewholder);
        }
//        TextView phone = BaseViewHolder.get(view,R.id.driving_call);
        else
        {
            viewholder= (Viewholder) view.getTag();
        }
//        CoachBrief temp = coach.get(i);
        try {
            viewholder.icon.setTag(Params.ImageCoach + URLEncoder.encode(coach.get(i).getCoachName() + ".jpg","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Log.i("sysy",Params.ImageCoach + URLEncoder.encode(coach.get(i).getCoachName() + ".jpg","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            new ImageLoaders().showImageBuAsyncTask(viewholder.icon, Params.ImageCoach + URLEncoder.encode(coach.get(i).getCoachName() + ".jpg","UTF-8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewholder.title.setText(coach.get(i).getCoachName());
        viewholder.address.setText("所属驾校：" + "晶崴驾校");//(coach.get(i).getBelongTo());
        Discuss.coachTimestamp=coach.get(i).getCoachTimestamp();

//        imageLoader.displayImage(temp.getCoachPic(),imageView);
//        File image = new File(temp.getCoachPic());//    文件是否存在
//        if (image.exists()){
//            Bitmap bitmap = PhotoUtils.resizeImage(temp.getCoachPic(), 100, 100);
//            imageView.setImageBitmap(bitmap);
//        }


//        title.setText(temp.getCoachName());
//        address.setText(temp.getBelongTo());
//        phone.setText(temp.getPhone());
        return view;
    }
    class Viewholder
    {
        public ImageView icon;
        public TextView title,address;
    }
}
