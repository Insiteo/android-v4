/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.insiteo.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.insiteo.sdk.Insiteo;
import com.insiteo.sdk.InsiteoDebug;
import com.insiteo.sdk.storage.PrefStorageManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivityTab extends AppCompatActivity {

    public static final String TAG = "MainActivityTab";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        PrefStorageManager prefStorage = new PrefStorageManager(this, PrefStorageManager.PREFERENCES);

        InsiteoDebug.initWithApiKey(this, prefStorage.getToken());
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }


    //**********************************************************************************************
    // Android M permissions request
    //**********************************************************************************************

    public void initializeSdk(){
        Fabric.with(this, new Crashlytics());
        PrefStorageManager prefStorage = new PrefStorageManager(this, PrefStorageManager.PREFERENCES);

        InsiteoDebug.initWithApiKey(this, prefStorage.getToken());

    }

    private static final int PERMISSION_LOCATION_REQUEST = 0;
    private static final int PERMISSION_WRITE_STORAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST = 0;
    private boolean isPermissionGranted = true;
    private String apiKey = null;
    private void checkPermissionStatus() {
        if (checkPermissions()) {
        } else {
            // All permissions are already granted we can start the SDK
            initializeSdk();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void displayPermissionsAlert(String title, String message, final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                requestPermissions(permissions, PERMISSION_REQUEST);
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                isPermissionGranted = false;
                Log.d(TAG, permissions[i] + " refused");
            } else {

                Log.w(TAG, permissions[i] + " granted");
            }
        }
        if (isPermissionGranted) {
            initializeSdk();
        }
        else {
            finish();
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final List<String> permissionsList = new ArrayList<String>();

            StringBuilder message = new StringBuilder();
            message.append("The application requires the following permissions: \n ");

            if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                message.append("\n - ACCESS_FINE_LOCATION in order to scan BLE and WIFI signals\n");

            }

            if (!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                message.append("\n - WRITE_EXTERNAL_STORAGE in order to write temporary files on the SD card \n");

            }

            if (!isPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
                message.append("\n - READ_PHONE_STATE to pause scans on phone calls \n");

            }

            if (!permissionsList.isEmpty()) {
                displayPermissionsAlert("Permission required", message.toString(),
                        permissionsList.toArray(new String[permissionsList.size()]));
                return true;
            }
        }
        return false;
    }

    private boolean isPermissionGranted(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_disconnect);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_disconnect:
                Insiteo.stop();
                PrefStorageManager prefStorage = new PrefStorageManager(this, PrefStorageManager.PREFERENCES);
                prefStorage.resetAllDatas();
                prefStorage.eraseToken();
                return true;
            case R.id.menu_update:
                if(Insiteo.getManager() != null) {
                    Insiteo.getManager().updateSettings();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}