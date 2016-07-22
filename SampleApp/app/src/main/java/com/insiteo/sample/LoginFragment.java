package com.insiteo.sample;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.insiteo.sdk.InsiteoDebug;
import com.insiteo.sdk.storage.PrefStorageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyril on 11/02/2016.
 */
public class LoginFragment extends Fragment implements AppCompatCallback {
    public static final String TAG = "LoginFragment";
    private EditText apikeyEditText;
    private Button loginButton;
    PrefStorageManager mPrefStorage;
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            mPrefStorage = new PrefStorageManager(getContext(), PrefStorageManager.PREFERENCES);

            apikeyEditText = (EditText) rootView.findViewById(R.id.apikey_edit);
            apikeyEditText.setText(mPrefStorage.getToken());
            loginButton = (Button) rootView.findViewById(R.id.login_button);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String apiKey = apikeyEditText.getText().toString();

                    InsiteoDebug.initWithApiKey(getContext(), apiKey);
                }
            });
            return rootView;
        }


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
