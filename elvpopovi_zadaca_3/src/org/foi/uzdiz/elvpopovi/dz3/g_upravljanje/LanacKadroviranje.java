/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
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
        switch(komanda[0].toUpperCase())
        {
            case "GODIŠNJI ODMOR": obradiGodisnji(komanda);
                break;
                
            default: sljedbenik.ObradiKomandu(komanda);   
        }     
    } 
    
    private void obradiGodisnji(String komanda[])
    {
        
    }
}
