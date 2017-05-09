package me.lancer.nodiseases.mvp.message.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.message.MessageBean;

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {

    private static final int TYPE_CONTENT_LARGE = -1;
    private static final int TYPE_CONTENT_SMALL = 0;
    private static final int TYPE_TITLE = 1;

    private Context context;
    private Handler handler;
    private List<MessageBean> list;
    private int type;
    private int[] colors = {R.color.red, R.color.pink, R.color.purple, R.color.deeppurple,
            R.color.indigo, R.color.blue, R.color.lightblue, R.color.cyan,
            R.color.teal, R.color.green, R.color.lightgreen, R.color.lime,
            R.color.yellow, R.color.amber, R.color.orange, R.color.deeporange};

    public ClassifyAdapter(Context context, Handler handler, int type, List<MessageBean> list) {
        this.context = context;
        this.handler = handler;
        this.list = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horiz_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (list.get(position) != null) {
            if (type == 0) {
                viewHolder.cvHoriz.setBackgroundResource(colors[position % 16]);
            } else {
                viewHolder.cvHoriz.setBackgroundResource(colors[(position + 7) % 16]);
            }
            viewHolder.tvTitle.setText(list.get(position).getTitle());
            viewHolder.cvHoriz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = Message.obtain(handler, 5, list.get(position).getId());
                    msg.sendToTarget();
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

        public CardView cvHoriz;
        public TextView tvTitle;

        public ViewHolder(View rootView) {
            super(rootView);
            cvHoriz = (CardView) rootView.findViewById(R.id.cv_horiz);
            tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        }
    }
}
