/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
    
    private LinkedHashMap<Integer,Vozac> mapaVozaca;
    private Integer trenutniVozac;
    
    VoziloKontekstSucelje kontekst;
    VoziloStatistika statistikaVozila;
    private int nosivost;
    private int brojSpremnika;
    private String ishodisteSustava;
    private ArrayList<Ulica> dodijeljeneUlice;
    private ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici;
    private Ispisivanje ispis;
    
    @Override
    public String dajId()
    {
        return id;
    }
    @Override
    public void PostaviIshodisteSustava(String ishodiste)
    {
        this.ishodisteSustava = new String(ishodiste);
    }
    @Override
    public String DajIshodisteSustava()
    {
        return ishodisteSustava;
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
    public void DodajVozaca(Vozac vozac)
    {
        mapaVozaca.put(vozac.DajId(),vozac);
        vozac.PridruziVozilo(this);
        if(trenutniVozac == null)
            trenutniVozac = vozac.DajId();
    }
    
    @Override
    public void UkloniVozaca(Vozac vozac)
    {
        if(vozac != null)
        {
            //ako je aktivan 
            if(trenutniVozac != null)
            {
                if(trenutniVozac==vozac.DajId())
                    RotirajVozace(); //probamo rotirati
                if(trenutniVozac==vozac.DajId()) //ako se nije imalo što rotirati
                    trenutniVozac = null;
            }
            else
                trenutniVozac = null;
            mapaVozaca.remove(vozac.DajId());
            vozac.UkloniPridruzenoVozilo();
        }
    }
    @Override
    public boolean PostaviTrenutnogVozaca(Integer id)
    {
        if(mapaVozaca.containsKey(id))
        {
            Vozac vozac = mapaVozaca.get(id);
            if(vozac!=null&&!vozac.JeLiBolovanje()&&!vozac.JeLiGodisnji())
                trenutniVozac = id;
            return true;
        }
        return false;
    }
    @Override
    public void UkloniTrenutnogVozaca()
    {
        Integer vozacIdStari = trenutniVozac;
        RotirajVozace();
        if(vozacIdStari == trenutniVozac)
            trenutniVozac = null;
    }
    public void RotirajVozace()
    {
        int i, duljina;
        Integer[] kljucevi = mapaVozaca.keySet().toArray(new Integer[0]);
        duljina = kljucevi.length;
        if(duljina<2 || trenutniVozac == null) //nema se što rotirati
            return;
        for(i=0; i<duljina; i++) //dolazi na poziciju trenutnog vozaca
            if(mapaVozaca.get(kljucevi[i]).DajId()==trenutniVozac)
                break;
        Integer vozacId;
        Vozac vozac;
        for(int j=1; j<duljina; j++)
        {
            vozacId = mapaVozaca.get(kljucevi[(i+j)%duljina]).DajId();
            vozac = mapaVozaca.get(vozacId);
            if(!vozac.JeLiBolovanje() && !vozac.JeLiGodisnji())
                trenutniVozac = vozacId;
        }
    }
    
    
    @Override
    public LinkedHashMap<Integer,Vozac> DajMapuVozaca()
    {
        return mapaVozaca;
    }
    @Override
    public Vozac DajTrenutnogVozaca()
    {
       Vozac vozac = mapaVozaca.get(trenutniVozac);
       return vozac;
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
        mapaVozaca = new LinkedHashMap<>();
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
        this.mapaVozaca = new LinkedHashMap<>();
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
