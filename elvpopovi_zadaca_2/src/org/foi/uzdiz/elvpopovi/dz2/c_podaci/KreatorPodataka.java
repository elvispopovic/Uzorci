/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.c_podaci;


/**
 *
 * @author elvis
 */
public class KreatorPodataka implements KreatorPodatakaSucelje
{
    @Override
    public PodaciSucelje KreirajPodatke(String tip, String staza) 
    {
        if(tip.equals("PARAMETRI"))
        {
            PodaciSucelje p = Parametri.getInstance();
            if(false==p.UcitajPodatke(staza))
                return null;
            return p;
        }  
        else if(tip.equals("PODACI"))
        {
            PodaciSucelje p = new Podaci();
            if(false==p.UcitajPodatke(staza))
                return null;
            return p;
        }
        else
            return null;
    }
}
