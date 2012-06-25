package com.multiweather;

public class GismeteoWeatherInfo extends WeatherInfo {

    private String phenomena;

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    @Override
    public String toString() {
        return "\nGismeteoWeatherInfo\n[getPhenomena()=" + getPhenomena() + ",\n getForecastTime()="
                + getForecastTime() + ",\n getTimeOfDay()=" + getTimeOfDay() + ",\n getAirTemperature()="
                + getAirTemperature() + ",\n getAtmospherePressure()=" + getAtmospherePressure()
                + ",\n getWindDirection()=" + getWindDirection() + ",\n getWindSpeed()=" + getWindSpeed()
                + ",\n getAirHumidity()=" + getAirHumidity() + ",\n getComfortAirTemperature()="
                + getComfortAirTemperature() + ",\n getClass()=" + getClass() + ",\n hashCode()=" + hashCode()
                + ",\n toString()=" + super.toString() + "]";
    }
}
