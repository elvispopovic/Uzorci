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
import org.foi.uzdiz.elvpopovi.dz3.h_automat.StanjeKvar;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class LanacKvar implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Ispisivanje ispis;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacKvar(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaPrikupljanje = simulacija.DajListaPrikupljanje();
        //listaKvar         = simulacija.DajListaKvar();
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
        VoziloKontekstSucelje kontekst;
        if(parametriVozila == null)
            return;
        if(komanda[0].equals("KVAR"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda KVAR");
            for(String s : parametriVozila)
            {
                VoziloSucelje vozilo = listaPrikupljanje.DajVozilo(s);
                if(vozilo!=null)
                {
                    kontekst = vozilo.dajKontekst();
                    kontekst.PostaviKvar();
                    kontekst.PostaviStanje(new StanjeKvar(kontekst));
                }
            }
        }
        else sljedbenik.ObradiKomandu(komanda, parametriVozila);
    } 
}
