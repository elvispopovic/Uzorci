/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
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
    private int trenutnaUlica, trenutniSpremnik, brojSpremnika, kolicinaPogonskog;
    private boolean kvar, zavrsenoPrikupljanje;
    private int preuzetoSpremnika;
    private float popunjenost;
    private boolean obrnutoKretanje;
    
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
    
    public int DajKolicinuPogonskog()
    {
        return kolicinaPogonskog;
    }
    public void SmanjiKolicinuPogonskog()
    {
        if(kolicinaPogonskog>0)
            kolicinaPogonskog--;
    }
    public void ObnoviKolicinuPogonskog()
    {
        kolicinaPogonskog = vozilo.dajKapacitetPogona();
    }
    
    
    @Override
    public int DajBrojTrenutneUlice()
    {
        return trenutnaUlica;
    }
    
    @Override
    public Ulica DajTrenutnuUlicu()
    {
        ArrayList<Ulica> listaUlica = vozilo.dajDodijeljeneUlice();
        if(listaUlica == null || listaUlica.size()==0 || listaUlica.size()<=trenutnaUlica)
            return null;
        return listaUlica.get(trenutnaUlica);
    }
    @Override
    public boolean JeLiObrnutoKretanje()
    {
        return obrnutoKretanje;
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

    public VoziloKontekst(VoziloSucelje vozilo)
    {
        this.vozilo = vozilo;
        this.simulacijske = null; //bit će injektirana
        ResetAll();
    }
    /**
     * Kontekst vozila kreira se prije statistike i drugih dijelova sadržanih u
     * SimulacijaProduct objektu. Zato je potreno naknadno injektirati simulacijski 
     * objekt budući ga nije moguće dodati u konstruktoru (tada je još uvijek null)
     * @param simulacijske Referenca na SimulacijaAbstractProduct objekt koji se injektira
     */
    @Override
    public void InjektirajSimulacijske(SimulacijaAbstractProduct simulacijske)
    {
        this.simulacijske = simulacijske;
    }
    
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacijske;
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
        Parametri parametri = Parametri.getInstance();
        SimulacijaSucelje simulacija = simulacijske.DajSimulacija();
        obrnutoKretanje = false;
        trenutniSpremnik = 0;
        trenutnaUlica++;
        if(trenutnaUlica>=brojUlica)
            zavrsenoPrikupljanje = true;
        else
        {
            int aktivnih = simulacija.BrojNecekajucihVozilaUUlici(DajTrenutnuUlicu().Id());
            if(aktivnih==1) //okrece se smjer
            {
                trenutniSpremnik = vozilo.dajDodijeljeneSpremnike().get(trenutnaUlica).size()-1;
                obrnutoKretanje = true;
                VoziloSucelje vozilo = simulacija.NecekajucaVozilaUUlici(DajTrenutnuUlicu().Id()).get(0);
                if(parametri.DajVrijednost("ispis")==0)
                    simulacija.Ispisi("Vozilo "+vozilo.dajId()+": "+vozilo.dajNaziv()+" je naišlo na drugo vozilo u ulici i okreće smjer prikupljanja.");
            }
            else
                DajStanje().Prijelaz("CEKANJE");
        } 
    }
    @Override
    public void PovecajTrenutniSpremnik()
    {
        int brojSpremnikaUUlici;
        if(zavrsenoPrikupljanje == true)
            return;
        brojSpremnikaUUlici = vozilo.dajDodijeljeneSpremnike().get(trenutnaUlica).size();
        if(!obrnutoKretanje)
            trenutniSpremnik++;
        else
            trenutniSpremnik--;
        if(trenutniSpremnik>=brojSpremnikaUUlici || trenutniSpremnik<0)
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
    /**
     * Koristi se pri promjeni sustava. Vozilo koje je završilo prikupljanje u
     * starom sustavu, mora poništiti završetak u novom, i postaviti ulice i
     * spremnike na nulu, zadržavajući stanje o prikupljenom otpadu iz starog sustava.
     */  
    public void ResetUliceISpremnici()
    {
        trenutnaUlica = 0;
        trenutniSpremnik = 0;
        zavrsenoPrikupljanje = false;
    }
    
    @Override
    public void ResetAll()
    {
        zavrsenoPrikupljanje = false;
        kvar = false;
        kolicinaPogonskog = vozilo.dajKapacitetPogona();
        trenutnaUlica = 0;
        trenutniSpremnik = 0;
        preuzetoSpremnika = 0;
        popunjenost = 0.0f;
        obrnutoKretanje = false;
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
