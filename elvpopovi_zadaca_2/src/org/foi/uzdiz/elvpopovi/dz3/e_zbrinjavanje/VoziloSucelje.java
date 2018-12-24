/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;

/**
 *
 * @author elvis
 */
public interface VoziloSucelje
{
    String dajId();
    ArrayList<Ulica> dajDodijeljeneUlice();
    ArrayList<ArrayList<Spremnik>> dajDodijeljeneSpremnike();
    void postaviDodijeljeneUlice(ArrayList<Ulica> ulice);
    void postaviDodijeljeneSpremnike(ArrayList<ArrayList<Spremnik>> spremnici);
    String dajNaziv();
    int dajNosivost();
    String[] dajVozace();
    VoziloKontekst dajKontekst();
    VoziloStatistika dajStatistiku();
    int dajVrstu();
    int dajTip();
    boolean IsprazniSpremnik();
    boolean ProvjeriNapunjenost();
    void IsprazniVozilo(int ciklusaOdvoz);
    boolean ProvjeriOdvoz();
}
