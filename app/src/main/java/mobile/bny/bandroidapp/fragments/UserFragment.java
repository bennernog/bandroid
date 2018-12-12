package mobile.bny.bandroidapp.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


import mobile.bny.androidtools.Toaster;
import mobile.bny.bandroidapp.activities.MainActivity;
import mobile.bny.bandroidapp.adapter.UserArrayAdapter;
import mobile.bny.bandroidapp.data.User;
import mobile.bny.bandroidapp.data.UserDatabase;

public class UserFragment extends ListFragment {
    User[] users;
    UserArrayAdapter adapter;
    public UserFragment() {
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fetchUsersFromDatabase();
        adapter = new UserArrayAdapter(getContext(),users);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        /**
         * use the id it return the value of getItemId(position) from the adapter
         * override that to ensure you have the index of the complete list and not
         * of the truncated list (search result)
         */
        User user = users[(int) id]; //

        Toaster.shortToast(getContext(),
                "You selected %s %s",
                user.getFirstName(),
                user.getLastName());
    }

    public void onSearch(CharSequence charSequence){
        if(adapter!=null)
            adapter.getFilter().filter(charSequence);
    }

    public ArrayList<User> getUsers(){
        return new ArrayList<User>(Arrays.asList(users));
    }

    void fetchUsersFromDatabase(){
        UserDatabase database = ((MainActivity)getActivity()).getUserDatabase();
        ArrayList<User> userList = database.getAll(database.getReadableDatabase());
        users = userList.toArray(new User[0]);
    }

    public void refreshAdapter(){
        if(adapter != null){
            fetchUsersFromDatabase();
            adapter.setUsers(users);
        }
    }
}
