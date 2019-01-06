/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje;

import java.util.ArrayList;
import java.util.Arrays;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz2.d_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Spremnik
{   

    public int dajNosivost()
    {
        return nosivost;
    }

    public float dajKolicinuOtpada() 
    {
        return kolicinaOtpada;
    }

    public int dajVrstuSpremnika() 
    {
        return vrstaSpremnika;
    }

    public int dajVrstuOtpada() 
    {
        return vrstaOtpada;
    }


    public int dajId() 
    {
        return id;
    }

    public int[] dajNaBroj() 
    {
        return naBroj;
    }
    public ArrayList<Korisnik> DajKorisnike()
    {
        return korisnici;
    }
    public Spremnik(int vrstaOtpada, String[] shema, String[] podaciZapis)
    {
        ispis = Ispisivanje.getInstance();
        naBroj = new int[3];
        id=0;
        try
        {
            this.vrstaOtpada = vrstaOtpada;
            int nazivIndeks = Arrays.asList(shema).indexOf("naziv"); 
            if(nazivIndeks==-1) //zbog moguce korupcije prvog karaktera
                nazivIndeks = Arrays.asList(shema).indexOf("aziv");
            int vrstaIndeks = Arrays.asList(shema).indexOf("vrsta");
            vrstaSpremnika = Integer.parseInt(podaciZapis[vrstaIndeks].replaceAll("\\p{Z}",""));
            for(int i=0; i<3; i++)
                naBroj[i] = Integer.parseInt(podaciZapis[i+vrstaIndeks+1].replaceAll("\\p{Z}",""));
            nosivost = Integer.parseInt(podaciZapis[vrstaIndeks+4].replaceAll("\\p{Z}",""));
            kolicinaOtpada=(float) 0.0;
            korisnici = new ArrayList<>();
        }
        catch(Exception e)
        {
            ispis.Ispisi("Greška prilikom parsiranja podatka o spremniku za "+vrstaOtpada+".");
        }
    }

    //kopiranje
    public Spremnik(Spremnik spremnik)
    {
        this.vrstaOtpada = spremnik.vrstaOtpada;
        this.vrstaSpremnika = spremnik.vrstaSpremnika;
        id = IdGenerator.getInstance().dajId();
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
    private Ispisivanje ispis;
    private int id;
    private String naziv;
    private ArrayList<Korisnik> korisnici;
    private int[] naBroj;
    private int vrstaOtpada;
    private int vrstaSpremnika;
    private float kolicinaOtpada=(float) 0.0;
    private int nosivost;
    
}
