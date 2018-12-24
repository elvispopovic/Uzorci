/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozilo;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;

/**
 *
 * @author elvis
 */
public class LanacStatus implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Ispisivanje ispis;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    private final ListaVozila listaOdvoz;
    private final ListaVozila listaParkiraliste;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacStatus(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaParkiraliste = this.simulacija.DajListaParkiraliste();
        listaPrikupljanje = this.simulacija.DajListaPrikupljanje();
        listaOdvoz        = this.simulacija.DajListaOdvoz();
        sljedbenik = null;
    }
    
    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {
        this.sljedbenik = sljedbenik;
    }
    @Override
    public void ObradiKomandu(String komanda[], ArrayList<String> parametriVozila)
    {
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        if(parametriVozila == null)
            return;
        if(komanda[0].equals("STATUS"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Komanda STATUS");
            ispis.Ispisi("Status vozila prikazan je tablično:");
            form.format("%14s |%4s |%12s |%12s |%10s |%8s |%11s |%12s","Status","ID","Naziv","Tip",
                    "Vrsta","Nosivost","Popunjenost","Ukupno otpada");
            ispis.Ispisi(sb.toString());
            ispis.Ispisi(String.join("", Collections.nCopies(98, "=")));
            ispisiStatusVozila(sb,form,listaParkiraliste, "Parkiralište");
            ispisiStatusVozila(sb,form,listaPrikupljanje, "Prikupljanje");
            ispisiStatusVozilaUKontroli(sb,form);
            ispisiStatusVozila(sb,form,listaOdvoz,"Odvoz");
            ispisiStatusZavrsenihVozila(sb,form);
        }
        else sljedbenik.ObradiKomandu(komanda, parametriVozila);
    } 
    
    private void ispisiStatusVozila(StringBuilder sb, Formatter form, ListaVozila lista, String status)
    {
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        sb.setLength(0);
        for(int i=0; i<lista.Velicina(); i++)
        {
            VoziloSucelje vozilo = lista.DajVozilo(i);
            if(vozilo.dajKontekst().DajKvar())
                continue;
            if(vozilo.dajTip()==0)
                tip="diesel";
            else
                tip="električni";
            sb.setLength(0);
            otpad = parametri.DajNazivOtpada(vozilo.dajVrstu());
            int nosivost = vozilo.dajNosivost();
            popunjenost = vozilo.dajKontekst().dajPopunjenost();
            ukupnoOtpada = vozilo.dajStatistiku().dajUkupnuKolicinuOtpada();
            form.format("%14s |%4s |%12s |%12s |%10s |%8d |%11.1f |%12s",
                    status,vozilo.dajId(),vozilo.dajNaziv(),tip,otpad,nosivost, 
                    (float)popunjenost, ukupnoOtpada);
            ispis.Ispisi(sb.toString());
        }
    }
    private void ispisiStatusZavrsenihVozila(StringBuilder sb, Formatter form)
    {
        ArrayList<VoziloSucelje> listaVozila = simulacija.DajProblemske().dajListuVozila();
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        for(VoziloSucelje vozilo:listaVozila)
        {
            if(!vozilo.dajKontekst().DajZavrseno())
                continue;
            if(vozilo.dajTip()==0)
                tip="diesel";
            else
                tip="električni";
            sb.setLength(0);
            otpad = parametri.DajNazivOtpada(vozilo.dajVrstu());
            int nosivost = vozilo.dajNosivost();
            popunjenost = vozilo.dajKontekst().dajPopunjenost();
            ukupnoOtpada = vozilo.dajStatistiku().dajUkupnuKolicinuOtpada();
            form.format("%14s |%4s |%12s |%12s |%10s |%8d |%11.1f |%12s",
                    "Završeno",vozilo.dajId(),vozilo.dajNaziv(),tip,otpad,nosivost, 
                    (float)popunjenost, ukupnoOtpada);
            ispis.Ispisi(sb.toString());
        } 
    }
    private void ispisiStatusVozilaUKontroli(StringBuilder sb, Formatter form)
    {
        ArrayList<VoziloSucelje> listaVozila = simulacija.DajProblemske().dajListuVozila();
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        for(VoziloSucelje vozilo:listaVozila)
        {
            if(!vozilo.dajKontekst().DajKontrolu())
                continue;
            if(vozilo.dajTip()==0)
                tip="diesel";
            else
                tip="električni";
            sb.setLength(0);
            otpad = parametri.DajNazivOtpada(vozilo.dajVrstu());
            int nosivost = vozilo.dajNosivost();
            popunjenost = vozilo.dajKontekst().dajPopunjenost();
            ukupnoOtpada = vozilo.dajStatistiku().dajUkupnuKolicinuOtpada();
            form.format("%14s |%4s |%12s |%12s |%10s |%8d |%11.1f |%12s",
                    "Kontrola",vozilo.dajId(),vozilo.dajNaziv(),tip,otpad,nosivost, 
                    (float)popunjenost, ukupnoOtpada);
            ispis.Ispisi(sb.toString());
        }
    }
}
