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
 * Upravljanje stanjem KVAR
 * @author elvis
 */
public class StanjeKvar implements VoziloStanjeSucelje
{
    public final String naziv;
    VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected MVCModelSucelje MVCmodel;
    /**
     * Konstruktor
     * @param kontekst referenca konteksta
     */
    public StanjeKvar(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "KVAR";
        if(kontekst!=null)
        {
            kontekst.PostaviKvar();
            vozilo = kontekst.DajVozilo();
            MVCmodel = kontekst.DajMVCModel();
            ispisiKvar();
        } 
    }
    /**
     * Metoda koja se poziva u simulaciji za kretanje unaprijed. Stanje kontrola ne radi ništa.
     */
    @Override
    public void Napredovanje()
    {
        
    }
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
     * Metoda koja prima vanjski zahtijev za promjenom stanja
     * @param novoStanje zadtraženo novo stanje
     */
    @Override
    public void Prijelaz(String novoStanje)
    {
        if(novoStanje.equals("ODVOZ"))
        {
            kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
        }
    }
    /**
     * Ispis informacija o stanju
     */
    private void ispisiKvar()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je u kvaru. Do sada preuzeto "+
                kontekst.dajPreuzetoSpremnika()+" spremnika.");
    }
}
