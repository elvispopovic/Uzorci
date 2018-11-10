/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;


import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.RandomGenerator;


/**
 *
 * @author elvis
 */
public class Ulica
{

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
    public Ulica() 
    {
        rnd = RandomGenerator.getInstance();
        mjesta = new int[3];
        korisnici = new ArrayList<ArrayList<Korisnik>>();
        for(int i=0; i<3; i++)
            korisnici.add(new ArrayList<>());
    }
    public boolean Init(String[] podaci)
    { 
        if(podaci==null||podaci.length<5)
            return false;
        naziv = podaci[0];
        kreirajMjesta(podaci);       
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
    private void kreirajMjesta(String[] podaci)
    {
        int brojMjesta, i, maxIndex, zbroj, razlika, max;
        brojMjesta = Integer.parseInt(podaci[1]);
        max=zbroj=0;
        maxIndex=0;
        for(i=0; i<3; i++)
        {
            float postotak = Float.parseFloat(podaci[i+2]);
            mjesta[i]=(int)(brojMjesta*postotak/100.0f+0.5f);
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
    
    
    private RandomGenerator rnd;
    private int[] mjesta;
    private ArrayList<ArrayList<Korisnik>> korisnici;
    private String naziv;
}
