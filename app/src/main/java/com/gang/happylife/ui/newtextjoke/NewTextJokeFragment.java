package com.gang.happylife.ui.newtextjoke;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gang.happylife.R;
import com.gang.happylife.api.Api;
import com.gang.happylife.base.baseparams.RequestParam;
import com.gang.happylife.mvp.MVPBaseFragment;
import com.gang.happylife.view.listview.CustomPtrFrameLayout;
import com.gang.happylife.view.listview.LoadMoreListView;
import com.gang.happylife.view.listview.PtrDefaultHandler;
import com.gang.happylife.view.listview.PtrFrameLayout;

import butterknife.BindView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewTextJokeFragment extends MVPBaseFragment<NewTextJokeContract.View, NewTextJokePresenter> implements NewTextJokeContract.View,MVPBaseFragment.OnReloadDataListener {

    @BindView(R.id.contentView)
    protected CustomPtrFrameLayout customPtrFrameLayout ;
    @BindView(R.id.list)
    protected LoadMoreListView listView ;
    private int currentPage = 1 ;
    private int pagetSize = 10 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //该句必须要加，否则会报错
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_new_text_joke);

        initListener();
        return getContentView();
    }

    private void initListener() {
        listView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                listView.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        customPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage=1 ;
                request(false);
            }
        });
        listView.setOnGetMoreListener(new LoadMoreListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                currentPage++;
                request(false);
            }
        });

    }

    @Override
    public void request(boolean isRefresh) {
        if (isRefresh){
            showLoadingPage("正在加载中...",R.mipmap.ic_loading);
        }
        RequestParam param = new RequestParam();
        param.put("key", Api.KEY);
        param.put("pagesize",pagetSize);
        param.put("page",currentPage);
        param.put("type","");
        mPresenter.getNewTextJokeData(param);
    }
}
