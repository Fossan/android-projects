
package pl.lodz.p.astroweather.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Wind {

    @SerializedName("chill")
    private String mChill;
    @SerializedName("direction")
    private String mDirection;
    @SerializedName("speed")
    private String mSpeed;

    public String getChill() {
        return mChill;
    }

    public void setChill(String chill) {
        mChill = chill;
    }

    public String getDirection() {
        return mDirection;
    }

    public void setDirection(String direction) {
        mDirection = direction;
    }

    public String getSpeed() {
        return mSpeed;
    }

    public void setSpeed(String speed) {
        mSpeed = speed;
    }

}
