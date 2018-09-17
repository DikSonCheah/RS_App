package com.example.kokwei217.rs_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private String uid;

    public CartAdapter(Context context, ArrayList<CartItem> cartItems) {
        super(context, 0, cartItems);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        uid = FirebaseAuth.getInstance().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("User Requests/" + uid);

        // Get the data item for this position
        final CartItem cartItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.itemName_cart);
        TextView tvQuantity = convertView.findViewById(R.id.itemQuantity_cart);
        // Populate the data into the template view using the data object
        tvName.setText(cartItem.getName());
        tvQuantity.setText(String.valueOf(cartItem.getQuantity()));

        Button removeButton = convertView.findViewById(R.id.removeButton_cart);
        // Cache row position inside the button using `setTag`
        removeButton.setTag(position);
        // Attach the click event handler
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                CartItem taggedItem = getItem(position);
                ref.child(taggedItem.getName()).removeValue();
                // Do what you want here...
                remove(taggedItem);



            }
        });
        //return the completed view
        return convertView;
    }
}


