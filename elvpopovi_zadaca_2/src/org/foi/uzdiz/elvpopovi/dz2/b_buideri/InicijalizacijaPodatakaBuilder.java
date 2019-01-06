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
public class InicijalizacijaPodatakaBuilder implements InicijalizacijaAbstractBuilder
{
    public InicijalizacijaPodatakaBuilder(String[] argumenti) 
    {
        this.argumenti = argumenti;
        if(argumenti.length>0)
            produkt = new InicijalizacijaPodatakaProduct(argumenti[0]);
        else produkt = null;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajParametre()
    {
        if(produkt!=null)
            produkt.UcitajParametre();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder KreirajIspisivac()
    {
        if(produkt!=null)
            produkt.KreirajIspisivac();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder KreirajIdGenerator()
    {
        if(produkt!=null)
            produkt.KreirajIdGenerator();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder KreirajRandomGenerator()
    {
        if(produkt!=null)
            produkt.KreirajRandomGenerator();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajPodrucja()
    {
        if(produkt!=null)
            produkt.UcitajPodatkePodrucja();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajUlice()
    {
        if(produkt!=null)
            produkt.UcitajPodatkeUlica();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajSpremnike()
    {
        if(produkt!=null)
            produkt.UcitajPodatkeSpremnika();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajVozila()
    {
        if(produkt!=null)
            produkt.UcitajPodatkeVozila();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajDispecera()
    {
        if(produkt!=null)
            produkt.UcitajPodatkeDispecera();
        return this;
    }
    @Override
    public InicijalizacijaAbstractProduct DajProdukt()
    {
        return produkt;
    }
    
    private final InicijalizacijaAbstractProduct produkt;
    private final String[] argumenti;
}
