package com.example.ApiRest_Tarea.util;

import com.example.ApiRest_Tarea.app.IndexController;
import com.example.ApiRest_Tarea.model.entity.CountryInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class util {

    private static final Logger logger = LoggerFactory.getLogger(util.class);
    public static final String API_URL = "https://restcountries.com/v3.1/name/";

    public static CountryInfo getJson(String jsonResponse) {

        CountryInfo countryInfo = new CountryInfo();
        try {
            JSONArray jsonarray = new JSONArray(jsonResponse);
            JSONObject jsonobject = jsonarray.getJSONObject(0);
            JSONObject objectName = jsonobject.getJSONObject("name");
            JSONObject objectlanguages = jsonobject.getJSONObject("languages");
            JSONObject objectCurrencies = jsonobject.getJSONObject("currencies");
            JSONObject objectCoatOfArms = jsonobject.getJSONObject("coatOfArms");

            String region = jsonobject.getString("region");
            String subregion = jsonobject.getString("subregion");
            String name_official = objectName.getString("official");
            String lenguajes = obtenerLenguajePais(objectlanguages.toString());
            String currencies = obtenerCurrenciesPais(objectCurrencies.toString());
            JSONObject objectId = objectCurrencies.getJSONObject(currencies);
            String nameMonedaP = objectId.getString("name");
            String symbolMonedaP = objectId.getString("symbol");
            String svg_coatOfArm = objectCoatOfArms.getString("svg");

            countryInfo.setRegion(region);
            countryInfo.setSubregion(subregion);
            countryInfo.setName(name_official);
            countryInfo.setLanguages(lenguajes);
            countryInfo.setCurrencies("Nombre Moneda: " + nameMonedaP + ", Symbolo: " + symbolMonedaP);
            countryInfo.setCoatOfArms(svg_coatOfArm);

        } catch (JSONException je) {
            logger.error("FECHA Y HORA ACTUAL [" + obtenerFechaActual() + "]  " + "RESPUESTA ERROR: [" + je.getStackTrace() + "]");
        }
        return countryInfo;
    }

    public static String obtenerCurrenciesPais(String currencies) {
        String resp = replaceCadena(currencies, "currencies");
        return resp.substring(0, 3);
    }

    public static String obtenerLenguajePais(String lenguajes) {

        String resp = replaceCadena(lenguajes, "languages");
        String[] valores = resp.split(":");
        StringJoiner respuesta = new StringJoiner(",");
        for (String vlor : valores) {
            if (vlor.length() > 3) {
                respuesta.add(vlor);
            }
        }
        return respuesta.toString();
    }

    public static String replaceCadena(String cadena, String value) {
        if (value.equals("languages") || value.equals("currencies")) {
            return cadena.replaceAll("[\"{}]", "").replaceAll(",", ":");
        }
        return cadena;
    }

    public static String obtenerFechaActual(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
    };

}
