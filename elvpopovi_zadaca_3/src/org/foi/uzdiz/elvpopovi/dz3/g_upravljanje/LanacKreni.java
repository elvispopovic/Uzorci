/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.Collections;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacKreni implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacKreni(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
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
        Parametri parametri = Parametri.getInstance();
        String[] komandaRazdvojeno = komanda[0].split(Pattern.quote(" "));
        if(komandaRazdvojeno[0].equals("KRENI"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                simulacija.Ispisi("Komanda KRENI");
            obradiKreni(komandaRazdvojeno);
            if(parametri.DajVrijednost("ispis")==1)
                porukaOIskljucenomPrikazu();
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda);       
    } 
    private void obradiKreni(String[] komandaRazdvojeno)
    {
        try
        {
            if(komandaRazdvojeno.length==1)
                izvrsiKomanduKreni(0);
            else
                izvrsiKomanduKreni(Integer.parseInt(komandaRazdvojeno[1].replaceAll("\\p{Z}","")));
        }
        catch(NumberFormatException e)
        {
            simulacija.Ispisi("Greška prilikom parsiranja broja ciklusa u komandi \"Kreni\".");
        }
    }
    private void izvrsiKomanduKreni(int brojCiklusa)
    {
        if(brojCiklusa==0)
            while(true)
            {
                simulacija.ObradiStanjaVozila();
                if(simulacija.provjeriZavrsetak()==true)
                    break;
            }
        else for(int i=0; i<brojCiklusa; i++)
        {
            simulacija.ObradiStanjaVozila();
                if(simulacija.provjeriZavrsetak()==true)
                    break;
        }
    }
    private void porukaOIskljucenomPrikazu()
    {
        simulacija.Ispisi(String.join("", Collections.nCopies(80, "*")));
        simulacija.Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        simulacija.Ispisi("*"+String.join("", Collections.nCopies(17, " "))+"Isključeno je ispisivanje poslova odvoza..."+
                String.join("", Collections.nCopies(18, " "))+"*");
        simulacija.Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        simulacija.Ispisi(String.join("", Collections.nCopies(80, "*")));
    }
}
