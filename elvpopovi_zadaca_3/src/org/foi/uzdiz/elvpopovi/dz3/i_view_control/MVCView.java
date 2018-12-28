/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_view_control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;

public class MVCView extends MVCObserver
{
    private MVCController controller;
    private MVCModelSucelje model;
    
    private ArrayList<String> redciIspisa;
    private LinkedList<String> redciUnosa;
    private int brg, brd;
    
    public MVCView()
    {
        Parametri parametri = Parametri.getInstance();
        redciUnosa = new LinkedList<>();
        controller = null;
        brg = parametri.DajVrijednost("brg");
        brd = parametri.DajVrijednost("brd");
    }
    
    public void KreirajController()
    {
        controller = new MVCController(brg, brd);
        inicijalizirajVT100();
    }
    
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
     
    public int prikaziPrezentacijskiDio(int i)
    {
        int j;
        if(redciIspisa == null)
            return 0;
        if(brg!=-1&&brd!=-1)
            obrisiPrezentacijskiDio();
        for(j=0; j<brg && i<redciIspisa.size(); j++,i++)
        {
            String s = redciIspisa.get(i);
            if(s.length()>135)
                s = s.substring(0, 135);
            System.out.println(s);
        }
        return j;
    }
    
    public void prikaziKomandniDio(String upit)
    {
        System.out.print("\033["+(brg+1)+";0H");
        System.out.println(String.join("", Collections.nCopies(80, "-")));
        obrisiKomandniDio();
        redciUnosa.addLast(upit);
        if(redciUnosa.size()>(brd-1))
            redciUnosa.removeFirst();
        for(String s:redciUnosa)
            System.out.println(s);
    }
    @Override
    public void Osvjezi()
    {
        redciIspisa = model.DohvatiPodatkeMVC();
    }  
    
    public void KomandniMod()
    {
        Osvjezi();
    }
    
    private void obrisiPrezentacijskiDio()
    {
        System.out.print("\033["+brg+";135H");
        System.out.print("\033[1J");
        System.out.print("\033[0;0H");
    }
    
    public void obrisiKomandniDio()
    {
        if(brg>0&&brd>0)
        {
            System.out.print("\033["+(brg+2)+";0H");
            System.out.print("\033[J");
            System.out.print("\033["+(brg+2)+";0H");
        }
    }
    
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
