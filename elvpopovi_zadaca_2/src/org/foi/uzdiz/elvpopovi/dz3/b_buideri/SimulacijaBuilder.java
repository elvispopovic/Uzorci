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
public class SimulacijaBuilder implements SimulacijaAbstractBuilder
{
    private final SimulacijaAbstractProduct produkt;
    
    
    public SimulacijaBuilder(InicijalizacijaAbstractProduct podaci, ProblemskiAbstractProduct problemski) 
    {
        produkt = new SimulacijaProduct(podaci, problemski);
    }
    
    @Override
    public SimulacijaAbstractBuilder KreirajDispecera()
    {
        if(produkt!=null)
            produkt.KreirajDispecera();
        return this;
    }

    @Override
    public SimulacijaAbstractBuilder KreirajStatistiku()
    {
        if(produkt!=null)
            produkt.KreirajStatistiku();
        return this;
    }

    @Override
    public SimulacijaAbstractBuilder KreirajSimulaciju()
    {
        if(produkt!=null)
            produkt.KreirajSimulaciju();
        return this;
    }
    @Override
    public SimulacijaAbstractBuilder PokreniSimulaciju()
    {
        if(produkt!=null)
            produkt.PokreniSimulaciju();
        return this;
    }
    @Override
    public SimulacijaAbstractBuilder IspisiStatistiku()
    {
        if(produkt!=null)
            produkt.IspisiStatistiku();
        return this;
    }
}
