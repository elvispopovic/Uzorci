/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_view_control.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StatistikaTablicno implements StatistikaSucelje
{
    protected StatistikaSucelje statistika;
    private StringBuilder sb;
    private Formatter form;
    protected MVCModelSucelje MVCmodel;
    public StatistikaTablicno(StatistikaSucelje statistika)
    {
        this.statistika = statistika;
        if(statistika != null)
            this.MVCmodel = statistika.DajSimulacijske().DajDispecer();
        sb = new StringBuilder();
        form = new Formatter(sb);
    }
    
    @Override
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return statistika.DajSimulacijske();
    }
    @Override
    public void IspisiStatistiku(ArrayList<VoziloSucelje> vozila)
    {
        MVCmodel.ObavijestiMVC();
        MVCmodel.Ispisi("STATISTIKA PREUZIMANJA OTPADA");
        form.format("%12s |%12s |","Vrsta","količina");
        MVCmodel.Ispisi(sb.toString());
        sb.setLength(0);
        form.format("%12s |%12s |","otpada"," [kg]");
        MVCmodel.Ispisi(sb.toString());
        ispisiOtpad();
        MVCmodel.ObavijestiMVC();
        MVCmodel.Ispisi("STATISTIKA VOZILA");
        sb.setLength(0);
        form.format("%4s |%12s |%9s |%9s |%10s |%9s |","","","  Broj","  Broj","Ukupna","Broj");
        MVCmodel.Ispisi(sb.toString());
        sb.setLength(0);
        form.format("%4s |%12s |%9s |%9s |%10s |%9s |","ID","Naziv","spremnika","mjesta","količina","odvoza");
        MVCmodel.Ispisi(sb.toString());
        ispisiVozila(vozila);
        MVCmodel.ObavijestiMVC();
    }
    private void ispisiOtpad()
    {
        float ukupno = (float)0.0;
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        MVCmodel.Ispisi(String.join("", Collections.nCopies(28, "=")));
        for(int i = 0; i<statistika.DajPodatke().length; i++)
        {
            sb.setLength(0);
            ukupno += statistika.DajPodatke()[i];
            form.format("%12s |%12."+brojDecimala+"f |",parametri.DajNazivOtpada(i),
                    statistika.DajPodatke()[i]);
            MVCmodel.Ispisi(sb.toString());
        }
        MVCmodel.Ispisi(String.join("", Collections.nCopies(28, "=")));
        sb.setLength(0);
        form.format("%12s |%12."+brojDecimala+"f |","UKUPNO",ukupno);
        MVCmodel.Ispisi(sb.toString());
        MVCmodel.Ispisi("");
    }
    
    private void ispisiVozila(ArrayList<VoziloSucelje> vozila)
    {
        int ukSpremnika=0, ukMjesta=0, ukBrojOdvoza=0;
        float ukKolicina =(float)0.0;
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        VoziloSucelje vozilo;
        VoziloStatistika statistikaVozila;
        MVCmodel.Ispisi(String.join("", Collections.nCopies(64, "=")));
        for(int i=0; i<vozila.size(); i++)
        {
            vozilo = vozila.get(i);
            if(vozilo == null)
                continue;
            statistikaVozila = vozilo.dajStatistikuVozila();
            sb.setLength(0);
            ukSpremnika+=vozilo.dajStatistikuVozila().dajBrojSpremnika();
            ukMjesta+=statistikaVozila.dajBrojMjesta();
            ukKolicina+=statistikaVozila.dajUkupnuKolicinuOtpada();
            ukBrojOdvoza+=statistikaVozila.dajBrojOdlazakaNaDeponij();
            form.format("%4s |%12s |%9d |%9d |%10."+brojDecimala+"f |%9d |",vozilo.dajId(),vozilo.dajNaziv(),
                    statistikaVozila.dajBrojSpremnika(),statistikaVozila.dajBrojMjesta(),
                    statistikaVozila.dajUkupnuKolicinuOtpada(), statistikaVozila.dajBrojOdlazakaNaDeponij());
            MVCmodel.Ispisi(sb.toString());
        }
        ispisiUkupno(ukSpremnika, ukMjesta, ukBrojOdvoza, ukKolicina);
    }
    private void ispisiUkupno(int ukSpremnika, int ukMjesta, int ukBrojOdvoza, float ukKolicina)
    {
        sb.setLength(0);
        MVCmodel.Ispisi(String.join("", Collections.nCopies(64, "=")));
        sb.setLength(0);
        form.format("%4s |%12s |%9d |%9s |%10.2f |%9d |","","UKUPNO",ukSpremnika,ukMjesta,
                ukKolicina,ukBrojOdvoza);
        MVCmodel.Ispisi(sb.toString());
    }

    @Override
    public float[] DajPodatke()
    {
        return statistika.DajPodatke();
    }

    @Override
    public void DodajStaklo(float kolicina)
    {
        statistika.DodajStaklo(kolicina);
    }

    @Override
    public void DodajPapir(float kolicina)
    {
        statistika.DodajPapir(kolicina);
    }

    @Override
    public void DodajMetal(float kolicina)
    {
        statistika.DodajMetal(kolicina);
    }

    @Override
    public void DodajBio(float kolicina)
    {
        statistika.DodajBio(kolicina);
    }

    @Override
    public void DodajMjesano(float kolicina)
    {
        statistika.DodajMjesano(kolicina);
    }

    @Override
    public float[] UkupneKolicine()
    {
       return statistika.UkupneKolicine();
    }

    
}
