/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.f_dinamika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import org.foi.uzdiz.elvpopovi.dz2.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz2.h_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StatistikaTablicno implements StatistikaSucelje
{
    protected StatistikaSucelje statistika;
    StringBuilder sb;
    Formatter form;
    Ispisivanje ispis;
    public StatistikaTablicno(StatistikaSucelje statistika)
    {
        this.statistika = statistika;
        sb = new StringBuilder();
        form = new Formatter(sb);
        ispis = Ispisivanje.getInstance();
    }
    @Override
    public void IspisiStatistiku(ArrayList<VoziloSucelje> vozila)
    {
        ispis.Ispisi("");
        ispis.Ispisi("STATISTIKA");
        form.format("%12s |%12s |","Vrsta","količina");
        ispis.Ispisi(sb.toString());
        sb.setLength(0);
        form.format("%12s |%12s |","otpada"," [kg]");
        ispis.Ispisi(sb.toString());
        ispisiOtpad();
        sb.setLength(0);
        form.format("%4s |%12s |%9s |%9s |%10s |%9s |","","","  Broj","  Broj","Ukupna","Broj");
        ispis.Ispisi(sb.toString());
        sb.setLength(0);
        form.format("%4s |%12s |%9s |%9s |%10s |%9s |","ID","Naziv","spremnika","mjesta","količina","odvoza");
        ispis.Ispisi(sb.toString());
        ispisiVozila(ispis, vozila);
    }
    private void ispisiOtpad()
    {
        float ukupno = (float)0.0;
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        ispis.Ispisi(String.join("", Collections.nCopies(28, "=")));
        for(int i = 0; i<statistika.DajPodatke().length; i++)
        {
            sb.setLength(0);
            ukupno += statistika.DajPodatke()[i];
            form.format("%12s |%12.2f |",parametri.DajNazivOtpada(i),
                    rnd.round(statistika.DajPodatke()[i],brojDecimala));
            ispis.Ispisi(sb.toString());
        }
        ispis.Ispisi(String.join("", Collections.nCopies(28, "=")));
        sb.setLength(0);
        form.format("%12s |%12.2f |","UKUPNO",rnd.round(ukupno,brojDecimala));
        ispis.Ispisi(sb.toString());
        ispis.Ispisi("");
    }
    
    private void ispisiVozila(Ispisivanje ispis, ArrayList<VoziloSucelje> vozila)
    {
        int ukSpremnika=0, ukMjesta=0, ukBrojOdvoza=0;
        float ukKolicina =(float)0.0;
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        VoziloSucelje vozilo;
        ispis.Ispisi(String.join("", Collections.nCopies(64, "=")));
        for(int i=0; i<vozila.size(); i++)
        {
            vozilo = vozila.get(i);
            sb.setLength(0);
            ukSpremnika+=vozilo.dajStatistiku().dajBrojSpremnika();
            ukMjesta+=vozilo.dajStatistiku().dajBrojMjesta();
            ukKolicina+=vozilo.dajStatistiku().dajUkupnuKolicinuOtpada();
            ukBrojOdvoza+=vozilo.dajStatistiku().dajBrojOdlazakaNaDeponij();
            form.format("%4s |%12s |%9d |%9d |%10.2f |%9d |",vozilo.dajId(),vozilo.dajNaziv(),
                    vozilo.dajStatistiku().dajBrojSpremnika(),vozilo.dajStatistiku().dajBrojMjesta(),
                    rnd.round(vozilo.dajStatistiku().dajUkupnuKolicinuOtpada(),brojDecimala),
                    vozilo.dajStatistiku().dajBrojOdlazakaNaDeponij());
            ispis.Ispisi(sb.toString());
        }
        ispisiUkupno(ukSpremnika, ukMjesta, ukBrojOdvoza, ukKolicina);
    }
    private void ispisiUkupno(int ukSpremnika, int ukMjesta, int ukBrojOdvoza, float ukKolicina)
    {
        sb.setLength(0);
        ispis.Ispisi(String.join("", Collections.nCopies(64, "=")));
        sb.setLength(0);
        form.format("%4s |%12s |%9d |%9s |%10.2f |%9d |","","UKUPNO",ukSpremnika,ukMjesta,
                ukKolicina,ukBrojOdvoza);
        ispis.Ispisi(sb.toString());
    }

    @Override
    public float[] DajPodatke()
    {
        return statistika.DajPodatke();
    }
}
