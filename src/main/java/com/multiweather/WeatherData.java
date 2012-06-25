package com.multiweather;

import java.util.Date;
import java.util.List;

public class WeatherData {
    
    private Date loadTime;
    
    private List<GismeteoWeatherInfo> gismeteoWeatherInfos;
    
    private List<ForecaWeatherInfo> forecaWeatherInfos;
    
    public WeatherData(Date loadTime) {
        this.loadTime = loadTime;
    }

    public Date getLoadTime() {
        return loadTime;
    }    

    public List<GismeteoWeatherInfo> getGismeteoWeatherInfos() {
        return gismeteoWeatherInfos;
    }

    public void setGismeteoWeatherInfos(List<GismeteoWeatherInfo> gismeteoWeatherInfos) {
        this.gismeteoWeatherInfos = gismeteoWeatherInfos;
    }

    public List<ForecaWeatherInfo> getForecaWeatherInfos() {
        return forecaWeatherInfos;
    }

    public void setForecaWeatherInfos(List<ForecaWeatherInfo> forecaWeatherInfos) {
        this.forecaWeatherInfos = forecaWeatherInfos;
    }
}
