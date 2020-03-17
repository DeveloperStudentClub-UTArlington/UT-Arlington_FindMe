package com.kevappsgaming.findme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kevappsgaming.findme.instant_messaging.ContactActivity;
import com.kevappsgaming.findme.login_register.LoginActivity;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private float curveRadius = 20F;
    private Switch userVisibility;
    private TextView userVisibilityText;
    private FloatingActionButton chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.map);
        chatButton = (FloatingActionButton) findViewById(R.id.button_chat);
        userVisibility = (Switch) findViewById(R.id.user_visibility);
        userVisibilityText = (TextView) findViewById(R.id.user_status);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userVisibility.setChecked(false);
        setChatButtonListener();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //mapView.setOutlineProvider() = ;
        }

        //when you already implement OnMapReadyCallback in the activity
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void setChatButtonListener(){
        chatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void checkSwitchStatus(){
        userVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    userVisibilityText.setText(getResources().getString(R.string.user_visibility_on));
                    userVisibilityText.setTextColor(getResources().getColor(R.color.green));
                } else {
                    // The toggle is disabled
                    userVisibilityText.setText(getResources().getString(R.string.user_visibility_off));
                    userVisibilityText.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        checkSwitchStatus();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }
}
