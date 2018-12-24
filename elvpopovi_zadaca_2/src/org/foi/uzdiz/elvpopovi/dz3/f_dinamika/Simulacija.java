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
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.h_podrska.RandomGenerator;

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
        vozila = problemske.dajListuVozila();
        parametri = Parametri.getInstance();
        preuzimanje = parametri.DajVrijednost("preuzimanje");
        ispis = Ispisivanje.getInstance();
        listaUlica = problemske.dajListuUlica();
        listaOdvoz = new ListaVozila();
        listaParkiraliste = new ListaVozila(problemske.dajListuVozila());
        listaPrikupljanje = new ListaVozila();
    }

    @Override
    public ListaVozila DajListaParkiraliste()
    {
        return listaParkiraliste;
    }
    
    @Override
    public ListaVozila DajListaPrikupljanje()
    {
        return listaPrikupljanje;
    }

    @Override
    public ListaVozila DajListaOdvoz()
    {
        return listaOdvoz;
    }
    
    @Override
    public ArrayList<VoziloSucelje> DajListuVozila()
    {
        return vozila;
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
        ProvjeriParametre();
        for(int i=0; i<listaParkiraliste.Velicina(); i++)
        {
            VoziloSucelje vozilo = listaParkiraliste.DajVozilo(i);
            if(vozilo!=null)
                listaPrikupljanje.UbaciVozilo(vozilo);
        }
        listaParkiraliste.ObrisiListu();
        PostaviListeUlica();
        //IspisiListuUlica();
        glavnaPetlja(); 
    }
    
    private void glavnaPetlja()
    {
        boolean zavrseno=false;
        while(!zavrseno)
        {
            zavrseno = true;
            if((ObradiVozilaUPrikupljanju()==false)||
               (ObradiVozilaZaZbrinjavanje()==false))
                zavrseno=false;
        } 
        if(parametri.DajVrijednost("ispis")==0)
            ispis.Ispisi("Sva vozila su odvezla otpad. Simulacija je završena.");
    }
    
    @Override
    public void PostaviListeUlica()
    {
        int i, j;
        int[] redoslijed;
        redoslijed = kreirajRedoslijed2(listaUlica.size());
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
        ArrayList<Ulica> ulice = new ArrayList<>(); 
        for(int i=0; i<listaUlica.size(); i++)
            ulice.add(listaUlica.get(redoslijed[i]));
        if(preuzimanje>0)
        {
            redoslijed = kreirajRedoslijed2(listaUlica.size());
            ulice.clear();
            for(int i=0; i<listaUlica.size(); i++)
                ulice.add(listaUlica.get(redoslijed[i]));
        } 
        return ulice;
    }

    protected int[] kreirajRedoslijed1(int velicina)
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
    //bez slucajnog redoslijeda
    protected int[] kreirajRedoslijed2(int velicina)
    {
        if(velicina==0) return null;
        int[] redoslijed = new int[velicina];
        for(int i=0; i<redoslijed.length; i++)
            redoslijed[i]=i;
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
            ArrayList<Korisnik> korisnici = s.DajKorisnike();            
            if(s.DajKorisnike().get(0).getUlica().equals(ulica))
                spremniciTrazeno.add(s);
        }       
        return spremniciTrazeno;
    }
    @Override
    public boolean ObradiVozilaUPrikupljanju()
    {
        if(listaPrikupljanje.Velicina()<1)
            return true;
        int[] redoslijed = kreirajRedoslijed2(listaPrikupljanje.Velicina());
        VoziloSucelje vozilo;
        for(int obrada=0; obrada < redoslijed.length; obrada++)
        {   
            if((vozilo = listaPrikupljanje.DajVozilo(redoslijed[obrada])) == null)
                return true;
            if(vozilo.dajKontekst().DajKvar())
                continue;
            if(vozilo.IsprazniSpremnik()==true)
                zavrsiSVozilom(vozilo, redoslijed[obrada]);
            else if((vozilo.ProvjeriNapunjenost())==true)
                PosaljiNaDeponij(redoslijed[obrada]);
        }
        return false;
    }
    @Override
    public boolean ObradiVozilaZaZbrinjavanje()
    {
        if(listaOdvoz.Velicina()<1)
            return true;
        VoziloSucelje vozilo;
        for(int obrada=0; obrada < listaOdvoz.Velicina(); obrada++)
        {
            if((vozilo = listaOdvoz.DajVozilo(obrada))== null)
                return true;
            if(vozilo.ProvjeriOdvoz()==true) 
            {
                listaOdvoz.IzdvojiVozilo(obrada);
                listaPrikupljanje.UbaciVozilo(vozilo);
            }
        }
        return false;
    }
    
    @Override
    public void PosaljiNaDeponij(int brojUListi)
    {
        if(listaPrikupljanje == null || brojUListi>=listaPrikupljanje.Velicina())
            return;
        VoziloSucelje vozilo = listaPrikupljanje.IzdvojiVozilo(brojUListi); 
        if(vozilo == null)
            return;
        float popunjenost = vozilo.dajKontekst().dajPopunjenost();
        switch(vozilo.dajVrstu())
        {
            case 0: statistika.DodajStaklo(popunjenost); break;
            case 1: statistika.DodajPapir(popunjenost); break;
            case 2: statistika.DodajMetal(popunjenost); break;
            case 3: statistika.DodajBio(popunjenost); break;
            case 4: statistika.DodajMjesano(popunjenost); break;
        }
        vozilo.IsprazniVozilo(brojRadnihCiklusaZaOdvoz);
        listaOdvoz.UbaciVozilo(vozilo); 
    }
    protected void zavrsiSVozilom(VoziloSucelje vozilo, int brojUListi)
    {
        if(parametri.DajVrijednost("ispis")==0)
            ispis.Ispisi("Vozilo "+vozilo.dajNaziv()+" je završilo.");
        listaPrikupljanje.IzdvojiVozilo(brojUListi);
        vozilo.dajKontekst().PostaviZavrseno();
    }
    
    void PrebaciNaParkiraliste(VoziloSucelje vozilo)
    {
        listaParkiraliste.UbaciVozilo(vozilo);
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

    protected ListaVozila listaParkiraliste, listaPrikupljanje, listaOdvoz;
    protected int brojRadnihCiklusaZaOdvoz;
    protected ArrayList<Ulica> listaUlica;
    protected ArrayList<VoziloSucelje> vozila;
    
}
