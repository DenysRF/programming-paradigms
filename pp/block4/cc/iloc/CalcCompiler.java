package block4.cc.iloc;

import block4.cc.iloc.model.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import block4.cc.ErrorListener;
import block4.cc.iloc.CalcParser.CompleteContext;
import block4.cc.iloc.Simulator;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/** Compiler from Calc.g4 to ILOC. */
public class CalcCompiler extends CalcBaseListener {
	/** Program under construction. */
	private Program prog;

	// Attribute maps and other fields
    private ParseTreeProperty<Reg> registers;
    private int numberOfRegisters;

	/** Compiles a given expression string into an ILOC program. */
	public Program compile(String text) {
		Program result = null;
		ErrorListener listener = new ErrorListener();
		CharStream chars = CharStreams.fromString(text);
		Lexer lexer = new CalcLexer(chars);
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		TokenStream tokens = new CommonTokenStream(lexer);
		CalcParser parser = new CalcParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		ParseTree tree = parser.complete();
		if (listener.hasErrors()) {
			System.out.printf("Parse errors in %s:%n", text);
			for (String error : listener.getErrors()) {
				System.err.println(error);
			}
		} else {
			result = compile(tree);
		}
		return result;
	}

	/** Compiles a given Calc-parse tree into an ILOC program. */
	public Program compile(ParseTree tree) {
		prog = new Program();
		registers = new ParseTreeProperty<>();
		numberOfRegisters = 0;

		addReg(tree);

		new ParseTreeWalker().walk(this, tree);
		emit(OpCode.out, new Str("Result: "), new Reg("r_" + (numberOfRegisters - 1)));
		return prog;
	}

	private void addReg(ParseTree tree) {
        registers.put(tree, new Reg("r_" + numberOfRegisters));
        numberOfRegisters++;
    }

	/** Constructs an operation from the parameters 
	 * and adds it to the program under construction. */
	private void emit(OpCode opCode, Operand... args) {
		this.prog.addInstr(new Op(opCode, args));
	}

    @Override
    public void exitPar(CalcParser.ParContext ctx) {
        addReg(ctx);
        emit(OpCode.i2i, registers.get(ctx.expr()), registers.get(ctx));
    }

    @Override
    public void exitMinus(CalcParser.MinusContext ctx) {
	    addReg(ctx);
	    Reg expr = registers.get(ctx.expr());
	    emit(OpCode.rsubI, expr, new Num(0), registers.get(ctx));
    }

    @Override
    public void exitNumber(CalcParser.NumberContext ctx) {
        addReg(ctx);
        emit(OpCode.loadI, new Num(Integer.parseInt(ctx.getText())), registers.get(ctx));
    }

    @Override
    public void exitTimes(CalcParser.TimesContext ctx) {
        addReg(ctx);
        Reg expr0 = registers.get(ctx.expr(0));
        Reg expr1 = registers.get(ctx.expr(1));
        emit(OpCode.mult, expr0, expr1, registers.get(ctx));
    }

    @Override
    public void exitPlus(CalcParser.PlusContext ctx) {
        addReg(ctx);
        Reg expr0 = registers.get(ctx.expr(0));
        Reg expr1 = registers.get(ctx.expr(1));
        emit(OpCode.add, expr0, expr1, registers.get(ctx));
    }

    /** Calls the compiler, and simulates and prints the compiled program. */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: [expr]+");
			return;
		}
		CalcCompiler compiler = new CalcCompiler();
		for (String expr : args) {
			System.out.println("Processing " + expr);
			Program prog = compiler.compile(expr);
			new Simulator(prog).run();
			System.out.println(prog.prettyPrint());
		}
	}
}
