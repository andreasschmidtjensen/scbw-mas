package eisbw.configuration;

public enum ParamEnum {
  MAP("map"), RACE("race"), DEBUG("debug"), SC_DIR("starcraft_location");

  private String parameter;

  private ParamEnum(String name) {
    parameter = name;
  }

  public String getParam() {
    return parameter;
  }
}
