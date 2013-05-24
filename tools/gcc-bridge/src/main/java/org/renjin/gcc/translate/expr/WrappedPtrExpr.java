package org.renjin.gcc.translate.expr;


import org.renjin.gcc.gimple.type.IndirectType;
import org.renjin.gcc.jimple.JimpleExpr;
import org.renjin.gcc.jimple.JimpleType;
import org.renjin.gcc.jimple.RealJimpleType;
import org.renjin.gcc.translate.FunctionContext;

public class WrappedPtrExpr extends AbstractExpr implements IndirectExpr {

  private JimpleExpr expr; 
  private JimpleType wrapperType;
  private JimpleType arrayType;
  
  public WrappedPtrExpr(JimpleExpr expr, JimpleType type) {
    this.expr = expr;
    this.wrapperType = type;
    try {
      this.arrayType = new RealJimpleType(Class.forName(wrapperType.toString()).getField("array").getType());
    } catch (Exception e) {
      throw new RuntimeException(e); // should not happen
    }
  }

  @Override
  public ArrayRef translateToArrayRef(FunctionContext context) {
    return new ArrayRef(backingArray(), backingArrayIndex());
  }

  private JimpleExpr backingArray() {
    return new JimpleExpr(expr + ".<" + wrapperType + ": " + arrayType + " array>");
  }

  private JimpleExpr backingArrayIndex() {
    return new JimpleExpr(expr + ".<" + wrapperType + ": int offset>");
  }

  @Override
  public IndirectType type() {
    throw new UnsupportedOperationException();
  }
}
