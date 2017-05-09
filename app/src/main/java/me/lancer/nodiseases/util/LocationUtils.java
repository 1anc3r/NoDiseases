/**
 *
 */
package me.lancer.nodiseases.util;

import android.text.TextUtils;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 辅助工具类
 *
 * @author hongming.wang
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class LocationUtils {
    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP = 2;

    public final static String KEY_URL = "URL";
    public final static String URL_H5LOCATION = "file:///android_asset/location.html";

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param location
     * @return
     */
    public synchronized static JSONObject getLocation(AMapLocation location) {
        try {
            if (null == location) {
                return null;
            }
            JSONObject jb = new JSONObject();
            if (location.getErrorCode() == 0) {
                jb.put("x", location.getLongitude());
                jb.put("y", location.getLatitude());
                jb.put("address", location.getAddress());
                return jb;
            } else {
                jb.put("error", "错误码:"+location.getErrorCode()+"\n错误信息:"+location.getErrorInfo()+"\n错误描述:"+location.getLocationDetail());
                return jb;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
