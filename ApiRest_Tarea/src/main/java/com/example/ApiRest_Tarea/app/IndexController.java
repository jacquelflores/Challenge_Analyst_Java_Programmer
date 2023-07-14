package com.example.ApiRest_Tarea.app;

import com.example.ApiRest_Tarea.model.entity.CountryInfo;
import com.example.ApiRest_Tarea.model.entity.Logs;
import com.example.ApiRest_Tarea.repository.RepositoryInfoCountry;
import com.example.ApiRest_Tarea.repository.RepositoryLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.example.ApiRest_Tarea.util.util.*;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private RepositoryLogs repositoryLogs;
    @Autowired
    private RepositoryInfoCountry repositoryInfoCountry;

    @GetMapping("/")
    public String verIndex() {
        return "index";
    }

    @RequestMapping("/pais")
    public String consultaPaisxNombre(@RequestParam String nombre, Model model) {
        model.addAttribute("titulo", "Information Country");

        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL + nombre, HttpMethod.GET, null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {

                String jsonResponse = response.getBody();
                logger.info("FECHA Y HORA ACTUAL [" + obtenerFechaActual() + "]  " + "VALOR INGRESADO: [" + nombre + "] " + "BODY RESPUESTA: APIREST: [" + jsonResponse + "]");
                conectMongoDBTraza("INFO", obtenerFechaActual(), nombre, jsonResponse);
                CountryInfo country = getJson(jsonResponse);

                model.addAttribute("nombreOficial", country.getName())
                        .addAttribute("region", country.getRegion())
                        .addAttribute("subregion", country.getSubregion())
                        .addAttribute("languages", country.getLanguages())
                        .addAttribute("currencies", country.getCurrencies())
                        .addAttribute("coatOfArms", country.getCoatOfArms());
                conectMongoDBInfoCountry(country);

            }
            return "country";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("estadoError", true);
            if (ex.getRawStatusCode() == 404) {
                logger.error("FECHA Y HORA ACTUAL [" + obtenerFechaActual() + "]  " + "VALOR INGRESADO: [" + nombre + "] " + "BODY RESPUESTA ERROR: [" + ex.getRawStatusCode() + "]");
                conectMongoDBTraza("ERROR", obtenerFechaActual(), nombre, ex.getRawStatusCode() + "");
            }
            return "index";
        }
    }

    private void conectMongoDBInfoCountry(CountryInfo country) {
        try {
            repositoryInfoCountry.save(country);
        } catch (Exception e) {
            logger.error("FECHA Y HORA ACTUAL [" + obtenerFechaActual() + "]  ERROR :: " + e.getMessage());
        }
    }

    private void conectMongoDBTraza(String tipo, String fecha, String entrada, String body) {
        Logs logsTraza = new Logs(tipo, fecha, entrada, body);

        try {
            repositoryLogs.save(logsTraza);
        } catch (Exception e) {
            logger.error("FECHA Y HORA ACTUAL [" + obtenerFechaActual() + "]  ERROR :: " + e.getMessage());
        }
    }

}

