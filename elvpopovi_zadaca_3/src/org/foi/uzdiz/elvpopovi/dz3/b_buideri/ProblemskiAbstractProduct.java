/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.PapirKreator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.StakloKreator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.MjesanoKreator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Raspon;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.BioKreator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.MetalKreator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.Vozilo;
import java.util.ArrayList;
import java.util.Arrays;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz3.j_podrska.RandomGenerator;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.ZbrinjavanjeFactory;

/**
 *
 * @author elvis
 */
public abstract class ProblemskiAbstractProduct 
{


    public ArrayList<Spremnik> dajSpremnike() 
    {
        return spremnici;
    }

    //hook metode za prototype uzorak
    public abstract ArrayList<Ulica> dajListuUlica();
    abstract void MultiplicirajKorisnike(Korisnik[] prototipovi);
    public abstract void KreirajPodrucja();
    abstract void kreirajUlicu(String[] shema, int i);
    abstract void kreirajSpremistaZaUliceIPodrucja();
    public abstract void IspisiUlice();
    abstract void podijeliSpremnike();
    abstract void ispisiKorisnikeStat();

    public ArrayList<VoziloSucelje> dajListuVozila() 
    {
        if(vozila==null)
            return null;
        ArrayList<VoziloSucelje> transformacija = new ArrayList<>();
        for(ArrayList<VoziloSucelje> vrsta:vozila)
            for(VoziloSucelje v:vrsta)
                transformacija.add(v);
        return transformacija;
    }

