package com.example.dronc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class PlayerListAdapter extends ArrayAdapter<Player> {

    private Context mContext;
    int mResource;

    public PlayerListAdapter(Context context, int resource, ArrayList<Player> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Get Player Info
        String name = getItem(position).getName();
        String drink = getItem(position).getDrink();

        //Layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //Set Player Info
        TextView textView_name = convertView.findViewById(R.id.listitem_name);
        TextView textView_drink = convertView.findViewById(R.id.listitem_drink);
        textView_name.setText(name);
        textView_drink.setText(drink);

        return convertView;
    }
    //getter for listitem player name
    public String getName(int position){
        return getItem(position).getName();
    }
}
