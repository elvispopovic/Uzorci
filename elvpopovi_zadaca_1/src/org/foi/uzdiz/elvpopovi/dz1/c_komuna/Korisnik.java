/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;


import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Spremnik;


/**
 *
 * @author elvis
 */
public class Korisnik
{

    public Ulica getUlica() 
    {
        return ulica;
    }
    public Korisnik(Ulica ulica, int kategorija, int brojSpremnika) 
    {
        this.ulica = ulica;
        this.kategorija = kategorija;
        kolicineOtpada = new float[5];
        spremnici = new ArrayList<>();
        id=0;
    }
    //kopirajuci konstruktor
    public Korisnik(Korisnik original, float[] kolicine)
    {
        this.ulica=original.ulica;
        this.kategorija = original.kategorija;
        kolicineOtpada = new float[5];
        spremnici = new ArrayList<>();
        id = IdGenerator.getInstance().getId();
        System.arraycopy(kolicine, 0, kolicineOtpada, 0, kolicine.length);
        //System.out.println("Korisniku u "+ulica.Naziv()+" kategorije "+kategorija+" pridruzen je id "+id);
    }
    public void DodajSpremnik(Spremnik spremnik)
    {
        spremnici.add(spremnik);
    }
    public float[] dajKolicineOtpada()
    {
        return kolicineOtpada;
    }
    public int dajId()
    {
        return id;
    }
    public int dajKategorija()
    {
        return kategorija;
    }
    public ArrayList<Spremnik> DajSpremnike()
    {
        return spremnici;
    }
    
    private int id;
    private Ulica ulica;
    private int kategorija;
    private float kolicineOtpada[];
    private ArrayList<Spremnik> spremnici;
}
