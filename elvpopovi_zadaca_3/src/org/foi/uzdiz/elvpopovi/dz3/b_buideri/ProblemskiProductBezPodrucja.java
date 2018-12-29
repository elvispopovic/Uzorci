/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.b_buideri;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Korisnik;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.PodrucjeSucelje;
import org.foi.uzdiz.elvpopovi.dz3.d_komuna.Ulica;

/**
 *
 * @author elvis
 */
public class ProblemskiProductBezPodrucja extends ProblemskiAbstractProduct
{
    private ArrayList<Ulica> ulice;
    
    public ProblemskiProductBezPodrucja(InicijalizacijaPodatakaProduct podaci) 
    {
        super(podaci);
        ulice = new ArrayList<>();
        ispis.Ispisi("Poziva se problem iz DZ_1.");
    }

    public ArrayList<Ulica> dajListuUlica(String podrucjeId)
    {
        return ulice;
    }
    public PodrucjeSucelje nadjiIshodiste(String podrucjeId)
    {
        return null;
    }

    @Override
    void kreirajUlicu(String[] shema, int i)
    {
        Ulica ulica = new Ulica(shema);
        ulica.Inicijaliziraj(podaci.dajUlice().DajPodatak(i));
        ulice.add(ulica);
    }
    @Override
    void MultiplicirajKorisnike(Korisnik[] prototipovi)
    {
        for (Ulica ulica : ulice) 
        {
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
                        kolicineOtpada[vrsta]=rnd.dajRandomFloat(rasponi[vrsta].dajMinimum()[j], rasponi[vrsta].dajMaksimum()[j],brojDecimala);
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
        ispis.Ispisi("Problemski product bez područja ne kreira područja.");
    }
    public void IspisiUlice()
    {
        IspisiUlice("");
    }
    @Override
    public void IspisiUlice(String podrucjeId)
    {
        ispis.Ispisi("Količine otpada po ulicama [kg]");
        for (Ulica ulica : ulice) 
        {
            if(ulica.Id()!=null&&!ulica.Id().equals(""))
                ispis.Ispisi("("+ulica.Id()+")",false);
            ispis.Ispisi(ulica.Naziv()+": staklo: "+zbrojKolicinaOtpada[0]+
                    ", papir: "+rnd.round(zbrojKolicinaOtpada[1], brojDecimala)+", metal: "+rnd.round(zbrojKolicinaOtpada[2], brojDecimala)+
                    ", bio: "+rnd.round(zbrojKolicinaOtpada[3], brojDecimala)+", miješano: "+rnd.round(zbrojKolicinaOtpada[4], brojDecimala));
        }
    }
    
    @Override
    void podijeliSpremnike()
    {
        for(Ulica ulica : ulice)
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
    @Override
    void ispisiKorisnikeStat(String podrucjeId)
    {
        for(Ulica ulica : ulice)
        {
            ispis.Ispisi(ulica.Naziv()+", broj korisnika: "+(ulica.dajMjesta()[0]+ulica.dajMjesta()[1]+ulica.dajMjesta()[2]));
            ArrayList<ArrayList<Korisnik>>korisnici = ulica.dajKorisnike();
            for(int vrsta=0; vrsta<3; vrsta++)
            {
                ispisiVrstuKorisnika(vrsta, ulica.dajMjesta()[vrsta]);
                ArrayList<Korisnik> korisniciVrsta = korisnici.get(vrsta);
                for(int i=0; i<korisniciVrsta.size(); i++)
                {
                    Korisnik k = korisniciVrsta.get(i);
                    ispis.Ispisi("      Korisnik "+k.dajId()+": ",false);
                    float[] kolicine = k.dajKolicineOtpada();
                    for(int j=0; j<kolicine.length; j++)
                        ispis.Ispisi((podaci.dajParametre().DajNazivOtpada(j)+": "+rnd.round(kolicine[j],brojDecimala)+" kg "),false);
                    ispis.Ispisi("");
                }
            }
        }
    }
      
    
}
