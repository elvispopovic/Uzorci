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
public interface ProblemskiAbstractBuilder 
{
    ProblemskiAbstractBuilder KreirajPodrucja();
    ProblemskiAbstractBuilder KreirajUlice();
    ProblemskiAbstractBuilder KreirajRaspone();
    ProblemskiAbstractBuilder KreirajKorisnike();
    ProblemskiAbstractBuilder IspisiUlice();
    ProblemskiAbstractBuilder KreirajSpremnike();
    ProblemskiAbstractBuilder KreirajVozila();
    InicijalizacijaAbstractProduct DajPodatke();
    ProblemskiAbstractProduct DajProblemske();
}
