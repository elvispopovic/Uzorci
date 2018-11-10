/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.b_podaci;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Parametri implements PodaciSucelje 
{
    private static volatile Parametri INSTANCA;
    private Ispisivanje ispis;
    private HashMap<String,String> datoteke;
    private HashMap<String,Integer> vrijednosti; 
    private boolean ucitano;
    private Parametri() {}
    public static Parametri getInstance() 
    { 
        if (INSTANCA == null) 
            synchronized(Parametri.class) 
            { 
                if (INSTANCA == null) 
                {
                    INSTANCA = new Parametri(); 
                    INSTANCA.datoteke = new HashMap<>();
                    INSTANCA.vrijednosti = new HashMap<>();
                    INSTANCA.ucitano = false;
                }
            } 
        return INSTANCA; 
    }
    

    @Override
    public boolean UcitajPodatke(String staza)
    {
        BufferedReader citac;
        String[] razdvojeno;
        int j;
        if(ucitano == true)
            return true;
        try
        {
            citac = new BufferedReader(new InputStreamReader(new FileInputStream(staza), "UTF-8"));
            brojacRedaka=0;
            for(int i=0; i<4; i++)
            {
                razdvojeno = procitajRedak(citac);
                if(razdvojeno.length>1)
                {
                    String[] provjera = razdvojeno[1].split(Pattern.quote("."));
                    if(provjera[provjera.length-1].equals("txt"))
                        datoteke.put(new String(razdvojeno[0]),new String(razdvojeno[1]));
                    else
                    {
                        System.err.print("Prilikom čitanja parametara pronađen je neispravni redak "+brojacRedaka+".");
                    }
                }
                else
                {
                    System.err.print("Prilikom čitanja parametara pronađen je neispravni redak "+brojacRedaka+".");
                }
            }
            int i=0;
            do
            {
                int vrijednost;
                razdvojeno = procitajRedak(citac);
                i++;
                if(razdvojeno!=null&&razdvojeno.length>1)
                {
                    try
                    {
                        vrijednost = Integer.parseInt(razdvojeno[1]);
                        vrijednosti.put(razdvojeno[0], vrijednost);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Poglešan zapis među parametrima i retku "+i);
                    }
                }
                else if(razdvojeno!=null)
                {
                    System.err.println("Prilikom čitanja parametara  otkriven neispravan redak"+brojacRedaka+".");
                }
            }while (razdvojeno != null);
            citac.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Datoteka parametara navedena u argumentu nije pronađena.");
            return false;
        }
        catch(IOException e)
        {
            System.err.println("Nije moguće pročitati datoteku "+staza);
            return false;
        }
        catch(NumberFormatException e)
        {
            System.err.println("Greška prilikom čitanja numeričkih vrijednosti. "+staza);
            return false;
        }

        return true;
    }
    @Override
    public int BrojZapisa()
    {
        int brojac=0;
        if(datoteke!=null&&datoteke.size()>0)
            brojac+=datoteke.size();
        if(vrijednosti!=null&&vrijednosti.size()>0)
            brojac+=vrijednosti.size();
        return brojac;
    }
    public String DajDatoteku(String naziv) 
    {
        return datoteke.get(naziv);
    }
    public int DajVrijednost(String naziv)
    {
        Integer vrijednost;
        if((vrijednost=vrijednosti.get(naziv))==null)
            return -1;
        else
            return vrijednost;
    }
    @Override
    public String DajNazivOtpada(int i)
    {
        switch(i)
        {
            case 0: return "staklo";
            case 1: return "papir";
            case 2: return "metal";
            case 3: return "bio";
            case 4: return "mješano";
            default: return "";
        }
    }
    
    private String[] procitajRedak(BufferedReader citac)
    {
        String redak;
        String[] dijelovi=null;
        brojacRedaka = 0;
        try
        {
            do
            {
                brojacRedaka++;
                if((redak = citac.readLine())==null)
                    return null;
            }while (redak.replaceAll("\\p{Z}","").equals(""));
            dijelovi = redak.split(Pattern.quote(":")); 
            dijelovi[1] = dijelovi[1].replaceAll("\\p{Z}","");
            return dijelovi;
        }
        catch(Exception e)
        {
            System.err.println("Iznimka prilikom čitanja retka parametara u retku "+brojacRedaka);
        }
        return null;
    }
    private int brojacRedaka;
}
