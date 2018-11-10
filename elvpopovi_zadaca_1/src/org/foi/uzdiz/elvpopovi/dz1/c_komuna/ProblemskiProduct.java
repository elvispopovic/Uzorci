/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;

import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.MjesanoKreator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.PapirKreator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.ZbrinjavanjeFactoryMethod;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Raspon;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.MetalKreator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.BioKreator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Vozilo;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.StakloKreator;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Spremnik;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaPodatakaProduct;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class ProblemskiProduct implements ProblemskiAbstractProduct
{

    @Override
    public ArrayList<Spremnik> getSpremnici() 
    {
        return spremnici;
    }

    @Override
    public ArrayList<Ulica> getListaUlica() 
    {
        return ulice;
    }

    @Override
    public ArrayList<Vozilo> getListaVozila() 
    {
        if(vozila==null)
            return null;
        ArrayList<Vozilo> transformacija = new ArrayList<>();
        for(ArrayList<Vozilo> vrsta:vozila)
            for(Vozilo v:vrsta)
                transformacija.add(v);
        return transformacija;
    }

    public ProblemskiProduct(InicijalizacijaPodatakaProduct podaci) 
    {
        this.podaci = podaci;
        rnd = RandomGenerator.getInstance();
        ispis = Ispisivanje.getInstance();
        stakloKreator = new StakloKreator();
        papirKreator = new PapirKreator();
        metalKreator = new MetalKreator();
        bioKreator = new BioKreator();
        mjesanoKreator = new MjesanoKreator();
        if(podaci!=null)
        {
            ulice = new ArrayList<Ulica>();
            brojDecimala = podaci.getParametri().DajVrijednost("brojDecimala");
            if(brojDecimala<0||brojDecimala>7)
                brojDecimala=0;
        }
    }
    
    @Override
    public void KreirajUlice()
    {
        if(podaci.getUlice()!=null)
            for(int i=0; i<podaci.getUlice().BrojZapisa(); i++)
            {
                try
                {
                    String[] zapis = podaci.getUlice().DajPodatak(i);
                    if(zapis.length<5)
                        throw new Exception("Premalo parametara u retku.");
                    for(int j=1; j<5; j++)
                        Integer.parseInt(zapis[j]);
                    Ulica ulica = new Ulica();
                    ulica.Init(podaci.getUlice().DajPodatak(i));
                    ulice.add(ulica);
                }
                catch(Exception e)
                {
                    ispis.Ispisi("Pogreška u zapisu "+i+" u podacima o ulicama: "+e.getMessage());
                }
            }
    }
    @Override
    public void KreirajKorisnike() 
    {
        Korisnik[] korisnikPrototip = new Korisnik[3];
        float[] kolicineOtpada = new float[5];
        float[] zbrojKolicinaOtpada = new float[5];
        Raspon[] rasponi = new Raspon[5];
        rasponi[0] = stakloKreator.KreirajRaspon(podaci);
        rasponi[1] = papirKreator.KreirajRaspon(podaci);
        rasponi[2] = metalKreator.KreirajRaspon(podaci);
        rasponi[3] = bioKreator.KreirajRaspon(podaci);
        rasponi[4] = mjesanoKreator.KreirajRaspon(podaci);
        ispis.Ispisi("Količine otpada po ulicama [kg]");
        for (Ulica ulica : ulice) 
        {
            int[] mjesta = ulica.dajMjesta();
            for(int i=0; i<5; i++)
                zbrojKolicinaOtpada[i]=0.0f;
            for(int i=0; i<3; i++)
                korisnikPrototip[i] = new Korisnik(ulica,i,podaci.getSpremnici().BrojZapisa());
            for(int j=0; j<mjesta.length; j++) //j= mali, srednji, veliki 
            {
                for(int i=0; i<mjesta[j]; i++)
                {   
                    for(int vrsta = 0; vrsta<5; vrsta++)
                    {
                        kolicineOtpada[vrsta]=rnd.getRandomFloat(rasponi[vrsta].getMin()[j], rasponi[vrsta].getMax()[j],brojDecimala);
                        zbrojKolicinaOtpada[vrsta]+=kolicineOtpada[vrsta];
                    }
                    Korisnik korisnik = new Korisnik(korisnikPrototip[j], kolicineOtpada);
                    //float[] kolic = korisnik.dajKolicineOtpada();
                    //System.out.println("Količine otpada - staklo: "+kolic[0]+", papir: "+kolic[1]+", metal: "+kolic[2]+", bio: "+kolic[3]+", mjesano: "+kolic[4]);
                    ulica.dodajKorisnika(korisnik);
                }
            }
            ispis.Ispisi(ulica.Naziv()+": staklo: "+zbrojKolicinaOtpada[0]+
                    ", papir: "+rnd.round(zbrojKolicinaOtpada[1], brojDecimala)+", metal: "+rnd.round(zbrojKolicinaOtpada[2], brojDecimala)+
                    ", bio: "+rnd.round(zbrojKolicinaOtpada[3], brojDecimala)+", miješano: "+rnd.round(zbrojKolicinaOtpada[4], brojDecimala));
        }
    }
    @Override
    public void KreirajSpremnike()
    {
        ArrayList<Spremnik> protoSpremnici = new ArrayList<>();
        int brojVrstaSpremnika, brojIspravnihZapisa;
        Parametri parametri = Parametri.getInstance();
        
        //kreiranje prototipova spremnika
        if(podaci.getSpremnici()!=null)
        {
            brojVrstaSpremnika = podaci.getSpremnici().BrojZapisa();
            brojIspravnihZapisa = 0;
            for(int spremnik=0; spremnik < brojVrstaSpremnika; spremnik++)
            {
                String[] zapis = podaci.getSpremnici().DajPodatak(spremnik);
                int tip, nosivost;
                int[] naBroj = new int[3];
                try
                {
                    if(zapis.length<6)
                        throw new Exception("Premali broj podataka u retku "+spremnik+" zapisa spremnika.");
                    for(int i=1; i<5; i++)
                        Integer.parseInt(zapis[i]);
                    tip = Integer.parseInt(zapis[1]);
                    naBroj[0] = Integer.parseInt(zapis[2]);
                    naBroj[1] = Integer.parseInt(zapis[3]);
                    naBroj[2] = Integer.parseInt(zapis[4]);
                    nosivost = Integer.parseInt(zapis[5]);
                    brojIspravnihZapisa++;
                    switch (zapis[0]) {
                        case "staklo":
                            protoSpremnici.add(stakloKreator.KreirajSpremnik(tip,naBroj, nosivost));
                            break;
                        case "papir":
                            protoSpremnici.add(papirKreator.KreirajSpremnik(tip, naBroj, nosivost));
                            break;
                        case "metal":
                            protoSpremnici.add(metalKreator.KreirajSpremnik(tip, naBroj, nosivost));
                            break;
                        case "bio":
                            protoSpremnici.add(bioKreator.KreirajSpremnik(tip, naBroj, nosivost));
                            break;
                        case "mješano":
                            protoSpremnici.add(mjesanoKreator.KreirajSpremnik(tip, naBroj, nosivost));
                            break;
                        default:
                            break;
                    }
                }
                catch(NumberFormatException e)
                {
                    ispis.Ispisi("Greška prilikom parsiranja podatka o spremnicima u zapisu "+spremnik+".");
                }  
                catch(Exception e)
                {
                    ispis.Ispisi(e.getMessage());
                }
            }
        
            spremnici = new ArrayList<>();
            for(Ulica ulica : ulice)
            {
                ArrayList<ArrayList<Korisnik>> korisnici = ulica.dajKorisnike();
                for(int s = 0; s < brojIspravnihZapisa; s++)
                {
                    for(int kategorija = 0; kategorija < 3; kategorija++)
                    {
                        //kopiranje spremnika odredjene vrste
                        ArrayList<Korisnik> korisnik = korisnici.get(kategorija);
                        if(protoSpremnici.get(s).getNaBroj()[kategorija]>0)
                        {
                            Spremnik spremnik = new Spremnik(protoSpremnici.get(s));
                            int brojac = spremnik.getNaBroj()[kategorija];
                            for(int i=0; i<korisnik.size(); i++)
                            {
                                if(brojac==0)
                                {
                                    brojac=spremnik.getNaBroj()[kategorija];
                                    spremnici.add(spremnik);
                                    spremnik = new Spremnik(spremnik);
                                }
                                korisnik.get(i).DodajSpremnik(spremnik);
                                spremnik.DodajKorisnika(korisnik.get(i));

                                brojac--;
                            }//kraj korisnika
                            if(spremnik.DajKorisnike().size()>0)
                                spremnici.add(spremnik);
                        }
                    }//kraj vrsta
                }//kraj vrsta
            }//kraj ulica  
        }
        if(parametri.DajVrijednost("ispis")==0)
            statKorisnici();
        //testiranje
        //ispisiUlice();
        //ispisiSpremnike();
        
        //ispisiKorisnike();
    }
    @Override
    public void KreirajVozila()
    {
        int brojVozila;
        vozila = new ArrayList<ArrayList<Vozilo>>();
        for(int i=0; i<5; i++)
        {
            ArrayList<Vozilo> vrstaVozila = new ArrayList<>();
            vozila.add(vrstaVozila);
        }
        ArrayList<Vozilo> protoVozila = new ArrayList<>();
        protoVozila.add(stakloKreator.KreirajVozilo());
        protoVozila.add(papirKreator.KreirajVozilo());
        protoVozila.add(metalKreator.KreirajVozilo());
        protoVozila.add(bioKreator.KreirajVozilo());
        protoVozila.add(mjesanoKreator.KreirajVozilo());
        if(podaci.getVozila()!=null&&protoVozila!=null)
        {
            brojVozila = podaci.getVozila().BrojZapisa();
            for(int v = 0; v < brojVozila; v++)
            {
                try
                {
                    String[] zapis = podaci.getVozila().DajPodatak(v);
                    if(zapis.length<5)
                        throw new Exception("Premalo parametara u retku.");
                    for(int i=1; i<4; i++)
                        Integer.parseInt(zapis[i]);
                    String naziv = zapis[0];
                    int tip = Integer.parseInt(zapis[1]);
                    int vrsta = Integer.parseInt(zapis[2]);
                    int nosivost = Integer.parseInt(zapis[3]);
                    String[] vozaci = zapis[4].split(Pattern.quote(",")); 
                    
                    Vozilo vozilo= new Vozilo(protoVozila.get(vrsta),naziv,tip,vozaci,nosivost);
                    vozila.get(vrsta).add(vozilo);
                }
                catch(NumberFormatException e)
                {
                    ispis.Ispisi("Greška prilikom parsiranja podatka o vozilima u zapisu "+v+": "+e.getMessage());
                }
                catch(Exception e)
                {
                    ispis.Ispisi(e.getMessage());
                }
            }
        }
        //ispisiVozila();
    }
    private void statKorisnici()
    {
        ispis.Ispisi("Statistika za korisnike");
        Parametri parametri = Parametri.getInstance();
        
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        for(Ulica ulica : ulice)
        {
            ispis.Ispisi(ulica.Naziv()+", broj korisnika: "+(ulica.dajMjesta()[0]+ulica.dajMjesta()[1]+ulica.dajMjesta()[2]));
            ArrayList<ArrayList<Korisnik>>korisnici = ulica.dajKorisnike();
            for(int vrsta=0; vrsta<3; vrsta++)
            {
                switch(vrsta)
                {
                    case 0: ispis.Ispisi("   Mali korisnici ("+ulica.dajMjesta()[vrsta]+")"); break;
                    case 1: ispis.Ispisi("   Srednji korisnici ("+ulica.dajMjesta()[vrsta]+")"); break;
                    case 2: ispis.Ispisi("   Veliki korisnici ("+ulica.dajMjesta()[vrsta]+")"); break;
                }
                
                ArrayList<Korisnik> korisniciVrsta = korisnici.get(vrsta);
                for(int i=0; i<korisniciVrsta.size(); i++)
                {
                    Korisnik k = korisniciVrsta.get(i);
                    ispis.Ispisi("      Korisnik "+k.dajId()+": ",false);
                    float[] kolicine = k.dajKolicineOtpada();
                    for(int j=0; j<kolicine.length; j++)
                        ispis.Ispisi((podaci.getParametri().DajNazivOtpada(j)+": "+rnd.round(kolicine[j],brojDecimala)+" kg "),false);
                    ispis.Ispisi("");
                }
            }
        }
    }

    /***********************************************/
    /*    Samo za testiranje - ne ispisuje u log   */
    /***********************************************/
    private void ispisiVozila()
    {
        for(ArrayList<Vozilo> listaPoVrsti:vozila)
            for(Vozilo v:listaPoVrsti)
                System.out.println("Vozilo "+v.getNaziv()+", vrsta: "+v.getVrsta()+", tip: "+v.getTip()+", nosivost: "+v.getNosivost());
    }
    private void ispisiSpremnike()
    {
        if(spremnici==null)
            return;
        for(Spremnik i:spremnici)
        {
            System.out.println("Spremnik id: "+i.getId()+", vrstaOtpada: "+i.getVrstaOtpada()+", vrstaSpremnika: "+i.getVrstaSpremnika());
            for(int j=0; j<i.getNaBroj().length; j++)
                System.out.print(j+": "+i.getNaBroj()[j]+" ");   
            System.out.println(System.getProperty("line.separator")+"Korisnici:");
            for(Korisnik kor:i.DajKorisnike())
                System.out.println("Korisnik id: "+kor.dajId()+", kategorija: "+kor.dajKategorija()+", ulica: "+kor.getUlica().Naziv());
        }
        System.out.println("Ukupni broj spremnika: "+spremnici.size());
    }
    /*
        Samo za testiranje - ne ispisuje u log
    */
    private void ispisiKorisnike()
    {
        if(ulice==null)
            return;
        for(Ulica ulica : ulice)
        {
            ArrayList<ArrayList<Korisnik>>korisnici = ulica.dajKorisnike();
            System.out.println("Ulica: "+ulica.Naziv());
            for(int vrsta=0; vrsta<3; vrsta++)
            {
                ArrayList<Korisnik> korisniciVrsta = korisnici.get(vrsta);

                for(int i=0; i<korisniciVrsta.size(); i++)
                {
                    Korisnik k = korisniciVrsta.get(i);
                    System.out.println("Korisnik "+k.dajId()+", kategorija "+k.dajKategorija());
                    ArrayList<Spremnik> spremniciKorisnika = k.DajSpremnike();
                    for(int s=0; s<spremniciKorisnika.size(); s++)
                    {
                        Spremnik sp = spremniciKorisnika.get(s);
                        System.out.print(sp.getImenovanjeVrste().get(sp.getVrstaOtpada())+"("+sp.getVrstaSpremnika()+") id: "+sp.getId()+", ");
                    }
                    System.out.println("");
                }
            }
        }
    }
    /*
        Samo za testiranje - ne ispisuje u log
    */
    private void ispisiUlice()
    {   
        if(ulice==null)
            return;
        for(Ulica ulica:ulice)
        {
            ArrayList<ArrayList<Korisnik>>korisnici = ulica.dajKorisnike();
            System.out.println("Ulica: "+ulica.Naziv());
            //mali
            for(int vrsta=0; vrsta<3; vrsta++)
            {
                ArrayList<Korisnik> korisniciVrsta = korisnici.get(vrsta);
                for(int i=0; i<korisniciVrsta.size(); i++)
                {
                    float[] kolicine = korisniciVrsta.get(i).dajKolicineOtpada();
                    System.out.println("Mali korisnik "+korisniciVrsta.get(i).dajId()+":  staklo: "+kolicine[0]+
                            ", papir: "+kolicine[1]+", metal: "+kolicine[2]+", bio: "+kolicine[3]+", mijesano: "+kolicine[4]);
                }
            }
            
        }
    }
    private final Ispisivanje ispis;
    private final RandomGenerator rnd;
    private final InicijalizacijaPodatakaProduct podaci;
    private ArrayList<Ulica> ulice;
    private ArrayList<Spremnik> spremnici;    
    private ArrayList<ArrayList<Vozilo>> vozila;
    
    private int brojDecimala;
    private int[] rasponiMin;
    private int[][] rasponiMax;
    
    ZbrinjavanjeFactoryMethod stakloKreator;
    ZbrinjavanjeFactoryMethod papirKreator;
    ZbrinjavanjeFactoryMethod metalKreator;
    ZbrinjavanjeFactoryMethod bioKreator;
    ZbrinjavanjeFactoryMethod mjesanoKreator;
    
}
