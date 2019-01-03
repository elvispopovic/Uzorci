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
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloStanjeSucelje;

/**
 *
 * @author elvis
 */
public class LanacStatus implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private final Parametri parametri;
    private final ListaVozila listaPrikupljanje;
    //private final ListaVozila listaOdvoz;
    private LanacKomandiApstraktni sljedbenik;
    
    public LanacStatus(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
        listaPrikupljanje = this.simulacija.DajListaVozilaSimulacija();
        sljedbenik = null;
    }
    
    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {
        this.sljedbenik = sljedbenik;
    }
    @Override
    public void ObradiKomandu(String[] komanda)
    {
        if(komanda[0].toUpperCase().equals("STATUS"))
        {
            if(parametri.DajVrijednost("ispis")==0)
                simulacija.Ispisi("Komanda STATUS");
            obradiStatus();
        }
        else sljedbenik.ObradiKomandu(komanda);
    } 

    private void obradiStatus()
    {
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        simulacija.Ispisi("Status vozila prikazan je tablično:");
        form.format("%14s |%3s |%12s |%11s |%8s |%8s |%8s |%10s |%8s |%5s|%23s |","Status","","Naziv","Tip",
                "Vrsta","Nosivost","Popunje-","Ukupno ","Pogonski","Smjer","Popis");
        simulacija.Ispisi(sb.toString());
        sb.setLength(0);
        form.format("%14s |%3s |%12s |%11s |%8s |%8s |%8s |%10s |%8s |%5s|%23s |","vozila","ID","vozila","pogona",
                "otpada","[kg]","nost [%]","otpada[kg]","sustav ","prik.","vozača");
        simulacija.Ispisi(sb.toString());
        simulacija.Ispisi(String.join("", Collections.nCopies(131, "=")));
        ispisiStatusVozila(sb,form);
    }
    
    private void ispisiStatusVozila(StringBuilder sb, Formatter form)
    {
        VoziloSucelje vozilo;
        VoziloKontekstSucelje kontekst;
        for(int obrada=0; obrada<listaPrikupljanje.Velicina(); obrada++)
        {
            vozilo = listaPrikupljanje.DajVozilo(obrada);
            VoziloStanjeSucelje stanje = vozilo.dajKontekst().DajStanje();
            ispisiRedakVozila(sb,form, vozilo,stanje.DajNaziv());
        }
    }
    
    private void ispisiRedakVozila(StringBuilder sb, Formatter form, VoziloSucelje vozilo, String status)
    {
        String tip, otpad, vozaci;
        float popunjenost, ukupnoOtpada;
        int kapacitet, punjenje;
        Parametri param = Parametri.getInstance();
        int brojDecimala = param.DajVrijednost("brojDecimala");
        if(vozilo.dajTip()==0)
            tip="diesel";
        else
            tip="električni";
        sb.setLength(0);
        otpad = parametri.DajNazivOtpada(vozilo.dajVrstu());
        int nosivost = vozilo.dajNosivost();
        VoziloKontekstSucelje kontekst = vozilo.dajKontekst();
        popunjenost = kontekst.dajPopunjenost();
        ukupnoOtpada = vozilo.dajStatistikuVozila().dajUkupnuKolicinuOtpada();
        form.format("%14s |%3s |%12s |%11s |%8s |%8d |%8."+brojDecimala+"f |%10."+brojDecimala+"f |%3d /%3d |%5s|%23s |",
                status,vozilo.dajId(),vozilo.dajNaziv(),tip,otpad,nosivost, 
                (float)popunjenost, ukupnoOtpada,kontekst.DajKolicinuPogonskog(),vozilo.dajKapacitetPogona(),
                (kontekst.JeLiObrnutoKretanje()?" - ":" + "),kreirajPopisVozaca(vozilo,","));
        simulacija.Ispisi(sb.toString());
    }
    private String kreirajPopisVozaca(VoziloSucelje vozilo, String delimiter)
    {
        int i;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> listaVozaca = vozilo.dajVozace();
        for(i=0; i<listaVozaca.size()-1; i++)
        {
            sb.append(listaVozaca.get(i));
            sb.append(delimiter);
        }
        sb.append(listaVozaca.get(i));
        return sb.toString();
    }
}
