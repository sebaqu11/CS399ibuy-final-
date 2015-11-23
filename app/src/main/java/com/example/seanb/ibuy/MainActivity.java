package com.example.seanb.ibuy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // we use an ArrayList with a HashMap to store two string values, name of item and amount

    private ArrayList<HashMap<String,String>> items = new ArrayList<HashMap<String, String>>();
    public static final String ITEM_NAME_KEY = "_item_name";
    public static final String ITEM_QTY_KEY = "_item_qty";
    public static final int PICK_CONTACT = 1;

    private EditText itemQty;
    private EditText itemName;
    public ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default layout shows a prompt to type an item and amount

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("iBuy");

        itemName = (EditText)findViewById(R.id.item_name);
        itemQty = (EditText)findViewById(R.id.item_qty);
        itemListView = (ListView)findViewById(R.id.item_list);
        Button addItem = (Button)findViewById(R.id.additem);
        Button shareItems = (Button)findViewById(R.id.share);

        // This brings up the contact list when the share button is pressed.
        // Right now the emulator has no network functionality, so it doesn't really do much.
        // The idea is to bring up contacts and share the list of items to another person.
        shareItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
        addItem.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // handles the ADD button method, adds the item with amount to the list

        if (!itemName.getText().toString().equals("") && !itemQty.getText().toString().equals("")) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put(ITEM_NAME_KEY, itemName.getText().toString());
            item.put(ITEM_QTY_KEY, itemQty.getText().toString());

            items.add(item);
                    final SimpleAdapter adapter = new SimpleAdapter(this, items,
                    R.layout.item_box,
                    new String[]{ITEM_NAME_KEY, ITEM_QTY_KEY},
                    new int[]{R.id.item_name_list, R.id.item_qty_list});
            itemListView.setAdapter(adapter);
            itemName.setText("");
            itemQty.setText("");

            itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                // handles removing items from the list by long click
                public boolean onItemLongClick(AdapterView<?> parent,
                                               View view, int position, long id) {
                    Log.v("long clicked", "pos: " + position);
                    items.remove(position);
                    itemListView.setAdapter(adapter);
                    return false;
                }
            });

        }else{}
    }
}
