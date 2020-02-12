package block4.cc.cfg;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import block4.cc.ErrorListener;
import block4.cc.cfg.FragmentParser.BreakStatContext;
import block4.cc.cfg.FragmentParser.ContStatContext;
import block4.cc.cfg.FragmentParser.ProgramContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/** Template top-down CFG builder. */
public class TopDownCFGBuilder extends FragmentBaseListener {
	/** The CFG being built. */
	private Graph graph;

	private ParseTreeProperty<Node> enters;
    private ParseTreeProperty<Node> exits;

    public TopDownCFGBuilder() {
        this.enters = new ParseTreeProperty<>();
        this.exits = new ParseTreeProperty<>();
    }

    @Override
    public void enterAssignStat(FragmentParser.AssignStatContext ctx) {
        enters.get(ctx).addEdge(exits.get(ctx));
    }

    @Override
    public void enterBlockStat(FragmentParser.BlockStatContext ctx) {
        Node enter = enters.get(ctx);
        Node exit = exits.get(ctx);

        Node node = enter;
        for (FragmentParser.StatContext stat : ctx.stat()) {
            Node childEntry = addNode(stat, stat.getText());
            Node childExit = addNode(stat, stat.getText() + "_end");
            enters.put(stat, childEntry);
            exits.put(stat, childExit);

            node.addEdge(childEntry);
            node = childExit;
        }
        node.addEdge(exit);
    }

    @Override
    public void enterDecl(FragmentParser.DeclContext ctx) {
        enters.get(ctx).addEdge(exits.get(ctx));
    }

    @Override
    public void enterPrintStat(FragmentParser.PrintStatContext ctx) {
        enters.get(ctx).addEdge(exits.get(ctx));
    }

    @Override
    public void enterProgram(FragmentParser.ProgramContext ctx) {
        Node node = new Node(-1);
        for (FragmentParser.StatContext stat : ctx.stat()) {
            Node childEntry = addNode(stat, stat.getText());
            Node childExit = addNode(stat, stat.getText() + "_end");
            enters.put(stat, childEntry);
            exits.put(stat, childExit);

            node.addEdge(childEntry);
            node = childExit;
        }
    }

    @Override
    public void enterWhileStat(FragmentParser.WhileStatContext ctx) {
        Node entrance = enters.get(ctx);
        Node exit = exits.get(ctx);

        Node childEntry = addNode(ctx.stat(), ctx.stat().getText());
        Node childExit = addNode(ctx.stat(), ctx.stat().getText() + "_end");
        enters.put(ctx.stat(), childEntry);
        exits.put(ctx.stat(), childExit);

        entrance.addEdge(childEntry);
        entrance.addEdge(exit);
        childExit.addEdge(entrance);
    }

    @Override
    public void enterIfStat(FragmentParser.IfStatContext ctx) {
        Node entrance = enters.get(ctx);
        Node exit = exits.get(ctx);

        Node childIfEntry = addNode(ctx.stat(0), ctx.stat(0).getText());
        Node childIfExit = addNode(ctx.stat(0), ctx.stat(0).getText() + "_end");
        enters.put(ctx.stat(0), childIfEntry);
        exits.put(ctx.stat(0), childIfExit);

        if (ctx.stat(1) == null) {
            entrance.addEdge(childIfEntry);
            childIfExit.addEdge(exit);
        } else {
            Node childElseEntry = addNode(ctx.stat(1), ctx.stat(1).getText());
            Node childElseExit = addNode(ctx.stat(1), ctx.stat(1).getText() + "_end");
            enters.put(ctx.stat(1), childElseEntry);
            exits.put(ctx.stat(1), childElseExit);

            entrance.addEdge(childIfEntry);
            childIfExit.addEdge(exit);
            childElseExit.addEdge(exit);
        }
    }

    /** Builds the CFG for a program contained in a given file. */
	public Graph build(File file) {
		Graph result = null;
		ErrorListener listener = new ErrorListener();
		try {
			CharStream chars = CharStreams.fromPath(file.toPath());
			Lexer lexer = new FragmentLexer(chars);
			lexer.removeErrorListeners();
			lexer.addErrorListener(listener);
			TokenStream tokens = new CommonTokenStream(lexer);
			FragmentParser parser = new FragmentParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(listener);
			ProgramContext tree = parser.program();
			if (listener.hasErrors()) {
				System.out.printf("Parse errors in %s:%n", file.getPath());
				for (String error : listener.getErrors()) {
					System.err.println(error);
				}
			} else {
				result = build(tree);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/** Builds the CFG for a program given as an ANTLR parse tree. */
	public Graph build(ProgramContext tree) {
        graph = new Graph();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, tree);
        return graph;
	}

	@Override
	public void enterBreakStat(BreakStatContext ctx) {
		throw new IllegalArgumentException("Break not supported");
	}

	@Override
	public void enterContStat(ContStatContext ctx) {
		throw new IllegalArgumentException("Continue not supported");
	}

	/** Adds a node to he CGF, based on a given parse tree node.
	 * Gives the CFG node a meaningful ID, consisting of line number and
	 * a further indicator.
	 */
	private Node addNode(ParserRuleContext node, String text) {
		return this.graph.addNode(node.getStart().getLine() + ": " + text);
	}

	/** Main method to build and print the CFG of a simple Java program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [filename]+");
			return;
		}
		TopDownCFGBuilder builder = new TopDownCFGBuilder();
		for (String filename : args) {
			File file = new File(filename);
			System.out.println(filename);
			System.out.println(builder.build(file));
		}
	}
}