    public ProblemskiAbstractProduct(InicijalizacijaPodatakaProduct podaci) 
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
            brojDecimala = podaci.dajParametre().DajVrijednost("brojDecimala");
            if(brojDecimala<0||brojDecimala>7)
                brojDecimala=0;
        }
    }
    
    final public void KreirajUlice()
    {
        ispis.Ispisi("Kreiraju se ulice.");
        
        String[] shema = podaci.dajUlice().DajShemu();
        int numerickiIndeks = Arrays.asList(shema).indexOf("broj mjesta");
        if(podaci.dajUlice()!=null)
            for(int i=0; i<podaci.dajUlice().BrojZapisa(); i++)
            {
                try
                {
                    String[] zapis = podaci.dajUlice().DajPodatak(i);
                    if(zapis.length<shema.length)
                        throw new Exception("Premalo parametara u retku.");
                    for(int j=0; j<4; j++)
                        Integer.parseInt(zapis[numerickiIndeks+j].replaceAll("\\p{Z}",""));                  
                    kreirajUlicu(shema, i);
                }
                catch(Exception e)
                {
                    ispis.Ispisi("Pogreška u zapisu "+i+" u podacima o ulicama: "+e.getMessage());
                }
            }
    }
    

    final public void KreirajRaspone()
    {
        rasponi = new Raspon[5];
        rasponi[0] = stakloKreator.KreirajRaspon(podaci);
        rasponi[1] = papirKreator.KreirajRaspon(podaci);
        rasponi[2] = metalKreator.KreirajRaspon(podaci);
        rasponi[3] = bioKreator.KreirajRaspon(podaci);
        rasponi[4] = mjesanoKreator.KreirajRaspon(podaci);
        kolicineOtpada = new float[5];
        zbrojKolicinaOtpada = new float[5];
    }

    final public void KreirajKorisnike() 
    {
        Korisnik[] korisnikPrototip = new Korisnik[3];
        MultiplicirajKorisnike(korisnikPrototip);
    }
    
    final public void KreirajSpremnike()
    {
        Parametri parametri = Parametri.getInstance();
        if(podaci.dajSpremnike()!=null)
        {
            kreirajPrototipoveSpremnika();
            spremnici = new ArrayList<>();
            podijeliSpremnike();
        }

        //testiranje
        //ispisiUlice();
        //ispisiSpremnike();
        //ispisiKorisnike();
    }
      
    final protected void PostaviSpremnik(int vrstaOtpada, ArrayList<Korisnik> korisniciKategorija)
    {
        int kategorija = korisniciKategorija.get(0).dajKategorija();
        if(protoSpremnici.get(vrstaOtpada).dajNaBroj()[kategorija]>0)
        {
            Spremnik spremnik = new Spremnik(protoSpremnici.get(vrstaOtpada));
            int brojac = spremnik.dajNaBroj()[kategorija];
            for(int i=0; i<korisniciKategorija.size(); i++)
            {
                if(brojac==0)
                {
                    brojac=spremnik.dajNaBroj()[kategorija];
                    spremnici.add(spremnik);
                    spremnik = new Spremnik(spremnik);
                }
                korisniciKategorija.get(i).DodajSpremnik(spremnik);
                spremnik.DodajKorisnika(korisniciKategorija.get(i));
                brojac--;
            }//kraj korisnika
            if(spremnik.DajKorisnike().size()>0)
                spremnici.add(spremnik);
        }
    }
    
    final public void KreirajVozila()
    {
        if(podaci.dajVozila()!=null)
        {
            KreirajPrototipoveVozila();
            vozila = new ArrayList<>();
            for(int i=0; i<5; i++)
            {
                ArrayList<VoziloSucelje> vrstaVozila = new ArrayList<>();
                vozila.add(vrstaVozila);
            }
            String[] shema = podaci.dajVozila().DajShemu();
            int tipIndeks = Arrays.asList(shema).indexOf("tip");
            popuniListuVozila(shema, tipIndeks);
        }
        //ispisiVozila();
    }
    
    private void popuniListuVozila(String[] shema, int tipIndeks)
    {
        for(int v = 0; v < podaci.dajVozila().BrojZapisa(); v++)
        {
            try
            {
                String[] zapis = podaci.dajVozila().DajPodatak(v);
                if(zapis.length<shema.length)
                    throw new Exception("Premalo parametara u retku.");
                for(int i=0; i<3; i++)
                    Integer.parseInt(zapis[i+tipIndeks].replaceAll("\\p{Z}",""));
                VoziloSucelje vozilo= new Vozilo(protoVozila,shema,zapis);
                vozila.get(vozilo.dajVrstu()).add(vozilo);
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
    
    private void kreirajPrototipoveSpremnika()
    {
        String[] shema = podaci.dajSpremnike().DajShemu();
        protoSpremnici = new ArrayList<>();
        for(int spremnik=0; spremnik < podaci.dajSpremnike().BrojZapisa(); spremnik++)
        {
            String[] zapis = podaci.dajSpremnike().DajPodatak(spremnik);
            try
            {  
                for(int i=1; i<6; i++)
                    Integer.parseInt(zapis[i].replaceAll("\\p{Z}",""));
                if(ZoviTvornicuSpremnika(shema,zapis)==false)
                    ispis.Ispisi("Nepoznati zapis ("+spremnik+") vrste spremnika (prema vrsti otpada).");
            }
            catch(NumberFormatException e)
            {
                ispis.Ispisi("Greška prilikom parsiranja podatka o spremnicima u zapisu "+spremnik+".");
            }
            catch(Exception e)
            {
                ispis.Ispisi("Greška prilikom kreiranja spremnika prema zapisu "+spremnik+".");
            }
        }
    }
    private void KreirajPrototipoveVozila()
    {
        String[] shema = podaci.dajVozila().DajShemu();
        int tipIndeks = Arrays.asList(shema).indexOf("tip");
        protoVozila = new ArrayList<>();
        for(int i=0; i<5; i++) //prema vrstama otpada zapisanima u parametrima
            ZoviTvornicuVozila(Parametri.getInstance().DajNazivOtpada(i));
    }
    private boolean ZoviTvornicuSpremnika(String[] shema, String[] zapis)
    {
        Parametri parametri = Parametri.getInstance();
        String naziv = zapis[Arrays.asList(shema).indexOf("naziv")];
                       
        if(naziv.equals(parametri.DajNazivOtpada(0))) //staklo
            protoSpremnici.add(stakloKreator.KreirajSpremnik(shema, zapis)); 
        else if(naziv.equals(parametri.DajNazivOtpada(1))) //papir
            protoSpremnici.add(papirKreator.KreirajSpremnik(shema, zapis)); 
        else if(naziv.equals(parametri.DajNazivOtpada(2))) //metal
            protoSpremnici.add(metalKreator.KreirajSpremnik(shema, zapis)); 
        else if(naziv.equals(parametri.DajNazivOtpada(3))) //bio
            protoSpremnici.add(bioKreator.KreirajSpremnik(shema, zapis)); 
        else if(naziv.equals(parametri.DajNazivOtpada(4))) //mješano
            protoSpremnici.add(mjesanoKreator.KreirajSpremnik(shema, zapis)); 
        else
        {
            ispis.Ispisi("Nazivi otpada u zapisima spremnika ne odgovaraju zapisima otpada u parametrima.");
            return false;
        }
        return true;
    }
    private boolean ZoviTvornicuVozila(String vrsta)
    {
        Parametri parametri = Parametri.getInstance();
        if(vrsta.equals(parametri.DajNazivOtpada(0))) //staklo
            protoVozila.add(stakloKreator.KreirajVozilo());
        else if(vrsta.equals(parametri.DajNazivOtpada(1))) //papir
            protoVozila.add(papirKreator.KreirajVozilo());
        else if(vrsta.equals(parametri.DajNazivOtpada(2))) //metal
            protoVozila.add(metalKreator.KreirajVozilo());
        else if(vrsta.equals(parametri.DajNazivOtpada(3))) //bio
            protoVozila.add(bioKreator.KreirajVozilo());
        else if(vrsta.equals(parametri.DajNazivOtpada(4))) //mješano
            protoVozila.add(mjesanoKreator.KreirajVozilo());
        else
        {
            ispis.Ispisi("Nazivi otpada u zapisima vozila ne odgovaraju zapisima otpada u parametrima.");
            return false;
        }
        return true;
    }

    final protected void ispisiVrstuKorisnika(int vrsta, int brojKorisnika)
    { 
        switch(vrsta)
        {
            case 0: ispis.Ispisi("   Mali korisnici ("+brojKorisnika+")"); break;
            case 1: ispis.Ispisi("   Srednji korisnici ("+brojKorisnika+")"); break;
            case 2: ispis.Ispisi("   Veliki korisnici ("+brojKorisnika+")"); break;
            default: ispis.Ispisi(">>Nepoznata vrsta korisnika. Moguća greška u podacima.");
        }
    }

    /***********************************************/
    /*    Samo za testiranje - ne ispisuje u log   */
    /***********************************************/
    private void ispisiVozila()
    {
        for(ArrayList<VoziloSucelje> listaPoVrsti:vozila)
            for(VoziloSucelje v:listaPoVrsti)
                System.out.println("Vozilo "+v.dajNaziv()+", vrsta: "+v.dajVrstu()+", tip: "+v.dajTip()+", nosivost: "+v.dajNosivost());
    }
    private void ispisiSpremnike()
    {
        if(spremnici==null)
            return;
        for(Spremnik i:spremnici)
        {
            System.out.println("Spremnik id: "+i.dajId()+", vrstaOtpada: "+i.dajVrstuOtpada()+
                    ", vrstaSpremnika: "+i.dajVrstuSpremnika()+", nosivost: "+i.dajNosivost());
            for(int j=0; j<i.dajNaBroj().length; j++)
                System.out.print(j+": "+i.dajNaBroj()[j]+" ");   
            System.out.println(System.getProperty("line.separator")+"Korisnici:");
            for(Korisnik kor:i.DajKorisnike())
                System.out.println("Korisnik id: "+kor.dajId()+", kategorija: "+kor.dajKategorija()+", ulica: "+kor.getUlica().Naziv());
        }
        System.out.println("Ukupni broj spremnika: "+spremnici.size());
    }
    
    protected final Ispisivanje ispis;
    protected final RandomGenerator rnd;
    protected final InicijalizacijaPodatakaProduct podaci;
    
    protected ArrayList<Spremnik> protoSpremnici;
    private ArrayList<Spremnik> spremnici;  
    private ArrayList<VoziloSucelje> protoVozila;
    private ArrayList<ArrayList<VoziloSucelje>> vozila;
    
    protected int brojDecimala;
    protected int[] rasponiMin;
    protected int[][] rasponiMax;
    protected Raspon[] rasponi;
    protected float[] kolicineOtpada;
    protected float[] zbrojKolicinaOtpada;
    
    ZbrinjavanjeFactory stakloKreator;
    ZbrinjavanjeFactory papirKreator;
    ZbrinjavanjeFactory metalKreator;
    ZbrinjavanjeFactory bioKreator;
    ZbrinjavanjeFactory mjesanoKreator;
    
}
