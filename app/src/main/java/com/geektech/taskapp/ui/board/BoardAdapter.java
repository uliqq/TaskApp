package com.geektech.taskapp.ui.board;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;
import com.geektech.taskapp.databinding.PagerBoardBinding;

import org.w3c.dom.Text;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] title = new String[]{"Welcome!", "Free", "Comfortable", "Start journey"};
    private String[] description = new String[]{"We are glad to meet you!", "0$ for the best experience!", "Absolutely user-oriented interface", "Let's Go-o-o!"};
    private int[] image = new int[] {R.drawable.stitch,R.drawable.free,R.drawable.excellence,R.drawable.rocket };
    private OnClick onClick;

    public void initListener(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDescription;
        private ImageView descImage;
        private Button startBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDesc);
            descImage = itemView.findViewById(R.id.imageView);
            startBtn = itemView.findViewById(R.id.startBtn);


        }

        public void bind(int position) {
            textTitle.setText(title[position]);
            textDescription.setText(description[position]);
            descImage.setImageResource(image[position]);

            if (position == title.length - 1) {
                startBtn.setVisibility(View.VISIBLE);
            } else {
                startBtn.setVisibility(View.GONE);
            }

            startBtn.setOnClickListener(v -> {
                onClick.OnClick();
            });
        }

    }
    public interface OnClick {
        void OnClick();
    }
}
