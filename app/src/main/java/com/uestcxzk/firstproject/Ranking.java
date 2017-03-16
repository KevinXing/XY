package com.uestcxzk.firstproject;

/**
 * Created by uestc on 3/15/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.uestcxzk.firstproject.Node;
import java.util.*;

public class Ranking {
    private PriorityQueue<Node> rankqueue;
    private AppCompatActivity activity;
    private int n = 10;
    private String TAG;

    public Ranking(AppCompatActivity a) {
        rankqueue = new PriorityQueue<>(n, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.val - o2.val;
            }
        });
        activity = a;
        TAG = "ranking";
    }

    public PriorityQueue<Node> getQueue() {
        SharedPreferences sp = activity.getSharedPreferences("rank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //Log.d("test", "test2");
        for (int i = 0; i < n; i++) {
            String id = String.valueOf(i);
            String s = sp.getString(id,"");
            if (s.length() == 0) {
                rankqueue.add(new Node("Kevin", i + 1));
            } else {
                //Log.d("test", s);
                String[] a = s.split(",");
                if (a.length == 2) {
                    String name = a[0];
                    int val = Integer.parseInt(a[1]);
                    rankqueue.add(new Node(name, val));
                }
                editor.remove(id);
                editor.apply();
            }
        }
        return rankqueue;
    }

    public void putQueue(PriorityQueue<Node> q) {
        rankqueue = q;
        int i = 0;
        SharedPreferences sp = activity.getSharedPreferences("rank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (Node node : rankqueue) {
            String s = node.name + "," + node.val;
            String rank = String.valueOf(i);
            //Log.d("test", rank + " " + s);
            editor.putString(rank, s);
            editor.commit();
            i++;
        }
    }


}
