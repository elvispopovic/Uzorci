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
 * Klasa koja upravlja ispisivanjem. U DZ_1 i DZ_2 ispisuje na stari, nekontrolirani način
 * U DZ_3 ispisuje u bugger koji preuzima MVC
 * Singleton
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
    /**
     * Vraća instancu
     * @return instanca
     */
    public static Ispisivanje getInstance()
    {
        return INSTANCA;
    }
    /**
     * Ako su definirane kritične vrijednosti, npr. datoteka ispisa, postaje aktivana
     * @return vraćena vrijednost istinitosti je li ispisivač aktivan
     */
    public boolean JeLiAktivan()
    {
        return aktivan;
    }
    /**
     * Povezivanje sa MVC-om. Ispisivač preuzima ulogu Modela i prima MVC observer
     * @param observer referenca na MVC observer
     */
    public void PrikljuciMVC(MVCObserver observer)
    {
        this.observers.add(observer);
    }
    /**
     * Odjavljuje se određeni MVC observer
     * @param observer referenca na MVC observer
     */
    public void OdjaviMVC(MVCObserver observer)
    {
        this.observers.remove(observer);
    }
    /**
     * Obavještava MVC da je potrebno obnoviti prikaz
     * @param cekanje zastavica koja pokazuje treba li čekati odobrenje korisnika kod ispisivanja
     */
    @Override
    public void ObavijestiMVC(boolean cekanje)
    {
        for(MVCObserver o:observers)
            o.Osvjezi(cekanje);
    }
    /**
     * Preko ove metode MVC dobavlja sadržaj ispisivačkog međuspremnika
     * @return referenca na ispisivački međuspremnik
     */
    @Override
    public ArrayList<String> DohvatiPodatkeMVC()
    {
        return redciIspisa;
    }
    /**
     * Prima komandu MVC Controllera
     * @param komanda polje znakovnih nizova koje sadrži komandu
     */
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
    /**
     * Ispisivanje
     * @param ispis sadržaj koji treba ispisati
     */
    public void Ispisi(String ispis)
    {
        Ispisi(ispis,true);
    }
    /**
     * Ispisivanje sa zastavicom za novi red
     * @param ispis
     * @param novaLinija 
     */
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
    /**
     * Pomoćna metoda koja poziva MVC kompatibilnu metodu
     */
    public void prikaziRetke()
    {
        ObavijestiMVC(true);
    }
    /**
     * Stari način ispisivanja na ekran kada nije aktivan MVC i za kompatibilnost unazad u DZ_1 i DZ_2
     * @param ispis sadržaj koji treba ispisati
     * @param novaLinija zastavica pijelaza u novi red
     */
    public void ispisiNaEkran(String ispis, boolean novaLinija)
    {
        if(novaLinija)
            System.out.println(ispis);
        else
            System.out.print(ispis+" ");
    }
    /**
     * Ispisivanje u međuspremnik
     * @param ispis sadržaj koji treba ispisati
     */
    public void ispisiUString(String ispis)
    {
        StringWriter pisacString = new StringWriter();
        pisacString.write(ispis); 
        redciIspisa.add(pisacString.toString());
    }
    /**
     * Ispisivanje u datoteku
     * @param ispis sadržaj koji treba ispisati
     * @param novaLinija zastavica novog reda
     */
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
    /**
     * Kreira izlaznu datoteku
     * @return uspješnost
     */
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
