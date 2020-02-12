package block3.cc.symbol;

import org.antlr.v4.runtime.Token;

public class TreeListener extends DeclUseBaseListener{

    private MySymbolTable t;

    private StringBuilder errors;

    public void init() {
        this.t = new MySymbolTable();
        this.errors = new StringBuilder();
        errors.append("Errors:\n");
    }

    @Override
    public void enterSeries(DeclUseParser.SeriesContext ctx) {
        super.enterSeries(ctx);
        t.openScope();
    }

    @Override
    public void exitSeries(DeclUseParser.SeriesContext ctx) {
        super.exitSeries(ctx);
        t.closeScope();
    }

    @Override
    public void enterDecl(DeclUseParser.DeclContext ctx) {
        super.enterDecl(ctx);
        if (!t.add(ctx.ID().toString())) {
            Token token = ctx.getStart();
            int line = token.getLine();
            int pos = token.getCharPositionInLine();
            String s = ctx.getText() + " at line " + line + ", position " + pos +
                    ": id already defined in current scope\n";
            errors.append(s);
        }
    }

    @Override
    public void enterUse(DeclUseParser.UseContext ctx) {
        super.enterUse(ctx);
        if (!t.contains(ctx.ID().toString())) {
            Token token = ctx.getStart();
            int line = token.getLine();
            int pos = token.getCharPositionInLine();
            String s = ctx.getText() + " at line " + line + ", position " + pos +
                    ": id not yet defined in enclosing scope\n";
            errors.append(s);
        }
    }

    public String getErrors() {
        return this.errors.toString();
    }
}
