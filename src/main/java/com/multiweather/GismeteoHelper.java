package com.multiweather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.multiweather.WeatherInfo.TimeOfDay;
import com.multiweather.WeatherInfo.WindDirection;

public class GismeteoHelper {

    public static List<GismeteoWeatherInfo> getGismeteoWeatherInfos(City city) throws ParserConfigurationException, SAXException, IOException {        
        Document document = IndexHelper.getDocument(city.getGismeteoUrl());        
        
        List<GismeteoWeatherInfo> weatherInfos = new ArrayList<GismeteoWeatherInfo>();

        NodeList forecasts = document.getElementsByTagName("FORECAST");

        for (int i = 0; i < forecasts.getLength(); i++) {
            GismeteoWeatherInfo weatherInfo = new GismeteoWeatherInfo();
            Element forecast = (Element) forecasts.item(i);
            
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, Integer.valueOf(forecast.getAttribute("year")));
            calendar.set(Calendar.MONTH, Integer.valueOf(forecast.getAttribute("month")) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(forecast.getAttribute("day")));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(forecast.getAttribute("hour")));
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            weatherInfo.setForecastTime(calendar.getTime());
            
            weatherInfo.setTimeOfDay(getTimeOfDay(forecast.getAttribute("tod")));
            Element phenomena = (Element) forecast.getElementsByTagName("PHENOMENA").item(0);
            weatherInfo.setPhenomena(getPhenomena(phenomena.getAttribute("cloudiness"),
                    phenomena.getAttribute("precipitation"), phenomena.getAttribute("rpower"),
                    phenomena.getAttribute("spower")));
            Element temperature = (Element) forecast.getElementsByTagName("TEMPERATURE").item(0);
            weatherInfo.setAirTemperature((Integer.valueOf(temperature.getAttribute("min")) + Integer
                    .valueOf(temperature.getAttribute("max"))) / 2);
            Element pressure = (Element) forecast.getElementsByTagName("PRESSURE").item(0);
            weatherInfo.setAtmospherePressure((Integer.valueOf(pressure.getAttribute("min")) + Integer.valueOf(pressure
                    .getAttribute("max"))) / 2);
            Element wind = (Element) forecast.getElementsByTagName("WIND").item(0);
            weatherInfo.setWindDirection(getWindDirection(wind.getAttribute("direction")));
            weatherInfo.setWindSpeed((Integer.valueOf(wind.getAttribute("min")) + Integer.valueOf(wind
                    .getAttribute("max"))) / 2);
            Element relwet = (Element) forecast.getElementsByTagName("RELWET").item(0);
            weatherInfo.setAirHumidity((Integer.valueOf(relwet.getAttribute("min")) + Integer.valueOf(relwet
                    .getAttribute("max"))) / 2);
            Element heat = (Element) forecast.getElementsByTagName("HEAT").item(0);
            weatherInfo.setComfortAirTemperature((Integer.valueOf(heat.getAttribute("min")) + Integer.valueOf(heat
                    .getAttribute("max"))) / 2);

            weatherInfos.add(weatherInfo);
        }
        
        Utils.log("Gismeteo weather infos for " + city.getCityId() + ": \n " + weatherInfos);
        return weatherInfos;
    }

    private static WindDirection getWindDirection(String direction) {
        switch (Integer.valueOf(direction)) {
        case 0:
            return WindDirection.NORTH;
        case 1:
            return WindDirection.NORTH_EAST;
        case 2:
            return WindDirection.EAST;
        case 3:
            return WindDirection.SOUTH_EAST;
        case 4:
            return WindDirection.SOUTH;
        case 5:
            return WindDirection.SOUTH_WEST;
        case 6:
            return WindDirection.WEST;
        case 7:
            return WindDirection.NORTH_WEST;
        default:
            throw new RuntimeException("No such wind direction: " + direction);
        }
    }   

    private static TimeOfDay getTimeOfDay(String tod) {
        // 0 - ночь 1 - утро, 2 - день, 3 - вечер
        switch (Integer.valueOf(tod)) {
        case 0:
            return TimeOfDay.NIGHT;
        case 1:
            return TimeOfDay.MORNING;
        case 2:
            return TimeOfDay.DAY;
        case 3:
            return TimeOfDay.EVENING;
        default:
            throw new RuntimeException("No such time of day: " + tod);
        }
    }

    private static String getPhenomena(String cloudiness, String precipitation, String rpower, String spower) {
        StringBuilder sb = new StringBuilder();

        switch (Integer.valueOf(cloudiness)) {
        case 0:
            sb.append("ясно");
            break;
        case 1:
            sb.append("малооблачно");
            break;
        case 2:
            sb.append("облачно");
            break;
        case 3:
            sb.append("пасмурно");
            break;
        default:
            throw new RuntimeException("No such cloudiness: " + cloudiness);
        }

        sb.append(", ");

        switch (Integer.valueOf(precipitation)) {
        case 4:
            if (rpower.equals("0")) {
                sb.append("возможен дождь");
            } else {
                sb.append("дождь");
            }
            break;
        case 5:
            if (rpower.equals("0")) {
                sb.append("возможен ливень");
            } else {
                sb.append("ливень");
            }
            break;
        case 6:
            if (rpower.equals("0")) {
                sb.append("возможен снег");
            } else {
                sb.append("снег");
            }
            break;
        case 7:
            if (rpower.equals("0")) {
                sb.append("возможен снег");
            } else {
                sb.append("снег");
            }
            break;
        case 8:
            if (spower.equals("0")) {
                sb.append("возможна гроза");
            } else {
                sb.append("гроза");
            }
            break;
        case 9:
            sb.append("осадки: нет данных");
            break;
        case 10:
            sb.append("без осадков");
            break;
        default:
            throw new RuntimeException("No such precipitation: " + precipitation);
        }       

        return sb.toString();
    }
}
