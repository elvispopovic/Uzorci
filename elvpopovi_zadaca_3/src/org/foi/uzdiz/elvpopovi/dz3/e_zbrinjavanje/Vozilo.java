/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Vozilo implements VoziloSucelje
{
    Parametri parametri;
    RandomGenerator rnd;
    Ispisivanje ispisivanje;
    private String id;
    String naziv;
    int vrsta, tip, ciklusaOdvoz, kapacitetPogona, punjenjePogona;
    private ArrayList<String> vozaci;
    VoziloKontekstSucelje kontekst;
    VoziloStatistika statistikaVozila;
    private int nosivost;
    private int brojSpremnika;
    private ArrayList<Ulica> dodijeljeneUlice;
    private ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici;
    private Ispisivanje ispis;
    
    @Override
    public String dajId()
    {
        return id;
    }
    
    @Override
    public ArrayList<Ulica> dajDodijeljeneUlice() 
    {
        return dodijeljeneUlice;
    }

    public int dajBrojSpremnika()
    {
        return brojSpremnika;
    }
    @Override
    public ArrayList<ArrayList<Spremnik>> dajDodijeljeneSpremnike() 
    {
        return dodijeljeniSpremnici;
    }

    @Override
    public void postaviDodijeljeneUlice(ArrayList<Ulica> ulice) 
    {
        dodijeljeneUlice = ulice;
        //ispisiUlice();
    }
    @Override
    public void postaviDodijeljeneSpremnike(ArrayList<ArrayList<Spremnik>> spremnici)
    {
        dodijeljeniSpremnici = spremnici;
        if(spremnici == null)
            return;
        brojSpremnika = 0;
        for(int j=0; j<dodijeljeniSpremnici.size(); j++)
            for(int i=0; i<dodijeljeniSpremnici.get(j).size(); i++)
                brojSpremnika++;
        //ispisiSpremnike();
    }
    
    @Override
    public String dajNaziv() 
    {
        return naziv;
    }

    @Override
    public ArrayList<String> dajVozace() 
    {
        return vozaci;
    }

    @Override
    public int dajNosivost() 
    {
        return nosivost;
    }
    @Override
    public VoziloKontekstSucelje dajKontekst()
    {
        return kontekst;
    }

    @Override
    public VoziloStatistika dajStatistikuVozila()
    {
        return statistikaVozila;
    }
    
    @Override
    public int dajVrstu() 
    {
        return vrsta;
    }
    @Override
    public int dajTip() 
    {
        return tip;
    }
    @Override
    public int dajKapacitetPogona()
    {
        return kapacitetPogona;
    }
    @Override
    public int dajPunjenjePogona()
    {
        return punjenjePogona;
    }
    
    public Vozilo(int vrsta)
    {
        this.vrsta = vrsta;
        parametri = Parametri.getInstance();
        ispis = Ispisivanje.getInstance();
        rnd = RandomGenerator.getInstance();
        vozaci = new ArrayList<>();
    }
    
    public Vozilo(ArrayList<VoziloSucelje> protoVozila, String[] shema, String[] podaciZapis)
    {
        int idIndeks = Arrays.asList(shema).indexOf("id");
        if(idIndeks==-1)
            idIndeks = Arrays.asList(shema).indexOf("d");
        if(idIndeks!=-1)
            id = podaciZapis[idIndeks];
        else
            id = String.valueOf(IdGenerator.getInstance().dajId());
        naziv = podaciZapis[Arrays.asList(shema).indexOf("naziv")];
        try
        {
            tip = Integer.parseInt(podaciZapis[Arrays.asList(shema).indexOf("tip")].replaceAll("\\p{Z}",""));
            vrsta = Integer.parseInt(podaciZapis[Arrays.asList(shema).indexOf("vrsta")].replaceAll("\\p{Z}",""));
            nosivost = Integer.parseInt(podaciZapis[Arrays.asList(shema).indexOf("vrsta")+1].replaceAll("\\p{Z}",""));
        }
        catch(NumberFormatException e)
        {
            ispis.Ispisi("Greška prilikom parsiranja podatka o vozilu za "+naziv+".");
        }
        Kopiraj(protoVozila.get(vrsta), shema, podaciZapis);
    }
 
 
    private void Kopiraj(VoziloSucelje original, String[] shema, String[] podaciZapis)
    {
        this.vrsta = original.dajVrstu(); 
        parametri = Parametri.getInstance();
        ispis = Ispisivanje.getInstance();
        rnd = RandomGenerator.getInstance();
        statistikaVozila = new VoziloStatistika();
        if(tip==0) //diesel
        {
            kapacitetPogona = parametri.DajVrijednost("kapacitetDizelVozila");
            punjenjePogona  = parametri.DajVrijednost("punjenjeDizelVozila");
        }
        else //elektricni
        {
            kapacitetPogona = parametri.DajVrijednost("kapacitetElektroVozila");
            punjenjePogona  = parametri.DajVrijednost("punjenjeElektroVozila");
        }
        this.vozaci = new ArrayList<>(); //lista se ne smije kopirati, mora se napraviti nova
        String[] vozaciPopis = podaciZapis[Arrays.asList(shema).indexOf("vozači")].split(Pattern.quote(",").replaceAll("\\p{Z}","")); 
            for(String v:vozaciPopis)
                this.vozaci.add(v);
        kontekst = new VoziloKontekst(this);
    }
    
    //testiranje
    private void ispisiUlice()
    {
        System.out.println("Vozilo "+naziv+":");
        for(Ulica i:dodijeljeneUlice)
            System.out.print(" "+i.Naziv()+";");
        System.out.println("");
    }
    private void ispisiSpremnike()
    {
        System.out.println("Vozilo "+naziv+", vrsta: "+vrsta);
        for(int j=0; j<dodijeljeneUlice.size(); j++)
        {
            System.out.println("   Ulica: "+dodijeljeneUlice.get(j).Naziv());
            for(Spremnik i:dodijeljeniSpremnici.get(j))
                System.out.println("      Spremnik: "+i.dajId()+", vrsta: "+i.dajVrstuOtpada()+", tip: "+i.dajVrstuSpremnika()+", kolicina otpada: "+i.dajKolicinuOtpada());
        }     
    }

}
