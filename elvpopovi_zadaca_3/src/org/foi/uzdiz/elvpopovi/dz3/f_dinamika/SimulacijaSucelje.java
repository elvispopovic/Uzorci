/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;

/**
 *
 * @author elvis
 */
public interface SimulacijaSucelje
{
    ArrayList<VoziloSucelje> DajListuVozilaPodaci();
    ProblemskiAbstractProduct DajProblemske();
    SimulacijaAbstractProduct DajSimulacijske();
    ListaVozila DajListaVozilaSimulacija();
    //ListaVozila DajListaOdvoz();
    boolean ProvjeriParametre();
    void Pokreni();
    boolean PromijeniIshodisteSustava(ArrayList<String> vozila, String ishodisteId);
    boolean ObradiStanjaVozila();
    int BrojNecekajucihVozilaUUlici(String ulicaId);
    ArrayList<VoziloSucelje> NecekajucaVozilaUUlici(String ulicaId);
    boolean provjeriZavrsetak();
    
    void Ispisi(String ispis);
    void Ispisi(String ispis, boolean novaLinija);
    ArrayList<String> DajRetkeIspisa();
}
