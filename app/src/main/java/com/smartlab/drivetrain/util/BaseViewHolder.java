package com.smartlab.drivetrain.util;

import android.util.SparseArray;
import android.view.View;

/**
 * @Description:万能的viewHolder
 * 可防止view滑动过程中数据错位等问题
 */ 
public class BaseViewHolder {
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
