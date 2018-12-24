/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz3.b_buideri.ProblemskiAbstractProduct;
import org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje.VoziloSucelje;

/**
 *
 * @author elvis
 */
public interface SimulacijaSucelje
{
    ArrayList<VoziloSucelje> DajListuVozila();
    ProblemskiAbstractProduct DajProblemske();
    ListaVozila DajListaParkiraliste();
    ListaVozila DajListaPrikupljanje();
    ListaVozila DajListaOdvoz();
    boolean ProvjeriParametre();
    void Pokreni();
    boolean ObradiVozilaUPrikupljanju();
    boolean ObradiVozilaZaZbrinjavanje();
    void PostaviListeUlica();
    void PosaljiNaDeponij(int brojUListi);
}
