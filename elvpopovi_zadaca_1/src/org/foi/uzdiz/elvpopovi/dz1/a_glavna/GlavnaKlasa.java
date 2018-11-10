/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.a_glavna;


import java.io.File;
import java.io.FileInputStream;
import org.foi.uzdiz.elvpopovi.dz1.e_dinamika.Simulacija;

/**
 *
 * @author elvis
 */
public class GlavnaKlasa 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    { 
        if(args.length<1)
        {
            System.out.println("Nedovoljni broj argumenata. Aplikacija se prekida.");
            return;
        }
        AplikacijskiDirektor direktor = new AplikacijskiDirektor(args);
        if(direktor.Inicijaliziraj()==false)
        {
            System.out.println("Neispravni podaci. Aplikacija ne može nastaviti dalje.");
            return;
        }
        Simulacija simulacija = new Simulacija(direktor.DajPodatke(), direktor.DajProblemske());
        simulacija.Pokreni();
    }
    
}
