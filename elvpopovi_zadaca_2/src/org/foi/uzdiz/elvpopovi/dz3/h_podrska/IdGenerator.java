/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_podrska;

/**
 *
 * @author elvis
 */
public class IdGenerator 
{
    private static volatile IdGenerator  INSTANCA;
    private Ispisivanje ispis;
    private int id;
    private IdGenerator(){}
    public static IdGenerator  getInstance()
    {
        if (INSTANCA == null) 
            synchronized(IdGenerator .class) 
            { 
                if (INSTANCA == null) 
                {
                    INSTANCA = new IdGenerator (); 
                    INSTANCA.id = 0;
                    INSTANCA.ispis = Ispisivanje.getInstance();
                    INSTANCA.ispis.Ispisi("Kreiran je generator identifikatora");
                }
            } 
        return INSTANCA; 
    }
    public int dajId()
    {
        return ++id;
    }
}
