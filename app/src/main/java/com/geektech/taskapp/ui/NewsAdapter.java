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


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnLongClickListener {

    private ArrayList<News> list = new ArrayList<>();

    public NewsAdapter() {

    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnLongClickListener(NewsAdapter.this);

        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(R.color.purple_700);
        } else {
            holder.itemView.setBackgroundColor(R.color.purple_200);
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
    @Override
    public boolean onLongClick(View view) {
        AlertDialog deleteBox = new AlertDialog.Builder();

        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.itemView.getId()) {
            deleteBox.setTitle("Delete");
            deleteBox.setMessage("Do you want to delete?");
            deleteBox.setIcon(R.drawable.ic_baseline_delete_24);

            deleteBox.setButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.remove(holder.getPosition());
                    dialog.dismiss();
                    notifyDataSetChanged();
                    Toast.makeText(deleteBox.getContext(), "Item has been deleted successfully", Toast.LENGTH_SHORT).show();
                }
            });

            deleteBox.setButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            deleteBox.create();
            return false;

        }

        return false;
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

            String time = (String) android.text.format.DateFormat.format("EEE, MMM d, yyyy 'y'", new Date(news.getCreatedAt()));
            textTime.setText(time);

        }
    }
}
