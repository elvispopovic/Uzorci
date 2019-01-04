/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozac;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacPreuzmi implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    /**
     * Konstruktor
     * @param simulacija Referenca na dekoriranu simulaciju
     */
    public LanacPreuzmi(SimulacijaSucelje dispecer)
    {
        this.simulacija = dispecer;
        listaPrikupljanje = dispecer.DajListaVozilaSimulacija();
        sljedbenik = null;
    }
    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {
        this.sljedbenik = sljedbenik;
    }
    @Override
    public void ObradiKomandu(String[] komanda)
    {
        if(listaPrikupljanje == null)
            return;
        if(komanda[0].replaceAll("\\p{Z}","").toUpperCase().equals("PREUZMI"))
        {
            simulacija.Ispisi("Komanda: PREUZMI");
            if(komanda.length>1)
                obradiPreuzmi(komanda);
            else
                simulacija.Ispisi("Neispravna komanda. Komanda treba biti oblika PREUZMI;vozač;[vozilo;]");
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda);     
    }
    
    private void obradiPreuzmi(String[] komanda)
    {
        ListaVozila listaVozila = simulacija.DajListaVozilaSimulacija();
        HashMap<Integer,Vozac> mapaVozaca = listaVozila.DajMapuVozaca();
        ListaVozila.Iterator listaVozilaIterator = listaVozila.DajIterator();
        for(Integer k:mapaVozaca.keySet())
        {
            Vozac vozac = mapaVozaca.get(k);
            if(vozac.DajIme().equals(komanda[1]))
            {
                VoziloSucelje trenutnoVozilo = vozac.DajPridruzenoVozilo();
                if(trenutnoVozilo != null)
                    trenutnoVozilo.UkloniVozaca(vozac);
                if(komanda.length<3)
                    return;
                ArrayList<String> parametriKomande = new ArrayList<>(Arrays.asList(komanda[2].split(Pattern.quote(",")))); 
                while(listaVozilaIterator.imaLiSlijedeceg())
                {
                    VoziloSucelje vozilo = listaVozilaIterator.slijedeci();
                    if(vozilo.dajId().equals(parametriKomande.get(0)))
                    {
                        vozilo.DodajVozaca(vozac);
                        return;
                    }
                }
                return;
            }
        }
        simulacija.Ispisi("Vozač tog imena ("+komanda[1]+") ne postoji.");
    }
}
