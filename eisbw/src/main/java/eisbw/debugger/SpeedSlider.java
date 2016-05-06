package eisbw.debugger;

import jnibwapi.JNIBWAPI;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedSlider extends JPanel {

  private static final long serialVersionUID = 1L;

  public int initialSpeed = 30;
  public int slowest = 50;
  public int fastest = 5;

  private boolean changed = false;

  private int speed = initialSpeed;

  public SpeedSlider() {
    final JSlider slider = new JSlider(JSlider.HORIZONTAL, fastest, slowest, initialSpeed);
    slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent event) {
        changed = true;
        speed = slider.getValue();
      }
    });
    
    slider.setInverted(true);
    
    JLabel label = new JLabel("Game Speed");
    add(label);
    add(slider);
  }

  public boolean speedChanged() {
    if (changed) {
      changed = false;
      return true;
    }
    return false;
  }

  public int getSpeed() {
    return speed;
  }
}
