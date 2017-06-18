package com.gang.happylife.ui.newtextjoke;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gang.happylife.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewTextJokeFragment extends MVPBaseFragment<NewTextJokeContract.View, NewTextJokePresenter> implements NewTextJokeContract.View {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getNewTextJokeData();
    }
}
