/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;

import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaAbstractProduct;

/**
 *
 * @author elvis
 */
public interface ProblemskiAbstractBuilder 
{
    ProblemskiAbstractBuilder KreirajUlice();
    ProblemskiAbstractBuilder KreirajKorisnike();
    ProblemskiAbstractBuilder KreirajSpremnike();
    ProblemskiAbstractBuilder KreirajVozila();
    InicijalizacijaAbstractProduct DajPodatke();
    ProblemskiAbstractProduct DajProblemske();
}
