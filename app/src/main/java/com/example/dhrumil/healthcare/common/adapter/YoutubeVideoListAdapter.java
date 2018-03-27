package com.example.dhrumil.healthcare.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.common.Config;
import com.example.dhrumil.healthcare.common.model.YoutubeVideoListModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 3/24/2018.
 */
class YoutubeVideoListViewHolder extends RecyclerView.ViewHolder
{
    public YouTubeThumbnailView thumbnail_youtube_video;
    public TextView txt_video_title;
    public TextView txt_video_duration;


    public YoutubeVideoListViewHolder(View itemView) {
        super(itemView);

        thumbnail_youtube_video = itemView.findViewById(R.id.thumbnail_youtube_video);
        txt_video_title = itemView.findViewById(R.id.txt_video_title);
        txt_video_duration = itemView.findViewById(R.id.txt_video_duration);
    }
}

public class YoutubeVideoListAdapter extends RecyclerView.Adapter<YoutubeVideoListViewHolder>{

    private Context mContext;
    private ArrayList<YoutubeVideoListModel> videoList;
    private LayoutInflater inflater;

    public YoutubeVideoListAdapter(Context mContext, ArrayList<YoutubeVideoListModel> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public YoutubeVideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.youtube_video_list_raw,parent,false);
        return (new YoutubeVideoListViewHolder(view));
    }

    @Override
    public void onBindViewHolder(YoutubeVideoListViewHolder holder, final int position) {
        holder.txt_video_title.setText(videoList.get(position).getTitle());

        holder.txt_video_duration.setText(videoList.get(position).getDuration());

        holder.thumbnail_youtube_video.initialize(Config.API_KEY , new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(videoList.get(position).getVideoId());

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
