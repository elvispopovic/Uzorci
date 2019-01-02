/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.d_komuna;


import java.util.ArrayList;
import java.util.Arrays;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;


/**
 *
 * @author elvis
 */
public class Ulica
{
    /**
     * Getter sifre (identifikatora) ulice
     * @return vraća identifikator
     */
    public String Id()
    {
        return id;
    }
    /**
     * Getter naziva ulice
     * @return vraća naziv ulice
     */
    public String Naziv() 
    {
        return naziv;
    }
    /**
     * vraća broj malih korisnika
     * @return broj malih korisnika
     */
    public int Mali()
    {
        if(mjesta!=null)
            return mjesta[0];
        else return 0;
    }
    /**
     * vraća broj srednjih korisnika
     * @return broj srednjih korisnika
     */
    public int Srednji()
    {
        if(mjesta!=null)
            return mjesta[1];
        else return 0;
    }
    /**
     * vraća broj velikih korisnika
     * @return broj velikih korisnika
     */
    public int Veliki()
    {
        if(mjesta!=null)
            return mjesta[2];
        else return 0;
    }
    /**
     * vraća broj mjesta u ulici
     * @return broj mjesta
     */
    public int[] dajMjesta()
    {
        return mjesta;
    }
    /**
     * vraća shemu zapisa ulice
     * @param shema 
     */
    public Ulica(String[] shema) 
    {
        ispis = Ispisivanje.getInstance();
        this.shema = shema;
        rnd = RandomGenerator.getInstance();
        id = new String("");
        mjesta = new int[3];
        korisnici = new ArrayList<>();
        for(int i=0; i<3; i++)
            korisnici.add(new ArrayList<>());
    }
    /**
     * Na osnovu podataka pročitanih iz datoteka inicijaliziraju se podaci u objektu ulice
     * @param podaci redak zapisa u datoteci ulica
     * @return ispravnost podataka
     */
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
    /**
     * Dodavanje korisnika
     * @param korisnik Korisnik koji se dodaje
     */
    public void dodajKorisnika(Korisnik korisnik)
    {
        int kategorija = korisnik.dajKategorija();
        if(kategorija>=0&&kategorija<3)
        {
            ArrayList<Korisnik> lista = korisnici.get(kategorija);
            lista.add(korisnik);
        }
    }
    /**
     * Vraća listu korisnika
     * @return lista korisnika
     */
    public ArrayList<ArrayList<Korisnik>> dajKorisnike()
    {
        return korisnici;
    }
    /**
     * Kreiranje mjesta u ulici
     * @param podaci redak zapisa
     * @param numerickiIndeks mjesto gdje se u retku zapisa nalati podatak
     * @throws NumberFormatException 
     */
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
