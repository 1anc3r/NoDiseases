package me.lancer.nodiseases.mvp.disease.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.PresenterFragment;
import me.lancer.nodiseases.mvp.disease.IDiseaseView;
import me.lancer.nodiseases.mvp.disease.DiseaseBean;
import me.lancer.nodiseases.mvp.disease.DiseasePresenter;
import me.lancer.nodiseases.mvp.disease.adapter.DiseaseAdapter;

/**
 * Created by HuangFangzhi on 2016/12/18.
 */

public class DiseaseListFragment extends PresenterFragment<DiseasePresenter> implements IDiseaseView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvDisease;
    private LinearLayout llNote;
    private TextView tvNote;

    private DiseaseAdapter adpDisease;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<DiseaseBean> lDisease = new ArrayList<>();

    private int what, type, obj, last = 0, page = 1;
    private String name;

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
                        if (page == 1) {
                            lDisease.clear();
                        }
                        lDisease.addAll((List<DiseaseBean>) msg.obj);
                        adpDisease.notifyDataSetChanged();
//                        if (page == 1) {
//                            lDisease.clear();
//                        }
//                        lDisease.addAll((List<DiseaseBean>) msg.obj);
//                        adpDisease = new DiseaseAdapter(getActivity(), type, lDisease);
//                        rvDisease.setAdapter(adpDisease);
                    }
                    if (lDisease.size() == 0) {
                        rvDisease.setVisibility(View.GONE);
                        llNote.setVisibility(View.VISIBLE);
                    } else {
                        rvDisease.setVisibility(View.VISIBLE);
                        llNote.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 4:
                    if (msg.obj != null) {
//                        lDisease.clear();
//                        lDisease.addAll((List<DiseaseBean>) msg.obj);
//                        adpDisease.notifyDataSetChanged();
                        lDisease.clear();
                        lDisease.add((DiseaseBean) msg.obj);
//                        Log.e("item", ((DiseaseBean) msg.obj).getMessage());
                        adpDisease = new DiseaseAdapter(getActivity(), type, name, lDisease);
                        rvDisease.setAdapter(adpDisease);
                    }
                    if (lDisease.size() == 0) {
                        rvDisease.setVisibility(View.GONE);
                        llNote.setVisibility(View.VISIBLE);
                    } else {
                        rvDisease.setVisibility(View.VISIBLE);
                        llNote.setVisibility(View.GONE);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private Runnable loadList = new Runnable() {
        @Override
        public void run() {
            presenter.loadList(type);
        }
    };

    private Runnable loadPlace = new Runnable() {
        @Override
        public void run() {
            presenter.loadPlace(type, obj, page);
        }
    };

    private Runnable loadDepartment = new Runnable() {
        @Override
        public void run() {
            presenter.loadDepartment(type, obj, page);
        }
    };

    private Runnable loadName = new Runnable() {
        @Override
        public void run() {
            presenter.loadName(type, name);
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
        initData();
        initView(view);
    }

    private void initData() {
        what = getArguments().getInt("what");
        type = getArguments().getInt("id");
        name = getArguments().getString("name");
        obj = getArguments().getInt("obj");
        Log.e("keyword", what + " " + type + " " + name + " " + obj);
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
        rvDisease = (RecyclerView) view.findViewById(R.id.rv_list);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvDisease.setLayoutManager(mStaggeredGridLayoutManager);
        adpDisease = new DiseaseAdapter(getActivity(), type, name, lDisease);
        rvDisease.setAdapter(adpDisease);
        rvDisease.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == adpDisease.getItemCount()) {
                    page += 1;
                    if (what == 1) {
                        new Thread(loadPlace).start();
                    } else if (what == 2) {
                        new Thread(loadDepartment).start();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                last = getMax(mStaggeredGridLayoutManager.findLastVisibleItemPositions(new int[mStaggeredGridLayoutManager.getSpanCount()]));
            }
        });
        llNote = (LinearLayout) view.findViewById(R.id.ll_note);
        tvNote = (TextView) view.findViewById(R.id.tv_note);
        tvNote.setText("这里没有你想要的信息，去隔壁看看吧\n∑(っ°Д°)っ");
        if (what == 0) {
            new Thread(loadList).start();
        } else if (what == 1) {
            new Thread(loadPlace).start();
        } else if (what == 2) {
            new Thread(loadDepartment).start();
        } else if (what == 3) {
            new Thread(loadName).start();
        }
    }

    private int getMax(int[] arr) {
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    @Override
    protected DiseasePresenter onCreatePresenter() {
        return new DiseasePresenter(this);
    }

    @Override
    public void showList(List<DiseaseBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showPlace(List<DiseaseBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showDepartment(List<DiseaseBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showName(DiseaseBean bean) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = bean;
        handler.sendMessage(msg);
    }

    @Override
    public void showShow(DiseaseBean bean) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = bean;
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

