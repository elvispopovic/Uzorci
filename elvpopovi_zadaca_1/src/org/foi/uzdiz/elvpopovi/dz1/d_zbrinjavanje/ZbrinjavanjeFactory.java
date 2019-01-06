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
public interface ZbrinjavanjeFactory 
{
    Raspon KreirajRaspon(InicijalizacijaPodatakaProduct podaci);
    Spremnik KreirajSpremnik(int tip, int[] naBroj, int nosivost);
    Vozilo KreirajVozilo();
}
