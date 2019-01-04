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
public class LanacIsprazni implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final ArrayList<VoziloSucelje> listaVozila;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacIsprazni(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
        listaVozila = this.simulacija.DajListuVozilaPodaci();
        listaPrikupljanje = simulacija.DajListaVozilaSimulacija();
        //listaKvar = simulacija.DajListaKvar();
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
        if(komanda[0].replaceAll("\\p{Z}","").toUpperCase().equals("ISPRAZNI"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                simulacija.Ispisi("Komanda ISPRAZNI");
            if(komanda.length>1)
                obradiIsprazni(komanda);
            else
                simulacija.Ispisi("Neispravna komanda. Komanda treba biti oblika ISPRAZNI;lista_vozila");
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda);   
    } 
    
    private void obradiIsprazni(String[] komanda)
    {
        ArrayList<String> parametriVozila = new ArrayList<>(Arrays.asList(komanda[1].split(Pattern.quote(",")))); 
        
        
        if(parametriVozila.isEmpty())
            return;
        for(String s : parametriVozila)
        {
            VoziloSucelje vozilo = listaPrikupljanje.DajVozilo(s);
            if(vozilo!=null)
            {
                VoziloKontekstSucelje kontekst = vozilo.dajKontekst();
                int indeks = listaPrikupljanje.DajIndeksVozila(s);
                if(indeks>=0)
                {
                    kontekst.DajStanje().Prijelaz("ODVOZ");
                }
            }
        }
    }
}
