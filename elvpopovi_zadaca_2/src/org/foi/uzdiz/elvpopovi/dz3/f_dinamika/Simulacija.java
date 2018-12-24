/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloStanje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.i_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Simulacija implements SimulacijaSucelje
{
 
    public Simulacija(ProblemskiAbstractProduct problemske, Statistika statistika)
    {
        if(problemske==null)
            return;
        this.problemske = problemske;
        this.statistika = statistika;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        if(ProvjeriParametre() == false)
            return;
        
        preuzimanje = parametri.DajVrijednost("preuzimanje");
        listaPrikupljanje = new ListaVozila(problemske.dajListuVozila());
        for(ListaVozila.Iterator iterator = listaPrikupljanje.DajIterator(); iterator.imaLiSlijedeceg();)
        {
            VoziloSucelje vozilo = iterator.slijedeci();
            if(vozilo != null && vozilo.dajKontekst() != null)
                vozilo.dajKontekst().InjektirajStatistiku(statistika);
        }
        PostaviListeUlica();
    }
    
    @Override
    public ListaVozila DajListaPrikupljanje()
    {
        return listaPrikupljanje;
    }

    
    @Override
    public ArrayList<VoziloSucelje> DajListuVozila()
    {
        return problemske.dajListuVozila();
    }
    
    @Override
    public ProblemskiAbstractProduct DajProblemske()
    {
        return problemske;
    }

    @Override
    public boolean ProvjeriParametre()
    {   
        preuzimanje = parametri.DajVrijednost("preuzimanje");
        if(brojRadnihCiklusaZaOdvoz==-1||preuzimanje==-1)
        {
            ispis.Ispisi("Nije moguće učitati potrebne parametre za rad simulacije.");
            return false;
        }
        if(preuzimanje<0||preuzimanje>1)
                ispis.Ispisi("Parametar \"preuzimanje\" nije ispravno postavljen (postavljen je na "+
                        preuzimanje+"),ali će se tretirati kao postavljen na 1.");
        brojRadnihCiklusaZaOdvoz = parametri.DajVrijednost("brojRadnihCiklusaZaOdvoz");
        return true;
    }
        
    @Override
    public void Pokreni()
    {
        
        for(int i=0; i<listaPrikupljanje.Velicina(); i++)
        {
            VoziloSucelje vozilo = listaPrikupljanje.DajVozilo(i);
            VoziloStanje stanje = vozilo.dajKontekst().DajStanje();
            stanje.Prijelaz("PRIKUPLJANJE");
        }
        
        //IspisiListuUlica();
        glavnaPetlja(); 
    }
    
    private void glavnaPetlja()
    {
        boolean zavrseno=false;
        while(!zavrseno)
        {
            ObradiStanjaVozila();                
            if(provjeriZavrsetak()==true)
                break;
        } 
        if(parametri.DajVrijednost("ispis")==0)
            ispis.Ispisi("Sva vozila su odvezla otpad. Simulacija je završena.");
    }

    private void PostaviListeUlica()
    {
        int i, j;
        int[] redoslijed;
        ArrayList<Ulica> listaUlica = problemske.dajListuUlica();
        redoslijed = kreirajRedoslijed(listaUlica.size());
        ArrayList<Ulica> ulice = null;
        for(j=0; j<listaPrikupljanje.Velicina(); j++)
        {
            ulice = napraviPlanUlica(redoslijed);
            VoziloSucelje v = listaPrikupljanje.DajVozilo(j);
            ArrayList<ArrayList<Spremnik>> spremniciPoUlicama = new ArrayList<>();
            for(Ulica u:ulice)
            {
                ArrayList<Spremnik> pronadjeno = pronadjiSpremnike(u, v.dajVrstu());
                spremniciPoUlicama.add(pronadjeno);
            }
            
            v.postaviDodijeljeneUlice(ulice);
            v.postaviDodijeljeneSpremnike(spremniciPoUlicama);
        }
        if(ulice != null)
            pronadjiSpremnike(ulice.get(0),0);                  
    }
    private ArrayList<Ulica> napraviPlanUlica(int[] redoslijed)
    {
        ArrayList<Ulica> listaUlica = problemske.dajListuUlica();
        ArrayList<Ulica> ulice = new ArrayList<>(); 
        if(preuzimanje>0)
        {
            redoslijed = kreirajRedoslijed(listaUlica.size());
            ulice.clear();
            for(int i=0; i<listaUlica.size(); i++)
                ulice.add(listaUlica.get(redoslijed[i]));
        } 
        else
            for(int i=0; i<listaUlica.size(); i++)
                ulice.add(listaUlica.get(redoslijed[i]));
        return ulice;
    }

    protected int[] kreirajRedoslijed(int velicina)
    {
        int i;
        RandomGenerator rnd = RandomGenerator.getInstance();
        if(velicina==0) return null;
        ArrayList<Integer> pocetniNiz = new ArrayList<>();
        ArrayList<Integer> zavrsniNiz = new ArrayList<>();
        for(i=0; i<velicina; i++)
            pocetniNiz.add(i);
        //IspisiRedoslijed(pocetniNiz);
        while(!pocetniNiz.isEmpty())
        {
            int slucajni = rnd.dajRandomInt(0,pocetniNiz.size()-1);
            zavrsniNiz.add(pocetniNiz.get(slucajni));
            pocetniNiz.remove(slucajni);
        }  
        int[] redoslijed = new int[zavrsniNiz.size()];
        for(i=0; i<zavrsniNiz.size(); i++)
            redoslijed[i] = zavrsniNiz.get(i);
        //IspisiRedoslijed(zavrsniNiz);
        return redoslijed;
    }

    protected ArrayList<Spremnik> pronadjiSpremnike(Ulica ulica, int vrsta)
    {
        ArrayList<Spremnik> spremniciVrsteOtpada = new ArrayList<>();
        ArrayList<Spremnik> spremniciTrazeno = new ArrayList<>();
        for(Spremnik s:problemske.dajSpremnike())
            if(s.dajVrstuOtpada()==vrsta)
                spremniciVrsteOtpada.add(s);
        for(Spremnik s:spremniciVrsteOtpada)
        {         
            if(s.DajKorisnike().get(0).getUlica().equals(ulica))
                spremniciTrazeno.add(s);
        }       
        return spremniciTrazeno;
    }
    /**
    * @return false - nema vozila za zbrinjavanje; true - postoji vozilo za zbrinjavanje
    */
    @Override
    public boolean ObradiStanjaVozila()
    {        
        VoziloSucelje vozilo;
        VoziloKontekstSucelje kontekst;
        Spremnik trenutni;
        ListaVozila.Iterator iterator = listaPrikupljanje.DajIterator();
        while(iterator.imaLiSlijedeceg())
        {
            vozilo = iterator.slijedeci();
            if(vozilo!=null)
            {
                kontekst = vozilo.dajKontekst();
                kontekst.DajStanje().Napredovanje();
            }
        }
        return true;
    }
    /**
    * @return false - nema vozila za zbrinjavanje; true - postoji vozilo za zbrinjavanje
    */

    
    @Override
    public boolean provjeriZavrsetak()
    {
        boolean rezultat;
        VoziloSucelje vozilo;
        VoziloKontekstSucelje kontekst;
        ListaVozila.Iterator iterator = listaPrikupljanje.DajIterator();
        rezultat = true;
        while(iterator.imaLiSlijedeceg())
        {
            vozilo = iterator.slijedeci();
            if(vozilo!=null)
            {
                String nazivStanja = vozilo.dajKontekst().DajStanje().DajNaziv();
                if(nazivStanja.equals("PRIKUPLJANJE") || nazivStanja.equals("ODVOZ"))
                    rezultat = false;
            }
        }
        return rezultat;
    }

    
    /*********** testiranje ***********/
    private void IspisiListuUlica()
    {   if(listaPrikupljanje!=null)
            for(int i=0; i<listaPrikupljanje.Velicina(); i++)
            {
                VoziloSucelje v = listaPrikupljanje.DajVozilo(i);
                System.out.println("Vozilo: "+v.dajNaziv()+", vrsta: "+v.dajVrstu());
                if(v.dajDodijeljeneUlice().size()>0)
                    for(int j=0; j<v.dajDodijeljeneUlice().size(); j++)
                    {
                        System.out.println("   "+j+": "+"Ulica: "+v.dajDodijeljeneUlice().get(j).Naziv());
                        if(v.dajDodijeljeneSpremnike().size()>0)
                        {
                            ArrayList<Spremnik> spremnici = v.dajDodijeljeneSpremnike().get(j);
                            for(Spremnik s:spremnici)
                               System.out.println("      "+j+": Spremnik "+
                                       s.dajId()+": vrsta: "+s.dajVrstuOtpada()+
                               ", tip: "+s.dajVrstuSpremnika());
                        }
                    }
            }
    }
    protected Statistika statistika;
    protected Parametri parametri;
    protected ProblemskiAbstractProduct problemske;
    protected int preuzimanje;
    protected Ispisivanje ispis;

    protected ListaVozila listaPrikupljanje;
    protected int brojRadnihCiklusaZaOdvoz;
}
