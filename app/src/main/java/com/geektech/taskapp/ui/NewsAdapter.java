package com.geektech.taskapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.geektech.taskapp.R;
import com.geektech.taskapp.models.News;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    private onItemClick click;

    public void setListener (onItemClick click) {
        this.click = click;
    }

    public NewsAdapter() {

    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(android.R.color.darker_gray);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
            this.list.remove(position);
            notifyDataSetChanged();
        }

    public News getItem(int position) {
        return list.get(position);
    }

    public void updateItem(int pos, News news) {
        list.set(pos, news);
        notifyItemChanged(pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textTitle;
            private TextView textTime;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.textTitle);
                textTime = itemView.findViewById(R.id.textTime);
            }

            public void bind(News news) {
                textTitle.setText(news.getTitle());

                itemView.setOnLongClickListener(view -> {
                    click.onLongClick(getAdapterPosition());
                return true;
                });

                String time = (String) android.text.format.DateFormat.format("EEE, MMM d, yyyy 'y'", new Date(news.getCreatedAt()));
                textTime.setText(time);
                itemView.setOnClickListener(view -> {
                    click.onClick(getAdapterPosition());
                });

            }
        }
    public interface onItemClick {

        void onLongClick(int position);

        void onClick(int position);
    }
    }
