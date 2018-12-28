/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciIteratorSucelje;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.PodaciSucelje;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Podrucje;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.PodrucjeSucelje;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;

/**
 *
 * @author elvis
 */
public class ProblemskiProductPodrucja extends ProblemskiAbstractProduct
{  
    Podrucje ishodisteSustava;
    
    @Override
    public ArrayList<Ulica> dajListuUlica() 
    {
        ArrayList<Ulica> rezultat = new ArrayList<>();
        if(ishodisteSustava!=null)
            ishodisteSustava.NapuniListuUlica(rezultat, true);
        return rezultat;
    }

    public ProblemskiProductPodrucja(InicijalizacijaPodatakaProduct podaci)
    {
        super(podaci);
        ishodisteSustava = null;
        ispis.Ispisi("Poziva se problem iz DZ_2.");
    }
    
    @Override
    void kreirajSpremistaZaUliceIPodrucja()
    {
        
    }
    @Override
    void kreirajUlicu(String[] shema, int i)
    {  
        ArrayList<PodrucjeSucelje> listaPodrucja = new ArrayList<>();
        ishodisteSustava.NapuniListuPodrucja(listaPodrucja, true);
        
        Ulica ulica = new Ulica(shema);
        ulica.Inicijaliziraj(podaci.dajUlice().DajPodatak(i));
        for(PodrucjeSucelje p : listaPodrucja)
        {
            for( String k2 : p.dajDijelove())
                if(k2.equals(ulica.Id()))
                    p.DodajUlicu(ulica);   
        }
    }
    @Override
    public void IspisiUlice()
    {
        ispis.prikaziRetke();
        ispis.Ispisi("Ispisuje se tablica otpada u pojedinim područjima i ulicama:");
        ArrayList<PodrucjeSucelje> listaPodrucja = new ArrayList<>();
        ishodisteSustava.NapuniListuPodrucja(listaPodrucja, true);
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        form.format("%13s |%30s |%12s |%11s |%11s |%10s |%13s |%12s","Opis","Naziv", 
        "staklo [kg]","papir [kg]","metal [kg]","bio [kg]","mješano [kg]","ukupno [kg]");
        ispis.Ispisi(sb.toString());
        ispis.Ispisi(" "+String.join("", Collections.nCopies(125, "=")));
        ishodisteSustava.IspisiKolicineOtpada();

    }
    
    @Override
    void MultiplicirajKorisnike(Korisnik[] prototipovi)
    {
        ArrayList<Ulica> listaUlica = new ArrayList<>();
        ishodisteSustava.NapuniListuUlica(listaUlica, true);
        for (Ulica ulica : listaUlica) 
        {
            int[] mjesta = ulica.dajMjesta();
            for(int i=0; i<5; i++)
                zbrojKolicinaOtpada[i]=0.0f;
            for(int i=0; i<3; i++)
                prototipovi[i] = new Korisnik(ulica,i,podaci.dajSpremnike().BrojZapisa());
            for(int j=0; j<mjesta.length; j++) //j= mali, srednji, veliki 
                for(int i=0; i<mjesta[j]; i++)
                {   
                    for(int vrsta = 0; vrsta<5; vrsta++)
                    {
                        kolicineOtpada[vrsta]=rnd.dajRandomFloat(rasponi[vrsta].dajMinimum()[j], 
                        rasponi[vrsta].dajMaksimum()[j],brojDecimala);
                        zbrojKolicinaOtpada[vrsta]+=kolicineOtpada[vrsta];
                    }
                    Korisnik korisnik = new Korisnik(prototipovi[j], kolicineOtpada);
                    ulica.dodajKorisnika(korisnik);
                }
        }
    }
    @Override
    public void KreirajPodrucja()
    {
        ispis.Ispisi("Kreiraju se područja.");
        ArrayList<String> dijelovi = new ArrayList<>();
        PodaciSucelje podaciPodrucja = podaci.dajPodrucja();
        HashMap<String,Podrucje> listaPodrucja = parsirajPodrucja(podaciPodrucja, dijelovi);
        popuniPodrucja(listaPodrucja);
        for(String k : listaPodrucja.keySet())
            if(dijelovi.contains(k)==false)
            {
                ishodisteSustava = listaPodrucja.get(k);
                break;
            }
    }
    
