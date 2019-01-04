/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.j_podrska;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author elvis
 */
public class RandomGenerator 
{
    private static volatile RandomGenerator  INSTANCA;
    private Ispisivanje ispis;
    private RandomGenerator () {}
    /**
     * vraća jedinstveni (singleton) instancu klase RandomGenerator
     * @param sjeme sjeme generatora
     * @return vraća jedinstveni (singleton) instancu klase RandomGenerator
     */
    public static RandomGenerator  getInstance(long sjeme) 
    { 
        RandomGenerator INSTANCA_LOC = RandomGenerator.INSTANCA;
        if (INSTANCA_LOC == null) 
            synchronized(RandomGenerator .class) 
            { 
            INSTANCA_LOC = RandomGenerator.INSTANCA;
                if (INSTANCA_LOC == null) 
                {
                    RandomGenerator.INSTANCA = INSTANCA_LOC = new RandomGenerator (); 
                    INSTANCA_LOC.random = new Random(sjeme);
                    INSTANCA_LOC.ispis = Ispisivanje.getInstance();
                    INSTANCA_LOC.ispis.Ispisi("Kreiran je random generator sa sjemenom "+sjeme);
                }
            } 
        return INSTANCA_LOC; 
    }
    /**
     * vraća jedinstveni (singleton) instancu klase RandomGenerator
     * @return vraća jedinstveni (singleton) instancu klase RandomGenerator
     */
    public static RandomGenerator getInstance()
    {
        return INSTANCA;
    }
    /**
     * vraća slučajni cijeli broj od param_od do param_do
     * @param _od donja granica
     * @param _do gornja granica
     * @return vraćeni slučajni broj
     */
    public int dajRandomInt(int _od, int _do)
    {
        int tmp;
        if(_od==_do)
            return _od;
        else if(_od>_do)
        {
            tmp=_od;
            _od=_do;
            _do=tmp;
        }
        tmp=_do-_od;
        return _od+(random.nextInt(tmp+1));
    }
    /**
     * vraća slučajni veliki cijeli broj od param_od do param_do
     * @param _od donja granica
     * @param _do gornja granica
     * @return vraćeni slučajni broj
     */
    public long dajRandomLong(long _od, long _do)
    {
        long tmp;
        if(_od==_do)
            return _od;
        else if(_od>_do)
        {
            tmp=_od;
            _od=_do;
            _do=tmp;
        }
        tmp=_do-_od;
        return _od+(Math.abs(random.nextLong())%(tmp+1));
    }
    /**
     * vraća slučajni broj dvostruke preciznosti od param_od do param_do
     * @param _od donja granica
     * @param _do gornja granica
     * @return vraćeni slučajni broj
     */
    public double dajRandomDouble(double _od, double _do, int brojDecimala)
    {
        double tmp;
        if(_od==_do)
            return _od;
        else if(_od>_do)
        {
            tmp=_od;
            _od=_do;
            _do=tmp;
        }
        tmp=_do-_od;
        if(brojDecimala<0)
            brojDecimala=0;
        else if(brojDecimala>7)
            brojDecimala=7;
        return round(_od+random.nextFloat()*tmp,brojDecimala);
    }
    /**
     * vraća slučajni broj jednostruke preciznosti od param_od do param_do
     * @param _od donja granica
     * @param _do gornja granica
     * @return vraćeni slučajni broj
     */
    public float dajRandomFloat(float _od, float _do, int brojDecimala)
    {
        float tmp;
        if(_od==_do)
            return _od;
        else if(_od>_do)
        {
            tmp=_od;
            _od=_do;
            _do=tmp;
        }
        tmp=_do-_od;
        if(brojDecimala<0)
            brojDecimala=0;
        else if(brojDecimala>7)
            brojDecimala=7;
        return round(_od+random.nextFloat()*tmp,brojDecimala);
    }
    /**
     * vraća minimum od dva broja unešena kao argumenti
     * @param a prvi argument
     * @param b drugi argument
     * @return vraćeni minimum argumenata
     */
    public int dajMin(int a, int b)
    {
        if(a<b)
            return a;
        else
            return b;
    }
    /**
     * vraća maksimum od dva broja unešena kao argumenti
     * @param a prvi argument
     * @param b drugi argument
     * @return vraćeni maksimum argumenata
     */
    public int dajMax(int a, int b)
    {
        if(a>b)
            return a;
        else
            return b;
    }
    /**
     * Zaokružuje decimalni broj jednostruke preciznosti na određeni broj decimala
     * @param a broj koji se zaokružuje
     * @param brojDecimala broj decimala na koji se broj zaokružuje
     * @return vraćeni zaokruženi decimalni broj
     */
    public float round(float a, int brojDecimala)
    {
        if(brojDecimala<0)
            brojDecimala=0;
        else if(brojDecimala>7)
            brojDecimala=7;
        BigDecimal bd = new BigDecimal(a);
        bd = bd.setScale(brojDecimala, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    /**
     * Zaokružuje decimalni broj dvostruke preciznosti na određeni broj decimala
     * @param a broj koji se zaokružuje
     * @param brojDecimala broj decimala na koji se broj zaokružuje
     * @return vraćeni zaokruženi decimalni broj
     */
    public double round(double a, int brojDecimala)
    {
        if(brojDecimala<0)
            brojDecimala=0;
        else if(brojDecimala>7)
            brojDecimala=7;
        BigDecimal bd = new BigDecimal(a);
        bd = bd.setScale(brojDecimala, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private Random random;
}
