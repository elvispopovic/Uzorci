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
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCView;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.IdGenerator;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class InicijalizacijaPodatakaProduct implements InicijalizacijaAbstractProduct
{
    private String[] argumenti;
    private final String datotekaParametara;
    private final KreatorPodatakaSucelje kreator;
    private PodaciSucelje parametri;
    private MVCView MVCview;
    private Ispisivanje ispisivanje;
    private PodaciSucelje podrucja, ulice, spremnici, vozila, dispecer;
    
    @Override
    public PodaciSucelje dajParametre() 
    {
        return parametri;
    }
    @Override
    public MVCView dajMVCview() 
    {
        return MVCview;
    }
    @Override
    public Ispisivanje dajIspisivanje() 
    {
        return ispisivanje;
    }

    @Override
    public PodaciSucelje dajPodrucja()
    {
        return podrucja;
    }
    @Override
    public PodaciSucelje dajUlice() 
    {
        return ulice;
    }

    @Override
    public PodaciSucelje dajSpremnike() 
    {
        return spremnici;
    }

    @Override
    public PodaciSucelje dajVozila() 
    {
        return vozila;
    }
    @Override
    public PodaciSucelje dajDispecera()
    {
        return dispecer;
    }

    public InicijalizacijaPodatakaProduct(String[] argumenti) 
    {
        this.argumenti = argumenti;
        kreator = new KreatorPodataka();
        this.datotekaParametara = argumenti[0];
    }
    
    @Override
    public void UcitajParametre()
    {
        parametri = kreator.KreirajPodatke("PARAMETRI", datotekaParametara);
        if(argumenti.length>=3)
        {
            int brg = pronadjiArgument("--brg");
            int brd = pronadjiArgument("--brd");
            if(brg == -1 || brd == -1 || (brg+brd)>32)
                nadopuniNedostajuciArgument(brg, brd);
            else
            {
                parametri.DodajVrijednost("brg", brg);
                parametri.DodajVrijednost("brd", brd);
            }
            System.out.println("Brg: "+brg+", brd: "+brd);
        }
    }
    public void InicijalizirajMVC()
    {
        MVCview = new MVCView();
    }
    
    
    
    private void nadopuniNedostajuciArgument(int brg, int brd)
    {
        if(brg>0 && brg<32)
        {
            parametri.DodajVrijednost("brg", brg);
            if(brd == -1 || (brg+brd > 32))
                parametri.DodajVrijednost("brd", 32-brg);
        }
        else if(brd>0 && brd<32)
        {
            parametri.DodajVrijednost("brd", brd);
            if(brg == -1  || (brg+brd > 32))
                parametri.DodajVrijednost("brg", 32-brd);
        }
    }
    

    
    private int pronadjiArgument(String preklopnik)
    {
        try
        {
            if(argumenti[1].equals(preklopnik))
                return Integer.parseInt(argumenti[2]);
            else if(argumenti.length>=5 && argumenti[3].equals(preklopnik))
                return Integer.parseInt(argumenti[4]);
            else return -1;
        }
        catch(NumberFormatException e)
        {
            System.err.println("Greška u parsiranju unešenih parametara za "+preklopnik);
            return -1;
        }
    }
    
    @Override
    public void KreirajIspisivac()
    {
        if(parametri==null)
            return;
        String datoteka = parametri.DajDatoteku("izlaz");
        ispisivanje = Ispisivanje.getInstance(new File(new File(datotekaParametara).getParent(), datoteka).getPath());
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
            podrucja = kreator.KreirajPodatke("PODACI", new File(new File(datotekaParametara).getParent(), datoteka).getPath());
        else podrucja = null;
    }
    
    @Override
    public void UcitajPodatkeUlica()
    {
        String datoteka = parametri.DajDatoteku("ulice");
        if(datoteka != null)
            ulice = kreator.KreirajPodatke("PODACI", new File(new File(datotekaParametara).getParent(), datoteka).getPath());
        else ulice = null;
    }
    @Override
    public void UcitajPodatkeSpremnika()
    {
        String datoteka = parametri.DajDatoteku("spremnici");
        if(datoteka != null)
            spremnici = kreator.KreirajPodatke("PODACI", new File(new File(datotekaParametara).getParent(), datoteka).getPath());
        else spremnici = null;
    }
    @Override
    public void UcitajPodatkeVozila()
    {
        String datoteka = parametri.DajDatoteku("vozila");
        if(datoteka != null)
            vozila = kreator.KreirajPodatke("PODACI", new File(new File(datotekaParametara).getParent(), datoteka).getPath());
        else vozila = null;
    }
    @Override
    public void UcitajPodatkeDispecera()
    {
        String datoteka = parametri.DajDatoteku("dispečer");
        if(datoteka != null)
            dispecer = kreator.KreirajPodatke("PODACI", new File(new File(datotekaParametara).getParent(), datoteka).getPath());
        else dispecer = null;
    }
    
}
