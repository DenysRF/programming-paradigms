package block3.cc.tabular;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.PrintWriter;

public class HTMLConverter extends LatexBaseListener {

    private StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        String n = "2"; // number of tabular file
        ErrorListener e = new ErrorListener();

        String path = "block3/cc/tabular/";
        CharStream chars = CharStreams.fromFileName(path + "tabular-" + n + ".tex");
        Lexer lexer = new LatexLexer(chars);
        lexer.removeErrorListeners();
        lexer.addErrorListener(e);

        TokenStream tokens = new CommonTokenStream(lexer);
        LatexParser parser = new LatexParser(tokens);
        ParseTree tree = parser.tabular();

        ParseTreeWalker walker = new ParseTreeWalker();
        parser.removeErrorListeners();
        parser.addErrorListener(e);
        if (e.getErrors().size() > 0) {
            System.err.println("Cannot convert file with syntax errors:\n");
            for (String s : e.getErrors()) {
                System.err.println(s);
            }
        } else {
            HTMLConverter c = new HTMLConverter();
            walker.walk(c, tree);

            PrintWriter pw = new PrintWriter(path + "html/" + n + ".html", "UTF-8");
            pw.print(c.getBuilder());
            pw.close();
        }

    }

    @Override
    public void enterTabular(LatexParser.TabularContext ctx) {
        super.enterTabular(ctx);
        sb.append("<html>\n<body>\n<table border=\"1\">\n");
    }

    @Override
    public void exitTabular(LatexParser.TabularContext ctx) {
        super.exitTabular(ctx);
        sb.append("</table>\n</body>\n</html>");
    }

    @Override
    public void enterRow(LatexParser.RowContext ctx) {
        super.enterRow(ctx);
        sb.append("<tr>\n");
    }

    @Override
    public void exitRow(LatexParser.RowContext ctx) {
        super.exitRow(ctx);
        sb.append("</tr>\n");
    }

    @Override
    public void enterCell(LatexParser.CellContext ctx) {
        super.enterCell(ctx);
        sb.append("\t<td>");
        sb.append(ctx.getText());
    }

    @Override
    public void exitCell(LatexParser.CellContext ctx) {
        super.exitCell(ctx);
        sb.append("</td>\n");
    }

    private StringBuilder getBuilder() {
        return sb;
    }
}
