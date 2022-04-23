package cn.Travels_App.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.Travels_App.model.entity.Travels;
import cn.Travels_App.persenter.HomePerenter;
import cn.Travels_App.R;
import cn.Travels_App.ui.activity.HistoryActivity;
import cn.Travels_App.ui.activity.MainActivity;
import cn.Travels_App.ui.activity.MyTravelsActivity;
import cn.Travels_App.ui.adapter.TjTravelsAdapter;
import cn.Travels_App.utils.ToastUtils;
import cn.Travels_App.view.HomeView;
import cn.Travels_App.base.BaseFragment;

public class HomeFragment extends BaseFragment<HomeView, HomePerenter> implements HomeView {

    //推荐景区列表
    List<Travels> tjSpotsList;

    @BindView(R.id.spotstjListViewId)
    ListView listView;
    //景区目的地
    @BindView(R.id.h_spotsMddTv)
    TextView h_spotsMddTv;
    //编写游记
    @BindView(R.id.h_qa)
    TextView h_qa;
    //个人中心
    @BindView(R.id.h_my)
    TextView h_my;
    //我的游记
    @BindView(R.id.my_tr)
    TextView h_gy;
    //历史记录
    @BindView(R.id.my_trjil)
    TextView h_his;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.hScrollView)
    HorizontalScrollView horizontalScrollView;


    HomeView mView;

    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomePerenter createPresenter() {
        mView = getMvpView();
        return new HomePerenter(getApp(),mView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showProgress() {

    }
    //成功
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onFailed(String msg) {
        ToastUtils.showToast(getContext(),msg);
    }

    @Override
    public void loadData(List<Travels> tjSpotsList) {
        this.tjSpotsList = tjSpotsList;
    }


    @Override
    public int getLayoutId() {
        return R.layout.home_frgm;
    }

    @Override
    public void initView() {
        initData();
        TjTravelsAdapter adapter=new TjTravelsAdapter(getActivity(),R.layout.tjlist_item,tjSpotsList);
        listView.setAdapter(adapter);
        setListViewHeight(listView);

        //去除水平滚动条
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        bannerview();


        h_spotsMddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("gotoFragmentTag","1");
                startActivity(intent);
            }
        });
        h_qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("gotoFragmentTag","2");
                startActivity(intent);
            }
        });
        h_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Home0");
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("gotoFragmentTag","3");
                startActivity(intent);
            }
        });
        h_gy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Home1");
                Intent intent = new Intent(getActivity(),
                        MyTravelsActivity.class);
                startActivity(intent);
            }
        });
        h_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Home2");
                Intent intent = new Intent(getActivity(),
                        HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + 15;
        listView.setLayoutParams(params);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initData() {
        //轮播图
        image.clear();
        title.clear();
        image.add(R.drawable.lb1);
        image.add(R.drawable.lb1);
        image.add(R.drawable.lb1);
        image.add(R.drawable.lb1);
        image.add(R.drawable.lb1);
        title.add("不一样的美");
        title.add("不一样的美");
        title.add("不一样的美");
        title.add("不一样的美");
        title.add("不一样的美");
        //查找推荐的景区
        createPresenter().findTjTravels();
    }

    private void bannerview() {
        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setImageLoader(new MyImageLoader());

        banner.setImages(image);

        banner.setBannerAnimation(Transformer.Default);

        banner.isAutoPlay(true);

        banner.setBannerTitles(title);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        banner.setDelayTime(3000);

        banner.setOnBannerListener(this::OnBannerClick);

        banner.start();
    }

    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
    }

    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}
