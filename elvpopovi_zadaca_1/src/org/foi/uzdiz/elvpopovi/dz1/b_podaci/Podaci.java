/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.b_podaci;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;


import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Podaci implements PodaciSucelje 
{
    private Ispisivanje ispis;
    private String[] shema;
    private ArrayList<String[]> listaPodataka;
    public Podaci()
    {
        ispis = Ispisivanje.getInstance();
        listaPodataka = new ArrayList<String[]>();
    }
    @Override
    public boolean UcitajPodatke(String staza)
    {
        BufferedReader citac;
        String[] razdvojeno;
        int i;
        try
        {
            citac = new BufferedReader(new InputStreamReader(new FileInputStream(staza), "UTF-8"));
            brojacRedaka=0;
            shema = procitajRedak(citac, staza);
            do
            {
                razdvojeno = procitajRedak(citac, staza);
                if(razdvojeno!=null&&(razdvojeno.length==shema.length))
                { 
                    listaPodataka.add(razdvojeno);
                }
                else if(razdvojeno!=null)
                {
                    ispis.Ispisi("Prilikom čitanja podataka iz "+new File(staza).getName()+" otkriven neispravan redak "+brojacRedaka+".");
                }   
            }while (razdvojeno != null);
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
        catch(Exception e)
        {
            ispis.Ispisi("Greška prilikom čitanja podataka "+staza);
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
    public String[] DajShemu()
    {
        if(shema!=null)
            return shema;
        else
            return null;
    }
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
        switch(i)
        {
            case 0: return "Staklo";
            case 1: return "Papir";
            case 2: return "Metal";
            case 3: return "Bio";
            case 4: return "Mješano";
            default: return "";
        }
    }
    private String[] procitajRedak(BufferedReader citac, String staza)
    {
        String redak;
        String[] dijelovi=null;
        try
        {
            do
            {
                brojacRedaka++;
                if((redak = citac.readLine())==null)
                    return null;
            }while (redak.replaceAll("\\p{Z}","").equals(""));
            dijelovi = redak.split(Pattern.quote(";")); 
            for(int i=1; i<dijelovi.length; i++)
                dijelovi[i] = dijelovi[i].replaceAll("\\p{Z}","");
            return dijelovi;
        }
        catch(IOException e)
        {
            ispis.Ispisi("Iznimka prilikom čitanja retka datoteke "+new File(staza).getName() +" u retku "+brojacRedaka+" parametara.");
        }
        return null;
    }
    private int brojacRedaka;
}
