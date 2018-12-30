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
public class StanjeZavrseno implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected MVCModelSucelje MVCmodel;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
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

    @Override
    public boolean Prijelaz(String novoStanje)
    {
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        
    }
    
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
