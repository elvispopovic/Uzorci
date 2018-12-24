/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Vozilo implements VoziloSucelje
{

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
        //ispisiSpremnike();
    }

    @Override
    public String dajNaziv() 
    {
        return naziv;
    }

    @Override
    public String[] dajVozace() 
    {
        return vozaci;
    }

    @Override
    public int dajNosivost() 
    {
        return nosivost;
    }
    @Override
    public VoziloKontekst dajKontekst()
    {
        return kontekst;
    }

    @Override
    public VoziloStatistika dajStatistiku()
    {
        return statistika;
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
    public Vozilo(int vrsta)
    {
        this.vrsta = vrsta;
        parametri = Parametri.getInstance();
        ispis = Ispisivanje.getInstance();
        rnd = RandomGenerator.getInstance();
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
            vozaci = podaciZapis[Arrays.asList(shema).indexOf("vozači")].split(Pattern.quote(",")); 
        }
        catch(NumberFormatException e)
        {
            ispis.Ispisi("Greška prilikom parsiranja podatka o vozilu za "+naziv+".");
        }
        Kopiraj(protoVozila.get(vrsta));
    }
    
    //vraca true kad zavrsi sve spremnike
    @Override
    public boolean IsprazniSpremnik()
    {
        if(kontekst==null || dodijeljeneUlice==null)
            return true;
        if(kontekst.dajTrenutnuUlicu()>=dodijeljeneUlice.size()) //nema vise ulica
            return provjeriPopunjenost(); //ako je i prazan to je kraj
        int ulicaBroj = kontekst.dajTrenutnuUlicu();
        ArrayList<Spremnik> spremnici = dodijeljeniSpremnici.get(ulicaBroj);
        provjeriSpremnike(spremnici, ulicaBroj);
        return false;
    }
    private boolean provjeriPopunjenost()
    {
        if(kontekst.dajPopunjenost()==(float)0.0)
            return true; 
        else
        {
            kontekst.SetPotrebnoPraznjenje();
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo (id: "+id+") "+naziv+" je obavilo sve ulice i odlazi poluprazno na deponij.");
            statistika.PovecajBrojOdlazakaNaDeponij();
            return false;
        }
    }
    private boolean provjeriSpremnike(ArrayList<Spremnik> spremnici, int ulicaBroj)
    {
        if(kontekst.dajTrenutniSpremnik()>=spremnici.size())
        {
            kontekst.PovecajTrenutnuUlicu();
            return false;
        }
        Spremnik spremnik = spremnici.get(kontekst.dajTrenutniSpremnik());
        if(kontekst.dajPopunjenost()+spremnik.dajKolicinuOtpada()>(float)nosivost)
        {
            kontekst.SetPotrebnoPraznjenje();
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo (id: "+id+") "+naziv+" je napunjeno pa odlazi na deponij istovariti otpad.");
            statistika.PovecajBrojOdlazakaNaDeponij();
            return false;
        }
        obradiSpremnik(spremnik);
        return false;
    }
    
    private void obradiSpremnik(Spremnik spremnik)
    {
        if(spremnik.dajKolicinuOtpada()>0)
        {
            int brojDecimala = parametri.DajVrijednost("brojDecimala");
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo (id: "+id+") "+naziv+" preuzima "+rnd.round(spremnik.dajKolicinuOtpada(),brojDecimala)+
                " kg otpada.");
            statistika.PovecajBrojSpremnika();
            statistika.PovecajBrojMjesta(spremnik.DajKorisnike().size());
            statistika.PovecajUkupnuKolicinuOtpada(spremnik.dajKolicinuOtpada());
            kontekst.PovecajPopunjenost(spremnik.IsprazniSpremnik());
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("   otpad u vozilu: "+rnd.round(kontekst.dajPopunjenost(),brojDecimala)+
                " kg, preostalo: "+
                rnd.round((float)nosivost-kontekst.dajPopunjenost(),brojDecimala)+" kg");
        }
        else
            prazanSpremnik(spremnik);
        kontekst.PovecajTrenutniSpremnik();
    }
    private void prazanSpremnik(Spremnik spremnik)
    {
        try
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo (id: "+id+") "+naziv+" nailazi na prazan spremnik u "+
                spremnik.DajKorisnike().get(0).getUlica().Naziv());
        }
        catch(Exception e)
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo "+naziv+" nailazi na prazan spremnik.");
        }
    }
    
    @Override
    public boolean ProvjeriNapunjenost()
    {
        return kontekst.postaviPotrebnoTrazenje();
    }
    @Override
    public void IsprazniVozilo(int ciklusaOdvoz)
    {
        kontekst.postaviBrojacOdvoza(ciklusaOdvoz);
        kontekst.Isprazni();
    }
    @Override
    public boolean ProvjeriOdvoz()
    {
        if(kontekst.dajBrojacOdvoza()>0)
        {
            kontekst.SmanjiBrojacOdvoza();
            return false;
        }
        else
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo "+naziv+" se vraća prazno sa deponija otpada.");
            return true;
        }
    }
    private void Kopiraj(VoziloSucelje original)
    {
        this.vrsta = original.dajVrstu(); //za sada se samo to preuzima iz originala
        parametri = Parametri.getInstance();
        ispis = Ispisivanje.getInstance();
        rnd = RandomGenerator.getInstance();
        kontekst = new VoziloKontekst();
        statistika = new VoziloStatistika();
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
    Parametri parametri;
    RandomGenerator rnd;
    Ispisivanje ispisivanje;
    private String id;
    String naziv;
    int vrsta, tip, ciklusaOdvoz;
    private String[] vozaci;
    VoziloKontekst kontekst;
    VoziloStatistika statistika;
    private int nosivost;
    private ArrayList<Ulica> dodijeljeneUlice;
    private ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici;
    private Ispisivanje ispis;
}
