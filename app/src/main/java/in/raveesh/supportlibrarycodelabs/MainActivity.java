package in.raveesh.supportlibrarycodelabs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    EditText firstName, surName, address;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        root = (RelativeLayout) findViewById(R.id.root);
        firstName = (EditText) findViewById(R.id.firstName);
        surName = (EditText) findViewById(R.id.surName);
        address = (EditText) findViewById(R.id.address);

        setStoredValues();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,                                  // Drawer Layout
                toolbar,                                        // toolbar
                R.string.app_name,                              // Title when opened
                R.string.action_settings) {                     // Title when closed

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            storeValues();
        }
        return super.onOptionsItemSelected(item);
    }

    private void storeValues() {
        SharedPreferences prefs = this.getSharedPreferences("stored", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("firstname", firstName.getText().toString());
        edit.putString("surname", surName.getText().toString());
        edit.putString("address", address.getText().toString());
        edit.apply();
        Toast.makeText(this, "Saved Details", Toast.LENGTH_LONG).show();
    }

    private void setStoredValues() {
        SharedPreferences prefs = this.getSharedPreferences("stored", MODE_PRIVATE);
        firstName.setText(prefs.getString("firstname", ""));
        surName.setText(prefs.getString("surname", ""));
        address.setText(prefs.getString("address", ""));
    }

    private void clearValues(){
        SharedPreferences prefs = this.getSharedPreferences("stored", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("firstname", "");
        edit.putString("surname", "");
        edit.putString("address", "");
        edit.apply();
    }
}
