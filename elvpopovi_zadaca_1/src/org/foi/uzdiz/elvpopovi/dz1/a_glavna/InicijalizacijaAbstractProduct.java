/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.a_glavna;

import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Podaci;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public interface InicijalizacijaAbstractProduct 
{
    void UcitajParametre();
    void KreirajIspisivac();
    void KreirajRandomGenerator();
    void KreirajIdGenerator();
    void UcitajPodatkeUlica();
    void UcitajPodatkeSpremnika();
    void UcitajPodatkeVozila();
    Ispisivanje getIspisivanje();
    Parametri getParametri();
    Podaci getUlice();
    Podaci getSpremnici();
    Podaci getVozila();
}
