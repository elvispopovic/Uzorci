/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;

/**
 *
 * @author elvis
 */
public interface VoziloKontekstSucelje
{

    
    void InjektirajSimulacijske(SimulacijaAbstractProduct simulacijske);
    SimulacijaAbstractProduct DajSimulacijske();
    StatistikaSucelje DajStatistikuOtpada();
    MVCModelSucelje DajMVCModel();
    VoziloSucelje DajVozilo();
    void PostaviPocetnoStanje();
    void PostaviStanje(VoziloStanjeSucelje stanje);
    VoziloStanjeSucelje DajStanje();
    boolean JeLiKvar();
    void PostaviKvar();
    void UkloniKvar();
    int DajKolicinuPogonskog();
    void ObnoviKolicinuPogonskog();
    void SmanjiKolicinuPogonskog();
    int DajBrojTrenutneUlice();
    Ulica DajTrenutnuUlicu();
    boolean JeLiObrnutoKretanje();
    int DajTrenutniSpremnik();
    int DajBrojDodijeljenihSpremnika();
    boolean JeLiZavrsenoPrikupljanje();
    float dajPopunjenost();
    int dajPreuzetoSpremnika();
    void PovecajTrenutnuUlicu();
    void PovecajTrenutniSpremnik();
    void Isprazni();
    void PovecajPopunjenost(float dopuna);
    void ResetUliceISpremnici();
    void ResetAll();
}
