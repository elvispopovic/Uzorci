/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.a_glavna;

/**
 *
 * @author elvis
 */
public interface InicijalizacijaAbstractBuilder 
{
    InicijalizacijaAbstractBuilder UcitajParametre();
    InicijalizacijaAbstractBuilder KreirajIspisivac();
    InicijalizacijaAbstractBuilder KreirajIdGenerator();
    InicijalizacijaAbstractBuilder KreirajRandomGenerator();
    InicijalizacijaAbstractBuilder UcitajUlice();
    InicijalizacijaAbstractBuilder UcitajSpremnike();
    InicijalizacijaAbstractBuilder UcitajVozila();
    InicijalizacijaAbstractProduct DajProdukt();
}
