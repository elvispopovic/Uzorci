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
 *
 * @author elvis
 */
public class StanjeKvar implements VoziloStanjeSucelje
{
    public final String naziv;
    VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected MVCModelSucelje MVCmodel;
    
    public StanjeKvar(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "KVAR";
        if(kontekst!=null)
        {
            vozilo = kontekst.DajVozilo();
            MVCmodel = kontekst.DajMVCModel();
            ispisiKvar();
        } 
    }

    @Override
    public void Napredovanje()
    {
        
    }

    @Override
    public String DajNaziv()
    {
        return naziv;
    }

    @Override
    public boolean Prijelaz(String novoStanje)
    {
        if(novoStanje.equals("ODVOZ"))
        {
            kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
            return true;
        }
        return false;
    }
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
