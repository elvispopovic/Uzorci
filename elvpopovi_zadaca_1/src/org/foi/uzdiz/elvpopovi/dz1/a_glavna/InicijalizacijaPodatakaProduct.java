/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.a_glavna;

import java.io.File;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.KreatorPodataka;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.KreatorPodatakaSucelje;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Podaci;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.RandomGenerator;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class InicijalizacijaPodatakaProduct implements InicijalizacijaAbstractProduct
{

    @Override
    public Parametri getParametri() 
    {
        return parametri;
    }
    @Override
    public Ispisivanje getIspisivanje() 
    {
        return ispisivanje;
    }

    @Override
    public Podaci getUlice() 
    {
        return ulice;
    }

    @Override
    public Podaci getSpremnici() 
    {
        return spremnici;
    }

    @Override
    public Podaci getVozila() 
    {
        return vozila;
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
    public void UcitajPodatkeUlica()
    {
        String datoteka = parametri.DajDatoteku("ulice");
        ulice = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
    }
    @Override
    public void UcitajPodatkeSpremnika()
    {
        String datoteka = parametri.DajDatoteku("spremnici");
        spremnici = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
    }
    @Override
    public void UcitajPodatkeVozila()
    {
        String datoteka = parametri.DajDatoteku("vozila");
        vozila = (Podaci)kreator.KreirajPodatke("PODACI", new File(new File(argument).getParent(), datoteka).getPath());
    }
   
    private final String argument;
    private final KreatorPodatakaSucelje kreator;
    private Parametri parametri;
    private Ispisivanje ispisivanje;
    private Podaci ulice, spremnici, vozila;
}
