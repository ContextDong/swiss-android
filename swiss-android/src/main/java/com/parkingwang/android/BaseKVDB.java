package com.parkingwang.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈小锅 (yoojia.chen@gmail.com)
 * @since 1.0
 */
class BaseKVDB {

    private final SharedPreferences mPreferences;
    private final Map<String, String> mMemCached = new HashMap<>();
    private final boolean mWriteAsync;

    public BaseKVDB(String dbName, Context context) {
        this(dbName, context, false);
    }

    public BaseKVDB(String dbName, Context context, boolean writeAsync) {
        mPreferences = context.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        mWriteAsync = writeAsync;
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload() {
        mMemCached.clear();
        mMemCached.putAll((Map<? extends String, ? extends String>) mPreferences.getAll());
    }

    public String get(String key) {
        return get(key, "");
    }

    public String get(String key, String defValue) {
        if (mMemCached.containsKey(key)) {
            return mMemCached.get(key);
        } else {
            return defValue;
        }
    }

    private void commit(SharedPreferences.Editor editor) {
        if (mWriteAsync) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public void set(final String key, final String value) {
        if (value == null || value.isEmpty()) {
            Log.e("KVDB", "Ignore set empty value for KEY[" + key + "]. You can try remove(key).");
            return;
        }
        mMemCached.put(key, value);
        SharedPreferences.Editor editor = mPreferences.edit().putString(key, value);
        commit(editor);
    }

    public void remove(final String key) {
        mMemCached.remove(key);
        commit(mPreferences.edit().remove(key));
    }

    public void clear() {
        mMemCached.clear();
        commit(mPreferences.edit().clear());
    }
}