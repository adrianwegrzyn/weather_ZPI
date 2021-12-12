package com.zpi.weather.file.domain;

public class WeatherLineValidator {
    static void validateLine(String[] splitData) throws Exception {
        String precipitation = splitData[0];
        String temperatureMin = splitData[11];
        String temperatureMax = splitData[10];
        String temperatureAvg = splitData[9];
        String windDirection = splitData[12];
        String windSpeed = splitData[13];
        String day = splitData[3];
        String month = splitData[2];
        String year = splitData[4];
        validatePrecipitation(precipitation);
        validateTemperature(temperatureMin);
        validateTemperature(temperatureMax);
        validateTemperature(temperatureAvg);
        validateWindDirection(windDirection);
        validateWindSpeed(windSpeed);
        validateDate(day, month, year);
    }

    private static void validateDate(String day, String month, String year ) throws Exception{
        int d = Integer.parseInt(day);
        int m = Integer.parseInt(month);
        int y = Integer.parseInt(year);
        if (d < 0 || d > 31 || m < 0 || m > 11 || y < 1800 || y > 2100) {
            throw new UnsupportedOperationException();
        }
    }

    private static void validatePrecipitation(String value) throws Exception{
        double precipitation = Double.parseDouble(value);
        if(precipitation < 0.0 || precipitation > 100.0 ) {
            throw new UnsupportedOperationException();
        }
    }

    private static void validateTemperature(String value) throws Exception{
        int temperature = Integer.parseInt(value);
        if(temperature < -80  || temperature > 150 ) {
            throw new UnsupportedOperationException();
        }
    }

    private static void validateWindDirection(String value) throws Exception {
        int windDirection = Integer.parseInt(value);
        if(windDirection < 0 || windDirection > 360 ) {
            throw new UnsupportedOperationException();
        }
    }

    private static void validateWindSpeed(String value) throws Exception {
        double windSpeed = Double.parseDouble(value);
        if(windSpeed < 0 || windSpeed > 500 ) {
            throw new UnsupportedOperationException();
        }
    }
}
