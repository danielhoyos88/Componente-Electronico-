/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.electroniccomponentsproject;

import gui.GUIMain;
import observer.ComponentLogger;
import com.mycompany.electroniccomponentsproject.service.ElectronicComponentService;

/**
 *
 * @author Daniel
 */
public class ElectronicComponentsProject {

    public static void main(String[] args) {
        ElectronicComponentService.getInstance().addObserver(new ComponentLogger());
        java.awt.EventQueue.invokeLater(() -> {
            new GUIMain().setVisible(true);
        });
    }
}
