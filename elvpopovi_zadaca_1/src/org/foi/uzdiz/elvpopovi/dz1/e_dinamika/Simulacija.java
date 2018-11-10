/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.e_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Vozilo;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Simulacija 
{
    public Simulacija(InicijalizacijaAbstractProduct podaci, ProblemskiAbstractProduct problemske)
    {
        if(problemske==null||podaci==null)
            return;
        this.problemske = problemske;
        this.podaci = podaci;
        ispis = Ispisivanje.getInstance();
        listaPrikupljanje = new ListaVozila(problemske.getListaVozila());
        listaOdvoz = new ListaVozila();
        listaUlica = problemske.getListaUlica();
        statistika = new Statistika();
    }
    public void Pokreni()
    {
        Parametri parametri = podaci.getParametri();
        RandomGenerator rnd = RandomGenerator.getInstance();
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        int ispisPreklopnik = parametri.DajVrijednost("ispis");
        int preuzimanje = parametri.DajVrijednost("preuzimanje");
        brojRadnihCiklusaZaOdvoz = parametri.DajVrijednost("brojRadnihCiklusaZaOdvoz");
       
        if(brojRadnihCiklusaZaOdvoz==-1||ispisPreklopnik==-1||preuzimanje==-1)
        {
            ispis.Ispisi("Nije moguće učitati potrebne parametre za rad simulacije.");
            return;
        }
        
        postaviListeUlica(preuzimanje);
        //IspisiListuUlica();
        boolean zavrseno=false;
        while(!zavrseno)
        {
            zavrseno = true;
            if(obradiVozilaUPrikupljanju()==false)
                zavrseno=false;
            obradiVozilaZaZbrinjavanje();
            /*
            try
            {
                sleep(100);
            }
            catch(InterruptedException e)
            {
                System.err.println("Dretva ne može otići na spavanje.");
            }
            */
        }
         if(parametri.DajVrijednost("ispis")==0)
            ispis.Ispisi("Sva vozila su odvezla otpad. Simulacija je završena.");
         ispis.Ispisi("");
         ispis.Ispisi("STATISTIKA");
         float[] otpadUkupno = statistika.UkupneKolicine();
         
         ArrayList<Vozilo> vozila = problemske.getListaVozila();

         for(int i = 0; i<otpadUkupno.length; i++)
             ispis.Ispisi(parametri.DajNazivOtpada(i)+": "+rnd.round(otpadUkupno[i],brojDecimala)+"kg");
         for(int i=0; i<vozila.size(); i++)
         {
             ispis.Ispisi(vozila.get(i).getNaziv()+" je preuzeo otpad "+vozila.get(i).getBrojSpremnika()+
                     " spremnika tj. "+vozila.get(i).getBrojMjesta()+" mjesta.");
             ispis.Ispisi("   Ukupna količina otpada: "+rnd.round(vozila.get(i).getKolicinaOtpada(),brojDecimala)+
                     " kg, broj odvoza na deponij: "+vozila.get(i).getBrojOdlazakaNaDeponij());
             
         }
    }
    

    //redoslijed je dan generatorom slucajnog broja
    private boolean obradiVozilaUPrikupljanju()
    {
        Vozilo vozilo;
        float popunjenost;
        Parametri parametri = Parametri.getInstance();
        if(listaPrikupljanje.Velicina()<1)
            return true;
        int[] redoslijed = kreirajRedoslijed(listaPrikupljanje.Velicina());
        if(redoslijed==null)
            return true;
        for(int obrada=0; obrada < redoslijed.length; obrada++)
        {   
            vozilo = listaPrikupljanje.DajVozilo(redoslijed[obrada]);
            //System.out.println("Prikupljanje: Obrađujem vozilo "+vozilo.getNaziv()+", vrsta: "+vozilo.getVrsta());
            if(vozilo.IsprazniSpremnik()==true)
            {
                if(parametri.DajVrijednost("ispis")==0)
                    ispis.Ispisi("Vozilo "+vozilo.getNaziv()+" je završilo.");
                Vozilo v = listaPrikupljanje.IzdvojiVozilo(redoslijed[obrada]);
                return false;
            }
            popunjenost = vozilo.getPopunjenost();
            if((vozilo.ProvjeriNapunjenost(brojRadnihCiklusaZaOdvoz))==true)
            {
                listaPrikupljanje.IzdvojiVozilo(redoslijed[obrada]); 
                                
                switch(vozilo.getVrsta())
                {
                    case 0: statistika.DodajStaklo(popunjenost); break;
                    case 1: statistika.DodajPapir(popunjenost); break;
                    case 2: statistika.DodajMetal(popunjenost); break;
                    case 3: statistika.DodajBio(popunjenost); break;
                    case 4: statistika.DodajMjesano(popunjenost); break;
                }
                listaOdvoz.UbaciVozilo(vozilo);
                return false;
            }
        }
        return false;
    }
    private void obradiVozilaZaZbrinjavanje()
    {
        if(listaOdvoz.Velicina()<1)
            return;
        for(int obrada=0; obrada < listaOdvoz.Velicina(); obrada++)
        {
            Vozilo vozilo = listaOdvoz.DajVozilo(obrada);
            //System.out.println("Odvoz: Obrađujem vozilo "+vozilo.getNaziv());
            if(vozilo.ProvjeriOdvoz()==true)
            {
                listaOdvoz.IzdvojiVozilo(obrada);
                listaPrikupljanje.UbaciVozilo(vozilo);
            }
        }
    }
    private int[] kreirajRedoslijed(int velicina)
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
            int slucajni = rnd.getRandomInt(0,pocetniNiz.size()-1);
            zavrsniNiz.add(pocetniNiz.get(slucajni));
            pocetniNiz.remove(slucajni);
        }  
        int[] redoslijed = new int[zavrsniNiz.size()];
        for(i=0; i<zavrsniNiz.size(); i++)
            redoslijed[i] = zavrsniNiz.get(i);
        return redoslijed;
        //IspisiRedoslijed(zavrsniNiz);
    }
    private void postaviListeUlica(int preuzimanje)
    {
        int i, j;
        int[] redoslijed;
        ArrayList<Ulica> ulice;
        ArrayList<ArrayList<Spremnik>> spremnici=new ArrayList<ArrayList<Spremnik>>();
        if(preuzimanje<0||preuzimanje>1)
                ispis.Ispisi("Parametar \"preuzimanje\" nije ispravno postavljen (postavljen je na "+preuzimanje+"), ali će se tretirati kao postavljen na 1.");
        redoslijed = kreirajRedoslijed(listaUlica.size());
        ulice = new ArrayList<>(); 
        for(i=0; i<listaUlica.size(); i++)
            ulice.add(listaUlica.get(redoslijed[i]));
        
        for(j=0; j<listaPrikupljanje.Velicina(); j++)
        {
            Vozilo v = listaPrikupljanje.DajVozilo(j);
            if(preuzimanje>0)
            {
                redoslijed = kreirajRedoslijed(listaUlica.size());
                ulice.clear();
                for(i=0; i<listaUlica.size(); i++)
                {
                    ulice.add(listaUlica.get(redoslijed[i]));
                }
            } 
            ArrayList<ArrayList<Spremnik>> spremniciPoUlicama = new ArrayList<>();
            for(Ulica u:ulice)
            {
                ArrayList<Spremnik> pronadjeno = pronadjiSpremnike(u, v.getVrsta());
                spremniciPoUlicama.add(pronadjeno);
            }
            v.setDodijeljeneUlice(ulice);
            v.setDodijeljeniSpremnici(spremniciPoUlicama);
        }
        pronadjiSpremnike(ulice.get(0),0);
        
    }
    private ArrayList<Spremnik> pronadjiSpremnike(Ulica ulica, int vrsta)
    {
        ArrayList<Spremnik> spremniciVrsteOtpada = new ArrayList<>();
        ArrayList<Spremnik> spremniciTrazeno = new ArrayList<>();
        for(Spremnik s:problemske.getSpremnici())
            if(s.getVrstaOtpada()==vrsta)
                spremniciVrsteOtpada.add(s);
        for(Spremnik s:spremniciVrsteOtpada)
        {
            ArrayList<Korisnik> korisnici = s.DajKorisnike();            
            if(s.DajKorisnike().get(0).getUlica().equals(ulica))
            {
                spremniciTrazeno.add(s);
            }
        }       
        return spremniciTrazeno;
    }
    /* testiranje */
    private void IspisiRedoslijed(ArrayList<Integer> niz)
    {
        if(niz==null) return;
        for(int i:niz)
            System.out.print(i+" ");
        System.out.println("");
    }
    private void IspisiListuUlica()
    {   if(listaPrikupljanje!=null)
            for(int i=0; i<listaPrikupljanje.Velicina(); i++)
            {
                Vozilo v = listaPrikupljanje.DajVozilo(i);
                System.out.println("Vozilo: "+v.getNaziv()+", vrsta: "+v.getVrsta());
                if(v.getDodijeljeneUlice().size()>0)
                    for(int j=0; j<v.getDodijeljeneUlice().size(); j++)
                    {
                        System.out.println("   "+j+": "+"Ulica: "+v.getDodijeljeneUlice().get(j).Naziv());
                        if(v.getDodijeljeniSpremnici().size()>0)
                        {
                            ArrayList<Spremnik> spremnici = v.getDodijeljeniSpremnici().get(j);
                            for(Spremnik s:spremnici)
                               System.out.println("      "+j+": Spremnik "+s.getId()+": vrsta: "+s.getVrstaOtpada()+", tip: "+s.getVrstaSpremnika());
                        }
                    }
            }
    }
    private Ispisivanje ispis;
    private ProblemskiAbstractProduct problemske;
    private InicijalizacijaAbstractProduct podaci;
    private ListaVozila listaPrikupljanje, listaOdvoz;
    private int brojRadnihCiklusaZaOdvoz;
    private ArrayList<Ulica> listaUlica;
    private StatistikaSucelje statistika;
}
