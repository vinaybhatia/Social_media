package com.polaroid.universalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ly.kite.socialmedia.common.Album;

public class FacebookAlbumAdapter extends RecyclerView.Adapter<FacebookAlbumAdapter.ViewHolder> {

    private ArrayList<Album> postsList;
    Context context;
    public FacebookAlbumAdapter(ArrayList<Album> postsList,Context context) {
        this.postsList = postsList;
        this.context = context;
    }

    @Override
    public FacebookAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_facebook_album, viewGroup, false);
        return new FacebookAlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FacebookAlbumAdapter.ViewHolder viewHolder, int i) {
        try {
            Glide.with(context).load(postsList.get(i).getCoverUri()).into(viewHolder.imageIV);
            viewHolder.nameTV.setText(postsList.get(i).getAlbumName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageIV;
        private TextView nameTV;

        public ViewHolder(View view) {
            super(view);
            imageIV = (ImageView) view.findViewById(R.id.imageIV);
            nameTV = (TextView) view.findViewById(R.id.nameTV);
        }
    }
}
