/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;

/**
 * U ovo stanje vozilo prelazi kada je u ulici više od jednog vozila pa mora čekati da vozila 
 * napuste ulicu
 * Upravljanje stanjem CEKANJE
 * @author elvis
 */
public class StanjeCekanje implements VoziloStanjeSucelje
{

    public final String naziv;
    protected VoziloSucelje vozilo;
    protected Ulica ulica;
    SimulacijaSucelje simulacija;
    protected VoziloKontekstSucelje kontekst;
    protected MVCModelSucelje MVCmodel;
    /**
     * Kontruktor
     * @param kontekst referenca konteksta vozila
     */
    public StanjeCekanje(VoziloKontekstSucelje kontekst)
    {
        this.naziv = "CEKANJE";
        this.kontekst = kontekst;
        if(this.kontekst != null)
        {
            MVCmodel = kontekst.DajMVCModel();
            simulacija = kontekst.DajSimulacijske().DajSimulacija();
            
            vozilo = kontekst.DajVozilo();  
            ulica = kontekst.DajTrenutnuUlicu();
            ispisiCekanje(0);
            Prijelaz("PRIKUPLJANJE");
        }
    }
    
    /**
     * Metoda koja se poziva u simulaciji za kretanje unaprijed. Za stanje ČEKANJE provjerava
     * postoji li broj aktivnih vozila 2 ili više i ako da, nastavlja čekati
     */
    @Override
    public void Napredovanje()
    {
        if(ulica == null) //nema dodijeljenih ulica
        {
            kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
            return;
        }
        int brojNecekajucihVozilaUUlici = simulacija.BrojNecekajucihVozilaUUlici(ulica.Id());
        if(brojNecekajucihVozilaUUlici>=2)
        {
            ispisiCekanje(1);
        }
        else
        {
            ispisiCekanje(2);
            Prijelaz("PRIKUPLJANJE");
        }
    }
    /**
     * Getter koji vraća naziv stanja
     * @return String koji je vraćeni naziv stanja
     */
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    /**
     * Metoda koja prima vanjski zahtijev za promjenom stanja
     * @param novoStanje zadtraženo novo stanje
     */
    @Override
    public void Prijelaz(String novoStanje)
    {
        int brojNecekajucihVozilaUUlici = simulacija.BrojNecekajucihVozilaUUlici(ulica.Id());
        if(novoStanje.equals("PRIKUPLJANJE")&&brojNecekajucihVozilaUUlici<2)
        {
            kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
        }
    }
    /**
     * Ispis informacija o stanju. Ovisno o modu ispisuje određenu fazu čekanja
     */
    private void ispisiCekanje(int mod)
    {
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0 || !kontekst.DajStanje().DajNaziv().equals("PRIKUPLJANJE"))
            return;
        int brojVozila = simulacija.BrojNecekajucihVozilaUUlici(ulica.Id());
        switch(mod)
        {
            case 0: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je ušlo u "+ulica.Naziv()+", i zateklo "+
                    (brojVozila-1)+" vozila u aktivnom prikupljanju te čeka da završe.");
                    break;
            case 1: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" čeka u "+ 
                ulica.Naziv() + " jer u njoj već aktivno prikuplja "+brojVozila+" vozila.");
                    break;
            case 2: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je završilo čekanje u "+ 
                ulica.Naziv() + ". Broj aktivnih: "+brojVozila);
                    break;
        } 
    }

}
