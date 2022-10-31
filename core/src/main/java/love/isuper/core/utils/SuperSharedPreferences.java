package love.isuper.core.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.Set;

/**
 * Created by guoshichao on 2022/1/12
 * 替换SharedPreferences为MMKV
 */
public final class SuperSharedPreferences {

    public static SuperSharedPreferences getDefaultSharedPreferences() {
        Context context = AppInfo.getApplication();
        String defaultName = context.getPackageName() + "_preferences";
        return new SuperSharedPreferences(context, defaultName, Context.MODE_PRIVATE);
    }

    public static SuperSharedPreferences getSharedPreferences(String name) {
        return new SuperSharedPreferences(AppInfo.getApplication(), name, Context.MODE_PRIVATE);
    }

    public static SuperSharedPreferences getSharedPreferences(String name, int mode) {
        return new SuperSharedPreferences(null, name, mode);
    }

    public static SuperSharedPreferences getSharedPreferences(Context context, String name, int mode) {
        return new SuperSharedPreferences(context, name, mode);
    }

    /**
     * WRITE_TO_MMKV 为ture表示数据写入MMKV，为false，表示数据从MMKV写入SharedPreferences
     * 发现项目中有一些是使用的SP，没有统一管理，所以还是使用SP，不使用MMKV
     */
    private static boolean mMMKVEnabled = false;
    public static void setMMKVEnable(boolean enable) {
        mMMKVEnabled = enable;
    }
    public static boolean isMMKVEnable() {
        return mMMKVEnabled;
    }

    private MMKV mmkv, defaultMMKV;
    private SharedPreferences spData;
    private SharedPreferences.Editor spEditor;

    private static boolean mmkvInited = false;
    public static void initMMKV(Application app) {
        if (mmkvInited) {
            return;
        }
        mmkvInited = true;

        if (SuperSharedPreferences.isMMKVEnable()) {
            String root = app.getFilesDir().getAbsolutePath() + "/mmkv";
            MMKVLogLevel logLevel = AppInfo.isDebug() ? MMKVLogLevel.LevelDebug : MMKVLogLevel.LevelError;
            try {
                MMKV.initialize(root, new MMKV.LibLoader() {
                    @Override
                    public void loadLibrary(String libName) {
                        try {
                            ReLinker.loadLibrary(app, libName);
                        } catch (Throwable ex) {
                            SuperSharedPreferences.setMMKVEnable(false);
                        }
                    }
                }, logLevel);
            } catch (Throwable ex) {
                SuperSharedPreferences.setMMKVEnable(false);
            }
        }
    }

    private SuperSharedPreferences(Context context, String name, int mode) {
        if (mMMKVEnabled) {
            try {
                MMKV.initialize(AppInfo.getApplication());
                this.mmkv = MMKV.mmkvWithID(name);
                this.defaultMMKV = MMKV.defaultMMKV();
            } catch (IllegalArgumentException iae) {
                String message = iae.getMessage();
                if (!TextUtils.isEmpty(message) && message.contains("Opening a multi-process MMKV")) {
                    try {
                        this.mmkv = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE);
                        this.defaultMMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null);
                    } catch (Throwable ex) {
                        //如果出现异常抛埋点给服务端
//                        SuperStatistics.getEvent().eventNormal("MMKV", 0, 102, name);
                        return;
                    }
                }
            } catch (Throwable ex) {
                //如果出现异常抛埋点给服务端
//                SuperStatistics.getEvent().eventNormal("MMKV", 0, 101, name);
                return;
            }
        }

        if (null == context) {
            context = AppInfo.getApplication();
        }

        if (null != context) {
            if (mMMKVEnabled) {
                if (null != defaultMMKV && !defaultMMKV.contains(name)) {
                    SharedPreferences sources = context.getSharedPreferences(name, mode);
                    mmkv.importFromSharedPreferences(sources);
                    defaultMMKV.encode(name, true);
                    LogUtils.i("SuperSharedPreferences", "transform SP-" + name + " to MMKV");
                }
            } else {
                spData = context.getSharedPreferences(name, mode);
            }
        }
    }

    public final class Editor {
        public Editor putString(String key, @Nullable String value) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, value);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putString(key, value);
                }
            }
            return this;
        }

        public Editor putStringSet(String key, @Nullable Set<String> values) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, values);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putStringSet(key, values);
                }
            }
            return this;
        }

        public Editor putInt(String key, int value) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, value);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putInt(key, value);
                }
            }
            return this;
        }

        public Editor putLong(String key, long value) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, value);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putLong(key, value);
                }
            }
            return this;
        }

        public Editor putFloat(String key, float value) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, value);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putFloat(key, value);
                }
            }
            return this;
        }

        public Editor putBoolean(String key, boolean value) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.encode(key, value);
                }
            } else {
                if (null != spEditor) {
                    spEditor.putBoolean(key, value);
                }
            }
            return this;
        }

        public Editor remove(String key) {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.removeValueForKey(key);
                }
            } else {
                if (null != spEditor) {
                    spEditor.remove(key);
                }
            }
            return this;
        }

        public Editor clear() {
            if (mMMKVEnabled) {
                if (null != mmkv) {
                    mmkv.clearAll();
                }
            } else {
                if (null != spEditor) {
                    spEditor.clear();
                }
            }
            return this;
        }

        /**
         * 无实际意义，只是为了适配以前已经调用了commit的旧的方式
         */
        public boolean commit() {
            if (!mMMKVEnabled) {
                if (null != spEditor) {
                    return spEditor.commit();
                }
            }
            return true;
        }

        /**
         * 无实际意义，只是为了适配以前已经调用了apply的旧的方式
         */
        public void apply() {
            if (!mMMKVEnabled) {
                if (null != spEditor) {
                    spEditor.apply();
                }
            }
        }
    }

    public Editor edit() {
        if (!mMMKVEnabled) {
            spEditor = spData.edit();
        }
        return new Editor();
    }

    @Nullable
    public String getString(String key, @Nullable String defValue) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getString(key, defValue);
            }
        } else {
            if (null != spData) {
                return spData.getString(key, defValue);
            }
        }
        return defValue;
    }

    @Nullable
    Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getStringSet(key, defValues);
            }
        } else {
            if (null != spData) {
                return spData.getStringSet(key, defValues);
            }
        }
        return defValues;
    }

    public int getInt(String key, int defValue) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getInt(key, defValue);
            }
        } else {
            if (null != spData) {
                return spData.getInt(key, defValue);
            }
        }
        return defValue;
    }

    public long getLong(String key, long defValue) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getLong(key, defValue);
            }
        } else {
            if (null != spData) {
                return spData.getLong(key, defValue);
            }
        }
        return defValue;
    }

    public float getFloat(String key, float defValue) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getFloat(key, defValue);
            }
        } else {
            if (null != spData) {
                return spData.getFloat(key, defValue);
            }
        }
        return defValue;
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.getBoolean(key, defValue);
            }
        } else {
            if (null != spData) {
                return spData.getBoolean(key, defValue);
            }
        }
        return defValue;
    }

    public boolean contains(String key) {
        if (mMMKVEnabled) {
            if (null != mmkv) {
                return mmkv.containsKey(key);
            }
        } else {
            if (null != spData) {
                return spData.contains(key);
            }
        }
        return false;
    }

}