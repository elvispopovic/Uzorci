/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.d_komuna;


import java.util.ArrayList;
import java.util.Arrays;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.RandomGenerator;


/**
 *
 * @author elvis
 */
public class Ulica
{
    public String Id()
    {
        return id;
    }
    public String Naziv() 
    {
        return naziv;
    }
    public int Mali()
    {
        if(mjesta!=null)
            return mjesta[1];
        else return 0;
    }
    public int Srednji()
    {
        if(mjesta!=null)
            return mjesta[2];
        else return 0;
    }
    public int Veliki()
    {
        if(mjesta!=null)
            return mjesta[3];
        else return 0;
    }
    public int[] dajMjesta()
    {
        return mjesta;
    }
    public Ulica(String[] shema) 
    {
        ispis = Ispisivanje.getInstance();
        this.shema = shema;
        rnd = RandomGenerator.getInstance();
        mjesta = new int[3];
        korisnici = new ArrayList<>();
        for(int i=0; i<3; i++)
            korisnici.add(new ArrayList<>());
    }
    public boolean Inicijaliziraj(String[] podaci)
    { 
        if(podaci==null||podaci.length<5)
            return false;
        int idIndeks = Arrays.asList(shema).indexOf("id");
        if(idIndeks==-1) //zbog moguce korupcije prvog slova
            idIndeks = Arrays.asList(shema).indexOf("d");
        if(idIndeks!=-1)
            id = podaci[idIndeks]; 
        naziv = podaci[Arrays.asList(shema).indexOf("naziv")];
        int numerickiIndeks = Arrays.asList(shema).indexOf("broj mjesta");
        try
        {
            kreirajMjesta(podaci, numerickiIndeks); 
        }
        catch(NumberFormatException e)
        {
            ispis.Ispisi("Greška prilikom kreiranja mjesta u ulici ");
        }
        return true;
    }
    public void dodajKorisnika(Korisnik korisnik)
    {
        int kategorija = korisnik.dajKategorija();
        if(kategorija>=0&&kategorija<3)
        {
            ArrayList<Korisnik> lista = korisnici.get(kategorija);
            lista.add(korisnik);
        }
    }
    public ArrayList<ArrayList<Korisnik>> dajKorisnike()
    {
        return korisnici;
    }
    private void kreirajMjesta(String[] podaci, int numerickiIndeks) throws NumberFormatException
    {
        int i, maxIndex, zbroj, razlika, max;
        int brojMjesta = Integer.parseInt(podaci[numerickiIndeks].replaceAll("\\p{Z}",""));
        float[] udjeli = new float[4]; //zadnji je zbroj
        for(i=0, udjeli[3]=(float)0.0; i<3; i++)
        {
            udjeli[i] = Float.parseFloat(podaci[i+numerickiIndeks+1].replaceAll("\\p{Z}",""))/(float)100.0;
            udjeli[3]+=udjeli[i];
        }
        for(i=0, max=0, zbroj=0, maxIndex=0; i<3; i++)
        {
            udjeli[i]*=(float)1.0/udjeli[3]; //ne vjerujem ni upisanim udjelima
            mjesta[i]=(int)(brojMjesta*udjeli[i]+0.5f);
            zbroj+=mjesta[i];
            if(mjesta[i]>max)
            {
                maxIndex=i;
                max=mjesta[maxIndex];
            }
        }
        razlika=zbroj-brojMjesta;
        mjesta[maxIndex]-=razlika;
    }
    
    
    private String[] shema;
    private RandomGenerator rnd;
    private int[] mjesta;
    private ArrayList<ArrayList<Korisnik>> korisnici;
    private String id;
    private String naziv;
    private Ispisivanje ispis;
}
