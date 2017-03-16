package com.uestcxzk.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.uestcxzk.firstproject.Node;
import com.uestcxzk.firstproject.Ranking;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class DisplayMessageActivity extends AppCompatActivity {
    private Ranking rank;
    private PriorityQueue<Node> rankqueue;
    private TableLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        table = (TableLayout) findViewById(R.id.table);

        setSupportActionBar(toolbar);

        rank = new Ranking(this);
        rankqueue = rank.getQueue();
        Intent intent = getIntent();

        table.setStretchAllColumns(true);

        ArrayList<Node> arr = new ArrayList<>();
        while (!rankqueue.isEmpty()) {
            arr.add(rankqueue.poll());
        }

        for (int i = arr.size() - 1; i >= 0; i--) {
            Node node = arr.get(i);
            TableRow row = new TableRow(DisplayMessageActivity.this);
            TextView name = new TextView(DisplayMessageActivity.this);
            name.setGravity(Gravity.CENTER);
            name.setText(node.name);
            TextView score = new TextView(DisplayMessageActivity.this);
            score.setGravity(Gravity.CENTER);
            score.setText(String.valueOf(node.val));
            row.addView(name);
            row.addView(score);
            table.addView(row);
        }
        table.invalidate();

    }

}
