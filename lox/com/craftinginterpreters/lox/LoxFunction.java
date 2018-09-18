package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    
    LoxFunction(Stmt.Function declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {

        // create the function's environment
        Environment environment = new Environment(closure);
        for(int i = 0; i < this.declaration.params.size(); i++) {
            environment.define(this.declaration.params.get(i).lexeme, args.get(i));
        }

        // execute the function's code with environment
        try {
            interpreter.executeBlock(this.declaration.body, environment);
        }
        catch (Return returnValue) {
            return returnValue.value;
        }

        return null;
    }
}
