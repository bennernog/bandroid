package mobile.bny.bandroidapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mobile.bny.androidtools.BSharedPreferences;
import mobile.bny.bandroidapp.GlideApp;
import mobile.bny.bandroidapp.R;
import mobile.bny.bandroidapp.data.User;
import mobile.bny.bandroidapp.views.ProgressImageView;
import mobile.bny.bandroidapp.views.ProgressRequest;
import mobile.bny.javatools.CollectionUtils;


public class UserArrayAdapter extends ArrayAdapter<User> implements Filterable {

    List<User> allUsers, users;
    private Filter filter;

    public UserArrayAdapter(@NonNull Context context, @NonNull User[] users) {
        super(context, 0, users);
        this.users = this.allUsers = Arrays.asList(users);
    }

    public void setUsers(User[] users) {
        this.users = this.allUsers = Arrays.asList(users);
        notifyDataSetChanged();
    }

    /** When implementing filterable you need to override getCount() and
     *  getItem(int) to use the mutable list of items, otherwise the super method will
     *  refer to the list at initialisation (see constructor)
     */
    @Override
    public int getCount() {
        return users.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        //since your list can be truncated by the filter, override this method to return the index of the item in the full list
        return allUsers.indexOf(getItem(position));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final UserArrayAdapter.ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_users,parent,false);
            vh = UserArrayAdapter.ViewHolder.create(convertView);
            convertView.setTag(vh);
        } else {
            vh = (UserArrayAdapter.ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        if(user == null)
            return super.getView(position, convertView, parent);

        int size = (int)(BSharedPreferences.getWidth(getContext()) /6f);
        ProgressRequest progressRequest = new ProgressRequest (vh.progressImageView); //contains a request that will hide the progressbar after loading the image

        vh.tvName.setText(String.format("%s %s", user.getFirstName(),user.getLastName()));
        vh.tvEmail.setText(user.getEmail());
        vh.tvGender.setText(user.getGender());

        GlideApp.with(getContext())
                .load(user.getAvatar())
                .placeholder(R.drawable.placeholder)
                .listener(progressRequest.getListener())
                .override(size)
                .into(vh.progressImageView.ImageView);

        return vh.rootView;
    }


    private static class ViewHolder {
        public final View rootView;
        public final ProgressImageView progressImageView;
        public final TextView tvName,tvEmail,tvGender;

        private ViewHolder(View rootView, ProgressImageView progressImageView, TextView... textView) {
            this.rootView = rootView;
            this.progressImageView = progressImageView;
            this.tvName = textView[0];
            this.tvEmail = textView[1];
            this.tvGender = textView[2];
        }

        public static ViewHolder create(View rootView) {
            ProgressImageView avatar = rootView.findViewById(R.id.piv);
            TextView name = rootView.findViewById(R.id.tv_username);
            TextView email = rootView.findViewById(R.id.tv_user_email);
            TextView gender = rootView.findViewById(R.id.tv_user_gender);

            return new ViewHolder(rootView,avatar,name,email,gender);
        }
    }



    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new UserFilter();

        return filter;
    }

    //Filter class to filter the userlist based on query
    private class UserFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();

            //Show complete list if query is empty
            if (charSequence == null || charSequence.length() == 0) {

                results.values = allUsers;
                results.count = allUsers.size();

            } else { //create list that matches query

                List<User> filteredList = new ArrayList<User>();

                if (CollectionUtils.notNullOrEmpty(allUsers)) {

                    for (User d : allUsers) { //compare query to users first and last name
                        if (d.getFirstName().toUpperCase().contains(charSequence.toString().toUpperCase()) ||
                                d.getLastName().toUpperCase().contains(charSequence.toString().toUpperCase()))
                            filteredList.add(d);
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();
                }


            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // return empty list if query doesn't match
            if (filterResults.count == 0)
                users = new ArrayList<User>();
            else { //create list of search results
                users = (List<User>) filterResults.values;
            }
            notifyDataSetChanged();//tell the adapter to update
        }
    }
}
