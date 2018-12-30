/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

/**
 *
 * @author elvis
 */
public interface LanacKomandiApstraktni
{
    void ObradiKomandu(String[] komanda);
    void DodajSljedbenika(LanacKomandiApstraktni sljedbenik);
}
