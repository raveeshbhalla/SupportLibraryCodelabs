package in.raveesh.supportlibrarycodelabs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    EditText firstName, surName, address;
    RelativeLayout root;
    FloatingActionButton fab;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeValues();
            }
        });

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

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fab.show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        firstName.addTextChangedListener(textWatcher);
        surName.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void storeValues() {
        SharedPreferences prefs = this.getSharedPreferences("stored", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("firstname", firstName.getText().toString());
        edit.putString("surname", surName.getText().toString());
        edit.putString("address", address.getText().toString());
        edit.apply();
        Snackbar.make(root, "Saved Details", Snackbar.LENGTH_LONG)
                .setAction("Clear", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearValues();
                        setStoredValues();
                        fab.hide();
                    }
                })
                .show();
        fab.hide();
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
