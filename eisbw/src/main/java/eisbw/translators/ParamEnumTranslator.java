package eisbw.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Parameter2Java;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eisbw.configuration.ParamEnum;

public class ParamEnumTranslator implements Parameter2Java<ParamEnum> {

  @Override
  public ParamEnum translate(Parameter param) throws TranslationException {
    if (!(param instanceof Identifier)) {
      throw new TranslationException("Invalid parameter");
    }
    String id = ((Identifier) param).getValue();

    for (ParamEnum params : ParamEnum.values()) {
      if (params.getParam().equals(id)) {
        return params;
      }
    }

    throw new TranslationException("ParamEnum did not contain value");
  }

  @Override
  public Class<ParamEnum> translatesTo() {
    return ParamEnum.class;
  }

}
