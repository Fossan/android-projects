
package pl.lodz.p.pogodynka.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Atmosphere {

    @SerializedName("humidity")
    private String mHumidity;
    @SerializedName("pressure")
    private String mPressure;
    @SerializedName("rising")
    private String mRising;
    @SerializedName("visibility")
    private String mVisibility;

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure;
    }

    public String getRising() {
        return mRising;
    }

    public void setRising(String rising) {
        mRising = rising;
    }

    public String getVisibility() {
        return mVisibility;
    }

    public void setVisibility(String visibility) {
        mVisibility = visibility;
    }

}
