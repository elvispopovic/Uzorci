/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.c_podaci;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Podaci implements PodaciSucelje 
{
    private Ispisivanje ispis;
    private String[] shema;
    private ArrayList<String[]> listaPodataka;
    private boolean ucitano;
    public Podaci()
    {
        ispis = Ispisivanje.getInstance();
        listaPodataka = new ArrayList<String[]>();
        ucitano=false;
        brojacRedaka = 0;
    }
    @Override
    public boolean UcitajPodatke(String staza)
    {
        BufferedReader citac;
        if(ucitano == true)
            return true;
        try
        {
            citac = new BufferedReader(new InputStreamReader(new FileInputStream(staza), "UTF-8"));
            procitajPodatke(citac,staza);
            citac.close();
            ispis.Ispisi("Pročitano je "+listaPodataka.size()+" redaka podataka iz "+staza);
        }
        catch(FileNotFoundException e)
        {
            ispis.Ispisi("Datoteka parametara navedena u argumentu nije pronađena.");
            return false;
        }
        catch(IOException e)
        {
            ispis.Ispisi("Nije moguće pročitati podatke "+staza);
            return false;
        }
        return true;
    }
    @Override
    public int BrojZapisa()
    {
        int brojac=0;
        if(listaPodataka!=null&&listaPodataka.size()>0)
            brojac+=listaPodataka.size();
        return brojac;
    }
    @Override
    public String[] DajShemu()
    {
        if(shema!=null)
            return shema;
        else
            return null;
    }
    @Override
    public String[] DajPodatak(int redniBroj)
    {
        if(listaPodataka!=null&&listaPodataka.size()>redniBroj)
            return listaPodataka.get(redniBroj);
        else
            return null;
    }
    @Override
    public String DajNazivOtpada(int i)
    {
        return Parametri.getInstance().DajNazivOtpada(i);
    }
    private void procitajPodatke(BufferedReader citac, String staza)
    {
        String[] razdvojeno;
        shema = procitajRedak(citac, staza);
        for(int i=0; i<shema.length; i++)
            shema[i]=shema[i].split(Pattern.quote(":"))[0];
        //ovo koriste vozila (u vozilo), ulice (lica), spremnici (Spremnik)
        shema[0]=shema[0].substring(1); //prvi znak moze biti korumpiran
        while((razdvojeno = procitajRedak(citac, staza))!=null)
        {
            if((razdvojeno.length==shema.length)||((shema.length-razdvojeno.length)==1))
                listaPodataka.add(razdvojeno);
            else
                ispis.Ispisi("Prilikom čitanja podataka iz "+new File(staza).getName()+
                " otkriven neispravan redak "+brojacRedaka+".");
        } 
    }
    
    private String[] procitajRedak(BufferedReader citac, String staza)
    {
        String redak;
        try
        {
            do
            {
                brojacRedaka++;
                if((redak = citac.readLine())==null)
                    return null;
            }while (redak.replaceAll("\\p{Z}","").equals(""));
            String[] dijelovi = redak.split(Pattern.quote(";")); 
            return dijelovi;
        }
        catch(IOException e)
        {
            ispis.Ispisi("Iznimka prilikom čitanja retka datoteke "+new File(staza).getName() +
            " u retku "+brojacRedaka+" parametara.");
        }
        return null;
    }
    private int brojacRedaka;

    @Override
    public PodaciIteratorSucelje dajIterator()
    {
        return new PodaciIterator(listaPodataka);
    }

    @Override
    public String DajDatoteku(String naziv)
    {
        return "";
    }

    @Override
    public int DajVrijednost(String naziv)
    {
        return -1;
    }

    @Override
    public void DodajVrijednost(String naziv, int vrijednost)
    {
        throw new UnsupportedOperationException("Dodavanje novih zapisa podacima nije predviđeno.");
    }

}
