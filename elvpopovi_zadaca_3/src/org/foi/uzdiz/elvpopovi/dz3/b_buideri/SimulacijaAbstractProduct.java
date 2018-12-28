/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.g_upravljanje.Dispecer;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCView;

/**
 *
 * @author elvis
 */
public interface SimulacijaAbstractProduct
{
    SimulacijaSucelje DajSimulacija();
    Dispecer DajDispecer();
    Statistika DajStatistika();
    void KreirajDispecera();
    void KreirajStatistiku();
    void KreirajSimulaciju();
    void PokreniSimulaciju();
    void IspisiStatistiku();
}
