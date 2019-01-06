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
public interface ZbrinjavanjeFactory 
{
    Raspon KreirajRaspon(InicijalizacijaPodatakaProduct podaci);
    Spremnik KreirajSpremnik(String[] shema, String[] zapis);
    Vozilo KreirajVozilo();
}
