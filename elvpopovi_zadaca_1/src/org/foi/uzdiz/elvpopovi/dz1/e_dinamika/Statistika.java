/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.e_dinamika;

/**
 *
 * @author elvis
 */
public class Statistika implements StatistikaSucelje
{
    private float[] podaci=null;
    Statistika()
    {
        podaci = new float[5];
        for(int i=0; i<5; i++)
            podaci[i]=(float) 0.0;
    }
    @Override
    public void DodajStaklo(float kolicina)
    {
        podaci[0]+=kolicina;
    }
    @Override
    public void DodajPapir(float kolicina)
    {
        podaci[1]+=kolicina;
    }
    @Override
    public void DodajMetal(float kolicina)
    {
        podaci[2]+=kolicina;
    }
    @Override
    public void DodajBio(float kolicina)
    {
        podaci[3]+=kolicina;
    }
    @Override
    public void DodajMjesano(float kolicina)
    {
        podaci[4]+=kolicina;
    }

    @Override
    public float[] UkupneKolicine()
    {
        return podaci;
    }
    
}
