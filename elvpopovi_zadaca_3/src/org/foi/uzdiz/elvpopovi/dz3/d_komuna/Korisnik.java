/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.d_komuna;


import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;


/**
 *
 * @author elvis
 */
public class Korisnik
{
    private final int id;
    private Ulica ulica;
    private int kategorija;
    private float kolicineOtpada[];
    private ArrayList<Spremnik> spremnici;
    
    public Ulica getUlica() 
    {
        return ulica;
    }
    /**
     * Konstruktor
     * @param ulica Ulica koja se pridružuje korisniku
     * @param kategorija kategorija korisnika (mali =0 , srednji = 1, veliki = 2)
     */
    public Korisnik(Ulica ulica, int kategorija) 
    {
        this.ulica = ulica;
        this.kategorija = kategorija;
        kolicineOtpada = new float[5];
        spremnici = new ArrayList<>();
        id=0;
    }
    /**
     * Kopirajući konstruktor
     * @param original originalni objekt
     * @param kolicine količine otpada
     */
    public Korisnik(Korisnik original, float[] kolicine)
    {
        this.ulica=original.ulica;
        this.kategorija = original.kategorija;
        kolicineOtpada = new float[5];
        spremnici = new ArrayList<>();
        id = IdGenerator.getInstance().dajId();
        System.arraycopy(kolicine, 0, kolicineOtpada, 0, kolicine.length);
    }
    /**
     * Korisniku se pridružuje spremnik
     * @param spremnik referenca na spremnik
     */
    public void DodajSpremnik(Spremnik spremnik)
    {
        spremnici.add(spremnik);
    }
    /**
     * Getter za količine otpada tog korisnika
     * @return količine otpada po vrsti otpada
     */
    public float[] dajKolicineOtpada()
    {
        return kolicineOtpada;
    }
    /**
     * Getter šifre spremnika
     * @return šifra (identifikator) spremnika 
     */
    public int dajId()
    {
        return id;
    }
    /**
     * Getter kategorije korisnika 
     * @return kategorija korisnika (mali=0, srednji=1, veliki=2)
     */
    public int dajKategorija()
    {
        return kategorija;
    }
    /**
     * Getter spremnika pridruženih tom korisniku
     * @return 
     */
    public ArrayList<Spremnik> DajSpremnike()
    {
        return spremnici;
    }
    
    
}
