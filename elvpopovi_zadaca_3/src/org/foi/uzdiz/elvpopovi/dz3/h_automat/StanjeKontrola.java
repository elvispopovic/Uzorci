/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;

/**
 * Upravljanje stanjem KONTROLA
 * @author elvis
 */
public class StanjeKontrola implements VoziloStanjeSucelje
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
    public StanjeKontrola(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "KONTROLA";
        if(kontekst != null)
        {
            vozilo = kontekst.DajVozilo();
            MVCmodel = kontekst.DajMVCModel();
            ispisiKontrolu();
        }
    }
    /**
     * Metoda koja prima vanjski zahtijev za promjenom stanja
     * @param novoStanje zatraženo novo stanje
     */
    @Override
    public void Prijelaz(String novoStanje)
    {
        if(novoStanje.equals("PRIKUPLJANJE"))
            kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
    }
    /**
     * Metoda koja se poziva u simulaciji za kretanje unaprijed. Stanje KONTROLA ne radi ništa.
     */
    @Override
    public void Napredovanje()
    {
        
    }
    /**
     * Ispis informacija o stanju
     */
    private void ispisiKontrolu()
    {
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je na kontroli. Do sada preuzeto "+
                kontekst.dajPreuzetoSpremnika()+" spremnika.");
    }  
}
