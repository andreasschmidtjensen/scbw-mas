package eisbw;

import eis.EIDefaultImpl;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.configuration.Configuration;
import eisbw.translators.ParamEnumTranslator;
import eisbw.translators.RaceTypeTranslator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StarcraftEnvironmentImpl extends EIDefaultImpl {

  private static final long serialVersionUID = 1L;
  private BwapiListener bwapiListener;
  private Configuration configuration;
  private Game game;

  public static void main (String[] args){
    try {
      new StarcraftEnvironmentImpl().init(new HashMap<String, Parameter>());
    } catch (ManagementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public StarcraftEnvironmentImpl() {
    super();
    installTranslators();
    game = new Game(this);
    bwapiListener = new BwapiListener(game);
  }

  private void installTranslators() {
    Translator translatorfactory = Translator.getInstance();
    translatorfactory.registerParameter2JavaTranslator(new ParamEnumTranslator());
    translatorfactory.registerParameter2JavaTranslator(new RaceTypeTranslator());
  }

  @Override
  public void init(Map<String, Parameter> parameters) throws ManagementException {
    super.init(parameters);
    setState(EnvironmentState.PAUSED);

    try {
      configuration = new Configuration(parameters);
      addEntity("player");

      if (!WindowsTools.isProcessRunning("Chaoslauncher.exe")) {
        WindowsTools.startChaoslauncher(configuration.getRace(), configuration.getMap(),
            configuration.get_sc_dir());
      }
    } catch (Exception ex) {
      Logger.getLogger(StarcraftEnvironmentImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected LinkedList<Percept> getAllPerceptsFromEntity(String entity)
      throws PerceiveException, NoEnvironmentException {
    return game.getPercepts(entity);
  }

  @Override
  protected boolean isSupportedByEnvironment(Action action) {
    return bwapiListener.getAction(action) != null;
  }

  @Override
  protected boolean isSupportedByType(Action action, String type) {
    return isSupportedByEnvironment(action);
  }

  @Override
  protected boolean isSupportedByEntity(Action action, String entity) {
    return bwapiListener.isSupportedByEntity(action, entity);
  }

  @Override
  protected Percept performEntityAction(String entity, Action action) throws ActException {
    bwapiListener.performEntityAction(entity, action);
    return null;
  }

  /**
   * Adds a unit to the environment.
   * 
   * @param unitName
   *          - the name of the unit
   * @param eisUnitType
   *          - the type of the unit
   */
  public void addToEnvironment(String unitName, String eisUnitType) {
    try {
      addEntity(unitName, eisUnitType);
    } catch (EntityException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Deletes a unit to the environment.
   * 
   * @param unitName
   *          - the name of the unit
   */
  public void deleteFromEnvironment(String unitName) {
    try {
      deleteEntity(unitName);
    } catch (EntityException exception) {
      exception.printStackTrace();
    } catch (RelationException exception) {
      // TODO Auto-generated catch block
      exception.printStackTrace();
    }
  }

}
