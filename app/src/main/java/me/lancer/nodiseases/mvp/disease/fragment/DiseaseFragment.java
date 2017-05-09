package me.lancer.nodiseases.mvp.disease.fragment;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.fragment.BaseFragment;
import me.lancer.nodiseases.mvp.message.fragment.MessageListFragment;
import me.lancer.nodiseases.ui.activity.MainActivity;

public class DiseaseFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private Toolbar toolbar;
    private int index = 0;
    private int what, type, id;
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.t_large);
        toolbar.setTitle("疾病自查");
        ((MainActivity) getActivity()).initDrawer(toolbar);
        initTabLayout(view);
        initData();
        inflateMenu();
        initSearchView();
        initView();
    }

    private void initTabLayout(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.vp_tab);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        Fragment newfragment = new DiseaseListFragment();
        Bundle data = new Bundle();
        data.putAll(getArguments());
        data.putInt("id", 0);
        data.putString("title", "疾病名称");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "疾病名称");

        newfragment = new DiseaseListFragment();
        data = new Bundle();
        data.putAll(getArguments());
        data.putInt("id", 1);
        data.putString("title", "疾病症状");
        newfragment.setArguments(data);
        adapter.addFrag(newfragment, "疾病症状");

        viewPager.setAdapter(adapter);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            index = bundle.getInt(getString(R.string.index));
        }
        viewPager.setCurrentItem(index, true);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initView() {
    }

    private void initData() {
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
}
