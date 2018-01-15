package Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mohamed on 09/01/2017.
 */

public class Utils {
    public static String Base_url="http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    public static  String ICON_URL="";
    public  static JSONObject getObject(String tagName , JSONObject jsonObject)throws JSONException{
        JSONObject jobj= jsonObject.getJSONObject(tagName);
        return  jobj;
    }
    public static  String getString(String tagName , JSONObject jsonObject)throws JSONException{
        return jsonObject.getString(tagName);
    }
    public static float getFloat(String tagName , JSONObject jsonObject) throws  JSONException{
        return (float) jsonObject.getDouble(tagName);
    }
    public static Double getDouble(String tagName , JSONObject jsonObject) throws  JSONException{
        return (Double) jsonObject.getDouble(tagName);
    }
    public  static  int getInt(String tagName , JSONObject jsonObject) throws JSONException{
        return (int) jsonObject.getInt(tagName);
    }


}
