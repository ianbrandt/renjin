package org.renjin.compiler;


import org.renjin.eval.Context;
import org.renjin.sexp.Environment;
import org.renjin.sexp.SEXP;

public interface CompiledBody {

  SEXP evaluate(Context context, Environment rho);
}
