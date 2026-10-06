package lexicography.analyzer;

public class Main {
    public static void main(String[] args) {
        String input = """
                WHILE stone1 <= 100
                    IF snake != ten ELSE
                        honest = SIN(3.14) * Cos(0.5) + tan(ten) - sqrt(81);
                        vase = (stone1 + 2.5) / (oven - 4) * 7;
                        knot = true;
                        note = "hello, stone";
                        ковка = стенка1 + 10 * сота;
                    THEN
                        tank = ten == 10;
                    END
                END
                
                count = 5 + 12abc;
                heat = 3.14.15 + 5. * 1..2;
                oath = kоt + stone;
                vest = 2 @ 3 # 4 $ 5;
                shake = "unclosed string
                oak = hat
                """;

        Analyzer analyzer = new Analyzer(input);
        Token token;
        do {
            token = analyzer.nextToken();
            System.out.println(token);
        } while (token.getClassType() != LexemeClass.EOF);
    }
}
