package com.example.fitapp;

public class Ejercicio {

    private String nombre;
    private int repeticiones;
    private int series;
    private String video;

    public Ejercicio(String nombre, int repeticiones, int series, String video) {
        this.nombre = nombre;
        this.repeticiones = repeticiones;
        this.series = series;
        this.video = video;
    }
    public Ejercicio() {

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }


}
