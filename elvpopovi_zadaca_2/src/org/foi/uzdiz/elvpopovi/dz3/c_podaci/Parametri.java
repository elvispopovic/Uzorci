/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.c_podaci;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 *
 * @author elvis
 */
public class Parametri implements PodaciSucelje 
{
    private static volatile Parametri INSTANCA;
    private ArrayList<String> naziviOtpada;
    private HashMap<String,String> datoteke;
    private LinkedHashMap<String,Integer> vrijednosti; 
    private boolean ucitano;
    private int brojacRedaka;
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
                    INSTANCA.naziviOtpada = new ArrayList<>();
                    INSTANCA.vrijednosti = new LinkedHashMap<>();
                    INSTANCA.ucitano = false;
                    INSTANCA.brojacRedaka = 0;
                }
            } 
        return INSTANCA; 
    }
    
    @Override
    public boolean UcitajPodatke(String staza)
    {
        if(ucitano == true)
            return true;
        try
        {
            BufferedReader citac = new BufferedReader(new InputStreamReader(new FileInputStream(staza), "UTF-8"));
            procitajDatoteke(citac);
            procitajNumericke(citac);
            generirajImenaVrstaotpada();
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
        if(i<0||i>=naziviOtpada.size())
            return "";
        else return naziviOtpada.get(i);
    }
    private void procitajDatoteke(BufferedReader citac)
    {
        String[] razdvojeno, provjera;
        while((razdvojeno = procitajRedak(citac))!=null)
            if(razdvojeno.length>1)
            {
                provjera = razdvojeno[1].split(Pattern.quote("."));
                if(provjera[provjera.length-1].equals("txt"))
                    datoteke.put(razdvojeno[0], razdvojeno[1]);
                else 
                {   //samo za prijelazni redak
                    try
                    {
                        int vrijednost = Integer.parseInt(razdvojeno[1]);
                        vrijednosti.put(razdvojeno[0], vrijednost);
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("Greška prilikom parsiranja prelaznog retka u parametrima.");
                    }
                    return;
                }
            }
            else
                System.err.print("Prilikom čitanja parametara pronađen je neispravni redak "+brojacRedaka+".");
    }
    private void procitajNumericke(BufferedReader citac)
    {
        String[] razdvojeno;
        int vrijednost;
        while((razdvojeno = procitajRedak(citac))!=null)
            if(razdvojeno.length>1)
            {
                try
                {
                    vrijednost = Integer.parseInt(razdvojeno[1]);
                    vrijednosti.put(razdvojeno[0], vrijednost);
                }
                catch(NumberFormatException e)
                {
                    System.err.println("Greška prilikom čitanja numeričkih vrijednosti u retku "+brojacRedaka+".");
                }
            }
            else
                System.err.print("Prilikom čitanja parametara pronađen je neispravni redak "+brojacRedaka+".");    
    }
    private String[] procitajRedak(BufferedReader citac)
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
            String[] dijelovi = redak.split(Pattern.quote(":")); 
            dijelovi[1] = dijelovi[1].replaceAll("\\p{Z}","");
            return dijelovi;
        }
        catch(Exception e)
        {
            System.err.println("Iznimka prilikom čitanja retka parametara u retku "+brojacRedaka);
        }
        return null;
    }
    private void generirajImenaVrstaotpada()
    {
        for(String key : vrijednosti.keySet())
        {
            if(key.contains("mali"))
            {
                String naziv = key.substring(4).toLowerCase();
                if(!naziv.equals("min"))
                    if(!naziviOtpada.contains(naziv))
                        naziviOtpada.add(naziv);
            }
        }
        //ispisiImenaOtpada();
    }
    
    @Override
    public PodaciIteratorSucelje dajIterator()
    {
        ArrayList<String[]> lista = new ArrayList<>();

        for(String key : datoteke.keySet())
        {
            String[] str = new String[2];
            str[0]=key;
            str[1]=datoteke.get(key);
            lista.add(str);
        }
        for(String key : vrijednosti.keySet())
        {
            String[] str = new String[2];
            str[0]=key;
            str[1]=String.valueOf(vrijednosti.get(key));
            lista.add(str);
        }
        return new PodaciIterator(lista);
    }
    
    /* za testiranje */
    private void ispisiImenaOtpada()
    {
        System.out.print("Nazivi vrsta otpada: ");
        for(String naziv : naziviOtpada)
            System.out.print(naziv+" ");
        System.out.println("");
    }
}
