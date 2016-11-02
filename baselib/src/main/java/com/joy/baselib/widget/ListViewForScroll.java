package com.joy.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 扩展ListView,解决与ScrollView冲突不能展开问题
 * @author ymx
 */
public class ListViewForScroll extends ListView {

	public ListViewForScroll(Context context) {
		super(context);
	}

	public ListViewForScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}