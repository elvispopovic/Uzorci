/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.b_buideri;

/**
 *
 * @author elvis
 */
public class ProblemskiBuilder implements ProblemskiAbstractBuilder 
{
    public ProblemskiBuilder(InicijalizacijaPodatakaProduct podaci) 
    {
        this.podaci = podaci;
        if(podaci.dajPodrucja()==null)
            product = new ProblemskiProductBezPodrucja(podaci);
        else
            product = new ProblemskiProductPodrucja(podaci);
    }
    @Override
    public ProblemskiAbstractBuilder KreirajPodrucja()
    {
        if(podaci.dajPodrucja()!=null)
            product.KreirajPodrucja();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder KreirajUlice()
    {
        if(podaci!=null)
           product.KreirajUlice();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder KreirajRaspone()
    {
        if(podaci!=null)
           product.KreirajRaspone();
        return this;
    }
    @Override
    public ProblemskiAbstractBuilder IspisiUlice()
    {
        if(podaci!=null)
           product.IspisiUlice();
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
