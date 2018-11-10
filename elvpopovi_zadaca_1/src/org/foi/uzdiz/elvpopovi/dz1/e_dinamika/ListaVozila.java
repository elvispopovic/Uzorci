/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.e_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Vozilo;

/**
 *
 * @author elvis
 */
public class ListaVozila 
{
    public ListaVozila(ArrayList<Vozilo> vozila)
    {
        mjesta = new ArrayList<Vozilo>();
        if(vozila!=null)
            for(Vozilo v:vozila)
            {
                mjesta.add(v);
            }
    }
    public ListaVozila()
    {
        mjesta = new ArrayList<Vozilo>();
    }
    public Vozilo IzdvojiVozilo(int v)
    {
        if(v>=mjesta.size())
            return null;
        Vozilo vozilo = mjesta.get(v);
        mjesta.remove(v);
        return vozilo;
    }
    public void UbaciVozilo(Vozilo v)
    {
        mjesta.add(v);
    }
    public Vozilo DajVozilo(int mjesto)
    {
        if(mjesto>=0&&mjesto<mjesta.size())
            return mjesta.get(mjesto);
        else
            return null;
    }
    public int Velicina()
    {
        return mjesta.size();
    }
    /* testiranje */
    public void Test()
    {
        Vozilo v;
        System.out.println("Broj vozila: "+mjesta.size());
        v = mjesta.get(3);
        System.out.println("Vozilo: "+v.getNaziv());
        mjesta.remove(3);
        System.out.println("Vozilo: "+v.getNaziv());
        System.out.println("Vozilo: "+mjesta.get(3).getNaziv());
        mjesta.add(3, v);
        System.out.println("Vozilo: "+mjesta.get(3).getNaziv());
        System.out.println("Broj vozila: "+mjesta.size());
        
    }
    private ArrayList<Vozilo> mjesta;
}
