/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.c_podaci;

import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public class PodaciIterator implements PodaciIteratorSucelje
{

    public PodaciIterator(ArrayList<String[]> listaPodataka)
    {
        index = 0;
        this.listaPodataka = listaPodataka;
    }
    private int index;
    private final ArrayList<String[]> listaPodataka;
    @Override
    public boolean imaLiSlijedeceg()
    {
        if(index < listaPodataka.size())
            return true;
        else
            return false;
    }

    @Override
    public String[] slijedeci()
    {
        if(this.imaLiSlijedeceg()==true)
            return listaPodataka.get(index++);
        else return null;
    }
    
}
