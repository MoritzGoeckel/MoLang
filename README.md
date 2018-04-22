# Molang
This is an fully home made interpreter for a c like language. I build this solely for educational purpose, so maybe you should not use this in production :horse:

## Example code
```
a = 10 * 30 + 1;
b = a / 3;
c = (10 + 3) * a;
```

## Features
- [x] Simple operators (+-*/%)
- [x] Assignment (=)
- [x] Operator precedence
- [x] Variables
- [x] Number literals (1, 2, 3 etc.)
- [x] Multiline programs (seperated by ';')
- [x] Brackets for changing precedence
- [ ] Bool type
- [ ] Jumps (if, else etc.)
- [ ] Loops
- [ ] Scope
- [ ] Functions
- [ ] String type

## Usage in Java
``` java
Molang lang = new Molang("a = 10 * 30 + 3; b = a / 3;");
lang.exec();

assertEquals(303, lang.getContext().getIdentifier("a").evaluate());
assertEquals(101, lang.getContext().getIdentifier("b").evaluate());
```

## Tests
The entire project is unit tested with JUnit 5 and the current line coverage is 92%.

### Assembly like interpreter
If you like interpreters you might check out my [Assembly like interpreter](https://github.com/MoritzGoeckel/Assembly-ish-Interpreter) written in Node.js
