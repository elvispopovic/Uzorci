/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class StanjeKontrola implements VoziloStanjeSucelje
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
        
    }
    private void ispisiKontrolu()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je na kontroli. Do sada preuzeto "+
                kontekst.dajPreuzetoSpremnika()+" spremnika.");
    }  
}
