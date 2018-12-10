package mobile.bny.bandroidapp.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import mobile.bny.androidtools.AsyncProgressUpdate;
import mobile.bny.androidtools.AsyncResponse;
import mobile.bny.androidtools.BandroidAsyncTask;
import mobile.bny.androidtools.InternetConnection;
import mobile.bny.androidtools.Toaster;
import mobile.bny.bandroidapp.R;
import mobile.bny.bandroidapp.data.UserData;
import mobile.bny.bandroidapp.data.UserDatabase;
import mobile.bny.bandroidapp.fragments.DialogSampleFragment;
import mobile.bny.bandroidapp.fragments.UserFragment;
import mobile.bny.dialogs.AsyncTaskDialog;
import mobile.bny.jsontools.JsonResponse;
import mobile.bny.views.MarqueeToolbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Menu menu;
    FloatingActionButton fab;
    DialogSampleFragment dialogSampleFragment;
    UserFragment userFragment;
    private int itemId;

    UserDatabase userDatabase;

    public boolean hasUsers() {
        return getUserDatabase().getCount(getUserDatabase().getReadableDatabase()) > 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MarqueeToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_header_title);
        toolbar.setSubtitle(R.string.nav_header_subtitle);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadUsersAsync().execute();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(R.id.nav_dialogs);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                getSupportFragmentManager().popBackStack();

        } else {

            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        hideOption(R.id.action_search);

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery(newText);
                return true;
            }
        };
        searchView.setOnQueryTextListener(onQueryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        showFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    void showFragment(@IdRes int id){
        switch (id){
            case R.id.nav_dialogs:
                if(dialogSampleFragment == null)
                    dialogSampleFragment = DialogSampleFragment.newInstance();
                setNewFragment(dialogSampleFragment);
                fab.hide();
                break;
            case R.id.nav_users:
                if(userFragment == null)
                    userFragment = UserFragment.newInstance();
                setNewFragment(userFragment, true);
                updateFab();
                fab.show();
                break;
            default:
                Toaster.comingSoon(getApplicationContext());
                break;
        }
        itemId = id;
    }

    public void updateFab(){
        fab.setImageResource(hasUsers() ? R.drawable.ic_upload : R.drawable.ic_download);
    }

    private void hideOption(int id) {
        if(menu == null)
            return;
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        if(menu == null)
            return;
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    void setNewFragment(Fragment fragment) {
        setNewFragment(fragment, false);
    }

    void setNewFragment(Fragment fragment, boolean searchable){
        if(searchable)
            showOption(R.id.action_search);
        else
            hideOption(R.id.action_search);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }

    public void searchQuery(CharSequence charSequence){
        if(userFragment != null && itemId == R.id.nav_users)
            userFragment.onSearch(charSequence);
    }


    public UserDatabase getUserDatabase() {
        if(userDatabase == null)
            userDatabase = new UserDatabase(MainActivity.this);
        return userDatabase;
    }

    class UploadUsersAsync extends BandroidAsyncTask<String> {

        AsyncTaskDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = AsyncTaskDialog.newInstance("Uploading Users", "Preparing to upload.");
            dialog.show(getSupportFragmentManager());
        }

        @Override
        protected void onPostExecute(AsyncResponse asyncResponse) {
            super.onPostExecute(asyncResponse);
            String finalMessage = "S";
            switch (asyncResponse){
                case SUCCESS:
                    finalMessage = "upload successful";
                    break;
                case NO_CONNECTION:
                    finalMessage = " Could not connect to internet";
                    break;
                case FAILURE:
                    finalMessage = "Something went wrong";
                    break;
            }

            dialog.updateDialog(finalMessage);
            new Handler().postDelayed(() -> {
                dialog.dismiss();
            } , 2000);
        }

        @Override
        protected void onProgressUpdate(AsyncProgressUpdate... values) {
            super.onProgressUpdate(values);
            dialog.updateDialog(values[0].getMessage());
        }
        @Override
        protected AsyncResponse doInBackground(String... strings) {

            try {
                publishProgress(new AsyncProgressUpdate("upload started"));

                JsonResponse jsonResponse = UserData.UPLOAD(MainActivity.this, userFragment.getUsers());

                publishProgress(new AsyncProgressUpdate("uploading"));

                if(jsonResponse == JsonResponse.SUCCESS)
                    return AsyncResponse.SUCCESS;

                else if( jsonResponse == JsonResponse.NO_CONNECTION)
                    return AsyncResponse.NO_CONNECTION;

                else
                    return AsyncResponse.FAILURE;

            } catch (Exception e) {
                e.printStackTrace();
                return AsyncResponse.FAILURE;
            }
        }

    }

    class DownloadUsersAsync extends BandroidAsyncTask<String> {

        AsyncTaskDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = AsyncTaskDialog.newInstance("Downloading Users", "Preparing to download.");
            dialog.show(getSupportFragmentManager());
        }

        @Override
        protected AsyncResponse doInBackground(String... strings) {

            try {
                if (InternetConnection.isConnected(MainActivity.this)) {
                    publishProgress(new AsyncProgressUpdate("download started"));

                    UserData userData = UserData.DOWNLOAD();
                    SQLiteDatabase db = getUserDatabase().getWritableDatabase();

                    publishProgress(new AsyncProgressUpdate("downloading"));

                    getUserDatabase().updateAllSafe(db, userData.getData(), UserDatabase.DATABASE_TABLE_NAME);

                    return AsyncResponse.SUCCESS;

                } else
                    return AsyncResponse.NO_CONNECTION;

            } catch (Exception e) {
                e.printStackTrace();
                return AsyncResponse.FAILURE;
            }
        }

        @Override
        protected void onProgressUpdate(AsyncProgressUpdate... values) {
            super.onProgressUpdate(values);
            dialog.updateDialog(values[0].getMessage());
        }

        @Override
        protected void onPostExecute(AsyncResponse asyncResponse) {
            super.onPostExecute(asyncResponse);
            String finalMessage = "S";
            switch (asyncResponse){
                case SUCCESS:
                    finalMessage = "Download successful";
                    break;
                case NO_CONNECTION:
                    finalMessage = " Could not connect to internet";
                    break;
                case FAILURE:
                    finalMessage = "Something went wrong";
                    break;
            }

            dialog.updateDialog(finalMessage);
            new Handler().postDelayed(() -> {
                userFragment.refreshAdapter();
                updateFab();
                dialog.dismiss();
            } , 1500);
        }

    }

    @Override
    protected void onDestroy() {
        if(userDatabase != null){
            userDatabase.close();
            userDatabase = null;
        }
        super.onDestroy();
    }
}
