/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;

/**
 *
 * @author elvis
 */
public class LanacKontrola implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacKontrola(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
        listaPrikupljanje = this.simulacija.DajListaVozilaSimulacija();
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
        if(komanda[0].replaceAll("\\p{Z}","").toUpperCase().equals("KONTROLA"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                simulacija.Ispisi("Komanda KONTROLA");
            if(komanda.length>1)
                obradiKontrola(komanda);
            else
                simulacija.Ispisi("Neispravna komanda. Komanda treba biti oblika KONTROLA;lista_vozila");
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda);       
    } 
    
    private void obradiKontrola(String[] komanda)
    {
        VoziloKontekstSucelje kontekst; 
        ArrayList<String> parametriVozila = new ArrayList<>(Arrays.asList(komanda[1].split(Pattern.quote(",")))); 
        if(parametriVozila.isEmpty())
            return;
        for(String s : parametriVozila)
        {
            s=s.replaceAll("\\p{Z}","");
            VoziloSucelje vozilo = listaPrikupljanje.DajVozilo(s);
            if(vozilo!=null)
            {
                
                kontekst = vozilo.dajKontekst();
                kontekst.DajStanje().Prijelaz("KONTROLA");
            }
        }
    }
}
