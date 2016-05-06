package eisbw.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Parameter2Java;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.types.RaceType;
import jnibwapi.types.RaceType.RaceTypes;

public class RaceTypeTranslator implements Parameter2Java<RaceType> {

  @Override
  public RaceType translate(Parameter param) throws TranslationException {
    if (!(param instanceof Identifier)) {
      throw new TranslationException("Invalid parameter for race");
    }
    String id = ((Identifier) param).getValue().toLowerCase();

    switch (id) {
      case "terran":
        return RaceTypes.Terran;
      case "protoss":
        return RaceTypes.Protoss;
      case "zerg":
        return RaceTypes.Zerg;
      default:
        break;
    }
    throw new TranslationException("Race " + id + " not found");
  }

  @Override
  public Class<RaceType> translatesTo() {
    return RaceType.class;
  }

}
