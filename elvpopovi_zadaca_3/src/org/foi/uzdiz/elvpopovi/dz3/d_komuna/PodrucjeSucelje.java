/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.d_komuna;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author elvis
 */
public interface PodrucjeSucelje
{
    void DodajPodrucje(PodrucjeSucelje p);
    void DodajUlicu(Ulica u);
    String dajId();
    String dajNaziv();
    String[] dajDijelove();
    PodrucjeSucelje PronadjiPodrucje(String id, boolean rekurzivno);
    void NapuniMapuPodrucja(HashMap<String,PodrucjeSucelje> lista, boolean rekurzivno);
    Ulica PronadjiUlicu(String id, boolean rekurzivno);
    void NapuniListuUlica(ArrayList<Ulica> lista, boolean rekurzivno);
    void NapuniMapuUlica(HashMap<String,Ulica> lista, boolean rekurzivno);
    float[] DajKolicineOtpada(float[] ulazno);
    void IspisiKolicineOtpada();
}
