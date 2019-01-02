/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

/**
 *
 * @author elvis
 */
public interface VoziloStanjeSucelje
{
    void Napredovanje();
    String DajNaziv();
    void Prijelaz(String novoStanje);
}
