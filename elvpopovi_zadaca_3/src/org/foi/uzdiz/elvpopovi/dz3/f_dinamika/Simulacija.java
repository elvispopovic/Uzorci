/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.io.BufferedWriter;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import java.util.ArrayList;
import java.util.HashMap;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.SimulacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.PodrucjeSucelje;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.StanjePrikupljanje;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloKontekstSucelje;
import org.foi.uzdiz.elvpopovi.dz3.i_MVC.MVCObserver;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;
import org.foi.uzdiz.elvpopovi.dz3.h_automat.VoziloStanjeSucelje;

/**
 *
 * @author elvis
 */
public class Simulacija implements SimulacijaSucelje
{
    protected int brg, brd;
    protected ArrayList<MVCObserver> mvc;
    protected Parametri parametri;
    protected ProblemskiAbstractProduct problemske;
    protected SimulacijaAbstractProduct simulacijske;
    protected int preuzimanje;
    protected Ispisivanje ispis;
    protected BufferedWriter pisacDatoteka;
    protected ListaVozila listaVozilaSimulacija;
    protected int brojRadnihCiklusaZaOdvoz;
    
    public Simulacija(ProblemskiAbstractProduct problemske, SimulacijaAbstractProduct simulacijske)
    {
        if(problemske==null)
            return;
        this.problemske = problemske;
        this.simulacijske = simulacijske;
        ispis = Ispisivanje.getInstance();
        parametri = Parametri.getInstance();
        if(ProvjeriParametre() == false)
            return;
        preuzimanje = parametri.DajVrijednost("preuzimanje");
        brg = parametri.DajVrijednost("brg");
        brd = parametri.DajVrijednost("brd");
        listaVozilaSimulacija = new ListaVozila(problemske.dajListuVozila());
        for(ListaVozila.Iterator iterator = listaVozilaSimulacija.DajIterator(); iterator.imaLiSlijedeceg();)
        {
            VoziloSucelje vozilo = iterator.slijedeci();
            if(vozilo != null && vozilo.dajKontekst() != null && simulacijske != null)
                vozilo.dajKontekst().InjektirajSimulacijske(simulacijske);
        }
        ArrayList<PodrucjeSucelje> ishodista = problemske.DajIshodistaSustava();
        if(ishodista!=null)
            PostaviListeUlica(ishodista.get(0).dajId());
        else
            PostaviListeUlica("");
    }
 
    @Override
    public ListaVozila DajListaVozilaSimulacija()
    {
        return listaVozilaSimulacija;
    }

    
    @Override
    public ArrayList<VoziloSucelje> DajListuVozilaPodaci()
    {
        return problemske.dajListuVozila();
    }
    
    @Override
    public ProblemskiAbstractProduct DajProblemske()
    {
        return problemske;
    }
    
    @Override
    public SimulacijaAbstractProduct DajSimulacijske()
    {
        return simulacijske;
    }

    @Override
    public boolean ProvjeriParametre()
    {   
        preuzimanje = parametri.DajVrijednost("preuzimanje");
        if(brojRadnihCiklusaZaOdvoz==-1||preuzimanje==-1)
        {
            Ispisi("Nije moguće učitati potrebne parametre za rad simulacije.");
            return false;
        }
        if(preuzimanje<0||preuzimanje>1)
                Ispisi("Parametar \"preuzimanje\" nije ispravno postavljen (postavljen je na "+
                        preuzimanje+"),ali će se tretirati kao postavljen na 1.");
        brojRadnihCiklusaZaOdvoz = parametri.DajVrijednost("brojRadnihCiklusaZaOdvoz");
        return true;
    }
        
