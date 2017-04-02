package scot.wildcamping.wildscotland.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import scot.wildcamping.wildscotland.Objects.User;
import scot.wildcamping.wildscotland.R;

/**
 * Created by Chris on 18-Mar-16.
 */
public class UserListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private SparseArray<User> fetchedUsers;
    List<User> filterUsers;
    private UserFilter userFilter;

    public UserListAdapter(Context context, SparseArray<User> fetchedUsers){
        this.context = context;
        this.fetchedUsers = fetchedUsers;
    }

    @Override
    public int getCount() {
        return fetchedUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return fetchedUsers.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_user_list, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView email = (TextView) convertView.findViewById(R.id.email);
        ImageView profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);

        String image = fetchedUsers.get(position).getProfile_pic();
        Bitmap compress = StringToBitMap(image);

        name.setText(fetchedUsers.get(position).getName());
        email.setText(fetchedUsers.get(position).getEmail());
        profile_pic.setImageBitmap(compress);

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

    @Override
    public Filter getFilter(){

        if(userFilter == null){
            userFilter = new UserFilter();
        }

        return userFilter;
    }

    private class UserFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<User> tempList = new ArrayList<User>();

                filterUsers = asList(fetchedUsers);

                // search content in friend list
                for (User user : filterUsers) {
                    if (user.getEmail().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = filterUsers.size();
                filterResults.values = filterUsers;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterUsers = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }
    }

    public static <C> List<C> asList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        List<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }
}