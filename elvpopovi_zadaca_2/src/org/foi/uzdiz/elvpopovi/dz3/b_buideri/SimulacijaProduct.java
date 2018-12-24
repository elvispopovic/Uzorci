/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.g_upravljanje.Dispecer;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Simulacija;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaTablicno;

/**
 *
 * @author elvis
 */
public class SimulacijaProduct implements SimulacijaAbstractProduct
{
    private Simulacija simulacija;
    private Dispecer dispecer;
    private Statistika statistika;
    private final InicijalizacijaAbstractProduct podaci;
    private final ProblemskiAbstractProduct problemski;
    
    public SimulacijaProduct(InicijalizacijaAbstractProduct podaci, ProblemskiAbstractProduct problemski)
    {
        this.podaci = podaci;
        this.problemski = problemski;
    }
    @Override
    public void KreirajStatistiku()
    {
        statistika = new Statistika();
    }
    @Override
    public void KreirajSimulaciju()
    {
        simulacija = new Simulacija(problemski, statistika);
    }
    @Override
    public void KreirajDispecera()
    {
        dispecer = new Dispecer(simulacija,this.podaci.dajDispecera());
    }
    @Override
    public void PokreniSimulaciju()
    {
        if(simulacija!=null && statistika!=null)
            if(podaci.dajDispecera()==null)
                simulacija.Pokreni();
            else
                dispecer.PokreniSimulaciju(simulacija, statistika);
    }
    @Override
    public void IspisiStatistiku()
    {
        StatistikaSucelje statistikaTablicno = new StatistikaTablicno(statistika);
        ArrayList<VoziloSucelje> vozila = simulacija.DajListuVozila();
        statistikaTablicno.IspisiStatistiku(vozila);
    }
}
