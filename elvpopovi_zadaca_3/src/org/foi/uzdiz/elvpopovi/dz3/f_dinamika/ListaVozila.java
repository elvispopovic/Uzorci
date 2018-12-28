/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public class ListaVozila 
{
    public ListaVozila(ArrayList<VoziloSucelje> vozila)
    {
        mjesta = new ArrayList<VoziloSucelje>();
        if(vozila!=null)
            for(VoziloSucelje v:vozila)
            {
                v.dajKontekst().PostaviPocetnoStanje();
                mjesta.add(v);
            }
    }
    public ListaVozila()
    {
        mjesta = new ArrayList<>();
    }
    public VoziloSucelje IzdvojiVozilo(int v)
    {
        if(v>=mjesta.size())
            return null;
        VoziloSucelje vozilo = mjesta.get(v);
        mjesta.remove(v);
        return vozilo;
    }
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
    public void UbaciVozilo(VoziloSucelje v)
    {
        if(v!=null)
            mjesta.add(v);
    }
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
    public VoziloSucelje DajVozilo(int mjesto)
    {
        if(mjesto>=0&&mjesto<mjesta.size())
            return mjesta.get(mjesto);
        else
            return null;
    }
    
    public void ObrisiListu()
    {
        mjesta.clear();
    }

    public int Velicina()
    {
        return mjesta.size();
    }
    
    public Iterator DajIterator()
    {
        return new Iterator(mjesta);
    }
    
    public class Iterator
    {
        private final ArrayList<VoziloSucelje> listaVozila;
        private int index;
        public Iterator(ArrayList<VoziloSucelje> listaVozila)
        {
            this.listaVozila = listaVozila;
        }
        public boolean imaLiSlijedeceg()
        {
            if(index < listaVozila.size())
                return true;
            else
                return false;
        }
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
    private ArrayList<VoziloSucelje> mjesta;
}
