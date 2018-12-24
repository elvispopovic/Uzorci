/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

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
