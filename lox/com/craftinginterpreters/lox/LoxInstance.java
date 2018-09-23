package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

// Runtime representation of an instance of a Lox class
public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    // accesses a property of the instance
    // returns either (1) a field (state stored in the instance), or (2) a method defined on the instance's class
    Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }

        LoxFunction method = klass.findMethod(this, name.lexeme);
        if (method != null) return method;

        throw new RuntimeError(name,
                "Undefined property '" + name.lexeme + "'.");
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}

