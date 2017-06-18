package com.gang.happylife.ui.newtextjoke;

import com.gang.happylife.base.basebean.ApiResponseWraperNoData;
import com.gang.happylife.bean.TextJokeBean;
import com.gang.happylife.mvp.BasePresenter;
import com.gang.happylife.mvp.BaseView;

import rx.Observable;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewTextJokeContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        Observable<ApiResponseWraperNoData<TextJokeBean>> getNewTextJokeData();
    }
}
