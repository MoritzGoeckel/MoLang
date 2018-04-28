# Molang
This is an fully home made interpreter for a c like language. I build this solely for educational purpose, so maybe you should not use this in production :horse:

## Example code
```
a = 10 * 30 + 1;
b = a / 3;
c = (10 + 3) * a;
d = a == b;
e = a < 10;

if e == false {
    c = 0;
}

x = 0;
while x < 10 {
    x = x + 3;
}

f = { b = b * 2; }

ff = {
    local a = 30;
    return a;
}

fff = (x, y){
    x * y;
}

z = fff(2,3) * 10;

```

## Features
- [x] Simple operators (+-*/%)
- [x] Assignment (=)
- [x] Operator precedence
- [x] Variables
- [x] Number literals (1, 2, 3 etc.)
- [x] Multi statement programs (separated by ';')
- [x] Brackets for changing precedence
- [x] Bool type
- [x] Comparisons
- [x] Jumps (if)
- [x] Loops (while)
- [x] Functions (and parameters)
- [x] Scope
- [x] Local and global variables
- [x] Unary operators
- [x] Prefix operators
- [x] Infix operators
- [ ] Postfix operators
- [x] Local variables
- [ ] String type
- [x] I/O
- [ ] Arrays

## Usage in Java
``` java
Molang lang = new Molang("a = 10 * 30 + 3; b = a / 3;");
lang.exec();

assertEquals(303, lang.getContext().getIdentifier("a").evaluate());
assertEquals(101, lang.getContext().getIdentifier("b").evaluate());
```

## Tests
The entire project is unit tested with JUnit 5 and the current line coverage is 94%.

### Assembly like interpreter
If you like interpreters you might check out my [Assembly like interpreter](https://github.com/MoritzGoeckel/Assembly-ish-Interpreter) written in Node.js
