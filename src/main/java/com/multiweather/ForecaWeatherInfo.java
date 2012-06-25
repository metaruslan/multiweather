package com.multiweather;

public class ForecaWeatherInfo extends WeatherInfo {

    private int precipitationProbability;
    
    private int thunderstormProbability;
    
    private int cloudiness;

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(int thunderstormProbability) {
        this.thunderstormProbability = thunderstormProbability;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return "\nForecaWeatherInfo\n[getPrecipitationProbability()=" + getPrecipitationProbability()
                + ",\n getThunderstormProbability()=" + getThunderstormProbability() + ",\n getCloudiness()="
                + getCloudiness() + ",\n getForecastTime()=" + getForecastTime() + ",\n getTimeOfDay()="
                + getTimeOfDay() + ",\n getAirTemperature()=" + getAirTemperature() + ",\n getAtmospherePressure()="
                + getAtmospherePressure() + ",\n getWindDirection()=" + getWindDirection() + ",\n getWindSpeed()="
                + getWindSpeed() + ",\n getAirHumidity()=" + getAirHumidity() + ",\n getComfortAirTemperature()="
                + getComfortAirTemperature() + ",\n getClass()=" + getClass() + ",\n hashCode()=" + hashCode()
                + ",\n toString()=" + super.toString() + "]";
    }
}
