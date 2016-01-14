package composite.khoaha.com.demo_commandpatternapplying;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HoangAnhKhoa on 1/14/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    ArrayList<String> hisStrings;

    public HistoryAdapter(ArrayList<String> hisStrings) {
        this.hisStrings = hisStrings;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        String history = (position + 1) + " - " + hisStrings.get(position);
        holder.tvHistory.setText(history);
    }

    @Override
    public int getItemCount() {
        return hisStrings.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvHistory)
        TextView tvHistory;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
