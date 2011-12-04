package r.compiler.ir.tac.operand;

import java.util.List;

import javax.sql.rowset.Joinable;

import com.google.common.base.Joiner;

import r.lang.Context;
import r.lang.Function;
import r.lang.FunctionCall;
import r.lang.PairList;
import r.lang.SEXP;
import r.lang.Symbol;

/**
 * Function call that is invoked with the full R
 * flexibility, no assumptions are made...
 */
public class DynamicCall implements Operand {

  private final Symbol name;
  private final List<Operand> arguments;
  
  public DynamicCall(Symbol name, List<Operand> arguments) {
    this.name = name;
    this.arguments = arguments;
  }

  public Symbol getName() {
    return name;
  }

  public List<Operand> getArguments() {
    return arguments;
  }

  @Override
  public Object retrieveValue(Context context, Object[] temps) {
    
    // build argument list 
    PairList.Builder argList = new PairList.Builder();
    for(Operand operand : arguments) {
      argList.add((SEXP)operand.retrieveValue(context, temps));
    }
    
    FunctionCall call = new FunctionCall(name, argList.build());
    return call.evaluate(context, context.getEnvironment());
  }

  @Override
  public String toString() {
    String statement;
    if(name.getPrintName().equals(">") || name.getPrintName().equals("<")) {
      statement = "dynamic< " + name + " >";
    } else {
      statement = "dynamic<" + name + ">";
    }
    return statement + "(" + Joiner.on(", ").join(arguments) + ")";
  }  
}