
package pl.lodz.p.pogodynka.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Units {

    @SerializedName("distance")
    private String mDistance;
    @SerializedName("pressure")
    private String mPressure;
    @SerializedName("speed")
    private String mSpeed;
    @SerializedName("temperature")
    private String mTemperature;

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure;
    }

    public String getSpeed() {
        return mSpeed;
    }

    public void setSpeed(String speed) {
        mSpeed = speed;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

}
