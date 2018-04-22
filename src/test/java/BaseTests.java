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
                {"4 * 2 + 3","11"},
                {"2 + 3 * 4", "14"},
                {"2 + 3 * 5 + 1", "18"},
                {"2 * 3 * 4 + 2", "26"},
                {"2 + 3 + 4 * 3 * 2 + 1", "30"}
        });
    }

    @Test
    void precedenceBracketSimpleTest() {
        executeTests(new String[][]{
                {"1 * (2 + 3)","5"},
                {"(2 + 3) * 4", "20"},
                {"2 + (3 * 5) + 1", "18"},
                {"(2 * 3) * (4 + 2)", "36"},
        });
    }

    @Test
    void precedenceBracketComplexTest() {
        executeTests(new String[][]{
                {"(2 + 3) + (4 * 3 * (2 + 1))", "41"},
                {"(2 + 3)", "5"},
                {"(2)", "2"},
                {"((2))", "2"}
        });
    }

    private void executeTests(String[][] codes){
        for (String[] code : codes) {
            Molang lang = new Molang(code[0]);
            Object actual = lang.execLine();
            assertEquals(code[1], actual.toString(), code[0] + " should be " + code[1] + " but was " + actual);
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
        Molang lang = new Molang("a = 1; b = 2; a = 4");
        lang.exec();
        assertEquals(4, lang.getContext().getIdentifier("a").evaluate());
        assertEquals(2, lang.getContext().getIdentifier("b").evaluate());
    }

    @Test
    void booleanTest() {
        Molang lang = new Molang("a = true; b = false;");
        lang.exec();
        assertEquals(true, lang.getContext().getIdentifier("a").evaluate());
        assertEquals(false, lang.getContext().getIdentifier("b").evaluate());
    }

    @Test
    void booleanComplexTest() {
        Molang lang = new Molang("a = true; b = false; c = a && b; d = false || a;");
        lang.exec();
        assertEquals(true, lang.getContext().getIdentifier("a").evaluate());
        assertEquals(false, lang.getContext().getIdentifier("b").evaluate());
        assertEquals(false, lang.getContext().getIdentifier("c").evaluate());
        assertEquals(true, lang.getContext().getIdentifier("d").evaluate());
    }

    @Test
    void comparisonIntegerTest() {
        executeTests(new String[][]{
                {"10 > 11", "false"},
                {"11 > 10", "true"},
                {"10 < 11", "true"},
                {"11 < 10", "false"},
                {"10 == 11", "false"},
                {"10 == 10", "true"}
        });
    }

    @Test
    void comparisonBooleanTest() {
        executeTests(new String[][]{
                {"false && false", "false"},
                {"false && true", "false"},
                {"true || false", "true"},
                {"false || false", "false"},
                {"true == true", "true"},
                {"false == false", "true"}
        });
    }

    @Test
    void multilineComplexTest() {
        Molang lang = new Molang("a = 10 * 30 + 3; b = a / 3; c = (10 + 3) * a;");
        lang.exec();
        assertEquals(303, lang.getContext().getIdentifier("a").evaluate());
        assertEquals(101, lang.getContext().getIdentifier("b").evaluate());
        assertEquals(13 * 303, lang.getContext().getIdentifier("c").evaluate());
    }
}
