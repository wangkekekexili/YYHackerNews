package com.yiyangzhu.yyhackernews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

/**
 * This adapter is created specifically for Hacker News and row_news layout.
 */
public class ListViewAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<Story> stories;

    public ListViewAdapter(Context context, List<Story> stories) {
        this.context = context;
        this.stories = stories;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.row_swipelayout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_news, null);
        return rowView;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        Story story = stories.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView urlTextView = (TextView) convertView.findViewById(R.id.url);

        titleTextView.setText(story.getTitle());
        urlTextView.setText(story.getUrl());

        ImageButton openButton = (ImageButton) convertView.findViewById(R.id.open);
        ImageButton archiveButton = (ImageButton) convertView.findViewById(R.id.archive);
        ImageButton favouriteButton = (ImageButton) convertView.findViewById(R.id.favourite);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stories.get(position).getUrl())));
            }
        });

    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
