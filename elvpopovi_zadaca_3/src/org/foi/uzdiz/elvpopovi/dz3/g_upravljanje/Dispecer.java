/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.io.StringWriter;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCModelSucelje;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciIteratorSucelje;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciSucelje;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCObserver;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Dispecer implements SimulacijaSucelje, MVCModelSucelje
{
    protected SimulacijaSucelje simulacija;
    protected ArrayList<MVCObserver> observers;
    protected LanacKomandiApstraktni lanacPripremi;
    protected final PodaciSucelje podaciDispecer;
    protected final Ispisivanje ispis;
    protected int brg, brd;
    public  Dispecer(SimulacijaSucelje simulacija, PodaciSucelje podaci)
    {
        this.simulacija = simulacija;
        this.podaciDispecer = podaci;
        observers = new ArrayList<>();
        ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        brg = parametri.DajVrijednost("brg");
        brd = parametri.DajVrijednost("brd");
        Ispisi("Kreiran je dispečer.");
    }
    
    @Override
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacija.DajSimulacijske();
    }
    
    public void PrikljuciMVC(MVCObserver observer)
    {
        this.observers.add(observer);
    }
    public void OdjaviMVC(MVCObserver observer)
    {
        this.observers.remove(observer);
    }
    @Override
    public void ObavijestiMVC()
    {
        for(MVCObserver o:observers)
            o.Osvjezi();
    }

    @Override
    public ArrayList<String> DohvatiPodatkeMVC()
    {
        return simulacija.DajRetkeIspisa();
    }

    @Override
    public void KomandaMVC(String[] komanda)
    {
        if(komanda[0].equals("OBRISI_PODATKE"))
        {
            ArrayList<String> redci = DohvatiPodatkeMVC();
            if(redci != null)
                redci.clear();
        }
        else
        {
            lanacPripremi.ObradiKomandu(komanda);
            ObavijestiMVC();
        }
    }
    
    private void inicijalizirajLanacKomandi()
    {
        LanacKomandiApstraktni lanacKreni, lanacKvar, lanacIsprazni, lanacStatus, lanacKontrola;
         lanacKontrola   = new LanacKontrola(simulacija);
        (lanacStatus     = new LanacStatus(simulacija)).DodajSljedbenika(lanacKontrola);
        (lanacIsprazni   = new LanacIsprazni(simulacija)).DodajSljedbenika(lanacStatus);
        (lanacKvar       = new LanacKvar(simulacija)).DodajSljedbenika(lanacIsprazni);
        (lanacKreni      = new LanacKreni(simulacija)).DodajSljedbenika(lanacKvar);
        (lanacPripremi   = new LanacPripremi(simulacija)).DodajSljedbenika(lanacKreni);
    }
    
    public void Pokreni(SimulacijaSucelje simulacija, Statistika statistika)
    {
        this.simulacija = simulacija;
        if(this.simulacija.ProvjeriParametre() == false)
            return;
        
        inicijalizirajLanacKomandi();
        PodaciIteratorSucelje iterator = podaciDispecer.dajIterator();
        //zapisane komande
        Ispisi("Izvršavanje zapisanih komandi");
        while(iterator.imaLiSlijedeceg())
        {
            String[] komanda=(iterator.slijedeci());
            lanacPripremi.ObradiKomandu(komanda);
        }
        ObavijestiMVC();
        komandniModIntro();
        //prijelaz u korisničke komande
        for(MVCObserver o:observers)
            o.KomandniMod();
    }

    @Override
    public ArrayList<VoziloSucelje> DajListuVozila()
    {
        return simulacija.DajListuVozila();
    }

    @Override
    public ProblemskiAbstractProduct DajProblemske()
    {
        return simulacija.DajProblemske();
    }

    @Override
    public ListaVozila DajListaPrikupljanje()
    {
        return simulacija.DajListaPrikupljanje();
    }


    @Override
    public boolean ObradiStanjaVozila()
    { 
        return false;
    }

    @Override
    public void Pokreni()
    {
        simulacija.Pokreni();
    }

    @Override
    public boolean ProvjeriParametre()
    {
        return simulacija.ProvjeriParametre();
    }

    @Override
    public boolean provjeriZavrsetak()
    {
        return true;
    }
    
    public void Ispisi(String ispis)
    {
        simulacija.Ispisi(ispis);
    }
    public void Ispisi(String ispis, boolean novaLinija)
    {
        simulacija.Ispisi(ispis, novaLinija);
    }
    public ArrayList<String> DajRetkeIspisa()
    {
        return simulacija.DajRetkeIspisa();
    }
    
    private void komandniModIntro()
    {
        Ispisi(String.join("", Collections.nCopies(80, "*")));
        Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        Ispisi("*"+String.join("", Collections.nCopies(27, " "))+"Prijelaz u komandni mod"+
                String.join("", Collections.nCopies(28, " "))+"*");
        Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        Ispisi(String.join("", Collections.nCopies(80, "*")));
    }

}
