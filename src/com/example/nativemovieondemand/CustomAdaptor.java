package com.example.nativemovieondemand;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomAdaptor extends ArrayAdapter<String>{
private final Activity context;
private final List<String> web;
private final List<Integer> imageId;
public CustomAdaptor(Activity context,List<String> web, List<Integer> imageId) {
super(context, R.layout.custom_list, web);
this.context = context;
this.web = web;
this.imageId = imageId;
}
@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.custom_list, null, true);
TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
txtTitle.setText(web.get(position));
imageView.setImageResource(imageId.get(position));
return rowView;
}
}