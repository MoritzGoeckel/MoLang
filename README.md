# MoLang
This is an fully home made interpreter for a C like language. No generators were used: it’s entirely hand crafted in Java. I build this solely for educational purpose, so maybe you should not use this in production :horse:

## Example code
```
// Basic operators and precedences
a = 10 * 30 + 1;
b = a / 3;
c = (10 + 3) * a;
d = a == b;
e = a < 10;

// Functions and local variables
fun = (x, y){
    local a = 3; //Overshadowing a
    return x * y * a;
}

// Functions as parameters
apply = (y,fun){
    return fun(y);
}
mul = (x){ return x * 5; }
result = apply(3, mul);

// Recursion
fibonacci = (x){
    if(x > 2){
        return fibonacci(x - 1) + fibonacci(x - 2);
    }
    return 1;
}
return fibonacci(10);
```

## Features
- [x] Simple operators (+-*/%)
- [x] Assignment (=)
- [x] Operator precedence
- [x] Variables
- [x] Number literals
- [x] Multi statement programs (separated by ';')
- [x] Brackets for changing precedence
- [x] Bool type
- [x] Comparisons
- [x] Jumps (if)
- [x] Loops (while)
- [x] Functions
- [x] Functions as parameters
- [ ] Functions as return type 
- [x] Scopes
- [x] Local and global variables
- [x] Unary operators
- [x] Prefix operators
- [x] Infix operators
- [x] Local variables
- [x] I/O
- [x] Comments (single / multiline)
- [ ] String type
- [ ] Arrays

## Usage in Java
``` java
String code = Resources.toString(Resources.getResource("complexExample.molang"), StandardCharsets.UTF_8);

MoLang lang = new MoLang(code);
assertEquals("55", lang.getScope().getReturnValue().toString());
```

## Tests
The entire project is unit tested with around 30 JUnit5 tests and the current line coverage is 94%.

### Assembly like interpreter
If you like interpreters you might check out my [Assembly like interpreter](https://github.com/MoritzGoeckel/Assembly-ish-Interpreter) written in Node.js
