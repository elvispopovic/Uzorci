/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Podaci;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciIteratorSucelje;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Simulacija;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.Statistika;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class Dispecer implements SimulacijaSucelje
{
    protected SimulacijaSucelje simulacija;
    private LanacKomandiApstraktni lanacPripremi;
    private final Podaci podaciDispecer;
    private final Ispisivanje ispis;
    public  Dispecer(SimulacijaSucelje simulacija, Podaci podaci)
    {
        this.simulacija = simulacija;
        this.podaciDispecer = podaci;
        ispis = Ispisivanje.getInstance();
        ispis.Ispisi("Kreiran je dispečer.");
    }
    
    private void inicijalizirajLanacKomandi()
    {
        LanacKomandiApstraktni lanacKreni, lanacKvar, lanacIsprazni, lanacStatus, lanacKontrola;
         lanacKontrola   = new LanacKontrola(simulacija);
        (lanacStatus     = new LanacStatus(simulacija)).DodajSljedbenika(lanacKontrola);
        (lanacIsprazni   = new LanacIsprazni(simulacija)).DodajSljedbenika(lanacStatus);
        (lanacKvar       = new LanacKvar(simulacija)).DodajSljedbenika(lanacIsprazni);
        (lanacKreni      = new LanacKreni(simulacija)).DodajSljedbenika(lanacKvar);
        (lanacPripremi   = new LanacPripremi(simulacija)).DodajSljedbenika(lanacKreni);
    }
    
    public void PokreniSimulaciju(Simulacija simulacija, Statistika statistika)
    {
        this.simulacija = simulacija;
        if(this.simulacija.ProvjeriParametre() == false)
            return;
        
        inicijalizirajLanacKomandi();
        PodaciIteratorSucelje iterator = podaciDispecer.dajIterator();
        while(iterator.imaLiSlijedeceg())
        {
            String[] komanda=(iterator.slijedeci());
            ArrayList<String> vozila = new ArrayList<>();
            if(komanda.length>1)
                vozila = new ArrayList<>(Arrays.asList(komanda[1].split(Pattern.quote(","))));
            PosaljiKomandu(komanda[0],vozila);
        }
    }

    private void PosaljiKomandu(String komanda, ArrayList<String> vozila)
    {
        String[] razdvojeno = komanda.split(Pattern.quote(" "));
        lanacPripremi.ObradiKomandu(razdvojeno, vozila);
    }

    @Override
    public ArrayList<VoziloSucelje> DajListuVozila()
    {
        return simulacija.DajListuVozila();
    }

    @Override
    public ProblemskiAbstractProduct DajProblemske()
    {
        return simulacija.DajProblemske();
    }

    @Override
    public ListaVozila DajListaParkiraliste()
    {
        return simulacija.DajListaParkiraliste();
    }

    @Override
    public ListaVozila DajListaPrikupljanje()
    {
        return simulacija.DajListaPrikupljanje();
    }

    @Override
    public ListaVozila DajListaOdvoz()
    {
        return simulacija.DajListaOdvoz();
    }

    @Override
    public boolean ObradiVozilaUPrikupljanju()
    {
        return simulacija.ObradiVozilaUPrikupljanju();
    }

    @Override
    public boolean ObradiVozilaZaZbrinjavanje()
    {
        return simulacija.ObradiVozilaZaZbrinjavanje();
    }

    @Override
    public void PostaviListeUlica()
    {
        simulacija.PostaviListeUlica();
    }

    @Override
    public void PosaljiNaDeponij(int brojUListi)
    {
        simulacija.PosaljiNaDeponij(brojUListi);
    }

    @Override
    public void Pokreni()
    {
    }

    @Override
    public boolean ProvjeriParametre()
    {
        return simulacija.ProvjeriParametre();
    }
}
