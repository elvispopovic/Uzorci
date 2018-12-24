/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

/**
 *
 * @author elvis
 */
public class StanjeParkiraliste implements VoziloStanje
{
    public final String naziv;
    protected VoziloKontekstSucelje kontekst;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
    StanjeParkiraliste(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "PARKIRALISTE";
        kontekst.UkloniKvar();
    }
    
    @Override
    public boolean Prijelaz(String novoStanje)
    {
        if(novoStanje.equals("PRIKUPLJANJE"))
        {
            kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
            return true;
        }
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        return;
    }
    
}
