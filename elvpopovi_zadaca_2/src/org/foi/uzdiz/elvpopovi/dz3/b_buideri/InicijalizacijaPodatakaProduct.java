/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import java.io.File;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.KreatorPodataka;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.KreatorPodatakaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Podaci;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.RandomGenerator;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class InicijalizacijaPodatakaProduct implements InicijalizacijaAbstractProduct
{

    @Override
    public Parametri dajParametre() 
    {
        return parametri;
    }
    @Override
    public Ispisivanje dajIspisivanje() 
    {
        return ispisivanje;
    }

    @Override
    public Podaci dajPodrucja()
    {
        return podrucja;
    }
    @Override
    public Podaci dajUlice() 
    {
        return ulice;
    }

    @Override
    public Podaci dajSpremnike() 
    {
        return spremnici;
    }

    @Override
    public Podaci dajVozila() 
    {
        return vozila;
    }
    @Override
    public Podaci dajDispecera()
    {
        return dispecer;
    }

    public InicijalizacijaPodatakaProduct(String argument) 
    {
        kreator = new KreatorPodataka();
        this.argument = argument;
    }
    
    @Override
    public void UcitajParametre()
    {
        parametri = (Parametri)kreator.KreirajPodatke("PARAMETRI", argument);
    }
    @Override
    public void KreirajIspisivac()
    {
        if(parametri==null)
            return;
        String datoteka = parametri.DajDatoteku("izlaz");
        ispisivanje = Ispisivanje.getInstance(new File(new File(argument).getParent(), datoteka).getPath());
    }
    @Override
    public void KreirajIdGenerator()
    {
        IdGenerator.getInstance();
    }
    @Override
    public void KreirajRandomGenerator()
    {
        long sjeme;
        sjeme = (long)parametri.DajVrijednost("sjemeGeneratora");
        if(sjeme != -1)
            RandomGenerator.getInstance(sjeme);
    }
    
    @Override
    public void UcitajPodatkePodrucja()
    {
        String datoteka = parametri.DajDatoteku("područja");
        if(datoteka != null)
            podrucja = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
        else podrucja = null;
    }
    
    @Override
    public void UcitajPodatkeUlica()
    {
        String datoteka = parametri.DajDatoteku("ulice");
        if(datoteka != null)
            ulice = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
        else ulice = null;
    }
    @Override
    public void UcitajPodatkeSpremnika()
    {
        String datoteka = parametri.DajDatoteku("spremnici");
        if(datoteka != null)
            spremnici = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
        else spremnici = null;
    }
    @Override
    public void UcitajPodatkeVozila()
    {
        String datoteka = parametri.DajDatoteku("vozila");
        if(datoteka != null)
            vozila = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
        else vozila = null;
    }
    @Override
    public void UcitajPodatkeDispecera()
    {
        String datoteka = parametri.DajDatoteku("dispečer");
        if(datoteka != null)
            dispecer = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
        else dispecer = null;
    }
   
    private final String argument;
    private final KreatorPodatakaSucelje kreator;
    private Parametri parametri;
    private Ispisivanje ispisivanje;
    private Podaci podrucja, ulice, spremnici, vozila, dispecer;
}
