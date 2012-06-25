package com.multiweather;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.multiweather.WeatherInfo.TimeOfDay;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class IndexHelper {
    
    private static final long MILLISECONDS_IN_HOUR = 1000 * 60 * 60;
    
    public static Document getDocument(String url) throws ParserConfigurationException, SAXException, IOException {
        Utils.log("Downloading file from url: " + url);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document document = dBuilder.parse(url);
        document.getDocumentElement().normalize();

        Utils.log("XML file parsed: \n" + format(document));
        return document;
    }
    
    public static WeatherData getWeatherData(ServletContext servletContext) throws ParserConfigurationException, SAXException, IOException, ParseException, TransformerException {
        WeatherData cachedWeatherData = (WeatherData)servletContext.getAttribute("com.multiweather.WeatherData");
        long now = new Date().getTime();
                             
        Utils.log("Cached weather data load time is " + (cachedWeatherData != null ? cachedWeatherData.getLoadTime() : "[cache is null]"));
        if (cachedWeatherData == null || now - cachedWeatherData.getLoadTime().getTime() > MILLISECONDS_IN_HOUR) {
            if (cachedWeatherData == null) {
                Utils.log("Cache is null. Loading from remote sources...");
            } else {
                Utils.log("Elapsed time \n" + formatMilliseconds(now - cachedWeatherData.getLoadTime().getTime())
                        + " is more than\n" + formatMilliseconds(MILLISECONDS_IN_HOUR) + "\nLoading from remote sources...");                
            }             
            List<GismeteoWeatherInfo> gismeteoWeatherInfos = GismeteoHelper.getGismeteoWeatherInfos();
            List<ForecaWeatherInfo> forecaWeatherInfos = ForecaHelper.getForecaWeatherInfos(gismeteoWeatherInfos);
            WeatherData newWeatherData = new WeatherData(new Date(now));
            newWeatherData.setGismeteoWeatherInfos(gismeteoWeatherInfos);
            newWeatherData.setForecaWeatherInfos(forecaWeatherInfos);
            servletContext.setAttribute("com.multiweather.WeatherData", newWeatherData);
            return newWeatherData;
        } else {
            Utils.log("Using cache");
            return cachedWeatherData;
        }
    }
    
    private static String formatMilliseconds(long milliseconds) {
        return milliseconds + " milliseconds (" + (milliseconds / 1000) + " seconds or " + (milliseconds / (1000 * 60)) + " minutes)"; 
    }

    public static String format(Document document) {
        try {
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatNode(Node node) throws TransformerException {
        // Set up the output transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        // Print the DOM node

        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(node);
        trans.transform(source, result);

        return sw.toString();
    }

    public static String getDispayableDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(date);
    }
    
    public static String constructWallMessage(List<GismeteoWeatherInfo> gismeteoInfos, List<ForecaWeatherInfo> forecaInfos) {
        GismeteoWeatherInfo dayGismeteoInfo = findDayGismeteoInfo(gismeteoInfos);
        ForecaWeatherInfo dayForecaInfo = findDayForecaInfo(forecaInfos);
        return "Внимание! Вещаю погоду! В Москве днем: на gismeteo.ru: " + dayGismeteoInfo.getAirTemperature() + ", на foreca.ru: " + dayForecaInfo.getAirTemperature() + ". Пригодилось? Скажи спасибо мне и смотри подробнее тут: vk.com/app2797247";
    }
    
    public static int calculateCounter(List<GismeteoWeatherInfo> gismeteoInfos, List<ForecaWeatherInfo> forecaInfos) {
        GismeteoWeatherInfo dayGismeteoInfo = findDayGismeteoInfo(gismeteoInfos);
        ForecaWeatherInfo dayForecaInfo = findDayForecaInfo(forecaInfos);
        return (dayGismeteoInfo.getAirTemperature() + dayForecaInfo.getAirTemperature()) / 2;
    }
    
    private static GismeteoWeatherInfo findDayGismeteoInfo(List<GismeteoWeatherInfo> weatherInfos) {
        for (GismeteoWeatherInfo weatherInfo : weatherInfos) {
            if (weatherInfo.getTimeOfDay() == TimeOfDay.DAY) {
                return weatherInfo;
            }
        }
        throw new RuntimeException("Day info not found");
    }
    
    private static ForecaWeatherInfo findDayForecaInfo(List<ForecaWeatherInfo> weatherInfos) {
        for (ForecaWeatherInfo weatherInfo : weatherInfos) {
            if (weatherInfo.getTimeOfDay() == TimeOfDay.DAY) {
                return weatherInfo;
            }
        }
        throw new RuntimeException("Day info not found");
    }
}
