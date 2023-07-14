package com.example.ApiRest_Tarea.model.entity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
public class Logs {

    private String tipoerror;
    private String fecha;
    private String valorIngresado;
    private String boddy;

    public Logs(String tipoerror, String fecha, String valorIngresado, String boddy) {
        this.tipoerror = tipoerror;
        this.fecha = fecha;
        this.valorIngresado = valorIngresado;
        this.boddy = boddy;
    }

    public String getTipoerror() {
        return tipoerror;
    }

    public void setTipoerror(String tipoerror) {
        this.tipoerror = tipoerror;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValorIngresado() {
        return valorIngresado;
    }

    public void setValorIngresado(String valorIngresado) {
        this.valorIngresado = valorIngresado;
    }

    public String getBoddy() {
        return boddy;
    }

    public void setBoddy(String boddy) {
        this.boddy = boddy;
    }
}
