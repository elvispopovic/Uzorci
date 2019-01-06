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
public class LanacPripremi implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final Ispisivanje ispis;
    private final ListaVozila listaPrikupljanje;
    private final ListaVozila listaParkiraliste;
    private final ListaVozila listaKontrola;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacPripremi(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaPrikupljanje = simulacija.DajListaPrikupljanje();
        listaParkiraliste = simulacija.DajListaParkiraliste();
        listaKontrola     = simulacija.DajListaKontrola();
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
            System.out.println("Komanda: PRIPREMI, +parkiraliste: "+listaParkiraliste.Velicina());
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda: PRIPREMI");
            for(String s : parametriVozila)
            {
                VoziloSucelje vozilo = listaParkiraliste.IzdvojiVozilo(s);
                if(vozilo == null)
                    vozilo = listaKontrola.IzdvojiVozilo(s);
                if(vozilo!=null)
                    listaPrikupljanje.UbaciVozilo(vozilo);
                if(vozilo!=null)
                    ispis.Ispisi("Komanda: PRIPREMI "+vozilo.dajId()+": "+vozilo.dajNaziv());
            }

        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda, parametriVozila);       
    } 
}
