/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.d_komuna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Podrucje implements PodrucjeSucelje
{   
    private final ArrayList<PodrucjeSucelje> podPodrucja;
    private final ArrayList<Ulica> ulice;
    private final String id;
    private String naziv;
    private String[] dijelovi;
    @Override
    public String dajId()
    {
        return id;
    }
    @Override
    public String dajNaziv()
    {
        return naziv;
    }
    @Override
    public String[] dajDijelove()
    {
        return dijelovi;
    }
    public Podrucje(String id, String naziv, String[] dijelovi)
    {
        this.id = id;
        this.naziv = naziv;
        this.dijelovi = dijelovi;
        podPodrucja = new ArrayList<>();
        ulice = new ArrayList<>();
    }
    @Override
    public void DodajPodrucje(PodrucjeSucelje p)
    {
        podPodrucja.add(p);
    }

    @Override
    public void DodajUlicu(Ulica u)
    {
        ulice.add(u);
    }
    @Override
    public PodrucjeSucelje PronadjiPodrucje(String id, boolean rekurzivno)
    {
        PodrucjeSucelje pTrenutno;
        for(PodrucjeSucelje p : podPodrucja) //BFS
            if(p.dajId().equals(id))
                return p;
        if(rekurzivno == false)
            return null;
        for(PodrucjeSucelje p : podPodrucja)
            if((pTrenutno = p.PronadjiPodrucje(id, rekurzivno))!=null)
                return pTrenutno;
        return null;
    }
    
    @Override
    public void NapuniListuPodrucja(ArrayList<PodrucjeSucelje> lista, boolean rekurzivno)
    {
        for(PodrucjeSucelje p : podPodrucja)
            lista.add(p);
        if(rekurzivno == false)
            return;
        for(PodrucjeSucelje p : podPodrucja)
            p.NapuniListuPodrucja(lista, rekurzivno);
    }
    
    @Override
    public void NapuniListuPodrucja(HashMap<String,PodrucjeSucelje> lista, boolean rekurzivno)
    {
        for(PodrucjeSucelje p : podPodrucja)
            lista.put(p.dajId(),p);
        if(rekurzivno == false)
            return;
        for(PodrucjeSucelje p : podPodrucja)
            p.NapuniListuPodrucja(lista, rekurzivno);
    }
    
    @Override
    public Ulica PronadjiUlicu(String id, boolean rekurzivno)
    {
        Ulica uTrenutna;
        for(Ulica u : ulice) //BFS
            if(u.Id().equals(id))
                return u;
        if(rekurzivno == false)
            return null;
        for(PodrucjeSucelje p : podPodrucja)
            if((uTrenutna = p.PronadjiUlicu(id, rekurzivno))!=null)
                return uTrenutna;
        return null;
    }
    @Override
    public void NapuniListuUlica(ArrayList<Ulica> lista, boolean rekurzivno)
    {
        for(Ulica u : ulice)
            lista.add(u);
        if(rekurzivno == false)
            return;
        for(PodrucjeSucelje p : podPodrucja)
            p.NapuniListuUlica(lista, rekurzivno);
    }
    @Override
    public float[] DajKolicineOtpada(float[] ulazno)
    {
        float[] rezultat = new float[6];
        for(int i=0; i<6; i++)
            rezultat[i]=ulazno[i];
        for(Ulica u : ulice)
        {
            for(ArrayList<Korisnik>al : u.dajKorisnike())
                for(Korisnik k : al)
                {
                    float[] kolicine = k.dajKolicineOtpada();
                    for(int i=0; i<5; i++)
                    {
                        rezultat[i]+=kolicine[i];
                        rezultat[5]+=kolicine[i]; //ukupno
                    }
                }
        }
        for(PodrucjeSucelje p : podPodrucja)
            rezultat = p.DajKolicineOtpada(rezultat);
        return rezultat;
    }
    @Override
    public void IspisiKolicineOtpada()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        float[] ulazno = new float[6];
        float[] kolicineOtpada = DajKolicineOtpada(ulazno);
        ispisiPodrucja(ispis,sb,form,ulazno, kolicineOtpada);
        
        if(ulice.size()>0)
        {
            ispis.Ispisi("  "+String.join("", Collections.nCopies(124, "-")));
            ispisiUlice(ispis,sb,form,ulazno);
        }
        ispis.Ispisi(" "+String.join("", Collections.nCopies(125, "=")));
        
        for(PodrucjeSucelje p : podPodrucja)
            p.IspisiKolicineOtpada();
    }
    private void ispisiPodrucja(Ispisivanje ispis, StringBuilder sb, Formatter form, float[] ulazno, float[] kolicineOtpada)
    {
        resetirajRekurzivneBrojaceOtpada(ulazno);
        form.format("%13s |%30s |%12.2f |%11.2f |%11.2f |%10.2f |%13.2f |%12.2f","područje",
        naziv,kolicineOtpada[0],kolicineOtpada[1],kolicineOtpada[2],kolicineOtpada[3],
                kolicineOtpada[4], kolicineOtpada[5]);
        ispis.Ispisi(sb.toString());
        if(podPodrucja.size()>0)
            ispis.Ispisi("  "+String.join("", Collections.nCopies(124, "-")));
        boolean prvi = true;
        for(PodrucjeSucelje p : podPodrucja)
        {
            sb.setLength(0);
            resetirajRekurzivneBrojaceOtpada(ulazno);
            kolicineOtpada = p.DajKolicineOtpada(ulazno);
            if(podPodrucja.size()>1)
                ispisiRed(p.dajNaziv(),"potpodručja",ispis,sb,form,prvi,kolicineOtpada);
            else
                ispisiRed(p.dajNaziv(),"potpodručje",ispis,sb,form,prvi,kolicineOtpada);
            prvi=false;
        }
    }
    private void ispisiUlice(Ispisivanje ispis, StringBuilder sb, Formatter form, float[] ulazno)
    {
        boolean prvi = true;
        float[] kolicine = new float[6];
        for(Ulica u : ulice)
        {
            resetirajRekurzivneBrojaceOtpada(kolicine);
            for(ArrayList<Korisnik>al : u.dajKorisnike())
                for(Korisnik k : al)
                {
                    float[] kol = k.dajKolicineOtpada();
                    for(int i=0; i<5; i++)
                    {
                        kolicine[i]+=kol[i];
                        kolicine[5]+=kol[i];
                    }
                }
            if(ulice.size()>1)
                ispisiRed(u.Naziv(),"ulice",ispis,sb,form,prvi,kolicine);
            else
                ispisiRed(u.Naziv(),"ulica",ispis,sb,form,prvi,kolicine);
            prvi=false;
        }
    }
    
    private void ispisiRed(String naziv, String uloga, Ispisivanje ispis, 
    StringBuilder sb, Formatter form,boolean prvi, float[] kolicine)
    {
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        sb.setLength(0);
        if(prvi==true)
            form.format("%13s |%30s |%12."+brojDecimala+"f |%11."+brojDecimala+"f |%11."+brojDecimala+"f |%10."+brojDecimala+"f |%13."+brojDecimala+"f |%12."+brojDecimala+"f",
            uloga,naziv,kolicine[0],kolicine[1],kolicine[2],kolicine[3],
            kolicine[4], kolicine[5]);
        else
            form.format("%13s |%30s |%12."+brojDecimala+"f |%11."+brojDecimala+"f |%11."+brojDecimala+"f |%10."+brojDecimala+"f |%13."+brojDecimala+"f |%12."+brojDecimala+"f","",
            naziv,kolicine[0],kolicine[1],kolicine[2],kolicine[3],
            kolicine[4], kolicine[5]);
        ispis.Ispisi(sb.toString());
    }
    
    private float[] resetirajRekurzivneBrojaceOtpada(float[] brojac)
    {
        for(int i=0; i<brojac.length; i++)
            brojac[i]=(float)0.0;
        return brojac;
    }
}
