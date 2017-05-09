package me.lancer.nodiseases.mvp.system.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.PresenterFragment;
import me.lancer.nodiseases.mvp.system.ISystemView;
import me.lancer.nodiseases.mvp.system.SystemBean;
import me.lancer.nodiseases.mvp.system.SystemPresenter;
import me.lancer.nodiseases.mvp.system.adapter.SystemAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class SystemListFragment extends PresenterFragment<SystemPresenter> implements ISystemView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvSystem;

    private SystemAdapter adpSystem;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<SystemBean> mList = new ArrayList<>();

    private int type;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        mList.clear();
                        mList.addAll((List<SystemBean>) msg.obj);
                        adpSystem = new SystemAdapter(getActivity(), type, mList);
                        rvSystem.setAdapter(adpSystem);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadAll = new Runnable() {
        @Override
        public void run() {
            presenter.loadAll(type);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
    }

    private void initData() {
        type = getArguments().getInt("id");
        new Thread(loadAll).start();
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.teal, R.color.green, R.color.yellow, R.color.orange, R.color.red, R.color.pink, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessageDelayed(msg, 800);
            }
        });
        rvSystem = (RecyclerView) view.findViewById(R.id.rv_list);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvSystem.setLayoutManager(mStaggeredGridLayoutManager);
        rvSystem.setItemAnimator(new DefaultItemAnimator());
        rvSystem.setHasFixedSize(true);
        adpSystem = new SystemAdapter(getActivity(), type, mList);
        adpSystem.setHasStableIds(true);
        rvSystem.setAdapter(adpSystem);
    }

    @Override
    protected SystemPresenter onCreatePresenter() {
        return new SystemPresenter(this);
    }

    @Override
    public void showAll(List<SystemBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showMsg(String log) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = log;
        handler.sendMessage(msg);
    }

    @Override
    public void showLoad() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void hideLoad() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }
}

