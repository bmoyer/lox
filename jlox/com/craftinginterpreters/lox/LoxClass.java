package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Map;

// Runtime representation of a class (not an instance) - stored directly in variable
// by interpreter when we encounter a class syntax node
public class LoxClass implements LoxCallable {
    final String name;
    final LoxClass superclass;
    private final Map<String, LoxFunction> methods;

    LoxClass(String name, LoxClass superclass, Map<String, LoxFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    LoxFunction findMethod(LoxInstance instance, String name) {
        if (methods.containsKey(name)) {
            return methods.get(name).bind(instance);
        }

        if (superclass != null) {
            return superclass.findMethod(instance, name);
        }

        return null;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = methods.get("init");
        if(initializer != null) {
            initializer.bind(instance).call(interpreter, arguments); // bound/invoked like a normal method call
        }
        return instance;
    }

    @Override
    public int arity() {
        LoxFunction initializer = methods.get("init");
        return (initializer == null) ? 0 : initializer.arity();
    }

    @Override
    public String toString() {
        return name;
    }
}
