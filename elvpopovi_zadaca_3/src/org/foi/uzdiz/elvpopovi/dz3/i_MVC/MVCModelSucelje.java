/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_MVC;

import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public interface MVCModelSucelje
{
    void PrikljuciMVC(MVCObserver observer);
    void OdjaviMVC(MVCObserver observer);
    void Ispisi(String ispis);
    void Ispisi(String ispis, boolean novaLinija);
    void ObavijestiMVC(boolean cekanje);
    ArrayList<String> DohvatiPodatkeMVC();
    void KomandaMVC(String[] komanda);
}
