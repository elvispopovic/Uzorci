/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCView;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public interface InicijalizacijaAbstractProduct 
{
    void UcitajParametre();
    void InicijalizirajMVC();
    void KreirajIspisivac();
    void KreirajRandomGenerator();
    void KreirajIdGenerator();
    void UcitajPodatkePodrucja();
    void UcitajPodatkeUlica();
    void UcitajPodatkeSpremnika();
    void UcitajPodatkeVozila();
    void UcitajPodatkeDispecera();
    Ispisivanje dajIspisivanje();
    PodaciSucelje dajParametre();
    MVCView dajMVCview();
    PodaciSucelje dajPodrucja();
    PodaciSucelje dajUlice();
    PodaciSucelje dajSpremnike();
    PodaciSucelje dajVozila();
    PodaciSucelje dajDispecera();
}
