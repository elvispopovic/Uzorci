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
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Podrucje implements PodrucjeSucelje
{   
    private final HashMap<String,PodrucjeSucelje> podPodrucja;
    private final ArrayList<Ulica> ulice;
    private final String id;
    private String naziv;
    private String[] dijelovi;
    /**
     * Getter za Šifru područja
     * @return vraća šifru područja kao string
     */
    @Override
    public String dajId()
    {
        return id;
    }
    /**
     * Getter za naziv područja
     * @return vraća naziv područja
     */
    @Override
    public String dajNaziv()
    {
        return naziv;
    }
    /**
     * Getter za dijelove: šifre potpodručja i ulica kako je upisano u datoteci
     * @return Šifre potpodručja i ulica
     */
    @Override
    public String[] dajDijelove()
    {
        return dijelovi;
    }
    /**
     * 
     * Konstruktor
     * @param id Šifra područja
     * @param naziv Naziv područja
     * @param dijelovi Šifre potpodručja i ulica
     */
    public Podrucje(String id, String naziv, String[] dijelovi)
    {
        this.id = id;
        this.naziv = naziv;
        this.dijelovi = dijelovi;
        //podPodrucja = new ArrayList<>();
        podPodrucja = new HashMap<>();
        ulice = new ArrayList<>();
    }
    /**
     * Području se dodaje potpodručje
     * @param p Referenca na objekt područja koje se dodaje kao potpodručje
     */
    @Override
    public void DodajPodrucje(PodrucjeSucelje p)
    {
        //podPodrucja.add(p);
        podPodrucja.put(p.dajId(),p);
    }
    /**
     * Području se dodaje ulica
     * @param u Referenca na objekt ulice koja se dodaje području
     */
    @Override
    public void DodajUlicu(Ulica u)
    {
        ulice.add(u);
    }
    /**
     * Pretražuju se potpodručja i ako se pronađe određeno područje vraća se kao referenca
     * @param id Šifra traženog potpodručja
     * @param rekurzivno Preklopnik rekurzivnog pretraživanja
     * @return Referenca objekta područja koje je pronađeno
     */
    @Override
    public PodrucjeSucelje PronadjiPodrucje(String id, boolean rekurzivno)
    {
        PodrucjeSucelje pTrenutno = null;
        /*
        for(PodrucjeSucelje p : podPodrucja) //BFS
            if(p.dajId().equals(id))
                return p;
        */
        pTrenutno = podPodrucja.get(id);
        if(pTrenutno != null)
            return pTrenutno;
        if(rekurzivno == false)
            return null;
        //for(PodrucjeSucelje p : podPodrucja)
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            if((pTrenutno = p.PronadjiPodrucje(id, rekurzivno))!=null)
                return pTrenutno;
        }
        return null;
    }
    /**
     * Popunjava se hash mapa svim potpodručjima
     * @param lista
     * @param rekurzivno 
     */
    @Override
    public void NapuniMapuPodrucja(HashMap<String,PodrucjeSucelje> lista, boolean rekurzivno)
    {
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            lista.put(p.dajId(),p);
        }
        if(rekurzivno == false)
            return;
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            p.NapuniMapuPodrucja(lista, rekurzivno);
        }
    }
    /**
     * Pretraživanje ulice u područjima i potpodručjima prema njezinoj šifri
     * @param id Šifra (identifikator) ulice
     * @param rekurzivno Preklopnik za rekurzivno pretraživanje
     * @return Objekt pronađene ulice. Null ako nije pronađena.
     */
    @Override
    public Ulica PronadjiUlicu(String id, boolean rekurzivno)
    {
        Ulica uTrenutna;
        for(Ulica u : ulice) //BFS
            if(u.Id().equals(id))
                return u;
        if(rekurzivno == false)
            return null;
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            if((uTrenutna = p.PronadjiUlicu(id, rekurzivno))!=null)
                return uTrenutna;
        }
        return null;
    }
    /**
     * U DZ1 ulice nemaju id, pa se koristi obična lista
     * @param lista Lista ulica koju treba popuniti ulicama
     * @param rekurzivno Preklopnik za rekurzivno pretraživanje
     */
    @Override
    public void NapuniListuUlica(ArrayList<Ulica> lista, boolean rekurzivno)
    {
        for(Ulica u : ulice)
            lista.add(u);
        if(rekurzivno == false)
            return;
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            p.NapuniListuUlica(lista, rekurzivno);
        }
    }
    /**
     * U DZ2 i DZ3 ulice imaju svoje identifikatore (šifre). Zato se koristi mapa 
     * gdje su ključevi upravo ti identifikatori
     * @param lista
     * @param rekurzivno 
     */
    public void NapuniMapuUlica(HashMap<String,Ulica> lista, boolean rekurzivno)
    {
        for(Ulica u : ulice)
            lista.put(u.Id(), u);
        if(rekurzivno == false)
            return;
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            p.NapuniMapuUlica(lista, rekurzivno);
        }
    }
    /**
     * Dobavljanje količina otpada. Metoda je rekurzivna.
     * @param ulazno Trenutni zbroj otpada u nadpodručjima, prema vrsti otpada
     * @return vraća zbroj otpada u potpodručjima razvrstano na vrste otpada
     */
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
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            rezultat = p.DajKolicineOtpada(rezultat);
        }
        return rezultat;
    }
    /**
     * Ispisivanje količina otpada. Metoda koristi privatnu metodu ispisiPodručja
     * za ispis svakog retka tablice
     */
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
        
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
            p.IspisiKolicineOtpada();
        }
    }
    /**
     * Ispis potpodručja i otpada u području. Ovu metodu koristi javna metoda IspisiKolicine otpada.
     * @param ispis Referenca na ispisivač
     * @param sb String builder
     * @param form Form builder
     * @param ulazno Brojač
     * @param kolicineOtpada Količine otpada po vrsti
     */
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
        for(String k : podPodrucja.keySet())
        {
            PodrucjeSucelje p = podPodrucja.get(k);
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
    /**
     * Ispisivanje ulica
     * @param ispis Referenca ispisivača
     * @param sb String Builder
     * @param form Form builder
     * @param ulazno količine otpada po vrsti
     */
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
    /**
     * Ispisuje se pojedini redak tablice
     * @param naziv Naziv entiteta u tablici, ime ulice ili područja
     * @param uloga String koji pokazuje što je taj entitet: npr ulica
     * @param ispis Referenca ispisivača
     * @param sb String builder
     * @param form Form builder
     * @param prvi oznaka za prvi red
     * @param kolicine količine
     */
    private void ispisiRed(String naziv, String uloga, Ispisivanje ispis, 
    StringBuilder sb, Formatter form,boolean prvi, float[] kolicine)
    {
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        sb.setLength(0);
        if(prvi==true)
            form.format("%13s |%30s |%12."+brojDecimala+"f |%11."+brojDecimala+"f |%11."+
                    brojDecimala+"f |%10."+brojDecimala+"f |%13."+brojDecimala+"f |%12."+brojDecimala+"f",
            uloga,naziv,kolicine[0],kolicine[1],kolicine[2],kolicine[3],
            kolicine[4], kolicine[5]);
        else
            form.format("%13s |%30s |%12."+brojDecimala+"f |%11."+brojDecimala+"f |%11."+brojDecimala+
                    "f |%10."+brojDecimala+"f |%13."+brojDecimala+"f |%12."+brojDecimala+"f","",
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
