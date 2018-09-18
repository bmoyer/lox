package com.craftinginterpreters.lox;

class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        super(null, null, false, false); // disables stack traces, etc used for actual error handling
        this.value = value;
    }
}
