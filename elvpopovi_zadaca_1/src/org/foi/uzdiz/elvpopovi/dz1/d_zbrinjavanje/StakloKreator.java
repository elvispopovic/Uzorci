/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaPodatakaProduct;

/**
 *
 * @author elvis
 */
public class StakloKreator implements ZbrinjavanjeFactory
{
    @Override
    public Spremnik KreirajSpremnik(int tip, int[] naBroj, int nosivost) 
    {
        return new Spremnik(0,tip, naBroj, nosivost);
    }

    @Override
    public Vozilo KreirajVozilo() 
    {
        return new Vozilo(0);
    }
    @Override
    public Raspon KreirajRaspon(InicijalizacijaPodatakaProduct podaci) 
    {
        return new Raspon(podaci, 0);
    }
}
