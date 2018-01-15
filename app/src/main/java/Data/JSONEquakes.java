package Data;

import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import model.Earthquakes;
import model.Places;


/**
 * Created by mohamed on 09/01/2017.
 */

public class JSONEquakes {
   public static Earthquakes getEarthquakes(String Data) {
        Earthquakes earthquakes = new Earthquakes();

       //creatJSon object from data
       try {
           JSONObject jsonObject = new JSONObject(Data);
           Places places = new Places();
           JSONObject geo = Utils.getObject("coordinates", jsonObject);
           places.setLon(Utils.getFloat("longitude", geo));
           places.setLat(Utils.getFloat("latitude", geo));
           places.setDepth(Utils.getFloat("depth", geo));
           places.setTitle(Utils.getString("title", geo));

           return earthquakes;
       } catch (JSONException e) {
           e.printStackTrace();
           return null;
       }



   }
}