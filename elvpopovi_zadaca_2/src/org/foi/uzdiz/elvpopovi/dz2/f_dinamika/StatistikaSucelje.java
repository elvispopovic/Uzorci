/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.f_dinamika;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje.VoziloSucelje;

/**
 *
 * @author elvis
 */
public interface StatistikaSucelje 
{
    float[] DajPodatke();
    void IspisiStatistiku(ArrayList<VoziloSucelje> vozila);
}
