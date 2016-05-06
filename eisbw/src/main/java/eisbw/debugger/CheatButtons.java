package eisbw.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CheatButtons extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;

  private ArrayList<String> actions;
  
  public CheatButtons() {
    actions = new ArrayList<String>();
    JButton resources = new JButton("Give recources");
    resources.setActionCommand("SHOW ME THE MONEY");
    resources.addActionListener(this);
    

    JButton mapview = new JButton("Show map");
    mapview.setActionCommand("black sheep wall");
    mapview.addActionListener(this);
    
    JButton godmode = new JButton("Enemy attacks deal 0 damage");
    godmode.setActionCommand("power overwhelming");
    godmode.addActionListener(this);

    add(resources);
    add(godmode);
    add(mapview);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    actions.add(event.getActionCommand());
  }

  public ArrayList<String> getActions() {
    return actions;
  }
  
  public void clean() {
    actions = new ArrayList<>();
  }
}
