/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class LanacPripremi implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final Ispisivanje ispis;
    private final ListaVozila listaPrikupljanje;
    private final ListaVozila listaParkiraliste;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacPripremi(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaPrikupljanje = simulacija.DajListaPrikupljanje();
        listaParkiraliste = simulacija.DajListaParkiraliste();
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
        if(komanda[0].equals("PRIPREMI"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda: PRIPREMI");
            for(String s : parametriVozila)
            {
                VoziloSucelje vozilo = listaParkiraliste.IzdvojiVozilo(s);
                if(vozilo!=null)
                {
                    vozilo.dajKontekst().UkloniKontrolu();
                    listaPrikupljanje.UbaciVozilo(vozilo);
                }
            }

        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda, parametriVozila);       
    } 
}
