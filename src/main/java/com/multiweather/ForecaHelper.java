package com.multiweather;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.multiweather.WeatherInfo.WindDirection;

public class ForecaHelper {

    public static List<ForecaWeatherInfo> getForecaWeatherInfos(List<GismeteoWeatherInfo> gismeteoWeatherInfos)
            throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
        Document document = IndexHelper
                .getDocument("http://http.foreca.com/feed/feed.php/6hour.xml?user=moscow&pass=N4nkR3At");

        List<ForecaWeatherInfo> weatherInfos = new ArrayList<ForecaWeatherInfo>();

        Element element = findByLocId(document, "100524901");

        NodeList allSteps = element.getElementsByTagName("step");

        for (WeatherInfo gismeteoWeatherInfo : gismeteoWeatherInfos) {
            Element step = findTheSameStep(allSteps, gismeteoWeatherInfo);

            ForecaWeatherInfo weatherInfo = new ForecaWeatherInfo();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            weatherInfo.setForecastTime(dateFormat.parse(step.getAttribute("dt")));

            weatherInfo.setTimeOfDay(gismeteoWeatherInfo.getTimeOfDay());

            weatherInfo.setPrecipitationProbability(Integer.valueOf(step.getAttribute("pp")));
            weatherInfo.setThunderstormProbability(Integer.valueOf(step.getAttribute("tp")));
            weatherInfo.setCloudiness(Integer.valueOf(step.getAttribute("c")));
            weatherInfo.setAirTemperature(Integer.valueOf(step.getAttribute("t")));
            weatherInfo.setAtmospherePressure(Integer.valueOf(step.getAttribute("p")));
            weatherInfo.setWindDirection(getWindDirection(step.getAttribute("wn")));
            weatherInfo.setWindSpeed(Math.round(Float.valueOf(step.getAttribute("ws"))));
            weatherInfo.setAirHumidity(Integer.valueOf(step.getAttribute("rh")));
            weatherInfo.setComfortAirTemperature(Integer.valueOf(step.getAttribute("tf")));

            weatherInfos.add(weatherInfo);
        }
        
        Utils.log("Foreca weather infos: \n " + weatherInfos);
        return weatherInfos;
    }    

    private static WindDirection getWindDirection(String wn) {
        // wn Направление ветра (8 направлений скорости ветра: N/NE/E/SE/S...)
        if ("N".equals(wn))
            return WindDirection.NORTH;
        if ("NE".equals(wn))
            return WindDirection.NORTH_EAST;
        if ("E".equals(wn))
            return WindDirection.EAST;
        if ("SE".equals(wn))
            return WindDirection.SOUTH_EAST;
        if ("S".equals(wn))
            return WindDirection.SOUTH;
        if ("SW".equals(wn))
            return WindDirection.SOUTH_WEST;
        if ("W".equals(wn))
            return WindDirection.WEST;
        if ("NW".equals(wn))
            return WindDirection.NORTH_WEST;
        throw new RuntimeException("No such wn: " + wn);
    }

    private static Element findTheSameStep(NodeList allSteps, WeatherInfo gismeteoWeatherInfo) throws ParseException, TransformerException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        for (int i = 0; i < allSteps.getLength(); i++) {
            if (dateFormat.parse(((Element) allSteps.item(i)).getAttribute("dt")).equals(
                    gismeteoWeatherInfo.getForecastTime())) {
                Utils.log("Foreca element that corresponds to Gismeteo element found: \n"
                        + IndexHelper.formatNode(allSteps.item(i)));
                return (Element) allSteps.item(i);
            }

        }
        throw new RuntimeException("No such Foreca step as in gismeteo. Gismeteo time: "
                + gismeteoWeatherInfo.getForecastTime());
    }

    private static Element findByLocId(Document document, String id) {
        NodeList nodeList = document.getElementsByTagName("loc");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (((Element) nodeList.item(i)).getAttribute("id").equals(id)) {
                return (Element) nodeList.item(i);
            }
        }
        throw new RuntimeException("Loc not found with id: " + id);
    }
}
