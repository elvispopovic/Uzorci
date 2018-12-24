/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;

/**
 *
 * @author elvis
 */
public interface VoziloKontekstSucelje
{

    
    void InjektirajStatistiku(Statistika statistika);
    StatistikaSucelje DajStatistikuOtpada();
    VoziloSucelje DajVozilo();
    void PostaviPocetnoStanje();
    void PostaviStanje(VoziloStanje stanje);
    VoziloStanje DajStanje();
    boolean DajKvar();
    void PostaviKvar();
    void UkloniKvar();
    int DajTrenutnuUlicu();
    int DajTrenutniSpremnik();
    int DajBrojDodijeljenihSpremnika();
    boolean JeLiZavrsenoPrikupljanje();
    float dajPopunjenost();
    int dajPreuzetoSpremnika();
    void PovecajTrenutnuUlicu();
    void PovecajTrenutniSpremnik();
    void Isprazni();
    void PovecajPopunjenost(float dopuna);
    void ResetAll();
    int  DajBrojacOdvoza();
    void postaviBrojacOdvoza(int brojOdvoza);
    void SmanjiBrojacOdvoza();
}
