package com.example.kokwei217.rs_app;


import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {
    public static MenuItem selectedItem;

    public AllFragment() {
        // Required empty public constructor
    }

    private ArrayList<MenuItem> getItemList() {
        ArrayList<MenuItem> itemList = new ArrayList<>();
//        MenuItem raspi = new MenuItem("Rasp Pi", 10, R.drawable.raspi);
//        MenuItem arduino = new MenuItem("Arduino", 10, R.drawable.arduino);
//        MenuItem buttons = new MenuItem("Buttons", 10, R.drawable.push_button);
//        MenuItem usb = new MenuItem("USB", 10, R.drawable.usb);
//        MenuItem led = new MenuItem("LED", 10, R.drawable.white_led);
//        MenuItem whatever = new MenuItem("Whatever", 10, R.drawable.no_image);
//        itemList.add(raspi);
//        itemList.add(arduino);
//        itemList.add(buttons);
//        itemList.add(usb);
//        itemList.add(led);
//        itemList.add(whatever);

        return itemList;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
//        final ArrayList<MenuItem> itemList = getItemList();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Electronic Component");
        nameList.add("Motor and Driver");
        nameList.add("Sensors");
        nameList.add("Microcontrollers");

//        for (MenuItem menuItem : itemList) {
//            nameList.add(menuItem.getName());
//        }

        final ListView lv = rootView.findViewById(R.id.item_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, nameList);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                int selectedFragment = i+1;
                MainActivity.viewPager.setCurrentItem(selectedFragment);

//                String name = itemList.get(i).getName();
//                int quantity = itemList.get(i).getAvailableQuantity();
//                int key = itemList.get(i).getKey();
//                selectedItem = new MenuItem(name, quantity, key);
//                Intent intent = new Intent(getActivity(), ItemImageActivity.class);
//                intent.putExtra(ItemImageActivity.itemImgKey,key);
//                intent.putExtra(ItemImageActivity.itemQuantityKey,10);
//                intent.putExtra(ItemImageActivity.itemNameKey , name);
//                startActivity(intent);
            }
        });
        return rootView;
    }

    public static MenuItem getSelectedItem() {
        return selectedItem;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
