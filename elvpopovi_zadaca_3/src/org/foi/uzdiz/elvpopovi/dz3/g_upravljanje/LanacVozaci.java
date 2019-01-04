/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozac;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacVozaci implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private LanacKomandiApstraktni sljedbenik;
    public LanacVozaci(SimulacijaSucelje dispecer)
    {
        this.simulacija = dispecer;
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
        if(komanda[0].toUpperCase().equals("VOZAČI"))
        {
            simulacija.Ispisi("Komanda VOZAČI");
            obradiVozaci();
        }
        else if(komanda[0].toUpperCase().equals("VOZACI"))
        {
            simulacija.Ispisi("Komanda VOZACI");
            obradiVozaci();
        }
        else sljedbenik.ObradiKomandu(komanda);  
    }
    
    private void obradiVozaci()
    { 
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        simulacija.Ispisi("Status vozila prikazan je tablično:");
        form.format("%12s |%18s |%8s |%9s |%10s |","Vozač","Pridruženo vozilo","Aktivan","Godišnji","Bolovanje");
        simulacija.Ispisi(sb.toString());
        simulacija.Ispisi(String.join("", Collections.nCopies(73, "=")));
        sb.setLength(0);
        ispisiPopisVozaca();
    }
    
    private void ispisiPopisVozaca()
    {
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        HashMap<Integer,Vozac> mapaVozaca = simulacija.DajListaVozilaSimulacija().DajMapuVozaca();
        for(Integer k:mapaVozaca.keySet())
        {
            boolean aktivan = false;
            Vozac vozac = mapaVozaca.get(k);
            VoziloSucelje vozilo = vozac.DajPridruzenoVozilo();
            Integer trenutniId = -1;
            String pridruzenoId ="--";
            String pridruzenoNaziv = "--------- -";
            if(vozilo!=null)
            {
                pridruzenoId = vozilo.dajId();
                pridruzenoNaziv = vozilo.dajNaziv();
                Vozac trenutniVozac = vozilo.DajTrenutnogVozaca();
                trenutniId = trenutniVozac.DajId();
                if(trenutniVozac != null && (trenutniId == vozac.DajId()))
                    aktivan = true;
            }
            sb.setLength(0);
            form.format("%12s | %3s:%13s |%8s |%9s |%10s |",vozac.DajIme(),
                    pridruzenoId,pridruzenoNaziv,
                    (aktivan?" DA ":" NE "),(vozac.JeLiGodisnji()?" DA ":" NE "),(vozac.JeLiBolovanje()?" DA ":" NE "));
            simulacija.Ispisi(sb.toString());
        }
    }
    
}
