package com.example.kokwei217.rs_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class CartActivity extends AppCompatActivity {
    private String name;
    private int quantity;

    private ListView listView;
    private ArrayList<CartItem> cartItemList = new ArrayList<>();
    private ArrayList<com.example.kokwei217.rs_app.MenuItem> menuItemArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private String uid;

    private com.example.kokwei217.rs_app.MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");

        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading cart...");
        progressDialog.show();

        listView = findViewById(R.id.lv_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        uid = firebaseAuth.getUid();
        ref = firebaseDatabase.getReference();
        ref.child("User Requests").child(uid).addValueEventListener(valueEventListener);

    }

    public void checkout(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Component Request");
        alertDialog.setMessage("Press Ok to confirm the request");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref.child("User Requests").child(uid).removeValue();
                Toast.makeText(CartActivity.this, "Your request has been sent to the QM for approval", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            cartItemList = new ArrayList<>();
            for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                CartItem cartItem = cartSnapshot.getValue(CartItem.class);
                cartItemList.add(cartItem);
            }

            progressDialog.dismiss();
            CartAdapter cartAdapter = new CartAdapter(getApplicationContext(), cartItemList);
            listView.setAdapter(cartAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
