/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.j_podrska;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCObserver;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;

/**
 *
 * @author elvis
 */
public class Ispisivanje  implements MVCModelSucelje
{

    private static volatile Ispisivanje INSTANCA;
    private ArrayList<MVCObserver> observers;
    private String staza;
    private BufferedWriter pisacDatoteka;
    private ArrayList<String> redciIspisa;
    private boolean aktivan;
    private Ispisivanje() {}
    public static Ispisivanje getInstance(String staza) 
    { 
        if (INSTANCA == null) 
            synchronized(Ispisivanje.class) 
            { 
                if (INSTANCA == null) 
                {
                    INSTANCA = new Ispisivanje(); 
                    INSTANCA.staza = staza;
                    INSTANCA.aktivan=true;
                    INSTANCA.observers = new ArrayList<>();
                    INSTANCA.redciIspisa = new ArrayList<>();
                    if(INSTANCA.kreirajIzlaz()==false)
                        INSTANCA.aktivan=false;
                    if(INSTANCA.aktivan==true)
                        INSTANCA.Ispisi("Kreiran je ispisivač.");
                }
            } 
        return INSTANCA; 
    }
    public static Ispisivanje getInstance()
    {
        return INSTANCA;
    }
    
    public boolean isAktivan()
    {
        return aktivan;
    }
    
    public void PrikljuciMVC(MVCObserver observer)
    {
        this.observers.add(observer);
    }
    public void OdjaviMVC(MVCObserver observer)
    {
        this.observers.remove(observer);
    }
    @Override
    public void ObavijestiMVC(boolean cekanje)
    {
        for(MVCObserver o:observers)
            o.Osvjezi(cekanje);
    }

    @Override
    public ArrayList<String> DohvatiPodatkeMVC()
    {
        return redciIspisa;
    }

    @Override
    public void KomandaMVC(String[] komanda)
    {
        switch(komanda[0])
        {
            case "OBRISI_PODATKE": 
                redciIspisa.clear();
                break;
        }
    }

    public void Ispisi(String ispis)
    {
        Ispisi(ispis,true);
    }
    public void Ispisi(String ispis, boolean novaLinija)
    {
        Parametri parametri = Parametri.getInstance();
        int brg = parametri.DajVrijednost("brg");
        int brd = parametri.DajVrijednost("brd");
        if(brg==-1||brd==-1)
            ispisiNaEkran(ispis, novaLinija);
        else 
            ispisiUString(ispis); 
        if(aktivan)
            ispisiUDatoteku(ispis, novaLinija);
    }
    
    public void prikaziRetke()
    {
        ObavijestiMVC(true);
    }
 
    public void ispisiNaEkran(String ispis, boolean novaLinija)
    {
        if(novaLinija)
            System.out.println(ispis);
        else
            System.out.print(ispis+" ");
    }
    
    public void ispisiUString(String ispis)
    {
        StringWriter pisacString = new StringWriter();
        pisacString.write(ispis); 
        redciIspisa.add(pisacString.toString());
    }
    
    public void ispisiUDatoteku(String ispis, boolean novaLinija)
    {
        try
        {
            pisacDatoteka = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staza, true), "UTF-8"));
            if(novaLinija)
                pisacDatoteka.write(ispis+System.lineSeparator());
            else
                pisacDatoteka.write(ispis+" ");
            pisacDatoteka.close();
        }
        catch(IOException e)
        {
            System.err.println("Greška prilikom pokušaja upisivanja u izlaznu datoteku.");
        }
    }
    
    private boolean kreirajIzlaz()
    {
        File file = new File(staza);
        try
        {
            if (file.exists())
                file.delete();
            file.createNewFile();
        }
        catch(IOException e)
        {
            System.err.println("Nije moguće kreirati datoteku za ispis izlaza.");
            return false;
        }
        return true;
    }


}
