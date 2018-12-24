/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_podrska;

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
    public static RandomGenerator getInstance()
    {
        return INSTANCA;
    }
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
    public int dajMin(int a, int b)
    {
        if(a<b)
            return a;
        else
            return b;
    }
    public int dajMax(int a, int b)
    {
        if(a>b)
            return a;
        else
            return b;
    }
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
