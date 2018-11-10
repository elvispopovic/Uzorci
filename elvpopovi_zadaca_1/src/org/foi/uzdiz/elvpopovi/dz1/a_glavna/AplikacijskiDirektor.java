/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.a_glavna;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.ProblemskiAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.ProblemskiBuilder;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Vozilo;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;

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
        InicijalizacijaAbstractBuilder podatkovniBuilder = new InicijalizacijaPodatakaBuilder(args);
        try
        {
            podatkovniBuilder 
                    .UcitajParametre()
                    .KreirajIspisivac()
                    .KreirajIdGenerator()
                    .KreirajRandomGenerator()
                    .UcitajUlice()
                    .UcitajSpremnike()
                    .UcitajVozila();
        }
        catch(Exception e)
        {
            return false;
        }
        InicijalizacijaPodatakaProduct podaciProdukt = (InicijalizacijaPodatakaProduct)podatkovniBuilder.DajProdukt();
        ispis = Ispisivanje.getInstance();
        Parametri parametri = podaciProdukt.getParametri();
        ispis.Ispisi("Init: sjeme generatora: "+podaciProdukt.getParametri().DajVrijednost("sjemeGeneratora"));
        ispis.Ispisi("Init: izlazna datoteka: "+podaciProdukt.getParametri().DajDatoteku("izlaz"));
        
        problemskiBuilder = new ProblemskiBuilder(podaciProdukt);
        problemskiBuilder   .KreirajUlice()
                            .KreirajKorisnike()
                            .KreirajSpremnike()
                            .KreirajVozila();
        return true;
    }

    public InicijalizacijaAbstractProduct DajPodatke()
    {
        return problemskiBuilder.DajPodatke();
    }
    public ProblemskiAbstractProduct DajProblemske()
    {
        return problemskiBuilder.DajProblemske();
    }
    private ProblemskiAbstractBuilder problemskiBuilder;
    private String[] args;
    private Ispisivanje ispis;
    private ArrayList<Integer> redoslijed;
    private ArrayList<Vozilo> listaZaZbrinjavanje;
}