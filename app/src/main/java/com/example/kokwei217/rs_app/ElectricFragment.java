package com.example.kokwei217.rs_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ElectricFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private ArrayList<String> nameList;
    private ArrayList<MenuItem> menuItemArrayList;
    private ListView lv;
    private ProgressBar progressBar;

    private View rootView;


    public ElectricFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_electric, container, false);
        lv = rootView.findViewById(R.id.item_list_electric);
        progressBar = rootView.findViewById(R.id.electric_ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        nameList = new ArrayList<>();
        menuItemArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Available Components/Electronic Component");
        ref.addValueEventListener(valueEventListener);
        return rootView;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                MenuItem menuItem = itemSnapshot.getValue(MenuItem.class);
                menuItemArrayList.add(menuItem);
                nameList.add(menuItem.getName());
            }

            progressBar.setVisibility(View.GONE);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, nameList);
            lv.setAdapter(arrayAdapter);
            lv.setOnItemClickListener(itemClickListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String itemName = menuItemArrayList.get(position).getName();
            int quantity = menuItemArrayList.get(position).getQuantity();
            int key;
            switch (itemName) {
                case "LCD Display 16pin":
                    key = R.drawable.lcd;
                    break;
                case "LDR":
                    key = R.drawable.ldr;
                    break;
                case "LED White":
                    key = R.drawable.white_led;
                    break;
                case "Push-button":
                    key = R.drawable.push_button;
                    break;
                default:
                    key = R.drawable.no_image;
                    break;
            }
            Intent intent = new Intent(getActivity(), ItemImageActivity.class);
            intent.putExtra(ItemImageActivity.itemImgKey, key);
            intent.putExtra(ItemImageActivity.itemNameKey, itemName);
            intent.putExtra(ItemImageActivity.itemQuantityKey, quantity);
            startActivity(intent);
        }
    };

}
