
package pl.lodz.p.astroweather.model;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class YahooWeather {

    @SerializedName("query")
    private Query mQuery;

    public Query getQuery() {
        return mQuery;
    }

    public void setQuery(Query query) {
        mQuery = query;
    }

    public Channel getWeather() {
        return getQuery().getResults().getChannel();
    }
}
