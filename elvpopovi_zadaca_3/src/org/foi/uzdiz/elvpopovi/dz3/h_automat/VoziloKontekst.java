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
 * Kontekst vozila. Pamti sve potrebne parametre odvoza.
 * Povezano je i sa strojem stanja vozila
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
    /**
     * Getter koji vraća objekt vozila
     * @return 
     */
    @Override
    public VoziloSucelje DajVozilo()
    {
        prebrojiSpremnike();
        return vozilo;
    }
    /**
     * Postavlja početno stanje (koje je stanje PARKIRALIŠTE)
     */
    @Override
    public void PostaviPocetnoStanje()
    {
        this.stanje = new StanjeParkiraliste(this);
    }
    /**
     * postavlja proizvoljno stanje
     * @param stanje 
     */
    @Override
    public void PostaviStanje(VoziloStanjeSucelje stanje)
    {
        this.stanje = stanje;
    }
    /**
     * Vraća trenutno stanje vozila
     * @return 
     */
    @Override
    public VoziloStanjeSucelje DajStanje()
    {
        return stanje;
    }
    /**
     * Vraća varijablu kvara.
     * Ova varijabla je dopunska varijabla uz samo stanje KVAR jer se koristi za dva prijelaza.
     * Potrebno je vozilo isprazniti pa pripremiti da bi se poništio kvar
     * @return stanje kvara
     */
    @Override
    public boolean JeLiKvar()
    {
        return kvar;
    }
    /**
     * Postavlja posebnu varijablu KVAR koja funkcionira uz stanje KVAR
     */
    @Override
    public void PostaviKvar()
    {
        kvar = true;
    }
    /**
     * Poništava se varijabla KVAR
     */
    @Override
    public void UkloniKvar()
    {
        kvar = false;
    }
    /**
     * Vraća količinu pogonskog goriva ili el. energije
     * @return cjelobrojna količina
     */
    public int DajKolicinuPogonskog()
    {
        return kolicinaPogonskog;
    }
    /**
     * Smanjuje količinu pogonskog sredstva
     */
    public void SmanjiKolicinuPogonskog()
    {
        if(kolicinaPogonskog>0)
            kolicinaPogonskog--;
    }
    /**
     * Puni pogonsko sredstvo na maksimum
     */
    public void ObnoviKolicinuPogonskog()
    {
        kolicinaPogonskog = vozilo.dajKapacitetPogona();
    }
    /**
     * Vraća indeks trenutne ulice iz liste ulica
     * @return indeks ulice
     */
    @Override
    public int DajBrojTrenutneUlice()
    {
        return trenutnaUlica;
    }
    /**
     * Vraća objekt trenutne ulice
     * @return trenutna ulica
     */
    @Override
    public Ulica DajTrenutnuUlicu()
    {
        ArrayList<Ulica> listaUlica = vozilo.dajDodijeljeneUlice();
        if(listaUlica == null || listaUlica.size()==0 || listaUlica.size()<=trenutnaUlica)
            return null;
        return listaUlica.get(trenutnaUlica);
    }
    /**
     * Vraća stanje kretanja. Vozilo može prikupljati otpad u ulici i obrnutim redoslijedom.
     * @return true za normalno kretanje, false za obrnuto
     */
    @Override
    public boolean JeLiObrnutoKretanje()
    {
        return obrnutoKretanje;
    }
    /**
     * Vraća indeks trenutnog spremnika iz liste spremnika
     * @return trenutni spremnik
     */
    @Override
    public int DajTrenutniSpremnik()
    {
        return trenutniSpremnik;
    }
    /**
     * Vraća broj spremnika koji treba obraditi
     * @return broj spremnika
     */
    @Override
    public int DajBrojDodijeljenihSpremnika()
    {
        return brojSpremnika;
    }
    /**
     * Vraća završetak prikupljanja
     * @return true ako je završeno, false inače
     */
    @Override
    public boolean JeLiZavrsenoPrikupljanje()
    {
        return zavrsenoPrikupljanje;
    }
    /**
     * vraća popunjenost vozila otpadom
     * @return količina kao decimalni broj
     */
    @Override
    public float dajPopunjenost()
    {
        return popunjenost;
    }
    /**
     * vraća broj preuzetih spremnika
     * @return broj spremnika
     */
    @Override
    public int dajPreuzetoSpremnika()
    {
        return preuzetoSpremnika;
    }
    /**
     * Konstruktor
     * @param vozilo vozilo na koje će se kontekst odnositi
     */
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
    /**
     * Vraća objekt produkta simulacijskog buildera. Tu su poveznice na statistiku i slično.
     * @return 
     */
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacijske;
    }
    /**
     * Vraća statistiku koristeći poveznicu sa produktom simulacijskog buildera
     * @return 
     */
    @Override
    public StatistikaSucelje DajStatistikuOtpada()
    {
        if(simulacijske != null)
            return simulacijske.DajStatistika();
        else 
            return null;
    }
    /**
     * Vraća MVC model koji se obično koristi za pristup ispisivanju
     * @return 
     */
    public MVCModelSucelje DajMVCModel()
    {
        if(simulacijske != null)
            return simulacijske.DajDispecer();
        else 
            return null;
    }
    /**
     * Povećava se trenutna ulica nakon što su prikupljeni svi spremnici u prošloj
     */
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
    /**
     * Jedan korak prikupljanja. Povećava se trenutni spremnik. Ako je to zadnji spremnik, povećava se i trenutna ulica.
     */
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
    
    /**
     * Pražnjenje vozila
     */
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
    /**
     * Resetira sve vrijednosti konteksta
     */
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
    /**
     * Kreće se po područjima i broji dodijeljene spremnike
     */
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
