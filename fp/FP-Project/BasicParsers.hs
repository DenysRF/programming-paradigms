-- Add your names and student numbers to the following file. Do not change anything else, since it is parsed.
-- Student 1: Denys Flederus (s1855034)
-- Student 2: Yernar Kumashev (s1702831)

module BasicParsers where
import Control.Applicative
import Data.Char
import Data.Monoid
import Test.QuickCheck
import PComb

{- FP2.1
letter: Parser that parses any one alphabetical (lowercase) letter.
dig:    Parser that parses any one digit. -}
letter :: Parser Char
letter = Parser p
        where
            p (Stream []) = []
            p (Stream (x:xs)) | x >= 'a' && x <='z' = [(x, (Stream xs))]
                              | otherwise = []

prop_letter :: String -> Bool
prop_letter xs
        | res == [] || (isAlpha $ fst $ head $ res) = True
        | otherwise = False
        where res = parse letter (Stream xs)

dig :: Parser Char
dig = Parser p
        where
            p (Stream []) = []
            p (Stream (x:xs)) | x >= '0' && x <='9' = [(x, (Stream xs))]
                              | otherwise = []

prop_dig :: String -> Bool
prop_dig xs
        | res == [] || (isDigit $ fst $ head $ res) = True
        | otherwise = False
        where res = parse dig (Stream xs)
{- FP2.2
between:    Function takes three parsers, parses them in order, discards the
    results of the 1st and 3rd parser and results in Parser a that results in
    [(a, Stream)] when parsed where "a" is the parse result of the 2nd parser
    and the Stream is the leftover Stream.
    List comprehension is used to parse the parsers sequentially but only
    the result of the 2nd parser "r" is put in the generator along with
    leftover Stream as "s3"
whitespace: Function that transforms a given parser to ignore whitespace
    characters (' ', '\t' and '\n') before and after the parser recognizer.
    This is done by using the above between function where the 1st and
    3rd parsers are defined to parse many (zero or more) whitespace characters. -}
between :: Parser a -> Parser b -> Parser c -> Parser b
between p1 p2 p3 = Parser(\xs -> [ (r,s3) | (_,s1) <- parse p1 xs,
                                        (r,s2) <- parse p2 s1,
                                        (_,s3) <- parse p3 s2])

whitespace :: Parser a -> Parser a
whitespace p =  between ws p ws
        where ws = (many (char '\n' <|> char '\t' <|> char ' '))

{- FP2.3
sep1:   Parser that parses input consisting of p's separated by s's.
    At least one time should the result of the first parser "p" be parsed
    then followed any amount of times by strictly the result of the second
    parser "s" (separator) followed by the result of the first parser "p" again.
    Here s *> p is used such that s is only parsed if p succeeds.
sep:    Same as sep1 but also succeeds on zero occurances of p.
option: Parser that results in a given "a" when the parser of type (Parser a) fails. -}
sep1 :: Parser a -> Parser b -> Parser [a]
sep1 p s = pure (:) <*> p <*> many (s *> p)

sep :: Parser a -> Parser b -> Parser [a]
sep p s = sep1 p s <|> pure []

option :: a -> Parser a -> Parser a
option x p = p <|> (pure x)

{- FP2.4
string:     Parser that parses a given string, possibly surrounded by and
                ignoring whitespace by calling the whitespace parser
                over repeated char parsers for every character in the
                given String.
integer:    Parser that parses an integer, possibly surrouned by and
                ignoring whitespace by calling the whitespace parses
                over repeated dig parsers for every digit in the given string.
                Read is mapped over the list of digits to form an integer.
                Integers that start with a 0 are valid, (00, 013, 007, etc.).
identifier: Parser that parses an identifier, an identifier starts with
                a lowercase letter and is followed by any amount of letters
                and/or digits. The identifier is possibly surrounded by and
                ignores whitespace.
parens:     Parser that transforms a given parser to parse what the given parser
                parses but now only when this is within parentheses.
                The parentheses can be surrounded by whitespace.
                The whitespace and parentheses are discarded in the parse result.
braces:     Parser same as "parens" but with braces instead of parentheses. -}
string :: String -> Parser String
string [] = pure []
string (x:xs) = whitespace ((:) <$> char x <*> string xs)

integer :: Parser Integer
integer = whitespace ((read) <$> ((:) <$> dig <*> many dig))

identifier :: Parser String
identifier = whitespace ((:) <$> letter <*> many (letter <|> dig))

symbol :: String -> Parser String
symbol xs = whitespace $ string xs

parens :: Parser a -> Parser a
parens p = between (whitespace (char '(')) p (whitespace (char ')'))

braces :: Parser a -> Parser a
braces p = between (whitespace (char '{')) p (whitespace (char '}'))
