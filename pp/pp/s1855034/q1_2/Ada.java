// Generated from C:/UT Modules/MOD08 - Programming Paradigms/pp/pp/s1855034/q1_2\Ada.g4 by ANTLR 4.7.2
package pp.s1855034.q1_2;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Ada extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUMBER=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DIGIT", "EXTENDED_DIGIT", "NUMERAL", "EXPONENT", "BASED_NUMERAL", "BASE", 
			"DECIMAL_LITERAL", "BASED_LITERAL", "NUMBER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NUMBER"
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


	public Ada(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Ada.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\3^\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\3\3\3\5\3\32\n\3\3\4\3\4\5\4\36\n\4\3\4\7\4!\n\4\f\4\16\4$\13\4\3\5"+
		"\3\5\5\5(\n\5\3\5\3\5\3\6\3\6\5\6.\n\6\3\6\7\6\61\n\6\f\6\16\6\64\13\6"+
		"\3\7\3\7\5\78\n\7\7\7:\n\7\f\7\16\7=\13\7\3\7\3\7\3\7\5\7B\n\7\7\7D\n"+
		"\7\f\7\16\7G\13\7\3\7\3\7\5\7K\n\7\3\7\5\7N\n\7\3\b\3\b\5\bR\n\b\3\t\3"+
		"\t\3\t\3\t\3\t\5\tY\n\t\3\n\3\n\5\n]\n\n\2\2\13\3\2\5\2\7\2\t\2\13\2\r"+
		"\2\17\2\21\2\23\3\3\2\7\3\2\62;\4\2CHch\4\2GGgg\3\2\64;\3\2\628\2d\2\23"+
		"\3\2\2\2\3\25\3\2\2\2\5\31\3\2\2\2\7\33\3\2\2\2\t%\3\2\2\2\13+\3\2\2\2"+
		"\rM\3\2\2\2\17O\3\2\2\2\21S\3\2\2\2\23\\\3\2\2\2\25\26\t\2\2\2\26\4\3"+
		"\2\2\2\27\32\5\3\2\2\30\32\t\3\2\2\31\27\3\2\2\2\31\30\3\2\2\2\32\6\3"+
		"\2\2\2\33\"\5\3\2\2\34\36\7a\2\2\35\34\3\2\2\2\35\36\3\2\2\2\36\37\3\2"+
		"\2\2\37!\5\3\2\2 \35\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\b\3\2\2"+
		"\2$\"\3\2\2\2%\'\t\4\2\2&(\7-\2\2\'&\3\2\2\2\'(\3\2\2\2()\3\2\2\2)*\5"+
		"\7\4\2*\n\3\2\2\2+\62\5\5\3\2,.\7a\2\2-,\3\2\2\2-.\3\2\2\2./\3\2\2\2/"+
		"\61\5\5\3\2\60-\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\f"+
		"\3\2\2\2\64\62\3\2\2\2\65\67\7\62\2\2\668\7a\2\2\67\66\3\2\2\2\678\3\2"+
		"\2\28:\3\2\2\29\65\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3"+
		"\2\2\2>N\t\5\2\2?A\7\62\2\2@B\7a\2\2A@\3\2\2\2AB\3\2\2\2BD\3\2\2\2C?\3"+
		"\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2HJ\7\63\2\2IK"+
		"\7a\2\2JI\3\2\2\2JK\3\2\2\2KL\3\2\2\2LN\t\6\2\2M;\3\2\2\2ME\3\2\2\2N\16"+
		"\3\2\2\2OQ\5\7\4\2PR\5\t\5\2QP\3\2\2\2QR\3\2\2\2R\20\3\2\2\2ST\5\r\7\2"+
		"TU\7%\2\2UV\5\13\6\2VX\7%\2\2WY\5\t\5\2XW\3\2\2\2XY\3\2\2\2Y\22\3\2\2"+
		"\2Z]\5\17\b\2[]\5\21\t\2\\Z\3\2\2\2\\[\3\2\2\2]\24\3\2\2\2\22\2\31\35"+
		"\"\'-\62\67;AEJMQX\\\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}