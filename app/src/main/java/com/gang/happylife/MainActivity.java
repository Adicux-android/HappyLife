package com.gang.happylife;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gang.happylife.ui.circle.CircleFragment;
import com.gang.happylife.ui.home.HomeFragment;
import com.gang.happylife.ui.imagejoke.ImageJokeFragment;
import com.gang.happylife.ui.person.PersonFragment;
import com.gang.happylife.ui.textjoke.TextJokeFragment;
import com.gang.happylife.view.tab.BarEntity;
import com.gang.happylife.view.tab.BottomTabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomTabBar.OnSelectListener{

    @BindView(R.id.tb)
    protected BottomTabBar tb ;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    private List<BarEntity> bars ;
    private HomeFragment homeFragment ;
    private TextJokeFragment textJokeFragment ;
    private ImageJokeFragment imageJokeFragment ;
    private PersonFragment personFragment ;
    private CircleFragment circleFragment ;
    private FragmentManager manager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initNavigationViewEvent();
        initBottomTabBar();
    }

    private void initBottomTabBar() {
        manager = getSupportFragmentManager();
        tb.setManager(manager);
        tb.setOnSelectListener(this);
        bars = new ArrayList<>();
        bars.add(new BarEntity("主页",R.mipmap.ic_home_select,R.mipmap.ic_home_unselect));
        bars.add(new BarEntity("段子",R.mipmap.ic_textjoke_select,R.mipmap.ic_textjoke_unselect));
        bars.add(new BarEntity("趣图",R.mipmap.ic_imagejoke_select,R.mipmap.ic_imagejoke_unselect));
        bars.add(new BarEntity("圈子",R.mipmap.ic_dt_select,R.mipmap.ic_dt_unselect));
        bars.add(new BarEntity("个人",R.mipmap.ic_my_select,R.mipmap.ic_my_unselect));
        tb.setBars(bars);
    }

    private void initNavigationViewEvent() {
        //还原navigationview item原生的颜色
//        navigationView.setItemTextColor(null);
//        navigationView.setItemIconTintList(null);
        /**设置MenuItem默认选中项**/
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setCheckable(true);//设置选项可选
                item.setChecked(true);//设置选型被选中

                switch (item.getItemId()){
                    case R.id.nav_news:
                        break;
                    case R.id.dynamic_picture:
                        break;
                    case R.id.nav_video:
                        break;
                }
                mDrawerLayout.closeDrawers();//关闭侧边菜单栏
                return true;
            }
        });
    }

    @Override
    public void onSelect(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                tb.switchContent(homeFragment);
                break;
            case 1:
                if (textJokeFragment == null) {
                    textJokeFragment = new TextJokeFragment();
                }
                tb.switchContent(textJokeFragment);
                break;
            case 2:
                if (imageJokeFragment == null) {
                    imageJokeFragment = new ImageJokeFragment();
                }
                tb.switchContent(imageJokeFragment);
                break;
            case 3:
                if (circleFragment == null) {
                    circleFragment = new CircleFragment();
                }
                tb.switchContent(circleFragment);
                break;
            case 4:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                }
                tb.switchContent(personFragment);
                break;
            default:
                break;
        }
    }
}
