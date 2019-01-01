/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.h_automat;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloStatistika;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCModelSucelje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class StanjePunjenjePogonskog implements VoziloStanjeSucelje
{
    public final String naziv;
    protected VoziloSucelje vozilo;
    protected VoziloKontekstSucelje kontekst;
    protected VoziloStatistika statistikaVozilo;
    protected MVCModelSucelje MVCmodel;
    protected int ciklusaPunjenjaPogona;
    public StanjePunjenjePogonskog(VoziloKontekstSucelje kontekst)
    {
        this.kontekst = kontekst;
        naziv = "PUNJENJE";
        if(kontekst != null)
        {
            MVCmodel = kontekst.DajMVCModel();
            vozilo = kontekst.DajVozilo(); 
            if(vozilo != null)
            {
                ciklusaPunjenjaPogona = vozilo.dajPunjenjePogona();
                statistikaVozilo = vozilo.dajStatistikuVozila();
                ispisiPunjenje(0);
                statistikaVozilo.PovecajBrojPunjenjaPogona();
            }
        }
    }

    @Override
    public void Napredovanje()
    {
        if(ciklusaPunjenjaPogona<=0)
        {
            kontekst.ObnoviKolicinuPogonskog();
            kontekst.PostaviStanje(new StanjePrikupljanje(kontekst));
            ispisiPunjenje(2);
        }
        else
            ispisiPunjenje(1);
        ciklusaPunjenjaPogona--;
    }

    @Override
    public String DajNaziv()
    {
        return naziv;
    }

    @Override
    public boolean Prijelaz(String novoStanje)
    {
        return false;
    }
    
    private void ispisiPunjenje(int mod)
    {
        RandomGenerator rnd = RandomGenerator.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(parametri.DajVrijednost("ispis")!=0)
            return;
        float punjenjePogona = (float)(ciklusaPunjenjaPogona/vozilo.dajPunjenjePogona()*100.0);
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        switch(mod)
        {
            case 0: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" puni pogonski sustav.");
                    break;
            case 1: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je na punjenju. Preostalo je "+ciklusaPunjenjaPogona+".");
                    break;
            case 2: MVCmodel.Ispisi("Vozilo "+vozilo.dajId()+", "+vozilo.dajNaziv()+" je završilo punjenje i vraća se u prikupljanje.");
                    break;
        }
    }
    
}
