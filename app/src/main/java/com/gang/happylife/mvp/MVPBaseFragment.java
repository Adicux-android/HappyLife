package com.gang.happylife.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.happylife.R;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class MVPBaseFragment<V extends BaseView,T extends BasePresenterImpl<V>> extends Fragment implements BaseView{
    public T mPresenter;
    private View view ;
    private LayoutInflater inflater;
    private ViewGroup container ;
    private View errorView ,contentView;
    private TextView error_tv ;
    private ImageView error_iv ;
    private RotateAnimation animation ;

    private View loginView ;
    private TextView loginTv ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);
    }
    public View setContentView(int resourceId) {
        view = inflater.inflate(resourceId, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    public View getContentView(){
        return  this.view ;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater ;
        this.container = container ;
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public  <T> T getInstance(Object o, int i) {
            try {
                return ((Class<T>) ((ParameterizedType) (o.getClass()
                        .getGenericSuperclass())).getActualTypeArguments()[i])
                        .newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }

    /**
     * 显示加载页面
     * @param tip
     * @param resId
     */
    public void showLoadingPage(String tip,int resId){
//        init();
        if (errorView==null){
            return;
        }
        if (error_iv==null){
            return;
        }
        if (error_tv==null){
            return;
        }
        if (contentView==null){
            return;
        }
        if (contentView==null){
            return;
        }
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
//        if (loginView!=null){
//            loginView.setVisibility(View.GONE);
//        }
        if (!TextUtils.isEmpty(tip)){
            error_tv.setText(tip);
        }else {
            error_tv.setText("数据正在加载...");
        }
        error_iv.setImageResource(resId);
        /** 设置旋转动画 */
        if (animation==null){
            animation =new RotateAnimation(0f,359f, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1000);//设置动画持续时间
            /** 常用方法 */
            animation.setRepeatCount(Integer.MAX_VALUE);//设置重复次数
            animation.startNow();
        }
        error_iv.setAnimation(animation);
    }
    /**
     * 显示错误页面
     * @param message
     * @param resId
     */
    public void showErrorView(String message,int resId){
        init();
        if (errorView==null){
            return;
        }
        if (error_iv==null){
            return;
        }
        if (error_tv==null){
            return;
        }
        if (contentView==null){
            return;
        }
        if (contentView==null){
            return;
        }
//        if (loginView!=null){
//            loginView.setVisibility(View.GONE);
//        }
        error_iv.setImageResource(resId);
        if (!TextUtils.isEmpty(message)){
            error_tv.setText(message);
        }else {
            error_tv.setText("数据加载失败！");
        }
        error_iv.setAnimation(null);
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    private void init() {
        errorView = findViewById(R.id.errorView);
        if (errorView!=null){
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReloadDataListener!=null){
                        onReloadDataListener.request(true);
                    }
                }
            });
        }
        error_iv = (ImageView) findViewById(R.id.error_iv);
        error_tv = (TextView) findViewById(R.id.error_tv);
        contentView = findViewById(R.id.contentView);
//        loginView = findViewById(R.id.loginView);
//        loginTv = (TextView) findViewById(R.id.login_tv);
    }
    /**
     * 获取控件id
     * @param id
     * @return
     */
    public View findViewById(int id){
        return view.findViewById(id) ;
    }

    private OnReloadDataListener onReloadDataListener;

    public void setOnReloadDataListener(OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    public interface OnReloadDataListener{
        void request(boolean isRefresh);
    }
}
