/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.g_upravljanje;

import java.util.ArrayList;

/**
 *
 * @author elvis
 */
public interface LanacKomandiApstraktni
{
    void ObradiKomandu(String[] komanda, ArrayList<String> parametriVozila);
    void DodajSljedbenika(LanacKomandiApstraktni sljedbenik);
}
