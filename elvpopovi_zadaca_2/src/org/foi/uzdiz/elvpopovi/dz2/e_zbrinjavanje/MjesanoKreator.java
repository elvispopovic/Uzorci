/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz2.b_buideri.InicijalizacijaPodatakaProduct;

/**
 *
 * @author elvis
 */
public class MjesanoKreator implements ZbrinjavanjeFactory
{

    @Override
    public Spremnik KreirajSpremnik(String[] shema, String[] zapis) 
    {
        return new Spremnik(4,shema, zapis);
    }


    @Override
    public Vozilo KreirajVozilo() 
    {
        return new Vozilo(4);
    }

    @Override
    public Raspon KreirajRaspon(InicijalizacijaPodatakaProduct podaci) 
    {
        return new Raspon(podaci, 4);
    }
    
}
