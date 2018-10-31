/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author giacomodeliberali
 */
public class TempLabel extends JLabel implements Serializable {

    public TempLabel() {
    }

    @Override
    public void setText(String text) {
        // (0°C × 9/5) + 32 = 32°F

        if (text == "") {
            super.setText("");
            return;
        }

        try {
            float celsiusValue = Float.parseFloat(text);

            float farenheitValue = celsiusValue * 9 / 5 + 32;

            super.setText("" + farenheitValue);

        } catch (NumberFormatException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public String getText() {
        return super.getText();
    }

}
