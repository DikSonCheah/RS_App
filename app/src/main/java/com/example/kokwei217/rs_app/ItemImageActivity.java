package com.example.kokwei217.rs_app;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ItemImageActivity extends AppCompatActivity {
    public static String itemImgKey = "itemImgKey";
    public static String itemQuantityKey = "itemQuantityKey";
    public static String itemNameKey = "itemNameKey";

    private int availableQuantity;
    private String itemName;
    private CartItem requestedItem;

    private Toast toast;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_image);

        Toolbar toolbar = findViewById(R.id.toolbar_itemImage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView itemImage = findViewById(R.id.item_img);
        TextView itemNameTV = findViewById(R.id.item_name);
        TextView availableQuantityTV = findViewById(R.id.available_quantity);

        availableQuantity = getIntent().getIntExtra(itemQuantityKey, 0);
        itemName = getIntent().getStringExtra(itemNameKey);
        int itemImg = getIntent().getIntExtra(itemImgKey, 0);

        itemImage.setImageResource(itemImg);
        itemNameTV.setText(itemName);
        setTitle(itemName);

        availableQuantityTV.setText("Available Quantity:" + availableQuantity);

        findViewById(R.id.request_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestItem();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("User Requests/");
    }

    public void requestItem() {
        EditText quantityET = findViewById(R.id.quantity_ET);
        int requestAmount = 0;
        try {
            requestAmount = Integer.valueOf(quantityET.getText().toString().trim());
        } catch (NumberFormatException nfe) {
            Toast.makeText(ItemImageActivity.this, "Not a valid number", Toast.LENGTH_SHORT).show();
        }
        if (requestAmount == 0) {
            Toast.makeText(ItemImageActivity.this, "Amount cant be 0", Toast.LENGTH_SHORT).show();
        } else if (requestAmount > availableQuantity) {
            Toast.makeText(ItemImageActivity.this, "Dont Request so much cb", Toast.LENGTH_SHORT).show();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT);
            toast.show();
            requestedItem = new CartItem(itemName, requestAmount);
            ref.child(uid).child(itemName).setValue(requestedItem);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                Intent cartActivity = new Intent(this, CartActivity.class);
                startActivity(cartActivity);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
