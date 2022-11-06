package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<Notes> {
    private Context mContext;
    private int mResource;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Notes> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView txtName = convertView.findViewById(R.id.tvName);
        TextView txtDesc = convertView.findViewById(R.id.tvDesc);

        txtName.setText(getItem(position).getName());
        txtDesc.setText(getItem(position).getDesc());

        return convertView;
    }
}
