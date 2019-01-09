/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 * Upravljanje stanjem ZAVRŠENO
 * U ovo stanje vozilo odlazi kada prikupi sve spremnike i odveze otpad posljednji puta
 * Vozilo se i iz tog stanja može prebaviti u drugi ishodišni sustav.
 * Samo stanje neće dozvoliti takav prijelaz, ali se može obaviti i neovisno.
 * @author elvis
 */
public class StanjeZavrseno implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected MVCModelSucelje MVCmodel;
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
    StanjeZavrseno(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "ZAVRSENO";
        if(kontekst==null)
            return;
        vozilo = kontekst.DajVozilo();
        if(kontekst != null)
        {
            MVCmodel = kontekst.DajMVCModel();
            ispisiZavrseno();
        }
    }
    /**
     * Metoda koja prima vanjski zahtijev za promjenom stanja
     * @param novoStanje zatraženo novo stanje
     */
    @Override
    public void Prijelaz(String novoStanje)
    {
        return;
    }
    /**
     * Metoda koja se poziva u simulaciji za kretanje unaprijed. Stanje ZAVRSENO ne radi ništa.
     */
    @Override
    public void Napredovanje()
    {
        
    }
    /**
     * Ispis informacija o stanju
     */
    private void ispisiZavrseno()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je završilo. Preuzeto "+
                kontekst.dajPreuzetoSpremnika()+" spremnika.");
    }
    
}
