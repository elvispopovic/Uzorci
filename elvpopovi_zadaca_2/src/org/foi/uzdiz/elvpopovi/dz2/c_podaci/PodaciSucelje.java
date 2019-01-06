/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.c_podaci;


/**
 *
 * @author elvpopovic
 */
public interface PodaciSucelje
{
    boolean UcitajPodatke(String staza);
    int BrojZapisa();
    String DajNazivOtpada(int i);
    PodaciIteratorSucelje dajIterator();
}


