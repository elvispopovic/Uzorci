/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.a_glavna;

import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaPodatakaProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaPodatakaBuilder;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaBuilder;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class AplikacijskiDirektor
{
    AplikacijskiDirektor(String[] args)
    {
        this.args = args;
    }
    public boolean Inicijaliziraj()
    {
        podatkovniBuilder = new InicijalizacijaPodatakaBuilder(args);
        try
        {
            podatkovniBuilder 
                    .UcitajParametre()
                    .KreirajIspisivac()
                    .KreirajIdGenerator()
                    .KreirajRandomGenerator()
                    .UcitajPodrucja()
                    .UcitajUlice()
                    .UcitajSpremnike()
                    .UcitajVozila()
                    .UcitajDispecera();
        }
        catch(Exception e)
        {
            return false;
        }
        InicijalizacijaPodatakaProduct podaciProdukt = (InicijalizacijaPodatakaProduct)podatkovniBuilder.DajProdukt();
        ispis = Ispisivanje.getInstance();
        Parametri parametri = podaciProdukt.dajParametre();
        ispis.Ispisi("Init: sjeme generatora: "+podaciProdukt.dajParametre().DajVrijednost("sjemeGeneratora"));
        ispis.Ispisi("Init: izlazna datoteka: "+podaciProdukt.dajParametre().DajDatoteku("izlaz"));
        
        problemskiBuilder = new ProblemskiBuilder(podaciProdukt);
        problemskiBuilder   .KreirajPodrucja()
                            .KreirajUlice()
                            .KreirajRaspone()
                            .KreirajKorisnike()
                            .IspisiUlice()
                            .KreirajSpremnike()
                            .KreirajVozila();
        return true;
    }
    public void IzvrsiSimulaciju()
    {
        
        simulacijaBuilder = new SimulacijaBuilder(podatkovniBuilder.DajProdukt(), problemskiBuilder.DajProblemske());
        simulacijaBuilder   .KreirajStatistiku()
                            .KreirajSimulaciju()
                            .KreirajDispecera()
                            .PokreniSimulaciju()
                            .IspisiStatistiku();
    }

    public InicijalizacijaAbstractProduct DajPodatke()
    {
        return problemskiBuilder.DajPodatke();
    }
    public ProblemskiAbstractProduct DajProblemske()
    {
        return problemskiBuilder.DajProblemske();
    }
    private InicijalizacijaAbstractBuilder podatkovniBuilder;
    private ProblemskiAbstractBuilder problemskiBuilder;
    private SimulacijaAbstractBuilder simulacijaBuilder;
    private String[] args;
    private Ispisivanje ispis;
}