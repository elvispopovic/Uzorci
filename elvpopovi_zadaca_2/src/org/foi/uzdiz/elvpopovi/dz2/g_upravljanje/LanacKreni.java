/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.g_upravljanje;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz2.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz2.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class LanacKreni implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Ispisivanje ispis;
    private final Parametri parametri;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacKreni(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
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
        int brojCiklusa;
        if(parametriVozila == null)
            return;
        if(komanda[0].equals("KRENI"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda KRENI ");
            try
            {
                if(komanda.length==1)
                    izvrsiKomanduKreni(0);
                else
                    izvrsiKomanduKreni(Integer.parseInt(komanda[1].replaceAll("\\p{Z}","")));
            }
            catch(NumberFormatException e)
            {
                ispis.Ispisi("Greška prilikom parsiranja broja ciklusa u komandi \"Kreni\".");
            }
        }
        else if(sljedbenik!=null)
            sljedbenik.ObradiKomandu(komanda, parametriVozila);       
    } 
    private void izvrsiKomanduKreni(int brojCiklusa)
    {
        simulacija.PostaviListeUlica();
        boolean zavrseno=false;
        if(brojCiklusa==0)
            while(!zavrseno)
            {
                zavrseno = true;
                if((simulacija.ObradiVozilaUPrikupljanju()==false)||
                   (simulacija.ObradiVozilaZaZbrinjavanje()==false))
                    zavrseno=false;
            }
        else for(int i=0; i<brojCiklusa; i++)
        {
            if((simulacija.ObradiVozilaUPrikupljanju()==true)&&
               (simulacija.ObradiVozilaZaZbrinjavanje()==true))
                break;
        }
    }
}
