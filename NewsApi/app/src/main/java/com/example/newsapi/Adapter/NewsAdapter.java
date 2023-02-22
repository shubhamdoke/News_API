package com.example.newsapi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapi.Model.NewsModel;
import com.example.newsapi.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsModel> news = new ArrayList<>();
    private Context context;

    public NewsAdapter(ArrayList<NewsModel> newsModels, Context context){
        news = newsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from( context ).inflate( R.layout.view_layout, parent, false );

        return new NewsViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.NewsViewHolder holder, final int position) {

        holder.title.setText(news.get(position).getTitle());
        holder.content.setText(news.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView imageView;
        TextView content;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title_txt);
            content = itemView.findViewById(R.id.news_desc_txt);
        }
    }
}
