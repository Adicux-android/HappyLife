package com.gang.happylife.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gang.happylife.R;
import com.gang.happylife.mvp.MVPBaseFragment;
import com.gang.happylife.ui.newimagejoke.NewImageJokeFragment;
import com.gang.happylife.ui.newtextjoke.NewTextJokeFragment;

import butterknife.BindView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class HomeFragment extends MVPBaseFragment<HomeContract.View, HomePresenter> implements HomeContract.View {

    @BindView(R.id.group)
    protected RadioGroup group ;
    @BindView(R.id.item1)
    protected RadioButton item1 ;
    @BindView(R.id.item2)
    protected RadioButton item2 ;
    private NewTextJokeFragment textJokeFragment ;
    private NewImageJokeFragment imageJokeFragment ;
    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //该句必须要加，否则会报错
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        initView();
        return getContentView();
    }

    private void initView() {
        manager = getChildFragmentManager();
        textJokeFragment = new NewTextJokeFragment();
        switchContent(textJokeFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.item1){
                    if (textJokeFragment==null){
                        textJokeFragment = new NewTextJokeFragment();
                    }
                    switchContent(textJokeFragment);
                    item1.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
                    item2.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                }else if (checkedId==R.id.item2){
                    if (imageJokeFragment==null){
                        imageJokeFragment = new NewImageJokeFragment();
                    }
                    switchContent(imageJokeFragment);
                    item1.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                    item2.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
                }
            }
        });
    }

    private Fragment current;
    /**
     * 切换当前显示的fragment
     */
    public void switchContent(Fragment to) {
        if (current != to) {
            FragmentTransaction transaction = manager.beginTransaction();

            if (current != null) {
                transaction.hide(current);
            }
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.add(R.id.f_content, to).commit();
            } else {

                transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            current = to;
        }
    }
}
