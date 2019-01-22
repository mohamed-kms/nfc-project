package com.example.mohamed.tchololbane.Model;

/**
 * Created by mohamed-kms on 25/06/18
 **/
public class Categories {
    private String Nom;
    private String Image;

    public Categories() {
    }

    public Categories(String nom, String image) {
        Nom = nom;
        Image = image;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String name) {
        Nom = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
