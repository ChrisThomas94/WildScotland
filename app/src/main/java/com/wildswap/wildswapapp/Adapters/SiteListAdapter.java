package com.wildswap.wildswapapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.util.Base64;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.wildswap.wildswapapp.R;
import com.wildswap.wildswapapp.Objects.Site;

/**
 * Created by Chris on 18-Mar-16.
 *
 */
public class SiteListAdapter extends BaseAdapter {

    private Context context;
    private SparseArray<Site> knownSites;

    public SiteListAdapter(Context context, SparseArray<Site> knownSites){
        this.context = context;
        this.knownSites = knownSites;
    }

    @Override
    public int getCount() {
        return knownSites.size();
    }

    @Override
    public Object getItem(int position) {
        return knownSites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_known_sites_list, null);
        }

        RelativeLayout site = (RelativeLayout) convertView.findViewById(R.id.site);
        ImageView siteThumbnail = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView amateur = (TextView) convertView.findViewById(R.id.classificationA);
        TextView casual = (TextView) convertView.findViewById(R.id.classificationC);
        TextView expert = (TextView) convertView.findViewById(R.id.classificationE);
        FrameLayout classA = (FrameLayout) convertView.findViewById(R.id.classAFrame);
        FrameLayout classC = (FrameLayout) convertView.findViewById(R.id.classCFrame);
        FrameLayout classE = (FrameLayout) convertView.findViewById(R.id.classEFrame);
        TextView country = (TextView) convertView.findViewById(R.id.country);
        TextView knownNum = (TextView) convertView.findViewById(R.id.knownNumber);

        List<Address> address = knownSites.get(position).getAddress();
        Address thisAddress = address.get(0);

        title.setText(knownSites.get(position).getTitle());

        if(thisAddress.getLocality() == null || thisAddress.getLocality().equals("null")){
            country.setText(thisAddress.getCountryName());
        } else {
            country.setText(thisAddress.getCountryName() + ", " + thisAddress.getLocality());
        }

        String amateurText = amateur.getText().toString();
        String casualText = casual.getText().toString();
        String expertText = expert.getText().toString();

        knownNum.setText(String.valueOf(knownSites.get(position).getNumOwners()));

        classA.setVisibility(View.INVISIBLE);
        classC.setVisibility(View.INVISIBLE);
        classE.setVisibility(View.INVISIBLE);

        amateur.setVisibility(View.INVISIBLE);
        casual.setVisibility(View.INVISIBLE);
        expert.setVisibility(View.INVISIBLE);

        if(knownSites.get(position).getClassification() == null){

        } else if(knownSites.get(position).getClassification().equals(amateurText)){
            amateur.setVisibility(View.VISIBLE);
            classA.setVisibility(View.VISIBLE);

        } else if(knownSites.get(position).getClassification().equals(casualText)){
            casual.setVisibility(View.VISIBLE);
            classC.setVisibility(View.VISIBLE);

        } else if(knownSites.get(position).getClassification().equals(expertText)){
            expert.setVisibility(View.VISIBLE);
            classE.setVisibility(View.VISIBLE);

        } else {

        }

        if(knownSites.get(position).getDisplay_pic() == null || knownSites.get(position).getDisplay_pic().equals("")){

            if(knownSites.get(position).getDistant() != null && knownSites.get(position).getNearby() != null && knownSites.get(position).getImmediate() != null){

                String distant = knownSites.get(position).getDistant();
                int distantID = context.getResources().getIdentifier("drawable/"+ distant, null, context.getPackageName());
                siteThumbnail.setImageResource(distantID);
            }

        } else {
            String image = knownSites.get(position).getDisplay_pic();
            Bitmap imageBit = StringToBitMap(image);
            siteThumbnail.setImageBitmap(imageBit);
        }



        return convertView;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
