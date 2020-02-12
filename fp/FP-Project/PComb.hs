-- Add your names and student numbers to the following file. Do not change anything else, since it is parsed.
-- Student 1: Denys Flederus (s1855034)
-- Student 2: Yernar Kumashev (s1702831)

module PComb where
import Control.Applicative
import Data.Char
import Data.Monoid
import Test.QuickCheck

-- Stream of Chars - can be extended with the location for error handling
data Stream = Stream [Char]
              deriving (Eq, Show)

{- FP1.1
Parser runs by calling parse on a "Parser a" and a "Stream [Char]" resulting
    in a list containing tuples where the fst contains what is parsed and the
    snd contains the leftover Stream. -}
data Parser a = Parser {
        parse :: Stream -> [(a, Stream)]
}

{- FP1.2
Functor instance of the Parser data type.
    Applies a given function "f" on what gets parsed by the parser;
    on "a" in [(a, Stream)] where the parser is "Parser a".
    Allows the use of <$> (fmap) on our parsers. -}
instance Functor Parser where
    fmap f (Parser p) = Parser (\xs -> [(f x, y) | (x,y) <- p xs])

{- FP1.3
Parser that parses a single given Char, when parsed returns empty list
    if Stream is empty or first element in the Stream does not match
    the given Char. -}
char :: Char -> Parser Char
char c = Parser p
    where
        p (Stream []) = []
        p (Stream (x:xs))   | c == x = [(x, (Stream xs))]
                            | otherwise = []

prop_char :: String -> Bool
prop_char [] = True
prop_char (x:xs)
        | x == (fst $ head $ res) = True
        | otherwise = False
        where res = parse (char x) (Stream (x:xs))

{- FP1.4
Parser that for any input results in an empty list. -}
failure :: Parser a
failure = Parser (\_ -> [])

{- FP1.5
Applicative instance for the Parser data type.
    TODO: comment in implementation
    Allows the usage of <*>, <* and *> on our parsers. -}
instance Applicative Parser where
    pure p = Parser (\xs -> [(p, xs)])
    p1 <*> p2 = Parser (\s -> [(r1 r2, s2) | (r1,s1) <- parse p1 s,
                                            (r2,s2) <- parse p2 s1])

{- FP1.6
Alternative instance for the Parser data type.
    When able apply the first parser, otherwise apply the second parser.
    also extends functionality of Applicative with many and some
    to use the Applicative for zero or more, or one or more parsers respectively.
    Allows the usage of <|>, many and some on our parsers. -}
instance Alternative Parser where
    empty = failure
    p1 <|> p2 = Parser p
            where p x   | (null result) = parse p2 x
                        | otherwise = result
                                where result = parse p1 x
    many p = some p <|> pure []
    some p = pure (:) <*> p <*> many p

{- FP1.7
Monoid instance for the parser data type.
    Apply all given parsers idependently on the input and return a list of
    possible parse results when parsed. -}
instance Monoid (Parser a) where
    mempty = failure
    mappend p1 p2 = Parser p
        where p x = (parse p1 x) ++ (parse p2 x)

{- FP1.8
Monoid wrapper for Parser a.
    NOT IMPLEMENTED -}
