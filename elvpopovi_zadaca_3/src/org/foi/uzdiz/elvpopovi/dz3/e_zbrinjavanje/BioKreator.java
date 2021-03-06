/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaPodatakaProduct;

/**
 *
 * @author elvis
 */
public class BioKreator implements ZbrinjavanjeFactory
{

    @Override
    public Spremnik KreirajSpremnik(String[] shema, String[] zapis) 
    {
        return new Spremnik(3,shema, zapis);
    }

    @Override
    public VoziloSucelje KreirajVozilo() 
    {
        return new Vozilo(3);
    }

    @Override
    public Raspon KreirajRaspon(InicijalizacijaPodatakaProduct podaci) 
    {
        return new Raspon(podaci, 3);
    }
    
}
