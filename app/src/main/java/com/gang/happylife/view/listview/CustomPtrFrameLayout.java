package com.gang.happylife.view.listview;

import android.content.Context;
import android.util.AttributeSet;

import com.gang.happylife.view.listview.header.WindmillHeader;

/**
 * Created by Administrator on 2017/7/2.
 */

public class CustomPtrFrameLayout extends PtrFrameLayout {
    public CustomPtrFrameLayout(Context context) {
        super(context);
    }

    public CustomPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        final WindmillHeader header = new WindmillHeader(getContext());
        setHeaderView(header);
        addPtrUIHandler(header);
    }

}
