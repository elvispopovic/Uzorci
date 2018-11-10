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
public interface StatistikaSucelje 
{
    void DodajStaklo(float kolicina);
    void DodajPapir(float kolicina);
    void DodajMetal(float kolicina);
    void DodajBio(float kolicina);
    void DodajMjesano(float kolicina);
    
    float[] UkupneKolicine();
}
