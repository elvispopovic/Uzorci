/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import java.util.ArrayList;
import java.util.HashMap;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozac;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StanjePrikupljanje implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected SimulacijaSucelje simulacija;
    protected VoziloStatistika statistikaVozilo;
    protected MVCModelSucelje MVCmodel;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
    public StanjePrikupljanje(VoziloKontekstSucelje kontekst)
    {
        naziv = "PRIKUPLJANJE";
        this.kontekst = kontekst;
        if(kontekst != null)
        {
            MVCmodel = kontekst.DajMVCModel();
            simulacija = kontekst.DajSimulacijske().DajSimulacija();
            vozilo = kontekst.DajVozilo();  
            if(vozilo != null)
            {
                statistikaVozilo = vozilo.dajStatistikuVozila();
                Prijelaz("CEKANJE");
            }
        }
    }
    
    @Override
    public void Prijelaz(String novoStanje)
    {
        switch(novoStanje)
        {
            case "ODVOZ": kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
                break;
            case "KONTROLA": kontekst.PostaviStanje(new StanjeKontrola(kontekst));
                break;
            case "KVAR": kontekst.PostaviStanje(new StanjeKvar(kontekst));
                break;
            case "CEKANJE": if(provjeriPrijelazCekanje()) kontekst.PostaviStanje(new StanjeCekanje(kontekst));
                break;
        }
    }
    
    private boolean provjeriPrijelazCekanje()
    {
        Ulica ulica = kontekst.DajTrenutnuUlicu();
        if(ulica!=null && simulacija.BrojNecekajucihVozilaUUlici(ulica.Id())>2)
            return true;
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        Parametri parametri = Parametri.getInstance();
        if(provjeriVozaca() == false)
        {
            if(parametri.DajVrijednost("ispis")!=0)
                MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+": "+vozilo.dajNaziv()+" nema raspoloživog vozača.");
            return;
        }
        preskociPrazne(); 
        if(!kontekst.JeLiZavrsenoPrikupljanje())
            obradiSpremnik();
        else
            posaljiZadnjePraznjenje();
    }
    
    private boolean provjeriVozaca()
    {
        Vozac vozac = vozilo.DajTrenutnogVozaca();
        if(vozac == null)
        {
            vozilo.RotirajVozace(); //pokusava se rotirati
            if(vozilo.DajTrenutnogVozaca() == null) //i dalje nema
            {
                HashMap<Integer,Vozac> mapaVozaca = simulacija.DajListaVozilaSimulacija().DajMapuVozaca();
                for(Integer k:mapaVozaca.keySet())
                {
                    Vozac v = mapaVozaca.get(k);
                    if(v != null && v.DajPridruzenoVozilo()==null)
                    {
                        vozilo.DodajVozaca(v); //sustav dodaje vozača koji nema dodijeljeno vozilo
                        vozilo.PostaviTrenutnogVozaca(v.DajId());
                        return true;
                    }
                }
            }
            return false;
        }
        else
            return true;
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
            kontekst.SmanjiKolicinuPogonskog();
            kolicina = (float)0.0;
            spremnik = dajTrenutniSpremnik();
            if(spremnik != null)
                kolicina = spremnik.dajKolicinuOtpada(); 
            if(kontekst.DajKolicinuPogonskog()<=0)
                kontekst.PostaviStanje(new StanjePunjenjePogonskog(kontekst));
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
                if(kontekst.DajKolicinuPogonskog()<=0)
                    kontekst.PostaviStanje(new StanjePunjenjePogonskog(kontekst));
            }
            else
            {
                kontekst.PostaviStanje(new StanjeOdvoz(kontekst));
                statistikaVozilo.PovecajBrojOdlazakaNaDeponij();
                kontekst.SmanjiKolicinuPogonskog();
            }
        }
        else
        {
            kontekst.PovecajTrenutniSpremnik();
            if(kontekst.DajKolicinuPogonskog()<=0)
                kontekst.PostaviStanje(new StanjePunjenjePogonskog(kontekst));
        }
        kontekst.SmanjiKolicinuPogonskog();
    }
    
    private void ispisiPreuzimanje()
    {
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        Spremnik spremnik = vozilo.dajDodijeljeneSpremnike().get(kontekst.DajBrojTrenutneUlice()).get(kontekst.DajTrenutniSpremnik());
        float popunjenost = (float)(kontekst.dajPopunjenost()/vozilo.dajNosivost()*100.0);
            MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" preuzima "+
                    rnd.round(spremnik.dajKolicinuOtpada(),brojDecimala)+
                    " kg otpada iz spremnika "+spremnik.dajId());
            MVCmodel.Ispisi("   Popunjenost: "+rnd.round(popunjenost, brojDecimala)+
                    "%, preuzeto "+kontekst.dajPreuzetoSpremnika()+
                    " spremnika, stanje pogona: "+kontekst.DajKolicinuPogonskog()+"/"+vozilo.dajKapacitetPogona());
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
        if(dodijeljeniSpremnici == null || dodijeljeniSpremnici.size()<=kontekst.DajBrojTrenutneUlice()||
                dodijeljeniSpremnici.get(kontekst.DajBrojTrenutneUlice()).size()<=kontekst.DajTrenutniSpremnik())
            return null;
        return dodijeljeniSpremnici.get(kontekst.DajBrojTrenutneUlice()).get(kontekst.DajTrenutniSpremnik());
    }
    
}
