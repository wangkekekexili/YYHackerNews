package com.yiyangzhu.yyhackernews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(getApplicationContext());

        final List<Story> stories = new ArrayList<>();
        final ListViewAdapter adapter = new ListViewAdapter(this, stories);
        ListView newsListView = (ListView) findViewById(R.id.news_listview);
        newsListView.setAdapter(adapter);

        Firebase hackerNewsFirebase = new Firebase("https://hacker-news.firebaseio.com/v0/newstories");
        hackerNewsFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Integer> storyIds = dataSnapshot.getValue(List.class);
                Collections.sort(storyIds, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer lhs, Integer rhs) {
                        if (lhs > rhs) {
                            return -1;
                        } else if (lhs < rhs) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                for (Integer id : storyIds) {
                    Firebase storyFirebase = new Firebase("https://hacker-news.firebaseio.com/v0/item/"
                            + Integer.toString(id));
                    storyFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                return;
                            }

                            Story story = dataSnapshot.getValue(Story.class);

                            if (story.getUrl() == null || !story.getUrl().startsWith("http")) {
                                return;
                            }

                            stories.add(story);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
