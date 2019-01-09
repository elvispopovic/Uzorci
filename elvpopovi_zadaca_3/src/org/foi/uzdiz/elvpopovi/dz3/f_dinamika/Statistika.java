/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 * Statistika prikupljenog otpada
 * @author elvis
 */
public class Statistika implements StatistikaSucelje
{

    private float[] podaci=null;
    private SimulacijaAbstractProduct simulacijske;
    Ispisivanje ispis = Ispisivanje.getInstance();
    MVCModelSucelje MVCmodel;
    /**
     * Konstruktor
     * @param simulacijske poveznica sa produktom simulacijskog buildera
     */
    public Statistika(SimulacijaAbstractProduct simulacijske)
    {
        this.simulacijske = simulacijske;
        MVCmodel = simulacijske.DajDispecer();
        podaci = new float[5];
        for(int i=0; i<5; i++)
            podaci[i]=(float) 0.0;
        ispis.prikaziRetke();
        ispis.Ispisi("Kreirano je statističko praćenje simulacije.");
    }
    /**
     * vraća produkt simulacijskog buildera.
     * @return produkt simulacijskog buildera
     */
    @Override
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacijske;
    }
    /**
     * vraća zapise količine otpada 
     * @return podaci o količinama pojedine vrste otpada kao niz decimalnih vrijednosti
     */
    @Override
    public float[] DajPodatke()
    {
        return podaci;
    }
    /**
     * vraća zapis količina stakla
     * @param kolicina količina stakla
     */
    @Override
    public void DodajStaklo(float kolicina)
    {
        podaci[0]+=kolicina;
    }
    /**
     * vraća zapis količina papira
     * @param kolicina količina papira
     */
    @Override
    public void DodajPapir(float kolicina)
    {
        podaci[1]+=kolicina;
    }
    /**
     * vraća zapis količina metala
     * @param kolicina količina metala
     */
    public void DodajMetal(float kolicina)
    {
        podaci[2]+=kolicina;
    }
    /**
     * vraća zapis količina bio otpada
     * @param kolicina količina bio otpada
     */
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
    /**
     * Vraća ukupne količine (zbroj)
     * @return zbroj masa prikupljenog otpada
     */
    @Override
    public float[] UkupneKolicine()
    {
        return podaci;
    }
    /**
     * Ispisuje statistiku vozila
     * @param vozila lista vozila
     */
    @Override
    public void IspisiStatistiku(ArrayList<VoziloSucelje> vozila)
    {
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        MVCmodel = simulacijske.DajDispecer();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        MVCmodel.Ispisi("");
        MVCmodel.Ispisi("STATISTIKA");
        for(int i = 0; i<podaci.length; i++)
             MVCmodel.Ispisi(parametri.DajNazivOtpada(i)+": "+rnd.round(podaci[i],brojDecimala)+"kg");
        for(int i=0; i<vozila.size(); i++)
        {
            VoziloStatistika voziloStatistika = vozila.get(i).dajStatistikuVozila();
            MVCmodel.Ispisi(vozila.get(i).dajNaziv()+" je preuzeo otpad "+voziloStatistika.dajBrojSpremnika()+
                    " spremnika tj. "+voziloStatistika.dajBrojMjesta()+" mjesta.");
            MVCmodel.Ispisi("   Ukupna količina otpada: "+rnd.round(voziloStatistika.dajUkupnuKolicinuOtpada(),
            brojDecimala)+" kg, broj odvoza na deponij: "+voziloStatistika.dajBrojOdlazakaNaDeponij());

        }
    }
}
