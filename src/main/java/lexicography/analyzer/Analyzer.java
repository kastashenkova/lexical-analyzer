package lexicography.analyzer;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    private static final Set<String> KEYWORDS = Set.of(
            "if", "while", "else", "then", "case", "switch", "for", "return", "end");
    private static final Set<String> FUNCTIONS = Set.of("sin", "cos", "tan", "sqrt");
    private static final Set<String> BOOLEANS = Set.of("true", "false");
    private static final Pattern IDENTIFIER = Pattern.compile(
            "(?iu)[астенковш][астенковш0-9]*|[asthenkov][asthenkov0-9]*");
    private static final Pattern NUMBER = Pattern.compile("\\d+(\\.\\d+)?");
    private static final String[] GROUPS = {"WHITESPACE", "STRING", "BADSTR", "WORD", "OPERATOR", "SYMBOL", "ERR"};
    private static final Pattern TOKEN = Pattern.compile(
            "(?<WHITESPACE>\\s+)"
                    + "|(?<STRING>\"[^\"\\n]*\")"
                    + "|(?<BADSTR>\"[^\\n]*)"
                    + "|(?<WORD>[\\p{L}\\p{N}_]+(?:\\.[\\p{L}\\p{N}_]*)*)"
                    + "|(?<OPERATOR>==|<=|>=|!=|[+\\-*/=<>])"
                    + "|(?<SYMBOL>[(),;{}])"
                    + "|(?<ERR>.)");

    private final Matcher matcher;

    public Analyzer(String input) {
        this.matcher = TOKEN.matcher(input);
    }

    public Token nextToken() {
        while (matcher.find()) {
            String value = matcher.group();
            Token token = switch (matchedGroup()) {
                case "WHITESPACE" -> null;
                case "STRING" -> new Token(LexemeClass.STRING, value);
                case "WORD" -> defineClass(value);
                case "OPERATOR" -> new Token(LexemeClass.OPERATOR, value);
                case "SYMBOL" -> new Token(LexemeClass.SYMBOL, value);
                default -> new Token(LexemeClass.ERROR, value);
            };
            if (token != null) {
                return token;
            }
        }
        return new Token(LexemeClass.EOF, "");
    }

    private String matchedGroup() {
        for (String name : GROUPS) {
            if (matcher.group(name) != null) {
                return name;
            }
        }
        return "ERR";
    }

    private Token defineClass(String value) {
        String universalCaseValue = value.toLowerCase();

        return switch (universalCaseValue) {
            case String s when KEYWORDS.contains(s)
                    -> new Token(LexemeClass.KEYWORD, value);

            case String s when FUNCTIONS.contains(s)
                    -> new Token(LexemeClass.FUNCTION, value);

            case String s when BOOLEANS.contains(s)
                    -> new Token(LexemeClass.BOOLEAN, value);

            case String s when NUMBER.matcher(s).matches()
                    -> new Token(LexemeClass.NUMBER, value);

            case String s when IDENTIFIER.matcher(s).matches()
                    -> new Token(LexemeClass.IDENTIFIER, value);

            default -> new Token(LexemeClass.ERROR, value);
        };
    }
}
