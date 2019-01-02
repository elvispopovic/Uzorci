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
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaTablicno;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCView;

/**
 *
 * @author elvis
 */
public class SimulacijaProduct implements SimulacijaAbstractProduct
{

    public SimulacijaSucelje DajSimulacija()
    {
        return simulacija;
    }

    public Dispecer DajDispecer()
    {
        return dispecer;
    }

    public Statistika DajStatistika()
    {
        return statistika;
    }
    private SimulacijaSucelje simulacija;
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
        statistika = new Statistika(this);
    }
    @Override
    public void KreirajSimulaciju()
    {
        simulacija = new Simulacija(problemski, this);
    }
    @Override
    public void KreirajDispecera()
    {
        dispecer = new Dispecer(simulacija,this.podaci.dajDispecera());
        //na ovom mjestu dispečer preuzima ulogu modela od ispisivača
        MVCView view = podaci.dajMVCview();
        if(view != null)
            view.InicijalizirajModel(dispecer);
    }
    @Override
    public void PokreniSimulaciju()
    {
        if(simulacija!=null && statistika!=null)
            if(podaci.dajDispecera()==null)
                simulacija.Pokreni();
            else
                dispecer.Pokreni(simulacija, statistika);
    }
    @Override
    public void IspisiStatistiku()
    {
        StatistikaSucelje statistikaTablicno = new StatistikaTablicno(statistika);
        ArrayList<VoziloSucelje> vozila = simulacija.DajListuVozilaPodaci();
        statistikaTablicno.IspisiStatistiku(vozila);
    }
}
