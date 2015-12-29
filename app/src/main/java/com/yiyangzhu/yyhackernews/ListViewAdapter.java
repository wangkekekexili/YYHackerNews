package com.yiyangzhu.yyhackernews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void fillValues(final int position, final View convertView) {
        final Story story = stories.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView urlTextView = (TextView) convertView.findViewById(R.id.url);

        titleTextView.setText(story.getTitle());
        urlTextView.setText(story.getUrl());

        ImageButton openButton = (ImageButton) convertView.findViewById(R.id.open);
        ImageButton archiveButton = (ImageButton) convertView.findViewById(R.id.archive);
        final ImageButton favouriteButton = (ImageButton) convertView.findViewById(R.id.favourite);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stories.get(position).getUrl())));
            }
        });

        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get id of current news at "position"
                int id = stories.get(position).getId();

                // remove from current stories
                stories.remove(position);
                notifyDataSetChanged();

                // update firebase
                Firebase archiveFirebase = new Firebase("https://yyhackernews.firebaseio.com/archive");
                Map<String, Object> update = new HashMap<>();
                update.put(Integer.toString(id), Integer.toString(id));
                archiveFirebase.updateChildren(update);

                // close surface
                SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.row_swipelayout);
                swipeLayout.close();
            }
        });

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get id of current news at "position"
                int id = stories.get(position).getId();
                String idString = Integer.toString(id);

                // remove from current stories
                stories.remove(position);
                notifyDataSetChanged();

                // update firebase
                Firebase archiveFirebase = new Firebase("https://yyhackernews.firebaseio.com/archive");
                Map<String, Object> update = new HashMap<>();
                update.put(idString, idString);
                archiveFirebase.updateChildren(update);

                Firebase favouriteFirebase = new Firebase("https://yyhackernews.firebaseio.com/favourite");
                update = new HashMap<>();
                update.put(idString, idString);
                favouriteFirebase.updateChildren(update);

                // close surface
                SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.row_swipelayout);
                swipeLayout.close(false);;
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
