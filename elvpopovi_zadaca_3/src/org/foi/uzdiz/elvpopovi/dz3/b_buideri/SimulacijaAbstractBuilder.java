/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCView;

/**
 *
 * @author elvis
 */
public interface SimulacijaAbstractBuilder 
{
    SimulacijaAbstractBuilder KreirajDispecera();
    SimulacijaAbstractBuilder KreirajStatistiku();
    SimulacijaAbstractBuilder KreirajSimulaciju();
    SimulacijaAbstractBuilder PokreniSimulaciju();
    SimulacijaAbstractBuilder IspisiStatistiku();
}
