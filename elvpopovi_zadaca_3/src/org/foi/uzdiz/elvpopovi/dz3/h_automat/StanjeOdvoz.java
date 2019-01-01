/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.StatistikaSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StanjeOdvoz implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected VoziloStatistika statistikaVozilo;
    protected MVCModelSucelje MVCmodel;
    protected int ciklusaOdvoz;
    int vrsta;
    
    @Override
    public String DajNaziv()
    {
        return naziv;
    }
    
    public StanjeOdvoz(VoziloKontekstSucelje kontekst)
    {
        naziv = "ODVOZ";
        Parametri parametri = Parametri.getInstance();
        ciklusaOdvoz = parametri.DajVrijednost("brojRadnihCiklusaZaOdvoz");
        this.kontekst = kontekst;
        if(kontekst != null)
        {
            MVCmodel = kontekst.DajMVCModel();
            this.vozilo = kontekst.DajVozilo();
            if(vozilo != null)
            {
                vrsta = vozilo.dajVrstu();
                ispisiOdvoz(0);
                statistikaVozilo = vozilo.dajStatistikuVozila();
                statistikaVozilo.PovecajBrojOdlazakaNaDeponij();
            }
            else
                vrsta = -1;
        }
    }
    
    @Override
    public boolean Prijelaz(String novoStanje)
    {
        return false;
    }
    
    @Override
    public void Napredovanje()
    {
        if(ciklusaOdvoz<=0)
        {
            float popunjenost = kontekst.dajPopunjenost();
            StatistikaSucelje statistikaOtpada = kontekst.DajStatistikuOtpada();
            if(statistikaOtpada != null && vrsta != -1)
                switch(vrsta)
                {
                    case 0: statistikaOtpada.DodajStaklo(popunjenost); break;
                    case 1: statistikaOtpada.DodajPapir(popunjenost); break;
                    case 2: statistikaOtpada.DodajMetal(popunjenost); break;
                    case 3: statistikaOtpada.DodajBio(popunjenost); break;
                    case 4: statistikaOtpada.DodajMjesano(popunjenost); break;
                }
            kontekst.Isprazni();
            if(kontekst.DajKvar())
            {
                kontekst.PostaviStanje(new StanjeParkiraliste(kontekst));
                ispisiOdvoz(2);
            }
            else
            {
                kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
                ispisiOdvoz(3);
            }
        }  
        else
            ispisiOdvoz(1);
        ciklusaOdvoz--;
    }  
    
    private void ispisiOdvoz(int mod)
    {
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        float popunjenost = (float)(kontekst.dajPopunjenost()/vozilo.dajNosivost()*100.0);
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        switch(mod)
        {
            case 0: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" odvozi otpad uz popunjenostod "+
                    rnd.round(popunjenost, brojDecimala)+"%.");
                    break;
            case 1: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je u odvozu. Preostalo je "+ciklusaOdvoz+".");
                    break;
            case 2: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je završilo odvoz i vraća se iz kvara.");
                    break;
            case 3: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je završilo odvoz i vraća se u prikupljanje.");
                    break;
        }     
    }
}
