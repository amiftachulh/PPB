package com.library.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id, title, developer, publisher, rating, review;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList developer, ArrayList publisher, ArrayList rating, ArrayList review) {
        this.context = context;
        this.activity = activity;
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.rating = rating;
        this.review = review;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.idTxt.setText(String.valueOf(id.get(position)));
        holder.titleTxt.setText(String.valueOf(title.get(position)));
        holder.developerTxt.setText(String.valueOf(developer.get(position)));
        holder.publisherTxt.setText(String.valueOf(publisher.get(position)));
        holder.ratingTxt.setText(String.valueOf(rating.get(position)));
        holder.reviewTxt.setText(String.valueOf(review.get(position)));
        holder.gamesListLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(id.get(position)));
            intent.putExtra("title", String.valueOf(title.get(position)));
            intent.putExtra("developer", String.valueOf(developer.get(position)));
            intent.putExtra("publisher", String.valueOf(publisher.get(position)));
            intent.putExtra("rating", String.valueOf(rating.get(position)));
            intent.putExtra("review", String.valueOf(review.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idTxt, titleTxt, developerTxt, publisherTxt, ratingTxt, reviewTxt;
        LinearLayout gamesListLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.gameId);
            titleTxt = itemView.findViewById(R.id.gameTitle);
            developerTxt = itemView.findViewById(R.id.gameDeveloper);
            publisherTxt = itemView.findViewById(R.id.gamePublisher);
            ratingTxt = itemView.findViewById(R.id.gameRating);
            reviewTxt = itemView.findViewById(R.id.gameReview);
            gamesListLayout = itemView.findViewById(R.id.gamesListLayout);
        }
    }
}
