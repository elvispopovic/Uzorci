/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCView;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

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
            produkt = new InicijalizacijaPodatakaProduct(argumenti);
        else produkt = null;
    }
    @Override
    public InicijalizacijaAbstractBuilder UcitajParametre()
    {
        if(produkt!=null)
            produkt.UcitajParametre();
        return this;
    }
    public  InicijalizacijaAbstractBuilder InicijalizirajMVC()
    {
        if(produkt!=null)
            produkt.InicijalizirajMVC();
        return this;
    }
    @Override
    public InicijalizacijaAbstractBuilder KreirajIspisivac()
    {
        if(produkt!=null)
        {
            produkt.KreirajIspisivac();
            Ispisivanje ispisivac = Ispisivanje.getInstance();
            MVCView view = produkt.dajMVCview();
            if(view != null)
                view.InicijalizirajModel(ispisivac);
        }
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
