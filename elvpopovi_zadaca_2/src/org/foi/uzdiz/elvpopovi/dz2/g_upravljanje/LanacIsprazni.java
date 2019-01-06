/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.g_upravljanje;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz2.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz2.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz2.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class LanacIsprazni implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Ispisivanje ispis;
    private final Parametri parametri;
    private final ArrayList<VoziloSucelje> listaVozila;
    private final ListaVozila listaPrikupljanje;
    private final ListaVozila listaKvar;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacIsprazni(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaVozila = this.simulacija.DajListuVozila();
        listaPrikupljanje = simulacija.DajListaPrikupljanje();
        listaKvar = simulacija.DajListaKvar();
        sljedbenik = null;
    }
    
    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {
        this.sljedbenik = sljedbenik;
    }
    @Override
    public void ObradiKomandu(String komanda[], ArrayList<String> parametriVozila)
    {
        if(parametriVozila == null)
            return;
        if(komanda[0].equals("ISPRAZNI"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda ISPRAZNI");
            izvrsiKomanduIsprazni(parametriVozila);
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda, parametriVozila);   
    } 
    
    private void izvrsiKomanduIsprazni(ArrayList<String> parametriVozila)
    {
        for(String s : parametriVozila)
        {
            VoziloSucelje vozilo = listaPrikupljanje.DajVozilo(s);
            if(vozilo!=null)
            {
                int indeks = listaPrikupljanje.DajVoziloIndeks(s);
                if(indeks>=0)
                    simulacija.PosaljiNaDeponij(indeks); 
            }
            else
            {
                vozilo = listaKvar.DajVozilo(s);
                if(vozilo!=null)
                {
                    vozilo = listaKvar.IzdvojiVozilo(s);
                    listaPrikupljanje.UbaciVozilo(vozilo);
                    int indeks = listaPrikupljanje.DajVoziloIndeks(s);
                    if(indeks>=0)
                        simulacija.PosaljiNaDeponij(indeks); 
                }
            }
        }
    }
}
