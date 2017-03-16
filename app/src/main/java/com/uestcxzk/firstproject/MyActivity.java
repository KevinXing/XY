package com.uestcxzk.firstproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import com.uestcxzk.firstproject.Ranking;
import com.uestcxzk.firstproject.Node;

import java.io.IOException;
import java.util.*;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.uestcxzk.firstproject.MESSAGE";
    private ImageButton ratButton, catButton, elephantButton;
    private ImageView playerImgClip, pcImgClip;
    private ProgressBar playerBlood, pcBlood;
    private TextView winText, playerText, pcText;

    private static final int BLOOD_MAX = 3;
    private String playerName = "Player";
    private String pcName = "";
    private final static String[] heroNames = {"Deadpool", "Hawkeye", "Iron Man", "Batman", "Spider Man", "The Hulk", "Green Arrow"};
    private PriorityQueue<Node> rankqueue;
    private Ranking rank;
    private int winStrike;

    @Override
    protected void onStop() {
        rank.putQueue(rankqueue);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playerImgClip = (ImageView)findViewById(R.id.player_img_clip) ;
        pcImgClip = (ImageView)findViewById(R.id.pc_img_clip) ;
        playerBlood = (ProgressBar) findViewById(R.id.player_blood);
        pcBlood = (ProgressBar) findViewById(R.id.pc_blood);
        winText = (TextView) findViewById(R.id.win_text);
        playerText = (TextView) findViewById(R.id.player_text);
        pcText = (TextView) findViewById(R.id.pc_text);
        rank = new Ranking(this);


        // Set font
        String fonts = "Sail-Regular.otf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fonts);
        winText.setTypeface(typeface);
        playerText.setTypeface(typeface);
        pcText.setTypeface(typeface);

        //playerBlood.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        playerBlood.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        playerBlood.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
        playerBlood.setMax(BLOOD_MAX);
        playerBlood.setProgress(0);

        pcBlood.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
        pcBlood.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
        pcBlood.setMax(BLOOD_MAX);
        pcBlood.setProgress(0);

        // getRanking
        rankqueue = rank.getQueue();

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        // input
        AlertDialog.Builder builder =new AlertDialog.Builder(MyActivity.this);
        builder.setTitle("Your Name");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                playerName = input.getText().toString();
                if (playerName.length() == 0) {
                    playerName = "Player";
                }
                playerText.setText(playerName);
                playerText.invalidate();
                int len = heroNames.length;
                Random r = new Random();
                int idx = r.nextInt(len);
                pcName = heroNames[idx];
                pcText.setText(pcName);
                pcText.invalidate();

            }
        });
        builder.show();




        // rat button
        ratButton=(ImageButton)findViewById(R.id.rat_button);
        ratButton.setOnClickListener(new Button.OnClickListener() {
            int w = 500;
            int h = 400;
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Dialog dialog=new AlertDialog.Builder(MyActivity.this)
                        .setTitle("")
                        .setMessage("Are you sure?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Bitmap bmpx = BitmapFactory.decodeResource(getResources(), R.drawable.mouse_pic);
                                bmpx = bitmapResize(w, h, bmpx);
                                playerImgClip.setImageBitmap(bmpx);
                                battle(0);
                            }
                        }).create();
                dialog.show();
            }
        });

        // cat button
        catButton=(ImageButton)findViewById(R.id.cat_button);
        catButton.setOnClickListener(new Button.OnClickListener() {
            int w = 500;
            int h = 400;
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Dialog dialog=new AlertDialog.Builder(MyActivity.this)
                        .setTitle("")
                        .setMessage("Are you sure?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Bitmap bmpx = BitmapFactory.decodeResource(getResources(), R.drawable.cat_pic);
                                bmpx = bitmapResize(w, h, bmpx);
                                playerImgClip.setImageBitmap(bmpx);
                                battle(1);
                            }
                        }).create();
                dialog.show();
            }
        });

        // elephant button
        elephantButton=(ImageButton)findViewById(R.id.elephant_button);
        elephantButton.setOnClickListener(new Button.OnClickListener() {
            int w = 500;
            int h = 400;
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Dialog dialog=new AlertDialog.Builder(MyActivity.this)
                        .setTitle("")
                        .setMessage("Are you sure?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Bitmap bmpx = BitmapFactory.decodeResource(getResources(), R.drawable.elephant_pic);
                                bmpx = bitmapResize(w, h, bmpx);
                                playerImgClip.setImageBitmap(bmpx);
                                battle(2);
                            }
                        }).create();
                dialog.show();
            }
        });

        // player blood

    }


    private void battle(int x) {
        Random r = new Random();
        int y = r.nextInt(3);
        int w = 500;
        int h = 400;

        winText.setVisibility(View.GONE);
        //playerBlood.setVisibility(View.VISIBLE);
        //pcBlood.setVisibility(View.VISIBLE);

        if (y == 0) {
            Bitmap bmpy = BitmapFactory.decodeResource(getResources(), R.drawable.mouse_pic);
            bmpy = bitmapResize(w, h, bmpy);
            pcImgClip.setImageBitmap(bmpy);
        } else if (y == 1) {
            Bitmap bmpy = BitmapFactory.decodeResource(getResources(), R.drawable.cat_pic);
            bmpy = bitmapResize(w, h, bmpy);
            pcImgClip.setImageBitmap(bmpy);
        } else if (y == 2) {
            Bitmap bmpy = BitmapFactory.decodeResource(getResources(), R.drawable.elephant_pic);
            bmpy = bitmapResize(w, h, bmpy);
            pcImgClip.setImageBitmap(bmpy);
        }

        TrembleAni tremble = new TrembleAni();
        tremble.setDuration(600);
        tremble.setRepeatCount(2);
        AlphaAnimation alphaGone = new AlphaAnimation(1, 0);
        alphaGone.setStartOffset(600);
        alphaGone.setDuration(400);
        AlphaAnimation alphaShow = new AlphaAnimation(0, 1);
        alphaShow.setDuration(50);
        AnimationSet as = new AnimationSet(true);
        as.addAnimation(tremble);
        as.addAnimation(alphaGone);

        if (x == y) {
            // draw
            if (playerImgClip.getVisibility() == View.GONE) {
                playerImgClip.startAnimation(alphaShow);
                playerImgClip.setVisibility(View.VISIBLE);
            }
            if (pcImgClip.getVisibility() == View.GONE) {
                pcImgClip.startAnimation(alphaShow);
                pcImgClip.setVisibility(View.VISIBLE);
            }
            playerImgClip.startAnimation(as);
            pcImgClip.startAnimation(as);
            //playerImgClip.setVisibility(View.GONE);
            //pcImgClip.setVisibility(View.GONE);

        } else if (y - x == 1 || (x == 2 && y == 0)) {
            // pc win
            if (playerImgClip.getVisibility() == View.GONE) {
                playerImgClip.startAnimation(alphaShow);
                playerImgClip.setVisibility(View.VISIBLE);
            }
            if (pcImgClip.getVisibility() == View.GONE) {
                pcImgClip.startAnimation(alphaShow);
                pcImgClip.setVisibility(View.VISIBLE);
            }
            playerImgClip.startAnimation(as);
            //playerImgClip.setVisibility(View.GONE);

            playerBlood.incrementProgressBy(1);
            if (playerBlood.getProgress() == playerBlood.getMax()){
                winText.setText(pcName + " Win!");
                winText.startAnimation(alphaShow);
                winText.setVisibility(View.VISIBLE);
                //winText.startAnimation(alphaGone);
                //winText.setVisibility(View.GONE);
                playerBlood.setProgress(0);
                pcBlood.setProgress(0);
                int len = heroNames.length;
                Random r1 = new Random();
                int idx = r1.nextInt(len);
                pcName = heroNames[idx];
                pcText.setText(pcName);
                pcText.invalidate();
                winStrike = 0;
            }

        } else {
            // player win
            if (playerImgClip.getVisibility() == View.GONE) {
                playerImgClip.startAnimation(alphaShow);
                playerImgClip.setVisibility(View.VISIBLE);
            }
            if (pcImgClip.getVisibility() == View.GONE) {
                pcImgClip.startAnimation(alphaShow);
                pcImgClip.setVisibility(View.VISIBLE);
            }

            pcImgClip.startAnimation(as);
            //pcImgClip.setVisibility(View.GONE);

            pcBlood.incrementProgressBy(1);
            if (pcBlood.getProgress() == pcBlood.getMax()){
                winText.setText(playerName + " Win!");
                winText.startAnimation(alphaShow);
                winText.setVisibility(View.VISIBLE);
                //winText.startAnimation(alphaGone);
                //winText.setVisibility(View.GONE);
                pcBlood.setProgress(0);
                playerBlood.setProgress(0);
                int len = heroNames.length;
                Random r1 = new Random();
                int idx = r1.nextInt(len);
                pcName = heroNames[idx];
                pcText.setText(pcName);
                pcText.invalidate();
                // update pq
                winStrike++;
                if (winStrike >= rankqueue.peek().val) {
                    rankqueue.poll();
                    rankqueue.offer(new Node(playerName, winStrike));
                }
            }

        }
    }

    private Bitmap bitmapResize(int w, int h, Bitmap g) {
        int width = g.getWidth();
        int height = g.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(g, 0, 0, width, height, matrix, true);
    }

    private class TrembleAni extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.getMatrix().setTranslate(
                    (float) Math.sin(interpolatedTime * 50) * 8,
                    (float) Math.sin(interpolatedTime * 50) * 8
            );
            super.applyTransformation(interpolatedTime, t);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                rank.putQueue(rankqueue);
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                startActivity(intent);
            }

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /** Called when the user clicks the Send button */
    public void gotoRanking(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }

}
