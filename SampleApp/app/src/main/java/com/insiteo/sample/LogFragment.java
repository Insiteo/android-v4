package com.insiteo.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insiteo.sdk.INSLoggerDelegate;
import com.insiteo.sdk.utils.DebugView;
import com.insiteo.sdk.INSLogger;

import java.util.logging.Level;

public class LogFragment extends Fragment {

    private static final String TAG = "LogFragment";

    private DebugView mDebugView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        mDebugView = (DebugView) rootView.findViewById(R.id.debug_view);

        mLogINSLoggerDelegate = new INSLoggerDelegate() {
            @Override
            public boolean onIntercepted(final String tag, final String message, final Level level) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int color;

                            if (level.intValue() >= Level.SEVERE.intValue()) {
                                color = getResources().getColor(R.color.log_severe);
                            } else if (level.intValue() >= Level.WARNING.intValue()) {
                                color = getResources().getColor(R.color.log_warning);
                            } else if (level.intValue() >= Level.INFO.intValue()) {
                                color = getResources().getColor(R.color.log_info);
                            } else {
                                color = getResources().getColor(R.color.log_all);
                            }

                            mDebugView.addEvent(message, color);
                        }
                    });
                }
                return true;
            }
        };
        INSLogger.setInterceptor(mLogINSLoggerDelegate);

        return rootView;
    }

    //**********************************************************************************************
    // Log interceptor
    //**********************************************************************************************

    INSLoggerDelegate mLogINSLoggerDelegate;
}
