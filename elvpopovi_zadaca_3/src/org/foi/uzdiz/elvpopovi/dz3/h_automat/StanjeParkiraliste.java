/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class StanjeParkiraliste implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloKontekstSucelje kontekst;
    protected SimulacijaSucelje simulacija;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
    StanjeParkiraliste(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "PARKIRALISTE";
        if(kontekst!=null)
        {
            kontekst.UkloniKvar();
        }
    }
    
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
    private boolean provjeriPrijelazCekanje()
    {
        Ulica ulica = kontekst.DajTrenutnuUlicu();
        if(ulica!=null && simulacija.BrojNecekajucihVozilaUUlici(ulica.Id())>2)
            return true;
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        return;
    }
    
}
