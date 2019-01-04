/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.PodrucjeSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacObradi implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final ListaVozila listaPrikupljanje;
    private LanacKomandiApstraktni sljedbenik;
    /**
     * Konstruktor
     * @param simulacija Referenca na dekoriranu simulaciju
     */
    public LanacObradi(SimulacijaSucelje dispecer)
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
        if(komanda[0].replaceAll("\\p{Z}","").toUpperCase().equals("OBRADI"))
        {
            simulacija.Ispisi("Komanda: OBRADI");
            if(komanda.length>2)
                obradiObradi(komanda);
            else
                simulacija.Ispisi("Neispravna komanda. Komanda treba biti oblika OBRADI;poducje;lista_vozila");
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda);     
    }
    
    private void obradiObradi(String komanda[])
    { 
        ProblemskiAbstractProduct problemske = simulacija.DajProblemske();
        PodrucjeSucelje ishodisteSustava = problemske.nadjiIshodiste(komanda[1]);
        if(ishodisteSustava == null)
        {
            simulacija.Ispisi("Greška. To ishodište sustava ne postoji.");
            return;
        }
        ArrayList<String> parametriKomande = new ArrayList<>(Arrays.asList(komanda[2].split(Pattern.quote(",")))); 
        if(parametriKomande.isEmpty())
        {
            simulacija.Ispisi("Nisu upisani parametri, odnosno šifre vozila.");
            return;
        }
        simulacija.PromijeniIshodisteSustava(parametriKomande, ishodisteSustava.dajId());
    }

    
    
}
