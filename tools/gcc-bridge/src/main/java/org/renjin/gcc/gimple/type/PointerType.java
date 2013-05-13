package org.renjin.gcc.gimple.type;

public class PointerType extends AbstractGimpleType {
  private GimpleType baseType;

  public GimpleType getBaseType() {
    return baseType;
  }

  public void setBaseType(GimpleType baseType) {
    this.baseType = baseType;
  }

  @Override
  public String toString() {
    return baseType.toString() + "*";
  }
}
