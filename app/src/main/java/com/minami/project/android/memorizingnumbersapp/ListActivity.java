package com.minami.project.android.memorizingnumbersapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListActivity extends AppCompatActivity {
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private SQLiteDatabase database;
    public static final String FILE = "shop_item.db";
    private ItemTouchHelper.SimpleCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        adapter = new MyRecyclerViewAdapter(shopItems);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Take action for the swiped item
                try {
                    int position = viewHolder.getAdapterPosition();
                    ShopItem item = shopItems.get(position);
                    shopItems.remove(position);
                    deleteData(item);
                    adapter.notifyDataSetChanged();
                } catch (Exception e){
                    Log.e("ListActivity", "onSwiped: " + e.getMessage());
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(ListActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(ListActivity.this, R.color.red))
                        .addActionIcon(android.R.drawable.ic_menu_delete)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    protected void onStart() {
        super.onStart();
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        // TODO delete
        //resetCount();
        readDataBase(database, shopItems);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calc_icon:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void addCode(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_code_dialog, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button add_btn = dialogView.findViewById(R.id.add_button);
        final EditText input_category = dialogView.findViewById(R.id.input_category);
        final EditText input_item = dialogView.findViewById(R.id.input_item);
        final CheckBox input_org = dialogView.findViewById(R.id.checkBox);
        final EditText input_code = dialogView.findViewById(R.id.input_code);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(input_code.getText())){
                    MainActivity.toast(view.getContext(), "Enter the item code.");
                    dialog.dismiss();
                }
                ShopItem item = new ShopItem();
                item.setCategory(input_category.getText().toString());
                item.setItem(input_item.getText().toString());
                if (input_org.isChecked()) item.setOrg("ORG");
                else item.setOrg("");
                item.setCode(input_code.getText().toString());
                shopItems.add(item);
                addData(item);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }

    private void addData(ShopItem item){
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "INSERT INTO item_list ('category', 'item', 'organic', 'code') " +
                "VALUES('" + item.getCategory() + "', '" + item.getItem() + "', '" + item.getOrg() + "', '" + item.getCode() + "')";
        database.execSQL(query);
        database.close();
    }

    private void deleteData(ShopItem item){
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "DELETE FROM item_list WHERE trial_count = " + item.getTrial_count();
        database.execSQL(query);
        database.close();
    }

    // TODO delete
    private void resetCount(){
        database = openOrCreateDatabase(FILE, MODE_PRIVATE, null);
        String query = "UPDATE item_list set score = 0, trial_count = 0";
        database.execSQL(query);
        database.close();
    }

    public static void readDataBase(SQLiteDatabase database, ArrayList<ShopItem> shopItems){
        String sql = "CREATE TABLE IF NOT EXISTS item_list" +
                "(category TEXT, item TEXT, organic TEXT, code TEXT PRIMARY KEY, " +
                "trial_count INTEGER DEFAULT (0), score INTEGER DEFAULT (0))";
        database.execSQL(sql);
        Cursor query = database.rawQuery(
                "SELECT * FROM item_list",
                null
        );
        if (query.moveToFirst()){
            do {
                ShopItem shopItem = new ShopItem();
                shopItem.setCategory(query.getString(query.getColumnIndex("category")));
                shopItem.setItem(query.getString(query.getColumnIndex("item")));
                shopItem.setOrg(query.getString(query.getColumnIndex("organic")));
                shopItem.setCode(query.getString(query.getColumnIndex("code")));
                shopItem.setTrial_count(query.getInt(query.getColumnIndex("trial_count")));
                shopItem.setScore(query.getInt(query.getColumnIndex("score")));
                shopItems.add(shopItem);
                // TODO delete
                Log.i("Count", "readDataBase: " + shopItem.getTrial_count());
                Log.i("score", "readDataBase: " + shopItem.getScore());
            } while (query.moveToNext());
            query.close();
            database.close();
        }
    }
}
