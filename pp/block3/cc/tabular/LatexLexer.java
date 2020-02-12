// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/block3/cc/tabular\Latex.g4 by ANTLR 4.7.2
package block3.cc.tabular;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LatexLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, SLASH=2, START=3, STARTARG=4, END=5, COMMENT=6, CELL=7, WS=8;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "SLASH", "START", "STARTARG", "END", "COMMENT", "CELL", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'&'", "'\\\\'", "'\\begin{tabular}'", null, "'\\end{tabular}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "SLASH", "START", "STARTARG", "END", "COMMENT", "CELL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LatexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Latex.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\nf\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\6\5+\n\5\r\5\16\5,\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\7\7A\n\7\f\7\16\7D\13\7\3\7\3\7\3\7\3\7\3"+
		"\b\6\bK\n\b\r\b\16\bL\3\b\6\bP\n\b\r\b\16\bQ\3\b\6\bU\n\b\r\b\16\bV\3"+
		"\b\6\bZ\n\b\r\b\16\b[\5\b^\n\b\3\t\6\ta\n\t\r\t\16\tb\3\t\3\t\2\2\n\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\3\2\b\5\2eenntt\3\2\f\f\f\2\13\f\17"+
		"\17\"\"%)^^}}\177\177\u00cd\u00ce\u0194\u0194\u2022\u2022\t\2%)^^}}\177"+
		"\177\u00cd\u00ce\u0194\u0194\u2022\u2022\r\2\13\f\17\17\"\"%)^^aa}}\177"+
		"\177\u00cd\u00ce\u0194\u0194\u2022\u2022\5\2\13\f\17\17\"\"\2m\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\3\23\3\2\2\2\5\25\3\2\2\2\7\30\3\2\2\2\t(\3\2\2"+
		"\2\13\60\3\2\2\2\r>\3\2\2\2\17]\3\2\2\2\21`\3\2\2\2\23\24\7(\2\2\24\4"+
		"\3\2\2\2\25\26\7^\2\2\26\27\7^\2\2\27\6\3\2\2\2\30\31\7^\2\2\31\32\7d"+
		"\2\2\32\33\7g\2\2\33\34\7i\2\2\34\35\7k\2\2\35\36\7p\2\2\36\37\7}\2\2"+
		"\37 \7v\2\2 !\7c\2\2!\"\7d\2\2\"#\7w\2\2#$\7n\2\2$%\7c\2\2%&\7t\2\2&\'"+
		"\7\177\2\2\'\b\3\2\2\2(*\7}\2\2)+\t\2\2\2*)\3\2\2\2+,\3\2\2\2,*\3\2\2"+
		"\2,-\3\2\2\2-.\3\2\2\2./\7\177\2\2/\n\3\2\2\2\60\61\7^\2\2\61\62\7g\2"+
		"\2\62\63\7p\2\2\63\64\7f\2\2\64\65\7}\2\2\65\66\7v\2\2\66\67\7c\2\2\67"+
		"8\7d\2\289\7w\2\29:\7n\2\2:;\7c\2\2;<\7t\2\2<=\7\177\2\2=\f\3\2\2\2>B"+
		"\7\'\2\2?A\n\3\2\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2D"+
		"B\3\2\2\2EF\7\f\2\2FG\3\2\2\2GH\b\7\2\2H\16\3\2\2\2IK\n\4\2\2JI\3\2\2"+
		"\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NP\n\5\2\2ON\3\2\2\2PQ\3\2\2"+
		"\2QO\3\2\2\2QR\3\2\2\2RT\3\2\2\2SU\n\6\2\2TS\3\2\2\2UV\3\2\2\2VT\3\2\2"+
		"\2VW\3\2\2\2W^\3\2\2\2XZ\n\6\2\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2"+
		"\2\2\\^\3\2\2\2]J\3\2\2\2]Y\3\2\2\2^\20\3\2\2\2_a\t\7\2\2`_\3\2\2\2ab"+
		"\3\2\2\2b`\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\b\t\2\2e\22\3\2\2\2\13\2,BLQ"+
		"V[]b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}