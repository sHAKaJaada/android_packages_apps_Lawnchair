package ch.deletescape.lawnchair.weather

import android.content.Context
import ch.deletescape.lawnchair.Utilities
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper
import com.kwabenaberko.openweathermaplib.models.CurrentWeather

class OWMWeatherAPI(context: Context) : WeatherAPI(), OpenWeatherMapHelper.CurrentWeatherCallback {

    private val apiKey = Utilities.getPrefs(context).getString("pref_weatherApiKey", "eaa824f860b4c670a78797be5ff99cd1")
    private val helper = OpenWeatherMapHelper().apply { setAppId(apiKey) }

    override var city: String = ""
    override var units: Units = Units.METRIC
        get() = field
        set(value) {
            field = value
            helper.setUnits(value.longName)
        }

    override fun getCurrentWeather() {
        helper.getCurrentWeatherByCityName(city, this)
    }

    override fun onSuccess(currentWeather: CurrentWeather) {
        onWeatherData(WeatherData(
                success = true,
                temp = currentWeather.main.temp.toInt(),
                icon = currentWeather.weatherArray[0].icon,
                units = units
        ))
    }

    override fun onFailure(p0: Throwable?) {
        onWeatherData(WeatherData(
                success = false,
                icon = "-1",
                units = units
        ))
    }
}