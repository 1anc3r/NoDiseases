package me.lancer.nodiseases.mvp.location.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.PresenterFragment;
import me.lancer.nodiseases.mvp.base.fragment.BaseFragment;
import me.lancer.nodiseases.mvp.disease.fragment.DiseaseFragment;
import me.lancer.nodiseases.mvp.location.ILocationView;
import me.lancer.nodiseases.mvp.location.LocationBean;
import me.lancer.nodiseases.mvp.location.LocationPresenter;
import me.lancer.nodiseases.mvp.system.fragment.SystemListFragment;
import me.lancer.nodiseases.ui.activity.MainActivity;
import me.lancer.nodiseases.util.LocationUtils;

public class LocationFragment extends PresenterFragment<LocationPresenter> implements ILocationView, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter {

    private Toolbar toolbar;
    private MarkerOptions markerOption;
    private AMap aMap;
    private MapView mMapView;
    private LatLng latlng;
    private Marker marker;
    private double x, y;
    private Bundle bundle;

    private int type = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    break;
                case 3:
                    if (msg.obj != null) {
                        for (LocationBean item : (List<LocationBean>) msg.obj) {
                            String address = item.getAddress();
                            if (address.length() > item.getName().length()) {
                                AddPosition(item.getX(), item.getY(), item.getName(), address.substring(0, item.getName().length()) + "\n" + address.substring(item.getName().length(), address.length()), type);
                            }else {
                                AddPosition(item.getX(), item.getY(), item.getName(), address, type);
                            }
                        }
                    }
                    mMapView.onCreate(bundle);
                    break;
            }
        }
    };

    private Runnable loadLocation = new Runnable() {
        @Override
        public void run() {
            presenter.loadLocation(type, x, y);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.t_large);
        toolbar.setTitle("附近医院");
        ((MainActivity) getActivity()).initDrawer(toolbar);
        mMapView = (MapView) view.findViewById(R.id.map);
        bundle = savedInstanceState;
        mMapView.onCreate(bundle);
        inflateMenu();
        initSearchView();
        initView();
        initData();
        mMapView.onCreate(bundle);
    }

    private void initView() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    private void initData() {
        AMapLocation location = new AMapLocationClient(getActivity()).getLastKnownLocation();
        try {
            JSONObject jb = LocationUtils.getLocation(location);
            if (jb != null) {
                if (!jb.has("error")) {
                    x = jb.getDouble("x");
                    y = jb.getDouble("y");
                    String address = jb.getString("address");
                    if (address.length() > 10) {
                        NowPosition(x, y, "我的位置", address.substring(0, address.length() / 2) + "\n" + address.substring(address.length() / 2, address.length()));
                    }else {
                        NowPosition(x, y, "我的位置", address);
                    }
                } else {
                    Log.e("error", jb.getString("error"));
                }
            }
            new Thread(loadLocation).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected LocationPresenter onCreatePresenter() {
        return new LocationPresenter(this);
    }

    private void setUpMap() {
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }

    public void NowPosition(double x, double y, String name, String address) {
        latlng = new LatLng(y, x);
        markerOption = new MarkerOptions();
        markerOption.position(new LatLng(y, x));
        markerOption.title(name).snippet(address);
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        marker = aMap.addMarker(markerOption);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(latlng).include(new LatLng(y + 1, x + 1)).include(new LatLng(y + 1, x - 1)).include(new LatLng(y - 1, x - 1)).include(new LatLng(y - 1, x + 1)).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20000));
    }

    public void AddPosition(double x, double y, String name, String address, int type) {
        latlng = new LatLng(y, x);
        markerOption = new MarkerOptions();
        markerOption.position(new LatLng(y, x));
        markerOption.title(name).snippet(address);
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker = aMap.addMarker(markerOption);
    }

    private void inflateMenu() {
        toolbar.inflateMenu(R.menu.menu_search);
    }

    private void initSearchView() {
        final SearchView searchView = (SearchView) toolbar.getMenu()
                .findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("搜索...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment newfragment = new DiseaseFragment();
                Bundle data = new Bundle();
                data.putInt("what", 3);
                data.putInt("obj", 0);
                data.putString("name", query);
                newfragment.setArguments(data);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_main, newfragment).commit();
                getActivity().invalidateOptionsMenu();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        showToast("你点击了infoWindow窗口" + marker.getTitle());
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        showToast("你点击的是" + marker.getTitle());
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
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

    @Override
    public void showList(List<LocationBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showLocation(List<LocationBean> list) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    public void showShow(LocationBean bean) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = bean;
        handler.sendMessage(msg);
    }

    @Override
    public void showName(LocationBean bean) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = bean;
        handler.sendMessage(msg);
    }
}
