/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;

/**
 *
 * @author elvis
 */
public interface VoziloSucelje
{
    String dajId();
    ArrayList<Ulica> dajDodijeljeneUlice();
    int dajBrojSpremnika();
    ArrayList<ArrayList<Spremnik>> dajDodijeljeneSpremnike();
    void postaviDodijeljeneUlice(ArrayList<Ulica> ulice);
    void postaviDodijeljeneSpremnike(ArrayList<ArrayList<Spremnik>> spremnici);
   
    String dajNaziv();
    int dajNosivost();
    void DodajVozaca(Vozac vozac);
    void UkloniVozaca(Vozac vozac);
    boolean PostaviTrenutnogVozaca(Integer id);
    void RotirajVozace();
    LinkedHashMap<Integer,Vozac> DajMapuVozaca();
    Vozac DajTrenutnogVozaca();
    VoziloKontekstSucelje dajKontekst();
    VoziloStatistika dajStatistikuVozila();
    int dajVrstu();
    int dajTip();
    int dajKapacitetPogona();
    int dajPunjenjePogona();
}
