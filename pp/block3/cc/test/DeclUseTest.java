package block3.cc.test;

import block3.cc.symbol.DeclUseLexer;
import block3.cc.symbol.DeclUseParser;
import block3.cc.symbol.TreeListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class DeclUseTest {

    String path = "block3/cc/test/programs/";
    private ParseTree parseDeclUse(String filename) throws IOException {
        CharStream chars = CharStreams.fromFileName(filename);
        Lexer lexer = new DeclUseLexer(chars);
        TokenStream tokens = new CommonTokenStream(lexer);
        DeclUseParser parser = new DeclUseParser(tokens);
        return parser.program();
    }
    @Test
    public void test() throws IOException {
        test("Errors:\n", path+"program0.txt");
        String s = makeError(true, "abc", 7, 0);
        test(s, path+"program1.txt");
    }

    private String makeError(boolean use, String id, int line, int pos) {
        String badUse = ": id not yet defined in enclosing scope\n";
        String badDecl = ": id already defined in current scope\n";
        StringBuilder sb = new StringBuilder();
        sb.append("Errors:\n");
        String s;
        if (use) {
            s = ("U:" + id);
        } else {
            s = ("D:" + id);
        }
        sb.append(s);
        s = " at line " + line + ", position " + pos;
        sb.append(s);
        if (use) {
            sb.append(badUse);
        } else {
            sb.append(badDecl);
        }

        return sb.toString();
    }

    private TreeListener listener = new TreeListener();
    private final ParseTreeWalker walker = new ParseTreeWalker();

    private void test(String error, String fileName) throws IOException {
        ParseTree tree = parseDeclUse(fileName);
        this.listener.init();
        this.walker.walk(this.listener, tree);
        assertEquals(error, listener.getErrors());

    }



}
