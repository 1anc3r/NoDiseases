package me.lancer.nodiseases.mvp.disease.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.disease.DiseaseBean;
import me.lancer.nodiseases.mvp.disease.activity.DiseaseDetailActivity;
import me.lancer.nodiseases.mvp.message.activity.MessageDetailActivity;
import me.lancer.nodiseases.util.LruImageCache;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {

    private static final int TYPE_CONTENT_LARGE = -1;
    private static final int TYPE_CONTENT_SMALL = 0;
    private static final int TYPE_TITLE = 1;

    private List<DiseaseBean> list;
    private RequestQueue mQueue;
    private Context context;
    private int type;
    private String keyword;
    private DiseaseBean item;

    public DiseaseAdapter(Context context, int type, String keyword, List<DiseaseBean> list) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.keyword = keyword;
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medimu_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if ((item = list.get(position)) != null) {
            if (getItemViewType(position) == TYPE_CONTENT_SMALL) {
                ForegroundColorSpan span;
                SpannableStringBuilder builder;
                if (keyword != null) {
                    String sName = item.getName();
                    if ((sName != null && sName.contains(keyword))) {
                        span = new ForegroundColorSpan(Color.RED);
                        builder = new SpannableStringBuilder(sName);
                        int index1 = sName.indexOf(keyword);
                        if (index1 != -1) {
                            builder.setSpan(span, index1, index1 + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        viewHolder.tvTitle.setText(builder);
                    } else {
                        viewHolder.tvTitle.setText(sName);
                    }
                    String sKeyword = "关键字 : " + item.getKeywords();
                    if ((sKeyword != null && sKeyword.contains(keyword))) {
                        span = new ForegroundColorSpan(Color.RED);
                        builder = new SpannableStringBuilder(sKeyword);
                        int index1 = sKeyword.indexOf(keyword);
                        if (index1 != -1) {
                            builder.setSpan(span, index1, index1 + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        viewHolder.tvKeyword.setText(builder);
                    } else {
                        viewHolder.tvKeyword.setText(sKeyword);
                    }
                    String sPlace = "发病处 : " + item.getPlace();
                    if ((sPlace != null && sPlace.contains(keyword))) {
                        span = new ForegroundColorSpan(Color.RED);
                        builder = new SpannableStringBuilder(sPlace);
                        int index1 = sPlace.indexOf(keyword);
                        if (index1 != -1) {
                            builder.setSpan(span, index1, index1 + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        viewHolder.tvPlace.setText(builder);
                    } else {
                        viewHolder.tvPlace.setText(sPlace);
                    }
                } else {
                    viewHolder.tvTitle.setText(item.getName());
                    viewHolder.tvPlace.setText("发病处 : " + item.getPlace());
                    viewHolder.tvKeyword.setText("关键字 : " + item.getKeywords());
                }
                LruImageCache cache = LruImageCache.instance();
                ImageLoader loader = new ImageLoader(mQueue, cache);
                viewHolder.ivImg.setDefaultImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
                viewHolder.ivImg.setImageUrl(item.getImg(), loader);
                viewHolder.cvLarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DiseaseDetailActivity.startActivity((Activity) context, item.getId(), type, list.get(position).getName(), list.get(position).getImg(), viewHolder.ivImg);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == -1) {
            return TYPE_CONTENT_LARGE;
        } else if (list.get(position).getType() == 0) {
            return TYPE_CONTENT_SMALL;
        } else if (list.get(position).getType() == 1) {
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvLarge;
        public NetworkImageView ivImg;
        public TextView tvTitle, tvPlace, tvKeyword;

        public ViewHolder(View rootView) {
            super(rootView);
            cvLarge = (CardView) rootView.findViewById(R.id.cv_medimu);
            ivImg = (NetworkImageView) rootView.findViewById(R.id.iv_img);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            tvPlace = (TextView) rootView.findViewById(R.id.tv_place);
            tvKeyword = (TextView) rootView.findViewById(R.id.tv_keyword);
        }
    }
}
