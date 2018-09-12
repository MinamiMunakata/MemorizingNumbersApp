package com.minami.project.android.memorizingnumbersapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.minami.project.android.memorizingnumbersapp.ListActivity.FILE;
import static com.minami.project.android.memorizingnumbersapp.ListActivity.readDataBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SQLiteDatabase database;
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    private TextView category_tv;
    private TextView org_tv;
    private TextView item_tv;
    private Button submit_btn;
    private EditText answer;
    private ImageView image_ox;
    private int next_index;
    private int previous_index;
    private ImageButton next_btn;
    private ImageButton previous_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category_tv = findViewById(R.id.category_tv);
        org_tv = findViewById(R.id.org_tv);
        item_tv = findViewById(R.id.item_tv);
        submit_btn = findViewById(R.id.submit_btn);
        answer = findViewById(R.id.input_answer);
        image_ox = findViewById(R.id.image_ox);

        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        readDataBase(database, shopItems);
        if (shopItems.size() > 0) {
            questionGenerator();
            next_btn = findViewById(R.id.next_btn);
            next_btn.setOnClickListener(this);
            previous_btn = findViewById(R.id.previous_btn);
            previous_btn.setOnClickListener(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        answer.setText("");
    }

    private void addCount(ShopItem item){
        int count = item.getTrial_count() + 1;
        item.setTrial_count(count);
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "UPDATE item_list SET trial_count = " + count + " WHERE code = " + item.getCode();
        database.execSQL(query);
        database.close();
    }

    private void addScore(ShopItem item){
        int new_score = item.getScore() + 1;
        toast(this.getApplicationContext(), String.valueOf(new_score));
        item.setScore(new_score);
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "UPDATE item_list SET score = " + new_score + " WHERE code = " + item.getCode();
        database.execSQL(query);
        database.close();
        toast(this.getApplicationContext(), String.valueOf(item.getScore()));
    }

    private void questionGenerator(){
        Random random = new Random();
        final int random_index = random.nextInt(shopItems.size());
        final ShopItem item = shopItems.get(random_index);
        if (shopItems.size() > 0){
            if (random_index == 0) {
                previous_index = shopItems.size() - 1;
                next_index = random_index + 1;
            } else if (random_index == shopItems.size() - 1) {
                previous_index = random_index - 1;
                next_index = 0;
            } else {
                previous_index = random_index - 1;
                next_index = random_index + 1;
            }
            category_tv.setText(item.getCategory());
            org_tv.setText(item.getOrg());
            item_tv.setText(item.getItem());
            submit_btn.setText("submit");
            if (shopItems.size() > 0) {
                submit(this.getApplication(),random_index, item);
            }
        }
    }

    private void submit(final Context c,final int index, final ShopItem item) {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().equals(item.getCode())){
                    image_ox.setImageResource(R.drawable.correct);
                    image_ox.setVisibility(View.VISIBLE);
                    toast(c,"Good!!");
                    addScore(item);
                    if (shopItems.size() > 0){
                        submit_btn.setText("next");
                        submit_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                image_ox.setVisibility(View.INVISIBLE);
                                answer.setText("");
                                questionGenerator();
                            }
                        });
                    } else {
                        submit_btn.setClickable(false);
                    }
                } else {
                    image_ox.setImageResource(R.drawable.wrong);
                    image_ox.setVisibility(View.VISIBLE);
                    toast(c,"Try again...");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_icon:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public static void toast(Context c, String s){
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        answer.setText("");
        int index = 0;
        if (shopItems.size() > 1) {
            if (view.getId() == R.id.previous_btn) {
                index = previous_index;
                if (previous_index == 0) {
                    previous_index = shopItems.size() - 1;
                } else {
                    previous_index--;
                }
            }
            if (view.getId() == R.id.next_btn) {
                index = next_index;
                if (next_index == shopItems.size() - 1) {
                    next_index = 0;
                } else {
                    next_index++;
                }
            }
            final ShopItem item = shopItems.get(index);
            category_tv.setText(item.getCategory());
            org_tv.setText(item.getOrg());
            item_tv.setText(item.getItem());
            submit_btn.setText("submit");

            addCount(item);
            final int finalIndex = index;
            submit(this.getApplicationContext(),finalIndex, item);
        }

    }
}
