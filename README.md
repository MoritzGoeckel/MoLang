# Molang
This is an fully home made interpreter for an c like language. I build this solely for educational purpose, so maybe you should not use this in production :)

## Example code
```
a = 10 * 30 + 1;
b = a / 3;
```

## Features
- [x] Simple operators (+-*/%)
- [x] Assignment (=)
- [x] Operator precedence
- [x] Variables
- [x] Number literals (1, 2, 3 etc.)
- [x] Multiline programs (seperated by ';')
- [ ] Brackets for changing precedence
- [ ] Bool type
- [ ] String type
- [ ] Jumps (if, else etc.)
- [ ] Loops
- [ ] Scope
- [ ] Functions

## Tests
The entire project is unit tested with JUnit 5 and a current line coverage of 92%.

### Assembly like interpreter
If you like interpreters you might check out my [Assembly like interpreter](https://github.com/MoritzGoeckel/Assembly-ish-Interpreter) written in Node.js
