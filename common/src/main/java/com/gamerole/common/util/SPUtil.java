package com.gamerole.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.gamerole.common.base.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SharePreference 工具类
 */
public class SPUtil {


    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param
     * @param key
     * @param object
     */
    public static void putBean(String key, Serializable object) {
        if (object == null) {
            return;
        }
        SharedPreferences.Editor editor = init();


        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectVal);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        editor.commit();
    }

    public static Object getBean(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, "");
            if (TextUtils.isEmpty(objectVal)) {
                return null;
            }
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bais.close();
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static SharedPreferences.Editor init() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.edit();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法    *    * @param context    * @param key    * @param object
     */
    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = init();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值    *    * @param context    * @param key    * @param defaultObject    * @return
     */
    public static <T> T get(String key, T defaultObject) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return (T) sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return (T) Integer.valueOf(sp.getInt(key, (Integer) defaultObject));
        } else if (defaultObject instanceof Boolean) {
            return (T) Boolean.valueOf(sp.getBoolean(key, (Boolean) defaultObject));
        } else if (defaultObject instanceof Float) {
            return (T) Float.valueOf(sp.getFloat(key, (Float) defaultObject));
        } else if (defaultObject instanceof Long) {
            return (T) Long.valueOf(sp.getLong(key, (Long) defaultObject));
        }
        return null;
    }

    /**
     * 清除所有数据    *    * @param context
     */
    public static void clear() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 查询某个key是否已经存在    *    * @param context    * @param key    * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对    *    * @param context    * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值    *    * @param context    * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = App.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类    *    * @author zhy    *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法      *      * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit      *      * @param editor
         */
        public static boolean apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return true;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            }
            return editor.commit();
        }
    }
}

