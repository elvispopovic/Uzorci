/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.c_komuna;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Spremnik;
import org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje.Vozilo;

/**
 *
 * @author elvis
 */
public interface ProblemskiAbstractProduct 
{
    void KreirajUlice();
    void KreirajKorisnike();
    void KreirajSpremnike();
    void KreirajVozila();
    ArrayList<Spremnik> getSpremnici();
    ArrayList<Ulica> getListaUlica();
    ArrayList<Vozilo> getListaVozila();
}
