/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.b_buideri;

import org.foi.uzdiz.elvpopovi.dz2.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz2.c_podaci.Podaci;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;

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
    void UcitajPodatkePodrucja();
    void UcitajPodatkeUlica();
    void UcitajPodatkeSpremnika();
    void UcitajPodatkeVozila();
    void UcitajPodatkeDispecera();
    Ispisivanje dajIspisivanje();
    Parametri dajParametre();
    Podaci dajPodrucja();
    Podaci dajUlice();
    Podaci dajSpremnike();
    Podaci dajVozila();
    Podaci dajDispecera();
}
