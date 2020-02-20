package com.meiling.mediaplayer;

import android.util.Log;


public class Ulog {

    private static boolean DEBUG = true;
    private static final int LENGTH_LIMIT = 2000;
    public static final String TAG = "_AndroidRuntime";//方面能够同时查看到异常信息

    public static void setDEBUG(boolean DEBUG) {
        Ulog.DEBUG = DEBUG;
    }

    public static void e(String msg) {
        if (DEBUG) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.e(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.e(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.e(TAG, msg.toString());
                }
            }
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.w(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.w(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.w(TAG, msg.toString());
                }
            }
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.i(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.i(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.i(TAG, msg.toString());
                }
            }
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            if (msg != null && msg.length() > 0) {
                if (msg.length() > LENGTH_LIMIT) {
                    int chunkCount = msg.length() / LENGTH_LIMIT;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = LENGTH_LIMIT * (i + 1);
                        if (max >= msg.length()) {
                            Log.d(TAG, msg.substring(LENGTH_LIMIT * i));
                        } else {
                            Log.d(TAG, msg.substring(LENGTH_LIMIT * i, max));
                        }
                    }
                } else {
                    Log.d(TAG, msg.toString());
                }
            }
        }
    }
}
