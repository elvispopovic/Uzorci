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
public class LanacKontrola implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Ispisivanje ispis;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    private final ListaVozila listaKontrola;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacKontrola(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaKontrola = this.simulacija.DajListaKontrola();
        listaPrikupljanje = this.simulacija.DajListaPrikupljanje();
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
        if(komanda[0].equals("KONTROLA"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda KONTROLA");
            for(String s : parametriVozila)
            {
                VoziloSucelje vozilo = listaPrikupljanje.IzdvojiVozilo(s);
                if(vozilo!=null)
                    listaKontrola.UbaciVozilo(vozilo);
            }
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda, parametriVozila);       
    } 
}
