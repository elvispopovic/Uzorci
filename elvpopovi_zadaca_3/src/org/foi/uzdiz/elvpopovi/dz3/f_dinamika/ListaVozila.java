/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozac;

/**
 * Lista vozila sa vlastitim iteratorom i metodama za manipulaciju
 * Ova lista kao objekt sadrži i mapu vozača
 * @author elvis
 */
public class ListaVozila 
{
    private ArrayList<VoziloSucelje> mjesta;
    HashMap<Integer,Vozac> mapaVozaca;
    /**
     * Kontriktor koji prima generičku listu podataka vozila, kreira vozila i puni 
     * internu listu referencama objekata vozila
     * @param vozila lista vozila u smislu podataka pročitanih iz datoteke
     */
    public ListaVozila(ArrayList<VoziloSucelje> vozila)
    {
        mjesta = new ArrayList<>();
        mapaVozaca = new HashMap<>();
        if(vozila!=null)
            for(VoziloSucelje v:vozila)
            {
                v.dajKontekst().PostaviPocetnoStanje();
                mjesta.add(v);
                LinkedHashMap<Integer,Vozac> mapa = v.DajMapuVozaca();
                for(Integer kljuc:mapa.keySet())
                    mapaVozaca.put(kljuc, mapa.get(kljuc));
            }
    }
    /**
     * Kreira praznu listu vozila
     */
    public ListaVozila()
    {
        mjesta = new ArrayList<>();
    }
    /**
     * Vraća mapu vozača
     * @return HashMap vozača
     */
    public HashMap<Integer,Vozac> DajMapuVozaca()
    {
        return mapaVozaca;
    }
    /**
     * Izvlači vozilo iz liste. Vozilo nakon toga neće biti u listi.
     * @param v indeks vozila u internoj listi
     * @return referenca objekta vozila
     */
    public VoziloSucelje IzdvojiVozilo(int v)
    {
        if(v>=mjesta.size())
            return null;
        VoziloSucelje vozilo = mjesta.get(v);
        mjesta.remove(v);
        return vozilo;
    }
    /**
     * Izdvaja vozilo iz liste prema njegovom identifikatoru
     * @param idVozila identifikator vozila
     * @return referenca objekta vozila
     */
    public VoziloSucelje IzdvojiVozilo(String idVozila)
    {
        for(int i = 0; i<mjesta.size(); i++)
        {
            VoziloSucelje vozilo = mjesta.get(i);
            if(vozilo.dajId().equals(idVozila))
            {
                mjesta.remove(i);
                return vozilo;
            }
        }
        return null;
    }
    /**
     * Unosi novo vozilo u listu
     * @param v referenca objeka vozilo
     */
    public void UbaciVozilo(VoziloSucelje v)
    {
        if(v!=null)
            mjesta.add(v);
    }
    /**
     * Getter vozila. Ne uklanja vozilo iz liste već samo daje njegovu referencu
     * @param idVozila identifikator vozila
     * @return referenca objekta vozila
     */
    public  VoziloSucelje DajVozilo(String idVozila)
    {
        for(int i = 0; i<mjesta.size(); i++)
        {
            VoziloSucelje vozilo = mjesta.get(i);
            if(vozilo.dajId().equals(idVozila))
                return vozilo;
        }
        return null;       
    }
    /**
     * vraća indeks vozila u listi
     * @param idVozila identifikator vozila
     * @return indeks
     */
    public int DajIndeksVozila(String idVozila)
    {
        for(int i = 0; i<mjesta.size(); i++)
        {
            VoziloSucelje vozilo = mjesta.get(i);
            if(vozilo.dajId().equals(idVozila))
                return i;
        }
        return -1;
    }    
    /**
     * vraća referencu vozila prema indeksu
     * @param mjesto indeks u listi vozila
     * @return referenca objekta vozilo
     */
    public VoziloSucelje DajVozilo(int mjesto)
    {
        if(mjesto>=0&&mjesto<mjesta.size())
            return mjesta.get(mjesto);
        else
            return null;
    }
    /**
     * briše listu vozila
     */
    public void ObrisiListu()
    {
        mjesta.clear();
    }
    /**
     * vraća veličinu liste vozila
     * @return veličina
     */
    public int Velicina()
    {
        return mjesta.size();
    }
    /**
     * vraća iterator tipa ListaVozila.Iterator. Iterator je interna klasa
     * @return iterator tipa ListaVozila.Iterator
     */
    public Iterator DajIterator()
    {
        return new Iterator(mjesta);
    }
    /**
     * Interna klasa Iterator
     */
    public class Iterator
    {
        private final ArrayList<VoziloSucelje> listaVozila;
        private int index;
        public Iterator(ArrayList<VoziloSucelje> listaVozila)
        {
            this.listaVozila = listaVozila;
        }
        /**
         * Vraća istinitost ako ima slijedećeg
         * @return true ako ima inače false
         */
        public boolean imaLiSlijedeceg()
        {
            if(index < listaVozila.size())
                return true;
            else
                return false;
        }
        /**
         * vraća slijedeći iterirani podatak, u ovom slučaju vozilo iz liste
         * @return podatak, konkretno: vozilo
         */
        public VoziloSucelje slijedeci()
        {
            if(this.imaLiSlijedeceg()==true)
                return listaVozila.get(index++);
            else return null;
        }
    }

    /* testiranje */
    public void Test()
    {
        VoziloSucelje v;
        System.out.println("Broj vozila: "+mjesta.size());
        v = mjesta.get(3);
        System.out.println("Vozilo: "+v.dajNaziv());
        mjesta.remove(3);
        System.out.println("Vozilo: "+v.dajNaziv());
        System.out.println("Vozilo: "+mjesta.get(3).dajNaziv());
        mjesta.add(3, v);
        System.out.println("Vozilo: "+mjesta.get(3).dajNaziv());
        System.out.println("Broj vozila: "+mjesta.size());
        
    }
    
}
