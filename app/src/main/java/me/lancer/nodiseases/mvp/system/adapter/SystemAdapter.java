package me.lancer.nodiseases.mvp.system.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.disease.fragment.DiseaseFragment;
import me.lancer.nodiseases.mvp.system.SystemBean;
import me.lancer.nodiseases.ui.activity.MainActivity;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.ViewHolder> {

    private List<SystemBean> list;
    private Context context;
    private int type;

    public SystemAdapter(Context context, int type, List<SystemBean> list) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            if (list.get(position).getList() != null) {
                viewHolder.tvTitle.setText(" " + list.get(position).getName());
                viewHolder.rvList.setVisibility(View.VISIBLE);
                StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                viewHolder.rvList.setLayoutManager(mStaggeredGridLayoutManager);
                viewHolder.rvList.setItemAnimator(new DefaultItemAnimator());
                viewHolder.rvList.setHasFixedSize(true);
                SystemAdapter adapter = new SystemAdapter(context, type, list.get(position).getList());
                adapter.setHasStableIds(true);
                viewHolder.rvList.setAdapter(adapter);
                viewHolder.tvTitle.setTextSize(24);
            } else {
                viewHolder.tvTitle.setText(list.get(position).getName());
                viewHolder.tvTitle.setGravity(Gravity.CENTER);
                viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
                viewHolder.cvGrid.setCardBackgroundColor(context.getResources().getColor(R.color.lightblue));
            }
            viewHolder.cvGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newfragment = new DiseaseFragment();
                    Bundle data = new Bundle();
                    data.putInt("what", type + 1);
                    data.putString("name", list.get(position).getName());
                    data.putInt("obj", list.get(position).getId());
                    newfragment.setArguments(data);
                    FragmentTransaction fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main, newfragment).commit();
                    ((MainActivity) context).invalidateOptionsMenu();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvGrid;
        public TextView tvTitle;
        public RecyclerView rvList;

        public ViewHolder(View rootView) {
            super(rootView);
            cvGrid = (CardView) rootView.findViewById(R.id.cv_grid);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            rvList = (RecyclerView) rootView.findViewById(R.id.rv_list);
        }
    }
}
