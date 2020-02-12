package project.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The ParseTreeGenerator generates IR instructions for sprockell
 * if there are no scoping, syntax and type errors
 * @author denys, yernar
 */
public class ParseTreeGenerator extends LanguageBaseListener{
    private ParseTreeProperty<AST> parseTree;
    private AST program;
    private  StringBuilder builder;
    private boolean valid;
    private final boolean GENERATE;

    private int varID = 0;
    private int scopeVarID = 0;
    private int sharedVarId = 1;

    private List<String> helperThreads = new ArrayList<>();
    private int numberHelperThreads = 0;
    private List<String> sharedVars = new ArrayList<>();

    public ParseTreeGenerator(boolean gen) {
        this.parseTree = new ParseTreeProperty<>();
        this.program = new AST("Prog");
        this.builder = new StringBuilder("module INSTRUCTIONS where \n" + "import CodeGeneration \n \n" + "prog = ");
        this.valid = false;
        this.GENERATE = gen;
    }

    /**
     * This method is the entry point for generating the IR for sprockell
     * @param path the path and filename of the program you want to generate IR for
     * @throws IOException because it reads from a file
     */
    public void start(String path) throws IOException {
        executeGenerator(path);
        if (valid && this.GENERATE) {
            PrintWriter writer = new PrintWriter("project/sprockell/src/INSTRUCTIONS.hs", "UTF-8"); //programming_paradigms/FinalProject/sprockell/src/

            String listOfProgs = "[prog";
            if (helperThreads.size() > 0) {
                int tid = 1;
                for (String str : helperThreads) {
                    builder.append("\n" + str + "\n");
                    listOfProgs += ", prog" + tid;
                    tid++;
                }
            }
            listOfProgs += "]";


            builder.append("\n \n" + "main = runCodeGen " + listOfProgs);
            writer.print(builder);
            writer.close();
        } else {
            if (!valid) {
                System.out.println("Program invalid");
            }

        }
    }

    /**
     * Parses the given program and checks for syntax errors, if there are syntax errors
     * print antlr will print them and this method return immediately.
     * Then calls other methods that will generate the IR
     * @param filename the path and filename of the program you want to generate IR for
     * @throws IOException because it reads from a file
     */
    private void executeGenerator(String filename) throws IOException {
        ParseTreeWalker walker = new ParseTreeWalker();
        CharStream chars = CharStreams.fromFileName(filename);
        Lexer lexer = new LanguageLexer(chars);

        TokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);

        ParseTree tree = parser.program();

