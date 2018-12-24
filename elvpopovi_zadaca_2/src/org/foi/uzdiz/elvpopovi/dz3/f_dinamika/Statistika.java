/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Statistika implements StatistikaSucelje
{
    private float[] podaci=null;
    public Statistika()
    {
        podaci = new float[5];
        for(int i=0; i<5; i++)
            podaci[i]=(float) 0.0;
        Ispisivanje.getInstance().Ispisi("Kreirano je statističko praćnje simulacije.");
    }
    @Override
    public float[] DajPodatke()
    {
        return podaci;
    }
    @Override
    public void DodajStaklo(float kolicina)
    {
        podaci[0]+=kolicina;
    }
    @Override
    public void DodajPapir(float kolicina)
    {
        podaci[1]+=kolicina;
    }
    public void DodajMetal(float kolicina)
    {
        podaci[2]+=kolicina;
    }
    @Override
    public void DodajBio(float kolicina)
    {
        podaci[3]+=kolicina;
    }
    @Override
    public void DodajMjesano(float kolicina)
    {
        podaci[4]+=kolicina;
    }

    @Override
    public float[] UkupneKolicine()
    {
        return podaci;
    }
    @Override
    public void IspisiStatistiku(ArrayList<VoziloSucelje> vozila)
    {
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        Ispisivanje ispis = Ispisivanje.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        ispis.Ispisi("");
        ispis.Ispisi("STATISTIKA");
        for(int i = 0; i<podaci.length; i++)
             ispis.Ispisi(parametri.DajNazivOtpada(i)+": "+rnd.round(podaci[i],brojDecimala)+"kg");
        for(int i=0; i<vozila.size(); i++)
        {
            VoziloStatistika voziloStatistika = vozila.get(i).dajStatistikuVozila();
            ispis.Ispisi(vozila.get(i).dajNaziv()+" je preuzeo otpad "+voziloStatistika.dajBrojSpremnika()+
                    " spremnika tj. "+voziloStatistika.dajBrojMjesta()+" mjesta.");
            ispis.Ispisi("   Ukupna količina otpada: "+rnd.round(voziloStatistika.dajUkupnuKolicinuOtpada(),
            brojDecimala)+" kg, broj odvoza na deponij: "+voziloStatistika.dajBrojOdlazakaNaDeponij());

        }
    }
}
