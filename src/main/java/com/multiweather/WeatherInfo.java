package com.multiweather;

import java.util.Date;

public class WeatherInfo {
    private Date forecastTime;
    private TimeOfDay timeOfDay;
    private int airTemperature;
    private int atmospherePressure;
    private WindDirection windDirection;
    private int windSpeed;
    private int airHumidity;
    private int comfortAirTemperature;
    
    public static enum TimeOfDay {
        NIGHT("Ночь"), MORNING("Утро"), DAY("День"), EVENING("Вечер");
        
        private String symbol;
        
        private TimeOfDay(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }      
    }
    
    public static enum WindDirection {
        //NORTH("↓"), NORTH_EAST("↙"), EAST("←"), SOUTH_EAST("↖ "), SOUTH("↑"), SOUTH_WEST("↗"), WEST("→"), NORTH_WEST("↘");
        NORTH("С"), NORTH_EAST("СВ"), EAST("В"), SOUTH_EAST("ЮВ"), SOUTH("Ю"), SOUTH_WEST("ЮЗ"), WEST("З"), NORTH_WEST("СЗ");
       
        private String symbol;
        
        private WindDirection(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    public Date getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(Date forecastTime) {
        this.forecastTime = forecastTime;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(int airTemperature) {
        this.airTemperature = airTemperature;
    }

    public int getAtmospherePressure() {
        return atmospherePressure;
    }

    public void setAtmospherePressure(int atmospherePressure) {
        this.atmospherePressure = atmospherePressure;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(int airHumidity) {
        this.airHumidity = airHumidity;
    }

    public int getComfortAirTemperature() {
        return comfortAirTemperature;
    }

    public void setComfortAirTemperature(int comfortAirTemperature) {
        this.comfortAirTemperature = comfortAirTemperature;
    }
}