    @Override
    public void Pokreni()
    {
        for(int i=0; i<listaVozilaSimulacija.Velicina(); i++)
        {
            VoziloSucelje vozilo = listaVozilaSimulacija.DajVozilo(i);
            VoziloStanjeSucelje stanje = vozilo.dajKontekst().DajStanje();
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
            Ispisi("Sva vozila su odvezla otpad. Simulacija je završena.");
    }
    
    public boolean PromijeniIshodisteSustava(ArrayList<String> vozila, String ishodisteId)
    {
        HashMap<String,VoziloSucelje> mapaVozila = problemske.dajMapuVozila();
        ArrayList<Ulica> listaUlica = problemske.dajListuUlicaIshodista(ishodisteId);
        ArrayList<VoziloSucelje> listaVozila = new ArrayList<>();
        for(String v:vozila)
            if(mapaVozila.containsKey(v))
                listaVozila.add(mapaVozila.get(v));
        if(listaVozila.size()==0)
        {
            Ispisi("Niti jedno od upisanih vozila ne postoji.");
            return false;
        }
        else
        {
            promijeniIshodisteZaListuVozila(ishodisteId, listaVozila, listaUlica);
            Ispisi("Novo ishodište je "+ishodisteId+".");
            return true;
        }
    }
    
    private void promijeniIshodisteZaListuVozila(String ishodisteId, ArrayList<VoziloSucelje> listaVozila, ArrayList<Ulica> listaUlica)
    {
        int[] redoslijed = kreirajRedoslijed(listaUlica.size());
        ArrayList<Ulica> ulice = null;
        ispisVozilaKojaMijenjajuIshodiste(listaVozila);
        for(VoziloSucelje v:listaVozila)
        {
            v.dajDodijeljeneSpremnike().clear();
            v.dajDodijeljeneUlice().clear();
            v.dajKontekst().ResetUliceISpremnici();
            ulice = napraviPlanUlica(ishodisteId, redoslijed);
            ArrayList<ArrayList<Spremnik>> spremniciPoUlicama = new ArrayList<>();
            for(Ulica u:ulice)
            {
                ArrayList<Spremnik> pronadjeno = pronadjiSpremnike(u, v.dajVrstu());
                spremniciPoUlicama.add(pronadjeno);
            }
            v.postaviDodijeljeneUlice(ulice);
            v.postaviDodijeljeneSpremnike(spremniciPoUlicama);
            v.dajKontekst().PostaviStanje(new StanjePrikupljanje(v.dajKontekst()));
            if(parametri.DajVrijednost("ispis")==0)
                Ispisi("Vozilo: "+v.dajId()+": "+v.dajNaziv()+", broj ulica: "+v.dajDodijeljeneUlice().size()+", broj spremnika: "+v.dajBrojSpremnika());
        }
        
    }
    
    private void ispisVozilaKojaMijenjajuIshodiste(ArrayList<VoziloSucelje> listaVozila)
    {
        StringBuilder sb = new StringBuilder();
        if(listaVozila.size()==1)
            Ispisi("Vozilo "+listaVozila.get(0)+" mijenja ishodište sustava i područje djelovanja.");
        else
        {
            Ispisi("Vozila ");
            sb.append("   ");
            for(VoziloSucelje v:listaVozila)
                sb.append(v.dajId()).append(": ").append(v.dajNaziv().replaceAll("\\p{Z}","")).append(", ");
            Ispisi(sb.toString());
            Ispisi("   mijenjaju ishodište sustava i područje djelovanja.");
        }
        
    }
    
    private void PostaviListeUlica(String ishodisteId)
    {
        int i, j;
        int[] redoslijed;
        PodrucjeSucelje ishodiste = null;
        if(!ishodisteId.equals(""))
        {
            ishodiste = problemske.nadjiIshodiste(ishodisteId);
            if(ishodiste == null)
                return;
        }
        ArrayList<Ulica> listaUlica = problemske.dajListuUlicaIshodista(ishodisteId);
        redoslijed = kreirajRedoslijed(listaUlica.size());
        ArrayList<Ulica> ulice = null;
        for(j=0; j<listaVozilaSimulacija.Velicina(); j++)
        {
            ulice = napraviPlanUlica(ishodisteId, redoslijed);
            VoziloSucelje v = listaVozilaSimulacija.DajVozilo(j);
            ArrayList<ArrayList<Spremnik>> spremniciPoUlicama = new ArrayList<>();
            for(Ulica u:ulice)
            {
                ArrayList<Spremnik> pronadjeno = pronadjiSpremnike(u, v.dajVrstu());
                spremniciPoUlicama.add(pronadjeno);
            }
            v.postaviDodijeljeneUlice(ulice);
            v.postaviDodijeljeneSpremnike(spremniciPoUlicama);
        }                 
    }
    private ArrayList<Ulica> napraviPlanUlica(String podrucjeId, int[] redoslijed)
    {
        ArrayList<Ulica> listaUlica = problemske.dajListuUlicaIshodista(podrucjeId);
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
        ListaVozila.Iterator iterator = listaVozilaSimulacija.DajIterator();
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

    public int BrojNecekajucihVozilaUUlici(String ulicaId)
    {
        if(ulicaId.equals(""))
            return 0;
        int brojVozila=0;
        ArrayList<VoziloSucelje> listaVozila = problemske.dajListuVozila();
        for(VoziloSucelje vozilo:listaVozila)
        {
            if(vozilo.dajKontekst().DajStanje().DajNaziv().equals("PRIKUPLJANJE"))
            {
                Ulica ulica = vozilo.dajKontekst().DajTrenutnuUlicu();
                if(ulica != null && ulica.Id().equals(ulicaId))
                    brojVozila++;
            }
        }
        return brojVozila;
    }
    
    public ArrayList<VoziloSucelje> NecekajucaVozilaUUlici(String ulicaId)
    {
        ArrayList<VoziloSucelje> izlaznaLista = new ArrayList<>();
        if(ulicaId.equals(""))
            return izlaznaLista;
        ArrayList<VoziloSucelje> listaVozila = problemske.dajListuVozila();
        for(VoziloSucelje vozilo:listaVozila)
        {
            if(vozilo.dajKontekst().DajStanje().DajNaziv().equals("PRIKUPLJANJE"))
            {
                Ulica ulica = vozilo.dajKontekst().DajTrenutnuUlicu();
                if(ulica != null && ulica.Id().equals(ulicaId))
                    izlaznaLista.add(vozilo);
            }
        }
        return izlaznaLista;
    }
    
    @Override
    public boolean provjeriZavrsetak()
    {
        boolean rezultat;
        VoziloSucelje vozilo;
        ListaVozila.Iterator iterator = listaVozilaSimulacija.DajIterator();
        rezultat = true;
        while(iterator.imaLiSlijedeceg())
        {
            vozilo = iterator.slijedeci();
            if(vozilo!=null)
            {
                String nazivStanja = vozilo.dajKontekst().DajStanje().DajNaziv();
                if((nazivStanja.equals("PRIKUPLJANJE") &&vozilo.DajTrenutnogVozaca()!=null) 
                        || nazivStanja.equals("ODVOZ") 
                        || nazivStanja.equals("PUNJENJE") || nazivStanja.equals("CEKANJE"))
                    rezultat = false;
            }
        }
        return rezultat;
    }
    
    public void Ispisi(String ispis)
    {
        Ispisi(ispis,true);
    }
    public void Ispisi(String ispis, boolean novaLinija)
    {
        Ispisivanje ispisivanje = Ispisivanje.getInstance();
        boolean aktivan = ispisivanje.isAktivan();
        if(brg==-1||brd==-1)
            ispisivanje.ispisiNaEkran(ispis, novaLinija);
        else 
            ispisivanje.ispisiUString(ispis); 
        if(aktivan)
            ispisivanje.ispisiUDatoteku(ispis, novaLinija);
    }

    public ArrayList<String> DajRetkeIspisa()
    {
        Ispisivanje ispisivanje = Ispisivanje.getInstance();
        return ispisivanje.DohvatiPodatkeMVC();
    }

    
    /*********** testiranje ***********/
    private void IspisiListuUlica()
    {   if(listaVozilaSimulacija!=null)
            for(int i=0; i<listaVozilaSimulacija.Velicina(); i++)
            {
                VoziloSucelje v = listaVozilaSimulacija.DajVozilo(i);
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
    
}
