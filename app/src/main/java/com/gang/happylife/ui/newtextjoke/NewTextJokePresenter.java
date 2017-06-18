package com.gang.happylife.ui.newtextjoke;

import com.gang.happylife.base.basebean.ApiResponseWraperNoData;
import com.gang.happylife.bean.TextJokeBean;
import com.gang.happylife.mvp.BasePresenterImpl;

import rx.Observable;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewTextJokePresenter extends BasePresenterImpl<NewTextJokeContract.View> implements NewTextJokeContract.Presenter{

    @Override
    public Observable<ApiResponseWraperNoData<TextJokeBean>> getNewTextJokeData() {
        return null;
    }
}
