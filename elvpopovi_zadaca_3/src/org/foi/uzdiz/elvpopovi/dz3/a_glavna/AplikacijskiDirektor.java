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
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractBuilder;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaBuilder;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCView;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;

/**
 * Aplikacijski direktor upravlja inicijalizacijom i pokretanjem svih dijelova aplikacije
 * @author elvis
 */
public class AplikacijskiDirektor
{
    AplikacijskiDirektor(String[] args)
    {
        this.args = args;
        podatkovniBuilder = new InicijalizacijaPodatakaBuilder(args);
    }
    /**
     * Inicijaliziraju se svi parametri i čitaju podaci iz datoteka
     * @return true ako je sve uspjelo
     */
    public boolean InicijalizirajParametre()
    {
        try
        {
            podatkovniBuilder 
                    .UcitajParametre()
                    .InicijalizirajMVC()
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
        return true;
    }
    /**
     * Provjeravaju se preklopnici --brg i --brd u DZ3
     * @return 
     */
    public boolean ProvjeriPreklopnike()
    {
        if(args.length>=3)
            if((Parametri.getInstance().DajVrijednost("brg")==-1)||(Parametri.getInstance().DajVrijednost("brd")==-1))
            {
                System.out.println("Neispravni argumenti --brg i/ili --brd. Aplikacija će se prekinuti.");
                return false;
            }
        return true;
    }
    /**
     * Kreiraju se potrebni objekti za rad aplikacije na osnovu ranije učitanih podataka
     * @return true ako je sve uspjelo
     */
    public boolean InicijalizirajPodatke()
    {
        Parametri parametri = Parametri.getInstance();
        InicijalizacijaPodatakaProduct podaciProdukt = (InicijalizacijaPodatakaProduct)podatkovniBuilder.DajProdukt();
        ispis = Ispisivanje.getInstance();
        if(parametri.DajVrijednost("brg")>0)
        {
            ispis.Ispisi("Zbog unešenih argumenata --brg i/ili --brd radi se o DZ_3.");
            ispis.Ispisi("Brg: "+parametri.DajVrijednost("brg")+", brd: "+parametri.DajVrijednost("brd"));
        }
        ispis.Ispisi("Init: sjeme generatora: "+podaciProdukt.dajParametre().DajVrijednost("sjemeGeneratora"));
        ispis.Ispisi("Init: izlazna datoteka: "+podaciProdukt.dajParametre().DajDatoteku("izlaz"));
        try
        {
            problemskiBuilder = new ProblemskiBuilder(podaciProdukt);
            problemskiBuilder   .KreirajPodrucja()
                                .KreirajUlice()
                                .KreirajRaspone()
                                .KreirajKorisnike()
                                .IspisiUlice()
                                .KreirajSpremnike()
                                .KreirajVozila();
        }
        catch(Exception e)
        {
            ispis.Ispisi("Nije uspjelo kreiranje objekata potrebnih za rad aplikacije.");
            return false;
        }
        return true;
    }
    /**
     * Pokreće se simulacijski dio i statistike koje prate pojedina vozila i ukupni otpad
     */
    public void IzvrsiSimulaciju()
    {
        
        simulacijaBuilder = new SimulacijaBuilder(podatkovniBuilder.DajProdukt(), problemskiBuilder.DajProblemske());
        simulacijaBuilder   .KreirajStatistiku()
                            .KreirajSimulaciju()
                            .KreirajDispecera()
                            .PokreniSimulaciju()
                            .IspisiStatistiku();
    }
    /**
     * Vraća se objekt koji sadrži sve generirane podatke
     * @return inicijalizirani podaci u zbirnom product objektu
     */
    public InicijalizacijaAbstractProduct DajPodatke()
    {
        return problemskiBuilder.DajPodatke();
    }
    /**
     * Vraća se objekt koji sadrži sve generirane objekte koje koristi aplikacija
     * @return inicijalizirani objekti u zbirnom product objektu
     */
    public ProblemskiAbstractProduct DajProblemske()
    {
        return problemskiBuilder.DajProblemske();
    }
    private MVCView MVCview;
    private InicijalizacijaAbstractBuilder podatkovniBuilder;
    private ProblemskiAbstractBuilder problemskiBuilder;
    private SimulacijaAbstractBuilder simulacijaBuilder;
    private String[] args;
    private Ispisivanje ispis;
}