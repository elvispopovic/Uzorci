/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.f_podrska;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        if (INSTANCA == null) 
            synchronized(RandomGenerator .class) 
            { 
                if (INSTANCA == null) 
                {
                    INSTANCA = new RandomGenerator (); 
                    INSTANCA.random = new Random(sjeme);
                    INSTANCA.ispis = Ispisivanje.getInstance();
                    INSTANCA.ispis.Ispisi("Kreiran je random generator sa sjemenom "+sjeme);
                }
            } 
        return INSTANCA; 
    }
    public static RandomGenerator getInstance()
    {
        return INSTANCA;
    }
    public int getRandomInt(int _od, int _do)
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
    public long getRandomLong(long _od, long _do)
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
    public double getRandomDouble(double _od, double _do, int brojDecimala)
    {
        double tmp, potencija;
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
    public float getRandomFloat(float _od, float _do, int brojDecimala)
    {
        float tmp, potencija;
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
    public int getMin(int a, int b)
    {
        if(a<b)
            return a;
        else
            return b;
    }
    public int getMax(int a, int b)
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
