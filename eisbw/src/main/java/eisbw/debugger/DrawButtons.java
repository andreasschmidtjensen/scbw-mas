package eisbw.debugger;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.util.BWColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DrawButtons extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;
  private boolean draw;

  public DrawButtons() {
    JButton drawButton = new JButton("Draw BuildLocations");
    draw = false;
    drawButton.addActionListener(this);
    
    add(drawButton);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    draw = !draw;
  }

  public void draw(JNIBWAPI api) throws NoTranslatorException, TranslationException {
    if (draw) {
      Translator translator = Translator.getInstance();
      ConstructionSitePerceiver constructions = new ConstructionSitePerceiver(api);
      List<Percept> percepts = constructions.perceive();
      for (Percept percept : percepts) {
        LinkedList<Parameter> params = percept.getParameters();
        int xpos = translator.translate2Java(params.get(0), Integer.class);
        int ypos = translator.translate2Java(params.get(1), Integer.class);
        api.drawBox(new Position(xpos, ypos, PosType.BUILD),
            new Position(xpos + 3, ypos + 3, PosType.BUILD), BWColor.Blue, false, false);
      }
    }
  }

}
