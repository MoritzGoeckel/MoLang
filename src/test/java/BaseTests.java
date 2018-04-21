import Expressions.RightValue;
import Tokenizer.Token;
import Tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseTests {
    @Test
    void tokenizerTest() {
        Tokenizer t = new Tokenizer();

        List<Token> tokens = t.tokenize("10 + 20 + 3 + 4");

        assertEquals("Numliteral", tokens.get(0).getType().getExpressionName());
        assertEquals("10", tokens.get(0).getValue());

        assertEquals("Plus", tokens.get(1).getType().getExpressionName());

        assertEquals("20", tokens.get(2).getValue());
    }

    @Test
    void astSimpleTest() {
        executeTests(new String[][]{
                {"1","1"},
                {"1 + 2", "3"},
                {"2 * 3", "6"},
                {"2 * 3 * 4", "24"},
                {"2 + 3 + 4", "9"},
        });
    }

    @Test
    void astComplexTest() {
        executeTests(new String[][]{
                {"1 * 2 + 3","5"},
                {"2 + 3 * 4", "14"},
                {"2 + 3 * 5 + 1", "18"},
                {"2 * 3 * 4 + 2", "26"},
                {"2 + 3 + 4 * 3 * 2 + 1", "30"}
        });
    }

    private void executeTests(String[][] codes){
        for (String[] code : codes) {
            Molang lang = new Molang(code[0]);
            assertEquals(new Integer(code[1]), lang.execLine());
        }
    }

    @Test
    void identifierTest() {
        Molang lang = new Molang("a = 10");
        assertEquals(new Integer(10), lang.execLine(), "Expression is 10");
        assertEquals(10, lang.getContext().getIdentifier("a").evaluate(), "Variable is 10");
    }

    @Test
    void identifierComplexTest() {
        Molang lang = new Molang("a = 10 * 5 + 3 * 2 * 1");
        assertEquals(new Integer(56), lang.execLine(), "Expression is 56");
        assertEquals(56, lang.getContext().getIdentifier("a").evaluate(), "Variable is 56");
    }

    @Test
    void multilineTest() {
        Molang lang = new Molang("a = 1; b = 2");
        lang.exec();
        assertEquals(1, lang.getContext().getIdentifier("a").evaluate(), "a is 1");
        assertEquals(2, lang.getContext().getIdentifier("b").evaluate(), "b is 2");
    }
}
