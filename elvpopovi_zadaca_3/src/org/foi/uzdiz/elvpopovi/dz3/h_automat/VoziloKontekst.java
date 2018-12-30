/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;

/**
 *
 * @author elvis
 */
public class VoziloKontekst implements VoziloKontekstSucelje 
{
    private final VoziloSucelje vozilo;
    //private StatistikaSucelje statistikaOtpada;
    private SimulacijaAbstractProduct simulacijske;
    private VoziloStanjeSucelje stanje;
    private int trenutnaUlica, trenutniSpremnik, brojSpremnika;
    private boolean kvar, zavrsenoPrikupljanje;
    private int preuzetoSpremnika;
    private float popunjenost;
    private int brojacOdvoza;
    
    @Override
    public VoziloSucelje DajVozilo()
    {
        prebrojiSpremnike();
        return vozilo;
    }
    
    @Override
    public void PostaviPocetnoStanje()
    {
        this.stanje = new StanjeParkiraliste(this);
    }
    
    @Override
    public void PostaviStanje(VoziloStanjeSucelje stanje)
    {
        this.stanje = stanje;
    }
    
    @Override
    public VoziloStanjeSucelje DajStanje()
    {
        return stanje;
    }
    
    @Override
    public boolean DajKvar()
    {
        return kvar;
    }
    
    @Override
    public void PostaviKvar()
    {
        kvar = true;
    }
    
    @Override
    public void UkloniKvar()
    {
        kvar = false;
    }
    
    @Override
    public int DajTrenutnuUlicu()
    {
        return trenutnaUlica;
    }

    @Override
    public int DajTrenutniSpremnik()
    {
        return trenutniSpremnik;
    }
    
    @Override
    public int DajBrojDodijeljenihSpremnika()
    {
        return brojSpremnika;
    }

    @Override
    public boolean JeLiZavrsenoPrikupljanje()
    {
        return zavrsenoPrikupljanje;
    }
    
    @Override
    public float dajPopunjenost()
    {
        return popunjenost;
    }
    @Override
    public int dajPreuzetoSpremnika()
    {
        return preuzetoSpremnika;
    }
    
    @Override
    public int DajBrojacOdvoza()
    {
        return brojacOdvoza;
    }
    @Override
    public void postaviBrojacOdvoza(int brojOdvoza)
    {
        this.brojacOdvoza = brojOdvoza;
    }
    
    public VoziloKontekst(VoziloSucelje vozilo)
    {
        this.vozilo = vozilo;
        this.simulacijske = null; //bit će injektirana
        ResetAll();
    }
    
    @Override
    public void InjektirajSimulacijske(SimulacijaAbstractProduct simulacijske)
    {
        this.simulacijske = simulacijske;
    }
    
    @Override
    public StatistikaSucelje DajStatistikuOtpada()
    {
        if(simulacijske != null)
            return simulacijske.DajStatistika();
        else 
            return null;
    }
    public MVCModelSucelje DajMVCModel()
    {
        if(simulacijske != null)
            return simulacijske.DajDispecer();
        else 
            return null;
    }
    
    @Override
    public void PovecajTrenutnuUlicu()
    {
        int brojUlica = vozilo.dajDodijeljeneUlice().size();
        trenutniSpremnik = 0;
        trenutnaUlica++;
        if(trenutnaUlica>=brojUlica)
            zavrsenoPrikupljanje = true;
    }
    @Override
    public void PovecajTrenutniSpremnik()
    {
        int brojSpremnikaUUlici;
        if(zavrsenoPrikupljanje == true)
            return;
        brojSpremnikaUUlici = vozilo.dajDodijeljeneSpremnike().get(trenutnaUlica).size();
        trenutniSpremnik++;
        if(trenutniSpremnik>=brojSpremnikaUUlici)
            PovecajTrenutnuUlicu();
    }
    
    
    @Override
    public void Isprazni()
    {
        this.popunjenost = (float)0.0;
    }
    @Override
    public void PovecajPopunjenost(float dopuna)
    {
        popunjenost+=dopuna;
        preuzetoSpremnika++;
    }
           
    public void ResetUliceISpremnici()
    {
        trenutnaUlica = 0;
        trenutniSpremnik = 0;
    }
    
    @Override
    public void ResetAll()
    {
        zavrsenoPrikupljanje = false;
        kvar = false;
        brojacOdvoza=0;
        trenutnaUlica = 0;
        trenutniSpremnik = 0;
        preuzetoSpremnika = 0;
        popunjenost = 0.0f;
    }
    
    @Override
    public void SmanjiBrojacOdvoza()
    {
        this.brojacOdvoza--;
    }


    private void prebrojiSpremnike()
    {
        ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici = vozilo.dajDodijeljeneSpremnike();
        ArrayList<Ulica> dodijeljeneUlice = vozilo.dajDodijeljeneUlice();
        brojSpremnika = 0;
        if(dodijeljeniSpremnici == null || dodijeljeneUlice == null || dodijeljeneUlice.size() == 0)
        {
            zavrsenoPrikupljanje = true;
            return;
        }
        for(int j=0; j<dodijeljeniSpremnici.size(); j++) //ulice
            for(int i=0; i<dodijeljeniSpremnici.get(j).size(); i++) //spremnici u ulici
                brojSpremnika++;
    }
}
