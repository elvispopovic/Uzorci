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
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.ListaVozila;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;


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
    //private final ListaVozila listaOdvoz;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacStatus(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        listaPrikupljanje = this.simulacija.DajListaPrikupljanje();
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
            ispisiStatusVozila(sb,form);
        }
        else sljedbenik.ObradiKomandu(komanda, parametriVozila);
    } 

    private void ispisiStatusVozila(StringBuilder sb, Formatter form)
    {
        VoziloSucelje vozilo;
        VoziloKontekstSucelje kontekst;
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        for(int obrada=0; obrada<listaPrikupljanje.Velicina(); obrada++)
        {
            vozilo = listaPrikupljanje.DajVozilo(obrada);
            kontekst = vozilo.dajKontekst();
            if(kontekst.DajStanje().DajNaziv().equals("KONTROLA"))
                ispisiRedakVozila(sb,form, vozilo,"Kontrola");
            else if(kontekst.DajStanje().DajNaziv().equals("PARKIRALISTE"))
                ispisiRedakVozila(sb,form, vozilo,"Parkiraliste");
            else if(kontekst.DajStanje().DajNaziv().equals("PRIKUPLJANJE"))
                ispisiRedakVozila(sb,form, vozilo,"Prikupljanje");
            else if(kontekst.DajStanje().DajNaziv().equals("ODVOZ"))
                ispisiRedakVozila(sb,form, vozilo,"Odvoz");
            else if(kontekst.DajStanje().DajNaziv().equals("KVAR"))
                ispisiRedakVozila(sb,form, vozilo,"Kvar");
            else if(kontekst.DajStanje().DajNaziv().equals("ZAVRSENO"))
                ispisiRedakVozila(sb,form, vozilo,"Zavrseno");
            else
                ispisiRedakVozila(sb,form, vozilo,"Prikupljanje");
        }
    }
    
    private void ispisiRedakVozila(StringBuilder sb, Formatter form, VoziloSucelje vozilo, String status)
    {
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        Parametri param = Parametri.getInstance();
        int brojDecimala = param.DajVrijednost("brojDecimala");
        if(vozilo.dajTip()==0)
            tip="diesel";
        else
            tip="električni";
        sb.setLength(0);
        otpad = parametri.DajNazivOtpada(vozilo.dajVrstu());
        int nosivost = vozilo.dajNosivost();
        popunjenost = vozilo.dajKontekst().dajPopunjenost();
        ukupnoOtpada = vozilo.dajStatistikuVozila().dajUkupnuKolicinuOtpada();
        form.format("%14s |%4s |%12s |%12s |%10s |%8d |%11."+brojDecimala+"f |%12."+brojDecimala+"f",
                status,vozilo.dajId(),vozilo.dajNaziv(),tip,otpad,nosivost, 
                (float)popunjenost, ukupnoOtpada);
        ispis.Ispisi(sb.toString());
    }
}
