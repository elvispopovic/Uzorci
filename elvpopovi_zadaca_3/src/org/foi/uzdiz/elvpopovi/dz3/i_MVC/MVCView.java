/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_MVC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
/**
 * View klasa MVC uzorka
 * @author elvis
 */
public class MVCView extends MVCObserver
{
    private MVCController controller;
    private MVCModelSucelje model;
    
    private ArrayList<String> redciIspisa;
    private LinkedList<String> redciUnosa;
    private int trenutnoIspisano, ukupnoIspisa;
    private int brg, brd;
    
    public MVCView()
    {
        Parametri parametri = Parametri.getInstance();
        redciUnosa = new LinkedList<>();
        controller = null;
        brg = parametri.DajVrijednost("brg");
        brd = parametri.DajVrijednost("brd");
        trenutnoIspisano = 0;
    }
    /**
     * Kreira kontroller
     */
    public void KreirajController()
    {
        controller = new MVCController(brg, brd);
        inicijalizirajVT100();
    }
    /**
     * Inicijalizira se model, preuzimaju se reference, a ako je prije bio priključen
     * neki drugi model, on se odjavljuje
     * Ova metoda traži i kreiranje Kontrolera
     * @param model referenca na postojeći model
     */
    public void InicijalizirajModel(MVCModelSucelje model)
    {
        
        if(this.model != null)
            this.model.OdjaviMVC(this);
        this.model = model;
        this.model.PrikljuciMVC(this);
        if(controller == null)
            KreirajController();
        controller.InicijalizirajMV(model, this);
    }
    /**
     * Prikazuje se gornji, prezentacijski dio ekrana
     * @param i broj redova koji su do sada prikazani od ukupnog broja redaka koje treba u više
     * navrata prikazati
     * @return vraća broj redaka koji su u ovom prolazu prikazani
     */
    public int prikaziPrezentacijskiDio(int i)
    {
        int j;
        if(redciIspisa == null)
            return 0;
        if(brg!=-1&&brd!=-1)
            ObrisiPrezentacijskiDio();
        ukupnoIspisa = redciIspisa.size();
        System.out.print("\033[1;32m");
        for(j=0; j<brg && i<ukupnoIspisa; j++,i++)
        {
            String s = redciIspisa.get(i);
            if(s.length()>135)
                s = s.substring(0, 135);
            System.out.println(s);
        }
        trenutnoIspisano = i;
        System.out.print("\033[0m");
        return j;
    }
    /**
     * prikazuje donji, komandni dio ekrana
     * @param upit upit, odnosno komanda koja se prikazuje u komandnom dijelu
     */
    public void prikaziKomandniDio(String upit)
    {
        System.out.print("\033[1;34m");
        System.out.print("\033["+(brg+1)+";0H");
        System.out.print(String.join("", Collections.nCopies(80, "-")));
        System.out.print(String.join("", Collections.nCopies(16, " ")));
        System.out.print("\033["+(brg+1)+";80H");
        System.out.println(trenutnoIspisano+"/"+ukupnoIspisa);
        obrisiKomandniDio();
        redciUnosa.addLast(upit);
        if(redciUnosa.size()>(brd-1))
            redciUnosa.removeFirst();
        for(String s:redciUnosa)
        {
            System.out.println(s+"\033[34m");
        }
        System.out.print("\033[0m");
    }
    /**
     * Osvježavanje prikaza
     * @param cekanje da li se čeka potvrda korisnika
     */
    @Override
    public void Osvjezi(boolean cekanje)
    {
        redciIspisa = model.DohvatiPodatkeMVC();
    }  
    /**
     * Rad u komandnom modu
     */
    public void KomandniMod()
    {
        Osvjezi(true);
    }
    /**
     * Briše sve retke prezentacijskog dijela ekrana
     */
    public void ObrisiPrezentacijskiDio()
    {
        System.out.print("\033["+brg+";135H");
        System.out.print("\033[1J");
        System.out.print("\033[0;0H");
    }
    /**
     * Briše sve retke komandnog dijela ekrana
     */
    public void obrisiKomandniDio()
    {
        if(brg>0&&brd>0)
        {
            System.out.print("\033["+(brg+2)+";0H");
            System.out.print("\033[J");
            System.out.print("\033["+(brg+2)+";0H");
        }
    }
    /**
     * Eksperimentalno. Postavlja neke parametre VT 100 terminala, i briše terminal
     */
    private void inicijalizirajVT100()
    {
        if(brg>0&&brd>0)
        {
            System.out.println("Postavljanje VT100 terminala");
            System.out.print("\033[7l");
            System.out.print("\033[H\033[2J");
        }
    }
}
