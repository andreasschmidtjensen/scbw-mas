package eisbw.configuration;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.Map;
import java.util.Map.Entry;

public class Configuration {

  private String race = null;
  private String map = null;
  private String scDir = null;
  private String debug = "false";

  public Configuration(Map<String, Parameter> parameters) 
      throws NoTranslatorException, TranslationException {
    parseParams(parameters);
    checkSanity();
  }

  private void parseParams(Map<String, Parameter> parameters) 
      throws NoTranslatorException, TranslationException {
    Translator translator = Translator.getInstance();
    for (Entry<String, Parameter> entry : parameters.entrySet()) {
      ParamEnum param = translator.translate2Java(new Identifier(entry.getKey()), ParamEnum.class);
      switch (param) {
        case DEBUG:
          setDebugMode(translator.translate2Java(entry.getValue(), String.class));
          break;
        case MAP:
          setMap(translator.translate2Java(entry.getValue(), String.class));
          break;
        case RACE:
          setRace(translator.translate2Java(entry.getValue(), String.class));
          break;
        case SC_DIR:
          setSC_dir(translator.translate2Java(entry.getValue(), String.class));
          break;
        default:
          break;
      }
    }
  }

  private void checkSanity() {
//    if (race == null || map == null || scDir == null) {
//      throw new IllegalStateException("Map, Race "
//          + "and starcraft directory have to be defined in the .mas2g file");
//    }
  }

  private void setSC_dir(String dir) {
    if (dir == null) {
      throw new IllegalStateException("starcraft directory must be provided");
    }
    this.scDir = dir;
  }

  private void setDebugMode(String debug) {
    this.debug = debug;
  }

  private void setMap(String map) {
    if (map == null) {
      throw new IllegalStateException("map must be provided");
    }
    this.map = map;
  }

  private void setRace(String race) {
    if (race == null) {
      throw new IllegalStateException("race must be provided");
    }
    this.race = race;
  }

  public String getDebugMode() {
    return debug;
  }

  public String getMap() {
    return map;
  }

  public String getRace() {
    return race;
  }

  public String get_sc_dir() {
    return scDir;
  }
}
