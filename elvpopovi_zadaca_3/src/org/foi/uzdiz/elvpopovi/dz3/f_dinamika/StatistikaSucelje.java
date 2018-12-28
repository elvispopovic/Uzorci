/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;

/**
 *
 * @author elvis
 */
public interface StatistikaSucelje 
{
    SimulacijaAbstractProduct DajSimulacijske();
    float[] DajPodatke();
    void DodajStaklo(float kolicina);
    void DodajPapir(float kolicina);
    void DodajMetal(float kolicina);
    void DodajBio(float kolicina);
    void DodajMjesano(float kolicina);
    float[] UkupneKolicine();
    void IspisiStatistiku(ArrayList<VoziloSucelje> vozila);
}
