package com.example.convertisseurdemonnaie;

public class Monnaie {

    private int id;
    private String devise;
    private double devise_calcul;

    public Monnaie() {
    }

    public Monnaie(String devise, double devise_calcul) {
        this.devise = devise;
        this.devise_calcul = devise_calcul;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String isbn) {
        this.devise = isbn;
    }

    public double getDevise_calcul() {
        return devise_calcul;
    }

    public void setDevise_calcul(double devise_calcul) {
        this.devise_calcul = devise_calcul;
    }

}