        TreeListener listener = new TreeListener();
        walker.walk(listener, tree);
        walker.walk(this, tree);
        if (GENERATE && (parser.getNumberOfSyntaxErrors() > 0 || !listener.getErrors().equals("Errors:\n") || !this.getErrors().equals("Errors:\n"))) {
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Could not compile because of syntax errors:\n");
            }
            if (!listener.getErrors().equals("Errors:\n")) {
                System.err.println("Could not compile because of type errors:\n");
                System.out.println(listener.getErrors());
            }
            if (!this.getErrors().equals("Errors:\n")) {
                System.err.println("Could not compile because of scoping errors:\n");
                System.out.println(this.getErrors());
            }
            return;
        }
        converter(program);
        valid = true;
    }

    /**
     * Constructs the IR for sprockell from the given abstract syntax tree
     * @param ast the abstract syntax tree that represents the program
     */
    private void converter(AST ast) {
        builder.append(ast.getName() + " [ ");
        for (AST child : ast.getChildren()) {
            if (child.getName().equals("Fork")) {
                builder.append("Fork, ");
                helperThreads.add(0, convertForkJoin(child));
            } else {
                builder.append(convert(child));
                builder.append(",");
            }

        }
        builder.replace(builder.length()-1, builder.length(), " ]");
        //System.out.println(builder.toString());
    }

    /**
     * helper method for converter() that helps traversing the abstract syntax tree
     * @param ast a subtree of the abstract syntax tree that represents the program
     * @return the generates IR for the given subtree
     */
    private String convert(AST ast){
        if (ast.getChildren().size() == 0) {
            return ast.getName();
        }
        if (ast.getName().equals("Block")) { // special case for a block statement, since it contains list of statements
            String result = "Block [";
            for (AST child : ast.getChildren()) {
                result = result + convert(child) + ", ";
            }
            result = result.substring(0, result.length() - 2) + ']';
            return result;
        } else if (ast.getName().equals("Fork")) {
            helperThreads.add(convertForkJoin(ast));
            return "Fork";
        }
        String result = ast.getName();
        for (AST child : ast.getChildren()) {
             result += " (";
             result = result + convert(child) + ") ";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String convertForkJoin(AST ast) {
        if (ast.getChildren().size() == 0) {
            return ast.getName();
        }
        numberHelperThreads++;
        String result = "prog" + numberHelperThreads + "= Prog [Forked, ";
        result += convert(ast.getChildren().get(0)) + ", HelperEnd]";
        return result;
    }


    //scope checking
    private SymbolTable st = new SymbolTable();
    private SymbolTable sharedSt = new SymbolTable();
    private StringBuilder errors = new StringBuilder("Errors:\n");


    /**
     * Open a scope when entering a block of statements within braces
     * @param ctx the context of the calling rule
     */
    @Override
    public void enterBlockStat(LanguageParser.BlockStatContext ctx) {
        st.openScope();
    }

    /**
     * A variable is declared, increase the scopeVarID for unique memory addresses
     * Also handle scope checking with the symbol table and add an error when needed
     * @param ctx the context of the calling rule
     */
    @Override
    public void enterDecl(LanguageParser.DeclContext ctx) {
        if (ctx.SHARED() != null) {
            boolean added  = sharedSt.add(ctx.ID().toString(), sharedVarId);
            if (!added) {
                Token token = ctx.getStart();
                int line = token.getLine();
                int pos = token.getCharPositionInLine();
                String s = ctx.ID().getText() + " at line " + line + ", position " + pos +
                        ": shared id already defined in current scope\n";
                errors.append(s);
            }
            //if successfully added
            sharedVars.add(ctx.ID().toString());
            return;
        }
        if (!st.add(ctx.ID().toString(), scopeVarID)) {
            Token token = ctx.getStart();
            int line = token.getLine();
            int pos = token.getCharPositionInLine();
            String s = ctx.ID().getText() + " at line " + line + ", position " + pos +
                    ": id already defined in current scope\n";
            errors.append(s);
        }
        scopeVarID++;
    }

    /**
     * When entering id, if the variable is not already declared (check with symbol table)
     * add an error
     * @param ctx the context of the calling rule
     */
    @Override
    public void enterId(LanguageParser.IdContext ctx) {
        String id = ctx.getText();
        boolean isShared = sharedVars.contains(id);
        if (!isShared) {
            if (!st.contains(ctx.ID().toString())) {
                Token token = ctx.getStart();
                int line = token.getLine();
                int pos = token.getCharPositionInLine();
                String s = ctx.getText() + " at line " + line + ", position " + pos +
                        ": id not yet defined in enclosing scope\n";
                errors.append(s);
            }
        } else {
            if (!sharedVars.contains(id) && !sharedSt.contains(id)) {
                Token token = ctx.getStart();
                int line = token.getLine();
                int pos = token.getCharPositionInLine();
                String s = ctx.getText() + " at line " + line + ", position " + pos +
                        ": shared id not yet defined in enclosing scope\n";
                errors.append(s);
            }
        }
    }

    /**
     * getter method for the error messages
     * @return list of errors in a string
     */
    public String getErrors() {
        return this.errors.toString();
    }

    //generating Haskell-readable file, for processing it later in haskell processor

    /**
     * creates new AST for ID or SharedID and adds it to the parse tree
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitId(LanguageParser.IdContext ctx) {
        AST ast;
        if ( st.get(ctx.ID().toString()) != -1){
            ast = new AST("ID(" + st.get(ctx.ID().toString()) +")");
        } else {
            ast = new AST("SharedID(" + sharedSt.get(ctx.ID().toString()) +")");
        }
        parseTree.put(ctx, ast);
    }

    /**
     * Based on if conditions creates a AST with one of the constants and adds it to the parse tree
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitConstExpr(LanguageParser.ConstExprContext ctx) {
        if (ctx.TRUE() != null) {
            parseTree.put(ctx, new AST("Bool(1)"));
        } else if (ctx.FALSE() != null){
            parseTree.put(ctx, new AST("Bool(0)"));
        } else {
            parseTree.put(ctx, new AST("Int(" + ctx.NUM().toString() + ")"));
        }
    }

    /**
     * Gets the expression in the brackets from parse tree and adds it to parse tree with new ctx
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitParExpr(LanguageParser.ParExprContext ctx) {
        parseTree.put(ctx, parseTree.get(ctx.expr()));
    }

    /**
     * Creates AST CmprExpr and adds to it as a child the comparison sign and 2 expressions
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitCompExpr(LanguageParser.CompExprContext ctx) {
        AST ast = new AST("CmprExpr");
        if (ctx.LeT() != null) {
            ast.addChild(new AST("LeT"));
        } else if (ctx.GrT() != null) {
            ast.addChild(new AST("GrT"));
        } else if (ctx.EQual() != null) {
            ast.addChild(new AST("EQual"));
        } else if (ctx.NEQual() != null) {
            ast.addChild(new AST("NEQual"));
        } else if (ctx.LEq() != null) {
            ast.addChild(new AST("LEq"));
        } else if (ctx.GEq() != null) {
            ast.addChild(new AST("GEq"));
        }
        ast.addChild(parseTree.get(ctx.expr(0)));
        ast.addChild(parseTree.get(ctx.expr(1)));
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called ADD with 2 expressions as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitAddExpr(LanguageParser.AddExprContext ctx) {
        if (ctx.ADD() != null) {
            AST ast = new AST("ADD");
            ast.addChild(parseTree.get(ctx.expr(0)));
            ast.addChild(parseTree.get(ctx.expr(1)));
            parseTree.put(ctx, ast);
        } else {
            AST ast = new AST("MINUS");
            ast.addChild(parseTree.get(ctx.expr(0)));
            ast.addChild(parseTree.get(ctx.expr(1)));
            parseTree.put(ctx, ast);
        }
    }

    /**
     * Creates new AST called MULT with 2 expressions as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitMultExpr(LanguageParser.MultExprContext ctx) {
        AST ast = new AST("MULT");
        ast.addChild(parseTree.get(ctx.expr(0)));
        ast.addChild(parseTree.get(ctx.expr(1)));
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called Block and adds all the stats from the parse tree as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitBlockStat(LanguageParser.BlockStatContext ctx) {
        st.closeScope();
        AST ast = new AST("Block");
        for (LanguageParser.StatContext stat : ctx.stat()){
            ast.addChild(parseTree.get(stat));
        }
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called While with 2 expressions as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitWhileStat(LanguageParser.WhileStatContext ctx) {
        AST ast = new AST("While");
        ast.addChild(parseTree.get(ctx.expr()));
        ast.addChild(parseTree.get(ctx.stat()));
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called If with first expression as a child, second expressions is optional
     * and can be empty, if it the case we add a new AST called Empty
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitIfStat(LanguageParser.IfStatContext ctx) {
        AST ast = new AST("If");
        ast.addChild(parseTree.get(ctx.expr()));
        ast.addChild(parseTree.get(ctx.stat(0)));
        if (ctx.ELSE() != null) {
            ast.addChild(parseTree.get(ctx.stat(1)));
        } else {
            ast.addChild(new AST("Empty"));
        }
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called Assign with 2 expressions as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitAssign(LanguageParser.AssignContext ctx) {
        AST ast = new AST("Assign");
        ast.addChild(parseTree.get(ctx.expr(0)));
        ast.addChild(parseTree.get(ctx.expr(1)));
        parseTree.put(ctx, ast);
    }

    /**
     * First we check if the we declaring a shared or local variable, then we create
     * based on the check the AST with specific name and variable id.
     * If variable was initialized we add the expression as child, but if it was not
     * we add default value, for that reason we crate AST with default value based on
     * variable type and add it to he parse tree
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitDecl(LanguageParser.DeclContext ctx) {
        if (ctx.SHARED() != null) {
            AST ast = new AST("SharedDecl");
            ast.addChild(new AST("SharedID(" +sharedVarId +")"));
            sharedVarId++;
            if (ctx.expr() != null){
                ast.addChild(parseTree.get(ctx.expr()));
                parseTree.put(ctx, ast);
            } else {
                if (parseTree.get(ctx.type()).getName().equals("IntType")) {
                    ast.addChild(new AST("Int(0)"));
                }  else {
                    ast.addChild(new AST("Bool(0)"));
                }
                parseTree.put(ctx, ast);
            }
        } else {
            AST ast = new AST("Decl");
            ast.addChild(new AST("ID(" + varID + ")"));
            varID++;
            if (ctx.expr() != null) {
                ast.addChild(parseTree.get(ctx.expr()));
            } else {
                if (parseTree.get(ctx.type()).getName().equals("IntType")) {
                    ast.addChild(new AST("Int(0)"));
                } else {
                    ast.addChild(new AST("Bool(0)"));
                }
            }
            parseTree.put(ctx, ast);
        }
    }

    /**
     * Creates new AST based on what is the content of the ctx and adds it to he parse tree
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitType(LanguageParser.TypeContext ctx) {
        AST ast;
        if (ctx.BOOL() != null) {
            ast = new AST("BoolType");
        } else {
            ast = new AST("IntType");
        }
        parseTree.put(ctx, ast);
    }

    /**
     * Since the AST program was already created, in this mehtod we just add
     * all the stats from the parse tree as a child AST
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitProgram(LanguageParser.ProgramContext ctx) {
        for (LanguageParser.StatContext stat : ctx.stat()){
            program.addChild(parseTree.get(stat));
        }
    }

    /**
     * Creates new AST called Fork with a statement as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitForkStat(LanguageParser.ForkStatContext ctx) {
        AST ast = new AST("Fork");
        ast.addChild(parseTree.get(ctx.stat()));
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called Join
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitJoinStat(LanguageParser.JoinStatContext ctx) {
        AST ast = new AST("Join");
        parseTree.put(ctx, ast);
    }

    /**
     * Creates new AST called Print with an expression as a child
     * @param ctx the context of the calling rule
     */
    @Override
    public void exitPrint(LanguageParser.PrintContext ctx) {
        AST ast = new AST("Print");
        ast.addChild(parseTree.get(ctx.expr()));
        parseTree.put(ctx, ast);
    }
}
