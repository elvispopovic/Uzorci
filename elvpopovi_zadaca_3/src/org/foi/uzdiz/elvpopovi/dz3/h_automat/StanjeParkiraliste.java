/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 * Upravljanje stanjem PARKIRALISTE
 * @author elvis
 */
public class StanjeParkiraliste implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloKontekstSucelje kontekst;
    protected SimulacijaSucelje simulacija;
    /**
     * Getter koji vraća naziv stanja
     * @return String koji je vraćeni naziv stanja
     */
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    /**
     * Konstruktor
     * @param kontekst referenca konteksta
     */
    StanjeParkiraliste(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "PARKIRALISTE";
        if(kontekst!=null)
        {
            kontekst.UkloniKvar();
        }
    }
    /**
     * Metoda koja prima vanjski zahtijev za promjenom stanja
     * @param novoStanje zatraženo novo stanje
     */
    @Override
    public void Prijelaz(String novoStanje)
    {
        if(kontekst == null)
            return;
        simulacija = kontekst.DajSimulacijske().DajSimulacija();
        simulacija.Ispisi("Prijelaz u stanju parkiralista na "+novoStanje);
        switch(novoStanje)
        {
            case "PRIKUPLJANJE": kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
                break;
            case "KONTROLA": kontekst.PostaviStanje(new StanjeKontrola(kontekst));
                break;
            case "KVAR": kontekst.PostaviStanje(new StanjeKvar(kontekst));
                break;
        }
    }

    /**
     * Metoda koja se poziva u simulaciji za kretanje unaprijed. 
     * Stanje PARKIRALISTE ne radi ništa.
     */
    @Override
    public void Napredovanje()
    {

    }
    
}
