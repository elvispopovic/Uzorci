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
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
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
    
    /*
    @Override
    public ArrayList<Ulica> dajListuUlica() 
    {
        ArrayList<Ulica> rezultat = new ArrayList<>();
        if(ishodisteSustava!=null)
            ishodisteSustava.NapuniListuUlica(rezultat, true);
        return rezultat;
    }
    */
    /**
     * Traži ulice u određenom ishodištu i vraća listu svih ulica u tom sustavu
     * @param podrucjeId Sifra određenog područja
     * @return Lista pronađenih ulica
     */
    public ArrayList<Ulica> dajListuUlicaIshodista(String ishodisteId)
    {
        PodrucjeSucelje ishodiste = null;
        ArrayList<Ulica> listaUlica = new ArrayList<>();
        ishodiste = nadjiIshodiste(ishodisteId);
        if(ishodiste != null)
            ishodiste.NapuniListuUlica(listaUlica, true);
        return listaUlica;
    }
    
    /**
     * Na osnovu Sifre koja se daje kao string, u listi ishodišta sustava
     * pretražuje se određeno ishodište sustava
     * @param podrucjeId Sifra određenog ishodista sustava
     * @return Vraćeno ishodište sustava prema šifri, null ako ne postoji
     */
    public PodrucjeSucelje nadjiIshodiste(String podrucjeId)
    {
        PodrucjeSucelje ishodiste = null;
        if(ishodistaSustava == null || ishodistaSustava.size() == 0)
            return null;
        for(PodrucjeSucelje p:ishodistaSustava)
            if(p.dajId().equals(podrucjeId))
            {
                ishodiste = p;
                break;
            }
        return ishodiste;
    }
    
    /**
     * Konstruktor problemskog produkta, detektira i vrstu DZ prema unešenim argumentima
     * @param podaci Produkt bildera podaci prosljeđuje se u konstruktor kao parametar
     */
    public ProblemskiProductPodrucja(InicijalizacijaPodatakaProduct podaci)
    {
        super(podaci);
        Parametri parametri = Parametri.getInstance();
        //ishodisteSustava = null;
        ishodistaSustava = new ArrayList<>();
        if(parametri.DajVrijednost("brg")==-1||parametri.DajVrijednost("brd")==-1)
            ispis.Ispisi("Poziva se problem iz DZ_2.");
        else
            ispis.Ispisi("Poziva se problem iz DZ_3.");
    }
    /**
     * Kreira ulicu i provjerava sva područja u svim ishodištima. Ako određeno područje
     * sadrži tu ulicu, ulica mu se pridružuje. 
     * @param shema Shema iz datoteke ulice, prvi red u datoteci
     * @param i     Redni broj podatka u daoteci ulica  
     */
    @Override
    void kreirajUlicu(String[] shema, int i)
    {  
        HashMap<String,PodrucjeSucelje> mapaPodrucja = new HashMap<>();
        Ulica ulica = new Ulica(shema);
        ulica.Inicijaliziraj(podaci.dajUlice().DajPodatak(i));
        for(PodrucjeSucelje ishodiste:ishodistaSustava)
        {
            ishodiste.NapuniMapuPodrucja(mapaPodrucja, true);
            for( String k2 : ishodiste.dajDijelove())
                if(k2.equals(ulica.Id()))
                    ishodiste.DodajUlicu(ulica);  
        }
        for(String k : mapaPodrucja.keySet())
        {
            PodrucjeSucelje p = mapaPodrucja.get(k);
            for( String k2 : p.dajDijelove())
                if(k2.equals(ulica.Id()))
                    p.DodajUlicu(ulica);   
        }
    }
    /**
     * Ispisuje ulice u svim ishodištima sustava i njihovim područjima
     */
    public void IspisiUlice()
    {
        ispis.prikaziRetke();
        for(PodrucjeSucelje p:ishodistaSustava)
        {
            ispis.Ispisi("Ishodiste sustava: "+p.dajId()+": "+p.dajNaziv());
            IspisiUlice(p.dajId());
        }
    }
    /**
     * Tablično ispisivanje informacija o otpadu u određenom području
     * Ispisuje se ukupni otpad koji uključuje i otpad u podpodručjima (rekurzivno)
     * @param podrucjeId Šifra područja za koje se ispisuje količina otpada
     */
    @Override
    public void IspisiUlice(String podrucjeId)
    {
        PodrucjeSucelje ishodiste = null;
        ispis.Ispisi("Ispisuje se tablica otpada u pojedinim područjima i ulicama:");
        HashMap<String,PodrucjeSucelje> mapaPodrucja = new HashMap<>();
        ishodiste = nadjiIshodiste(podrucjeId);
        if(ishodiste == null)
            return;
        ishodiste.NapuniMapuPodrucja(mapaPodrucja, true);
        StringBuilder sb = new StringBuilder();
        Formatter form = new Formatter(sb);
        form.format("%13s |%30s |%12s |%11s |%11s |%10s |%13s |%12s","Opis","Naziv", 
        "staklo [kg]","papir [kg]","metal [kg]","bio [kg]","mješano [kg]","ukupno [kg]");
        ispis.Ispisi(sb.toString());
        ispis.Ispisi(" "+String.join("", Collections.nCopies(125, "=")));
        ishodiste.IspisiKolicineOtpada();
    }
    /**
     * Multipliciraju se korisnici prema prototipu korisnika 
     * @param prototipovi lista prototipova korisnika
     */
    @Override
    public void MultiplicirajKorisnike(Korisnik[] prototipovi)
    {
        HashMap<String, Ulica> mapaUlica = new HashMap<>();
        for(PodrucjeSucelje ishodiste:ishodistaSustava)
            ishodiste.NapuniMapuUlica(mapaUlica, true);
        for(String k : mapaUlica.keySet())
        {
            Ulica ulica = mapaUlica.get(k);
            int[] mjesta = ulica.dajMjesta();
            for(int i=0; i<5; i++)
                zbrojKolicinaOtpada[i]=0.0f;
            for(int i=0; i<3; i++)
                prototipovi[i] = new Korisnik(ulica,i);
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
    /**
     * Područja se kreiraju prema podacima iz zapisa u datoteci
     * Uz to se pronalaze i ishodišta sustava, tj. područja koja nisu ničija potpodručja
     */
    @Override
    public void KreirajPodrucja()
    {
        ispis.Ispisi("Kreiraju se područja.");
        ArrayList<String> dijelovi = new ArrayList<>();
        PodaciSucelje podaciPodrucja = podaci.dajPodrucja();
        HashMap<String,PodrucjeSucelje> mapaPodrucja = parsirajPodrucja(podaciPodrucja, dijelovi);
        popuniPodrucja(mapaPodrucja);
        for(String k : mapaPodrucja.keySet())
            if(dijelovi.contains(k)==false)
            {
                ishodistaSustava.add(mapaPodrucja.get(k));
            }
    }
    /**
     * Parser redaka iz zapisa područja
     * @param podaciPodrucja produkt podatkovnog bildera
     * @param dijelovi potpodručja ili ulice
     * @return vraća mapu područja
     */
    private HashMap<String,PodrucjeSucelje> parsirajPodrucja(PodaciSucelje podaciPodrucja, ArrayList<String> dijelovi)
    {
        HashMap<String,PodrucjeSucelje> mapaPodrucja = new HashMap<>();
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
            mapaPodrucja.put(si[idIndex],new Podrucje(si[idIndex],si[nazivIndex],d));
            for(String s : d)
                dijelovi.add(s);
        }
        return mapaPodrucja;
    }
    /**
     * Ova metoda na linearan način spaja područja. Sva područja u listi nastaloj čitanjem
     * podataka se spajaju sa drugim područjima prema dijelovima u zapisu
     * @param mapaPodrucja linearna mapa područja
     */
    private void popuniPodrucja(HashMap<String,PodrucjeSucelje> mapaPodrucja)
    {
        for(String k : mapaPodrucja.keySet())
        {
            PodrucjeSucelje p = mapaPodrucja.get(k);
            String[] dijelovi = p.dajDijelove();
            for(String d : dijelovi)
            {
                if(d.charAt(0)=='p' && mapaPodrucja.get(d)!=null)
                    p.DodajPodrucje(mapaPodrucja.get(d));
            }
        }
    }
    /**
     * Ispisuje informacije o korisnicima u određenom području
     * @param podrucjeId Šifra (identifikator) područja
     */
    @Override
    void ispisiKorisnikeStat(String podrucjeId)
    {
        ArrayList<Ulica> listaUlica = new ArrayList<>();
        PodrucjeSucelje ishodiste = null;
        ishodiste = nadjiIshodiste(podrucjeId);
        if(ishodiste == null)
            return;
        ishodiste.NapuniListuUlica(listaUlica, true);
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
    /**
     * Prolazi svim ishodištima i njihovim potpodručjima, pronalazi ulice i dodjeljuje im spremnike
     * Podpodručja pojedinih ishodišta smiju se preklapati. Spremnici im neće biti dodijeljeni dva puta
     * jer se prvo sagradi mapa ulica u kojoj se ulice ne mogu ponavljati, a onda se ulicama
     * linearno dodjeljuju spremnici
     */
    @Override
    void podijeliSpremnike()
    {
        HashMap<String,Ulica> mapaUlica = new HashMap<>();
        for(PodrucjeSucelje p:ishodistaSustava)
            p.NapuniMapuUlica(mapaUlica, true);
        for(String k : mapaUlica.keySet())
        {
            Ulica ulica = mapaUlica.get(k);
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
        HashMap<String,PodrucjeSucelje> mapaPodrucja = new HashMap<>();
        for(PodrucjeSucelje ishodiste:ishodistaSustava)
        {
            ishodiste.NapuniMapuPodrucja(mapaPodrucja, true);
            System.out.println("Ishodiste sustava "+ishodiste.dajId()+": "+ishodiste.dajNaziv());
            for(String k : mapaPodrucja.keySet())
            {
                PodrucjeSucelje p = mapaPodrucja.get(k);
                System.out.println("Podrucje "+p.dajId()+": "+p.dajNaziv());
                ArrayList<Ulica> listaUlica = new ArrayList<>();
                p.NapuniListuUlica(listaUlica, false);
                for(Ulica ulica : listaUlica)
                    System.out.println("   Ulica "+ulica.Id()+": "+ulica.Naziv());
            }
        }
    }
     
    
}
