<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="com.multiweather.IndexHelper" %>
<%@ page import="com.multiweather.WeatherData" %>
<%@ page import="com.multiweather.GismeteoWeatherInfo" %>
<%@ page import="com.multiweather.ForecaWeatherInfo" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="styles.css" rel="stylesheet" type="text/css">
<script src="http://vkontakte.ru/js/api/xd_connection.js?2" type="text/javascript"></script>
<script type="text/javascript" src="http://userapi.com/js/api/openapi.js?48"></script>
<script src="scripts.js" type="text/javascript"></script>
<title>Погода в Москве</title>
</head>
<body><a href="http://vk.com/club35235703" target="_blank" id="referenceButtonLink"><button id="referenceButton">Оставить отзыв в группе</button></a><h1>Погода в Москве</h1>
<div id="fromDifferentSourcesNote">из разных источников</div>
<%
WeatherData weatherData = IndexHelper.getWeatherData(request.getServletContext());
List<GismeteoWeatherInfo> gismeteoInfos = weatherData.getGismeteoWeatherInfos();
List<ForecaWeatherInfo> forecaInfos = weatherData.getForecaWeatherInfos();
%>
<table id="buttons">
    <tr>
        <td><button id="postOnWallButton" onclick="postToWall('<%=IndexHelper.constructWallMessage(gismeteoInfos, forecaInfos)%>');" title="У себя на стене">Сообщить друзьям</button></td>
        <td>
            <table>
                <tr>
                    <td>
                        <button id="postOnWallButton" onclick="postToFriendsWall('<%=IndexHelper.constructWallMessage(gismeteoInfos, forecaInfos)%>');" title="У него на стене">Сообщить другу</button>
                    </td>
                    <td style="padding-top: 5px;">
                        <select id="friendSelect">
                            <option value="-1">Выбери друга</option>                            
                        </select>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="whichWallCell">У себя на стене</td>
        <td class="whichWallCell">У него на стене</td>
    </tr>    
</table>
<table id="mainInfo" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="7" class="sourceNote">
            По данным <a href="http://gismeteo.ru" target="_blank">Gismeteo.Ru</a>
        </td>
    </tr>    
    <tr class="header">
        <td>Время</td>
        <td>Атмосферные явления</td>
        <td>Tемпература воздуха</td>
        <td>Атмосферное давление</td>
        <td>Ветер</td>
        <td>Влажность воздуха</td>
        <td>Комфорт</td>
    </tr>
    <%for (GismeteoWeatherInfo weatherInfo : gismeteoInfos) { %>
    <tr>
        <td>            
            <%= weatherInfo.getTimeOfDay().getSymbol() %>
            <span class="littleTime"><%= IndexHelper.getDispayableDate(weatherInfo.getForecastTime())%></span>
        </td>
        <td class="phenomenaCell"><%= weatherInfo.getPhenomena() %></td>
        <td><%= weatherInfo.getAirTemperature() %> °C</td>
        <td><%= weatherInfo.getAtmospherePressure() %> мм рт.ст.</td>
        <td><%= weatherInfo.getWindDirection().getSymbol() %>, <span class="nobr"><%= weatherInfo.getWindSpeed() %> м/сек</span></td>
        <td><%= weatherInfo.getAirHumidity() %> %</td>
        <td><%= weatherInfo.getComfortAirTemperature() %></td>
    </tr>
    <% } %>    
    <tr>
        <td colspan="7" class="sourceNote">
            По данным <a href="http://foreca.com" target="_blank">Foreca</a><a href="#forecaQuote" id="forecaQuoteLink">*</a>
        </td>
    </tr>    
    <tr class="header">
        <td>Время</td>
        <td>Атмосферные явления</td>
        <td>Tемпература воздуха</td>
        <td>Атмосферное давление</td>
        <td>Ветер</td>
        <td>Влажность воздуха</td>
        <td>Комфорт</td>
    </tr>
    <%for (ForecaWeatherInfo weatherInfo : forecaInfos) { %>
    <tr>
        <td>
            <%= weatherInfo.getTimeOfDay().getSymbol() %>
            <span class="littleTime"><%= IndexHelper.getDispayableDate(weatherInfo.getForecastTime())%></span>
        </td>
        <td class="phenomenaCell">
            <span class="nobr">Вероятность осадков: <%= weatherInfo.getPrecipitationProbability() %>%</span>
            <span class="nobr">Вероятность грозы: <%= weatherInfo.getThunderstormProbability() %>%</span>
            <span class="nobr">Облачность: <%= weatherInfo.getCloudiness() %>%</span>
        </td>
        <td><%= weatherInfo.getAirTemperature() %> °C</td>
        <td><%= weatherInfo.getAtmospherePressure() %> мм рт.ст.</td>
        <td><%= weatherInfo.getWindDirection().getSymbol() %>, <span class="nobr"><%= weatherInfo.getWindSpeed() %> м/сек</span></td>
        <td><%= weatherInfo.getAirHumidity() %> %</td>
        <td><%= weatherInfo.getComfortAirTemperature() %></td>
    </tr>
    <% } %>   
</table>

<div id="forecaQuote">
*Foreca предоставляет метеорологическую информацию <a href="http://corporate.foreca.com/ru/news/2/27/finskaya-kompaniya-Foreca-predostavlyaet-meteorologicheskuyu-informatsiyu-dlya-Google/" target="_blank">для Google</a> и <a href="http://corporate.foreca.com/en/news/21/19/Foreca-provides-weather-services-to-Russian-biggest-search-engine-Yandex/" target="_blank">для Яндекс</a>
</div>
<br />
<script type="text/javascript">
  VK.init({apiId: 2797247, onlyWidgets: true});
</script>

<!-- Put this div tag to the place, where the Poll block will be -->
<div id="vkPollContainer">
<div id="vk_poll"></div>
</div>
<script type="text/javascript">
VK.Widgets.Poll("vk_poll", {width: "300"}, "24021574_6083b674176565ee31");
</script>

<!-- Weather data loaded at <%= weatherData.getLoadTime()%> -->
</body>
<script type="text/javascript">  
  weatherInit(<%=IndexHelper.calculateCounter(gismeteoInfos, forecaInfos)%>);
</script>
</html>