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
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozac;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacKadroviranje implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    public LanacKadroviranje(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
        listaPrikupljanje = simulacija.DajListaVozilaSimulacija();
        sljedbenik = null;
    }
    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {
        this.sljedbenik = sljedbenik;
    }
    
    @Override
    public void ObradiKomandu(String komanda[])
    {
        if(listaPrikupljanje == null)
            return;
        switch(komanda[0].replaceAll("\\p{Z}","").toUpperCase())
        {
            case "GODIŠNJIODMOR": obradiGodisnjiBolovanje(komanda,1);
                break;
            case "GODISNJIODMOR": obradiGodisnjiBolovanje(komanda,1);
                break;
            case "BOLOVANJE": obradiGodisnjiBolovanje(komanda,2);
                break;
                
            default: sljedbenik.ObradiKomandu(komanda);   
        }     
    } 
    
    private void obradiGodisnjiBolovanje(String komanda[], int godisnjiBolovanje)
    {
        HashMap<Integer,Vozac> mapaVozaca = simulacija.DajListaVozilaSimulacija().DajMapuVozaca();
        ArrayList<String> parametriVozaca = new ArrayList<>(Arrays.asList(komanda[1].split(Pattern.quote(",")))); 
        if(parametri.DajVrijednost("ispis")==0)
                simulacija.Ispisi("Komanda "+komanda[0]);
        for(Integer vozacId:mapaVozaca.keySet())
        {
            Vozac vozac = mapaVozaca.get(vozacId);
            for(String p:parametriVozaca)
            {
                if(vozac != null && vozac.DajIme().equals(p))
                {
                    if(godisnjiBolovanje == 1)
                        vozac.PostaviGodisnji();
                    else if(godisnjiBolovanje == 2)
                        vozac.PostaviBolovanje();
                }
            }
        }
    }
    
    private void obradiBolovanje(Vozac vozac)
    {
        vozac.PostaviBolovanje();  
    }
    
    private void obradiGodisnji(Vozac vozac)
    {
        vozac.PostaviGodisnji();  
    }
}
