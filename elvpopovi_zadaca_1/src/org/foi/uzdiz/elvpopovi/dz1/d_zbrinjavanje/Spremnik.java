/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje;

import java.util.ArrayList;
import java.util.HashMap;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.Korisnik;

/**
 *
 * @author elvis
 */
public class Spremnik
{   

    public float getKolicinaOtpada() 
    {
        return kolicinaOtpada;
    }

    public int getVrstaSpremnika() 
    {
        return vrstaSpremnika;
    }

    public int getVrstaOtpada() 
    {
        return vrstaOtpada;
    }

    public HashMap<Integer, String> getImenovanjeVrste() 
    {
        return imenovanjeVrsteOtpada;
    }

    public int getId() 
    {
        return id;
    }

    public int[] getNaBroj() 
    {
        return naBroj;
    }
    public ArrayList<Korisnik> DajKorisnike()
    {
        return korisnici;
    }
    public Spremnik(int vrstaOtpada, int tip, int[] naBroj, int nosivost)
    {
        imenovanjeVrsteOtpada = new HashMap<>();
        imenovanjeVrsteOtpada.put(0, "staklo");
        imenovanjeVrsteOtpada.put(1, "papir");
        imenovanjeVrsteOtpada.put(2, "metal");
        imenovanjeVrsteOtpada.put(3, "bio");
        imenovanjeVrsteOtpada.put(4, "mješano");
        this.vrstaOtpada = vrstaOtpada; //papir, metal itd.
        id=0;
        this.naBroj = new int[3];
        this.nosivost = nosivost;
        kolicinaOtpada=(float) 0.0;
        System.arraycopy(naBroj, 0, this.naBroj,0,naBroj.length);
        korisnici = new ArrayList<>();
        //System.out.println("Kreiran je spremnik tipa "+tip+", i vrste "+imenovanjeVrste.get(vrsta)+", nosivosti "+nosivost);
    }
    public Spremnik(Spremnik spremnik)
    {
        this.imenovanjeVrsteOtpada = spremnik.imenovanjeVrsteOtpada;
        this.vrstaOtpada = spremnik.vrstaOtpada;
        this.vrstaSpremnika = spremnik.vrstaSpremnika;
        id = IdGenerator.getInstance().getId();
        naBroj = new int[3];
        System.arraycopy(spremnik.naBroj, 0, naBroj,0,spremnik.naBroj.length);
        korisnici = new ArrayList<>();
        this.nosivost = spremnik.nosivost;
        kolicinaOtpada=(float) 0.0;
    }
    public void DodajKorisnika(Korisnik k)
    {
        korisnici.add(k);
        if(k!=null)
        {
            float[] kolicineOtpadaKorisnika = k.dajKolicineOtpada();
            if(vrstaOtpada<kolicineOtpadaKorisnika.length)
            {
                if(kolicinaOtpada+kolicineOtpadaKorisnika[vrstaOtpada]>(float)nosivost)
                    kolicinaOtpada=(float)nosivost;
                else
                    kolicinaOtpada=kolicinaOtpada+kolicineOtpadaKorisnika[vrstaOtpada];
            }
            
        }
        
    }
    public float IsprazniSpremnik()
    {
        float kolicina = kolicinaOtpada;
        kolicinaOtpada=(float) 0.0;
        return kolicina;
    }
    private int id;
    private HashMap<Integer,String> imenovanjeVrsteOtpada;
    private ArrayList<Korisnik> korisnici;
    private int[] naBroj;
    private int vrstaOtpada;
    private int vrstaSpremnika;
    private float kolicinaOtpada=(float) 0.0;
    private int nosivost;
    
}
