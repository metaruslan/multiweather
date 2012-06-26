package com.multiweather;

public enum City {
    MOSCOW("moscow", "Москва", "Москве", true, "http://informer.gismeteo.ru/xml/27612_1.xml", true),
    TVER("tver", "Тверь", "Твери", true, "http://informer.gismeteo.ru/xml/27402_1.xml", false);
    
    private String cityId;
    private String name;
    private String nameInGenitiveCase;
    private boolean gismeteoSupported;
    private String gismeteoUrl;
    private boolean forecaSupported;
        
    City(String cityId, String name, String nameInGenitiveCase, boolean gismeteoSupported, String gismeteoUrl, boolean forecaSupported) {
        this.cityId = cityId;
        this.name = name;
        this.nameInGenitiveCase = nameInGenitiveCase;
        this.gismeteoSupported = gismeteoSupported;
        this.gismeteoUrl = gismeteoUrl;
        this.forecaSupported = forecaSupported;
    }

    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public String getNameInGenitiveCase() {
        return nameInGenitiveCase;
    }

    public boolean isGismeteoSupported() {
        return gismeteoSupported;
    }
    
    public String getGismeteoUrl() {
        return gismeteoUrl;
    }

    public boolean isForecaSupported() {
        return forecaSupported;
    }    
}
