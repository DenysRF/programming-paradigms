module Block3S6 where

import Data.Foldable
import Data.Monoid
import Text.Read
import Data.Char
-- import Control.Applicative

import Block3

-- Ex 7
instance Monoid Int where
    mempty = 0
    mappend = (+)

sumint :: [Int] -> Int
sumint = mconcat

-- Ex 8
data A a = A { fromA :: Maybe a }
            deriving Show

instance Monoid (A a) where
    mempty = A Nothing
    mappend (A Nothing) y = y
    mappend (A (Just x)) _ = A (Just x)

firstInt :: [String] -> Maybe Int
firstInt xs = fromA $ mconcat [ A (readMaybe x :: Maybe Int) | x <- xs ]

-- Ex 9
addstr :: String -> String -> Maybe String
addstr xs ys = show <$> ((+) <$> (readMaybe xs :: Maybe Int) <*> (readMaybe ys :: Maybe Int))

-- Ex 10
data MyList a = Nil | Cons a (MyList a)
                deriving (Show, Eq)

instance Functor MyList where
    fmap f Nil = Nil
    fmap f (Cons a l) = Cons (f a) (f <$> l)

mylst   = Cons 1    $ Cons 2    $ Cons 3    $ Nil
mylst2  = Cons 10   $ Cons 20   $ Cons 30   $ Nil
mylst3  = Cons 100  $ Cons 200  $ Cons 300  $ Nil

myzipWith3' :: (a -> b -> c -> d) -> MyList a -> MyList b -> MyList c -> MyList d
myzipWith3' f Nil _ _ = Nil
myzipWith3' f _ Nil _ = Nil
myzipWith3' f _ _ Nil = Nil
myzipWith3' f (Cons x xs) (Cons y ys) (Cons z zs) = Cons (f x y z) $ myzipWith3' f xs ys zs

instance Applicative MyList where
    pure x = Cons x Nil
    Nil <*> _ = Nil
    _ <*> Nil = Nil
    Cons f fs <*> Cons x xs = Cons (f x) (fs <*> xs)

myzipWith :: (a -> b -> c) -> MyList a -> MyList b -> MyList c
myzipWith f xs ys = f <$> xs <*> ys

myzipWith3 :: (a -> b -> c -> d) -> MyList a -> MyList b -> MyList c -> MyList d
myzipWith3 f xs ys zs = f <$> xs <*> ys <*> zs

-- Ex 11
getInt :: IO Integer
getInt = read <$> getLine

f :: IO Integer
f = (+) <$> getInt <*> getInt

-- Ex 12
justs :: [Maybe a] -> Maybe [a]
justs [] = Just []
justs ((Just m):ms) = (:) <$> Just m <*> justs ms
justs (Nothing:ms) = Nothing

-- Ex 13
data MyEndo a = MyEndo {
    apply :: a -> a
}

instance Monoid (MyEndo a) where
    mempty = MyEndo id
    mappend (MyEndo a) (MyEndo b) = MyEndo (a . b)

listtoendo :: [a -> a] -> MyEndo a
listtoendo fs = mconcat $ map MyEndo fs

transform :: String -> String
transform = apply $ listtoendo [map toUpper, reverse, filter isLetter]

-- Ex 14
data Parser r = P {
    runParser :: String -> [(r, String)]
}

char :: Char -> Parser Char
char c = P p
    where
    p []                 = []
    p (x:xs) | c == x    = [(x, xs)]
             | otherwise = []

parseOne :: Parser Char
parseOne = char '1'

-- should return true
parseOneSucces :: Bool
parseOneSucces = runParser parseOne "123" == [('1', "23")]
-- should return false
parseOneFail :: Bool
parseOneFail = runParser parseOne "321" == [('3', "21")]

instance Functor Parser where
    fmap f (P p) = P (\xs -> [ (f x, ys) | (x,ys) <- p xs ] )

parseOneInt :: Parser Int
parseOneInt = digitToInt <$> (char '1')

instance Applicative Parser where
    pure p = P (\xs -> [(p, xs)])
    p1 <*> p2 = P (\s -> [ (r1 r2, s2)
                            | (r1, s1) <- runParser p1 s,
                              (r2, s2) <- runParser p2 s1 ])

parseAB :: Parser (Char, Char)
parseAB = (,) <$> char 'a' <*> char 'b'

parseABC :: Parser String
parseABC = (\a b c -> a:b:c:[]) <$> char 'a' <*> char 'b' <*> char 'c'

parseString :: String -> Parser String
parseString [] = pure ""
parseString (x:xs) = (:) <$> char x <*> parseString xs

-- Ex 15
fibonacci :: IO (Integer -> Integer)
fibonacci = evalFun <$> (parserFun <$> readFile "fib.txt")

fib5 :: IO Integer
fib5 = fibonacci <*> pure 5

fibs :: IO [Integer]
fibs = (map <$> fibonacci) <*> pure [0..]
