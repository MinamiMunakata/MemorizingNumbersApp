package com.minami.project.android.memorizingnumbersapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static com.minami.project.android.memorizingnumbersapp.ListActivity.FILE;
import static com.minami.project.android.memorizingnumbersapp.ListActivity.readDataBase;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    private TextView category_tv;
    private TextView org_tv;
    private TextView item_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category_tv = findViewById(R.id.category_tv);
        org_tv = findViewById(R.id.org_tv);
        item_tv = findViewById(R.id.item_tv);
        questionGenerater();
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        readDataBase(database, shopItems);
    }

    private void addCount(ShopItem item){
        item.setTrial_count(item.getTrial_count() + 1);
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "UPDATE item_list SET trial_count = " + item.getTrial_count() + 1 + " WHERE code = " + item.getCode();
        database.execSQL(query);
        database.close();
    }

    private void questionGenerater(){
        Random random = new Random();
        int random_index = random.nextInt(shopItems.size());
        ShopItem item = shopItems.get(random_index);
        category_tv.setText(item.getCategory());
        org_tv.setText(item.getOrg());
        item_tv.setText(item.getItem());
        addCount(item);

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
}
