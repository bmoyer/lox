package com.craftinginterpreters.lox;

import java.util.List;

interface LoxCallable {

    // interpreter passed in case the class implementing call() needs it
    Object call(Interpreter interpreter, List<Object> arguments);
    int arity();
}

