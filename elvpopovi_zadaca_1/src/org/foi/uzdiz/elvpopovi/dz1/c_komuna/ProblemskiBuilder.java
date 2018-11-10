/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;

import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaPodatakaProduct;

/**
 *
 * @author elvis
 */
public class ProblemskiBuilder implements ProblemskiAbstractBuilder 
{

    public ProblemskiBuilder(InicijalizacijaPodatakaProduct podaci) 
    {
        this.podaci = podaci;
        product = new ProblemskiProduct(podaci);
    }
    public ProblemskiAbstractBuilder KreirajUlice()
    {
        if(podaci!=null)
           product.KreirajUlice();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder KreirajKorisnike() 
    {
        if(podaci!=null)
           product.KreirajKorisnike();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder KreirajSpremnike() 
    {
        if(podaci!=null)
           product.KreirajSpremnike();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder KreirajVozila() 
    {
        if(podaci!=null)
           product.KreirajVozila();
        return this;
    }
    public InicijalizacijaAbstractProduct DajPodatke()
    {
        return podaci;
    }
    public ProblemskiAbstractProduct DajProblemske() 
    {
        return product;
    }
    
    private ProblemskiAbstractProduct product;
    private InicijalizacijaPodatakaProduct podaci;
}
