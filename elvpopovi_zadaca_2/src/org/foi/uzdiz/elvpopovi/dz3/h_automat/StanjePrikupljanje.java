/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StanjePrikupljanje implements VoziloStanje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected VoziloStatistika statistikaVozilo;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
    public StanjePrikupljanje(VoziloKontekstSucelje kontekst)
    {
        naziv = "PRIKUPLJANJE";
        this.kontekst = kontekst;
        if(kontekst == null)
            return;
        this.vozilo = kontekst.DajVozilo();  
        if(vozilo != null)
            statistikaVozilo = vozilo.dajStatistikuVozila();
    }
    
    @Override
    public boolean Prijelaz(String novoStanje)
    {
        if(novoStanje.equals("ODVOZ"))
        {
            kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
            return true;
        }
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        preskociPrazne();
        if(!kontekst.JeLiZavrsenoPrikupljanje())
            obradiSpremnik();
        else
            posaljiZadnjePraznjenje();
    }
    
    private void preskociPrazne()
    {
        Spremnik spremnik = dajTrenutniSpremnik();
        float kolicina = (float)0.0;
        if(spremnik != null)
            kolicina = spremnik.dajKolicinuOtpada();
        while(kolicina==0.0 && !kontekst.JeLiZavrsenoPrikupljanje())
        {
            kontekst.PovecajTrenutniSpremnik();
            kolicina = (float)0.0;
            spremnik = dajTrenutniSpremnik();
            if(spremnik != null)
                kolicina = spremnik.dajKolicinuOtpada(); 
        };
    }
    
    private void obradiSpremnik()
    {
        Spremnik spremnik = dajTrenutniSpremnik();
        if(spremnik != null && spremnik.dajKolicinuOtpada()>0.0)
        {
            if(kontekst.dajPopunjenost()+spremnik.dajKolicinuOtpada()<(float)vozilo.dajNosivost())
            {
                kontekst.PovecajPopunjenost(spremnik.dajKolicinuOtpada());
                ispisiPreuzimanje();
                statistikaVozilo.PovecajBrojSpremnika();
                statistikaVozilo.PovecajBrojMjesta(spremnik.DajKorisnike().size());
                statistikaVozilo.PovecajUkupnuKolicinuOtpada(spremnik.dajKolicinuOtpada());
                spremnik.IsprazniSpremnik();
                kontekst.PovecajTrenutniSpremnik();
            }
            else
            {
                kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
                statistikaVozilo.PovecajBrojOdlazakaNaDeponij();
            }
        }
        else
            kontekst.PovecajTrenutniSpremnik();
    }
    
    private void ispisiPreuzimanje()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri == null || parametri.DajVrijednost("preuzimanje")!=0 || vozilo == null)
            return;
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        Spremnik spremnik = vozilo.dajDodijeljeneSpremnike().get(kontekst.DajTrenutnuUlicu()).get(kontekst.DajTrenutniSpremnik());
        float popunjenost = (float)(kontekst.dajPopunjenost()/vozilo.dajNosivost()*100.0);
        ispis.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" preuzima "+
                rnd.round(spremnik.dajKolicinuOtpada(),brojDecimala)+
                " kg otpada iz spremnika "+spremnik.dajId()+", popunjenost: "+rnd.round(popunjenost, brojDecimala)+
                "%, preuzeto "+kontekst.dajPreuzetoSpremnika()+" spremnika.");
    }
    
    private void posaljiZadnjePraznjenje()
    {
        if(kontekst.dajPopunjenost()>0.0)
        {
            kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
            statistikaVozilo.PovecajBrojOdlazakaNaDeponij();
        }
        else
            kontekst.PostaviStanje(new StanjeZavrseno(kontekst));
    }

    private Spremnik dajTrenutniSpremnik()
    {
        ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici = vozilo.dajDodijeljeneSpremnike();
        if(dodijeljeniSpremnici == null || dodijeljeniSpremnici.size()<=kontekst.DajTrenutnuUlicu()||
                dodijeljeniSpremnici.get(kontekst.DajTrenutnuUlicu()).size()<=kontekst.DajTrenutniSpremnik())
            return null;
        return dodijeljeniSpremnici.get(kontekst.DajTrenutnuUlicu()).get(kontekst.DajTrenutniSpremnik());
    }
    
}
