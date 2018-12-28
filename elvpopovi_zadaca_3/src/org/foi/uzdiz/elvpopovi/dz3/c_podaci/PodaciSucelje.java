/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.c_podaci;


/**
 *
 * @author elvpopovic
 */
public interface PodaciSucelje
{
    boolean UcitajPodatke(String staza);
    int BrojZapisa();
    String[] DajShemu();
    String[] DajPodatak(int redniBroj);
    String DajNazivOtpada(int i);
    String DajDatoteku(String naziv);
    int DajVrijednost(String naziv);
    void DodajVrijednost(String naziv, int vrijednost);
    PodaciIteratorSucelje dajIterator();
}


