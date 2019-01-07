/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import java.util.ArrayList;
import java.util.Collections;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciIteratorSucelje;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciSucelje;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCObserver;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 * Dispečer dekorira simulaciju. Donosi lanac komandi kojima upravlja simulacijom.
 * @author elvis
 */
public class Dispecer implements SimulacijaSucelje, MVCModelSucelje
{
    protected SimulacijaSucelje simulacija;
    protected ArrayList<MVCObserver> observers;
    protected LanacKomandiApstraktni lanacObradi;
    protected final PodaciSucelje podaciDispecer;
    protected int brg, brd;
    /**
     * Konstruktor
     * @param simulacija dekorirana simulacija
     * @param podaci poveznica na builder produkt podataka
     */
    public  Dispecer(SimulacijaSucelje simulacija, PodaciSucelje podaci)
    {
        this.simulacija = simulacija;
        this.podaciDispecer = podaci;
        observers = new ArrayList<>();
        Ispisivanje ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        brg = parametri.DajVrijednost("brg");
        brd = parametri.DajVrijednost("brd");
        Ispisi("Kreiran je dispečer.");
    }
    /**
     * Dohvaća svoju referencu prema simulacija produktu
     * @return što je produkt simulacijskog buildera
     */
    @Override
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacija.DajSimulacijske();
    }
    /**
     * unosi MVC u svoju listu observera
     * @param observer MVC observer koji se priključuje
     */
    public void PrikljuciMVC(MVCObserver observer)
    {
        this.observers.add(observer);
    }
    /**
     * Odjava MVC observera
     * @param observer MVC observer koji se odjavljuje
     */
    public void OdjaviMVC(MVCObserver observer)
    {
        this.observers.remove(observer);
    }
    /**
     * Notifikacija MVC sustava
     * Uglavnom čeka korisnika za nastavak. Iznimka je kraj programa kada se korisnika više ne čeka.
     * @param cekanje treba li pitati korisnika za nastavak
     */
    @Override
    public void ObavijestiMVC(boolean cekanje)
    {
        for(MVCObserver o:observers)
            o.Osvjezi(cekanje);
    }
    /**
     * Ovu metodu zove MVC kada treba dohvatiti podatke i to:
     * View koji dohvaća podatke
     * Controller kojeg zanima samo broj redaka podataka da može upravljati korisničkim nastavljanjem
     * @return 
     */
    @Override
    public ArrayList<String> DohvatiPodatkeMVC()
    {
        return simulacija.DajRetkeIspisa();
    }
    /**
     * Ovdje MVC šalje komande prema poslovnoj logici programa, konkretno samoj simulaciji
     * @param komanda poruke koje opisuju pojedine komande
     */
    @Override
    public void KomandaMVC(String[] komanda)
    {
        if(komanda[0].equals("OBRISI_PODATKE"))
        {
            ArrayList<String> redci = DohvatiPodatkeMVC();
            if(redci != null)
                redci.clear();
        }
        else if(!komanda[0].toUpperCase().equals("IZLAZ"))
        {
            lanacObradi.ObradiKomandu(komanda);
            ObavijestiMVC(true);
        }
    }
    /**
     * Kreira se lanac komandi kao lanac odgovornosti
     */
    private void inicijalizirajLanacKomandi()
    {
        LanacKomandiApstraktni lanacPripremi, lanacKreni, lanacKvar, lanacIsprazni, lanacStatus, lanacKontrola, lanacTerminalni;
        LanacKomandiApstraktni lanacVozaci, lanacPreuzmi, lanacKadroviranje;
         lanacTerminalni = new LanacTerminalni(simulacija);
        (lanacVozaci     = new LanacVozaci(simulacija)).DodajSljedbenika(lanacTerminalni);
        (lanacPreuzmi    = new LanacPreuzmi(simulacija)).DodajSljedbenika(lanacVozaci);
        (lanacKadroviranje = new LanacKadroviranje(simulacija)).DodajSljedbenika(lanacPreuzmi);
        (lanacKontrola   = new LanacKontrola(simulacija)).DodajSljedbenika(lanacKadroviranje);
        (lanacStatus     = new LanacStatus(simulacija)).DodajSljedbenika(lanacKontrola);
        (lanacIsprazni   = new LanacIsprazni(simulacija)).DodajSljedbenika(lanacStatus);
        (lanacKvar       = new LanacKvar(simulacija)).DodajSljedbenika(lanacIsprazni);
        (lanacKreni      = new LanacKreni(simulacija)).DodajSljedbenika(lanacKvar);
        (lanacPripremi   = new LanacPripremi(simulacija)).DodajSljedbenika(lanacKreni);
        (lanacObradi     = new LanacObradi(simulacija)).DodajSljedbenika(lanacPripremi);
    }
    /**
     * Metoda koja izvršava popis zadanih tj. učitanih komandi komandi
     * @param simulacija
     * @param statistika 
     */
    public void Pokreni(SimulacijaSucelje simulacija, Statistika statistika)
    {
        this.simulacija = simulacija;
        if(this.simulacija.ProvjeriParametre() == false)
            return;
        
        inicijalizirajLanacKomandi();
        PodaciIteratorSucelje iterator = podaciDispecer.dajIterator();
        //zapisane komande
        Ispisi("Izvršavanje zapisanih komandi");
        while(iterator.imaLiSlijedeceg())
        {
            String[] komanda=(iterator.slijedeci());
            lanacObradi.ObradiKomandu(komanda);
        }
        ObavijestiMVC(true);
        
        //prijelaz u korisničke komande
        if(brg>0&&brd>0)
        {
            komandniModIntro();
            for(MVCObserver o:observers)
                o.KomandniMod();
        }
    }
    /**
     * Dohvača listu vozila zapisanu u podacima (ne stvarnu listu vozila)
     * @return lista zapisa vozila stvorena na osnovu datoteke Vozila.txt
     */
    @Override
    public ArrayList<VoziloSucelje> DajListuVozilaPodaci()
    {
        return simulacija.DajListuVozilaPodaci();
    }
    /**
     * Vraća produkt problemskog buildera
     * @return produkt problemskog buildera
     */
    @Override
    public ProblemskiAbstractProduct DajProblemske()
    {
        return simulacija.DajProblemske();
    }
    /**
     * Vraća listu vozila iz dekorirane simulacije
     * @return lista vozila
     */
    @Override
    public ListaVozila DajListaVozilaSimulacija()
    {
        return simulacija.DajListaVozilaSimulacija();
    }

    /**
     * Metoda se ne koristi. Tu je radi kompatibilnosti sa dekoriranom simulacijom.
     * @return 
     */
    @Override
    public boolean ObradiStanjaVozila()
    { 
        return false;
    }
    /**
     * vraća broj vozila u ulici koja ne čekaju, tj. aktivno prikupljaju otpad
     * @param ulicaId identifikator ulice
     * @return broj vozila koja su u ulici i aktivno prikupljaju otpad
     */
    @Override
    public int BrojNecekajucihVozilaUUlici(String ulicaId)
    {
        return simulacija.BrojNecekajucihVozilaUUlici(ulicaId);
    }
    /**
     * Vraća listu vozila koja aktivno prikupljaju otpad u ulici
     * @param ulicaId identifikator ulice
     * @return lista vozila u ulici koja aktivno prikupljaju otpad
     */
    @Override
    public ArrayList<VoziloSucelje> NecekajucaVozilaUUlici(String ulicaId)
    {
        return simulacija.NecekajucaVozilaUUlici(ulicaId);
    }
    /**
     * Mijenja se ishodište sustava. Odnosi se na metodu dekorirane simulacije
     * @param vozila lista vozila koja mijenjaju ishodište
     * @param ishodiste novo ishodište (šifra)
     * @return uspjeh promjene
     */
    public boolean PromijeniIshodisteSustava(ArrayList<String> vozila, String ishodiste)
    {
        return simulacija.PromijeniIshodisteSustava(vozila,ishodiste);
    }
    /**
     * Metoda samo radi kompatibilnosti sa simulacijom
     */
    @Override
    public void Pokreni()
    {
        simulacija.Pokreni();
    }
    /**
     * provjera ispravnosti parametara
     * @return uspješnost operacije promjene parametara
     */
    @Override
    public boolean ProvjeriParametre()
    {
        return simulacija.ProvjeriParametre();
    }
    /**
     * Provjerava mogućnost završetka simulacije: ako su sva vozila prikupila otpad ili ostala čekati, 
     * ostala u kvaru, bez vozača ili na kontroli
     * @return 
     */
    @Override
    public boolean provjeriZavrsetak()
    {
        return true;
    }
    /**
     * Simulacija je povezana sa MVC-om kao njegov model, pa ju View koristi kao ispisivač
     * Ispisivač ne ispisuje u konzolu nego u memoriju koju preuzima MVC i ispisuje u prezentacijski dio ekrana
     * ako je aktivan
     * @param ispis tekst koji treba ispisati
     */
    public void Ispisi(String ispis)
    {
        simulacija.Ispisi(ispis);
    }
    /**
     * Ispis sa mogućnosti upravljanja prelaskom u novu liniju.
     * @param ispis tekst koji treba ispisati
     * @param novaLinija oznaka prelaska u novu liniju
     */
    public void Ispisi(String ispis, boolean novaLinija)
    {
        simulacija.Ispisi(ispis, novaLinija);
    }
    /**
     * Vraća zapisane retke ispisa koje će View u MVC-u zaista ispisati
     * @return lista ispisanih redaka
     */
    public ArrayList<String> DajRetkeIspisa()
    {
        return simulacija.DajRetkeIspisa();
    }
    /**
     * Ispisuje specijalni intro koji korisnika obavještava da ulazi u komandni mod
     */
    private void komandniModIntro()
    {
        Ispisi(String.join("", Collections.nCopies(80, "*")));
        Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        Ispisi("*"+String.join("", Collections.nCopies(27, " "))+"Prijelaz u komandni mod"+
                String.join("", Collections.nCopies(28, " "))+"*");
        Ispisi("*"+String.join("", Collections.nCopies(78, " "))+"*");
        Ispisi(String.join("", Collections.nCopies(80, "*")));
    }

    

}