    private HashMap<String,Podrucje> parsirajPodrucja(PodaciSucelje podaciPodrucja, ArrayList<String> dijelovi)
    {
        HashMap<String,Podrucje> listaPodrucja = new HashMap<>();
        String[] shema = podaciPodrucja.DajShemu();
        int idIndex = Arrays.asList(shema).indexOf("id");
            if(idIndex==-1)
                idIndex = Arrays.asList(shema).indexOf("d");
        int nazivIndex = Arrays.asList(shema).indexOf("naziv");
        int dijeloviIndex = Arrays.asList(shema).indexOf("dijelovi");
        PodaciIteratorSucelje iterator = podaciPodrucja.dajIterator();
        while(iterator.imaLiSlijedeceg()==true)
        {
            String[] si = iterator.slijedeci();
            if(dijeloviIndex>=si.length)
                continue;
            String[] d = si[dijeloviIndex].split(Pattern.quote(","));
            listaPodrucja.put(si[idIndex],new Podrucje(si[idIndex],si[nazivIndex],d));
            for(String s : d)
                dijelovi.add(s);
        }
        return listaPodrucja;
    }
   
    private void popuniPodrucja(HashMap<String,Podrucje> listaPodrucja)
    {
        for(String k : listaPodrucja.keySet())
        {
            Podrucje p = listaPodrucja.get(k);
            String[] dijelovi = p.dajDijelove();
            for(String d : dijelovi)
            {
                if(d.charAt(0)=='p' && listaPodrucja.get(d)!=null)
                    p.DodajPodrucje(listaPodrucja.get(d));
            }
        }
    }
    
    @Override
    void ispisiKorisnikeStat()
    {
        ArrayList<Ulica> listaUlica = new ArrayList<>();
        ishodisteSustava.NapuniListuUlica(listaUlica, true);
        for(Ulica ulica : listaUlica)
        {
            ispis.Ispisi(ulica.Naziv()+", broj korisnika: "+(ulica.dajMjesta()[0]+ulica.dajMjesta()[1]+
            ulica.dajMjesta()[2]));
            ArrayList<ArrayList<Korisnik>>korisnici = ulica.dajKorisnike();
            for(int vrsta=0; vrsta<3; vrsta++)
            {
                ispisiVrstuKorisnika(vrsta, ulica.dajMjesta()[vrsta]);
                ArrayList<Korisnik> korisniciVrsta = korisnici.get(vrsta);
                for(int i=0; i<korisniciVrsta.size(); i++)
                {
                    Korisnik korisnik = korisniciVrsta.get(i);
                    ispis.Ispisi("      Korisnik "+korisnik.dajId()+": ",false);
                    float[] kolicine = korisnik.dajKolicineOtpada();
                    for(int j=0; j<kolicine.length; j++)
                        ispis.Ispisi((podaci.dajParametre().DajNazivOtpada(j)+": "+
                        rnd.round(kolicine[j],brojDecimala)+" kg "),false);
                    ispis.Ispisi("");
                }
            }
        }
    }
    
   @Override
    void podijeliSpremnike()
    {
        ArrayList<Ulica> listaUlica = new ArrayList<>();
        ishodisteSustava.NapuniListuUlica(listaUlica, true);
        for(Ulica ulica : listaUlica)
        {
            ArrayList<ArrayList<Korisnik>> korisnici = ulica.dajKorisnike();
            for(int s = 0; s < protoSpremnici.size(); s++)
                for(int kategorija = 0; kategorija < 3; kategorija++)
                {
                    ArrayList<Korisnik> korisniciKategorija = korisnici.get(kategorija);
                    if(korisniciKategorija!=null && korisniciKategorija.size()>0)
                        PostaviSpremnik(s,korisniciKategorija);
                }
        }
    }
    
    /* za testiranje */
    void ispisiPodrucja()
    {
        ArrayList<PodrucjeSucelje> podrucja = new ArrayList<>();
        ishodisteSustava.NapuniListuPodrucja(podrucja, true);
        System.out.println("Ishodiste sustava "+ishodisteSustava.dajId()+": "+ishodisteSustava.dajNaziv());
        for(PodrucjeSucelje p : podrucja)
        {
            System.out.println("Podrucje "+p.dajId()+": "+p.dajNaziv());
            ArrayList<Ulica> listaUlica = new ArrayList<>();
            p.NapuniListuUlica(listaUlica, false);
            for(Ulica ulica : listaUlica)
                System.out.println("   Ulica "+ulica.Id()+": "+ulica.Naziv());
        }
    }
     
    
}
