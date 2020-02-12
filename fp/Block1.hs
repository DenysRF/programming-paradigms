module Block1 where

import Test.QuickCheck
import Data.Char
import Data.List

-- Ex 1.2
total1 :: Int -> Int
total1 0 = 0
total1 n = total1 (n-1) + n

total2 :: Int -> Int
total2 n = (n * (n+1)) `div` 2

prop_total :: Int -> Property
prop_total n = (n >= 0) ==> total1 n == total2 n

-- Ex 1.3
prop_add :: Int -> Int -> Bool
prop_add x y = (x + y) == (y + x)

prop_sub :: Int -> Int -> Bool
prop_sub x y = (x - y) == (y - x)

-- Ex 1.4
code :: Char -> Char
code c
    | (c `elem` ['a'..'w']) || (c `elem` ['A'..'W']) = chr $ ord c + 3
    | (c `elem` ['x'..'z']) || c `elem` ['X'..'Z'] = chr $ ord c - 23
    | otherwise = c

code_n :: Int -> Char -> Char
code_n n c
    | (c `elem` ['a'..'z']) = chr $ ((ord c - ord 'a' + n) `mod` 26) + ord 'a'
    | (c `elem` ['A'..'Z']) = chr $ ((ord c - ord 'A' + n) `mod` 26) + ord 'A'
    | otherwise = c

prop_same :: Char -> Bool
prop_same c = code c == code_n 3 c
-- Alternative where c is constrained between ['a'..'z']++['A'..'Z']
-- prop_same :: Char -> Property
-- prop_same c = ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) ==> code c == code_n 3 c

prop_same_shift :: Int -> Char -> Bool
prop_same_shift n c = code_n (26-n) (code_n n c) == c

-- Ex 1.5
interest :: Num a => Int -> a -> a -> a
interest 0 a _ = a
interest n a r = interest (n-1) (a*(r+1)) r

-- Ex 1.6
root :: Floating a  => Ord a => a -> a -> a -> [a]
root a b c
    | discr a b c == 0 = [-b / (2*a)]
    | discr a b c > 0 = [(-b + sqrt(b^2 - 4*a*c)) / (2*a), (-b - sqrt(b^2 - 4*a*c)) / (2*a)]
    | otherwise = error "negative discriminant"

discr :: Num a => a -> a -> a -> a
discr a b c = b^2 - 4*a*c

-- Ex 1.7
extrX :: Fractional a => a -> a -> a -> a
extrX a b c = -b / (2*a)

extrY :: Fractional a => a -> a -> a -> a
extrY a b c = a*(extrX a b c)^2 + b*(extrX a b c) + c

-- Ex 1.8
myLength :: [a] -> Int
myLength [] = 0
myLength xs = 1 + (myLength $ tail xs)

prop_myLength :: [a] -> Bool
prop_myLength xs = myLength xs == length xs

mysum :: Num a => [a] -> a
mysum [] = 0
mysum (x:xs) = x + mysum xs

prop_mysum :: Num a => Eq a => [a] -> Bool
prop_mysum xs = sum xs == mysum xs

myreverse :: [a] -> [a]
myreverse [] = []
myreverse (x:xs) = myreverse xs ++ [x]

prop_myreverse :: Eq a => [a] -> Bool
prop_myreverse xs = reverse xs == myreverse xs

mytake :: Int -> [a] -> [a]
mytake _ [] = []
mytake 0 _ = []
mytake n (x:xs) = x : mytake (n-1) xs

prop_mytake :: Eq a => Int -> [a] -> Property
prop_mytake n xs = (n >= 0) ==> mytake n xs == take n xs

myelem :: Eq a => a -> [a] -> Bool
myelem _ [] = False
myelem x (y:ys)
    | x == y = True
    | otherwise = myelem x ys

prop_myelem :: Eq a => a -> [a] -> Bool
prop_myelem x ys = elem x ys == myelem x ys

myconcat :: [[a]] -> [a]
myconcat [] = []
myconcat (xs:xss) = xs ++ myconcat xss

prop_myconcat :: Eq a => [[a]] -> Bool
prop_myconcat xss = concat xss == myconcat xss

mymaximum :: Ord a => [a] -> a
mymaximum [] = error "empty list"
mymaximum [x] = x
mymaximum (x:x':xs)
    | x > x' = mymaximum (x : xs)
    | otherwise = mymaximum (x':xs)

prop_mymaximum :: Num a => Ord a => [a] -> Property
prop_mymaximum xs = not (null xs) ==> mymaximum xs == maximum xs

myzip :: [a] -> [b] -> [(a, b)]
myzip _ [] = []
myzip [] _ = []
myzip (x:xs) (y:ys) = (x, y) : myzip xs ys

prop_myzip :: Eq a => Eq b => [a] -> [b] -> Bool
prop_myzip xs ys = zip xs ys == myzip xs ys

-- Ex 1.9
r :: Num a => a -> a -> [a]
r a d = a : (r (a+d) d)

r1 :: Num a => a -> a -> Int -> a
r1 a d n = (r a d)!!(n-1)

totalr :: Num a => a -> a -> Int -> Int -> a
totalr a d i j
    | i == j = r1 a d i
    | otherwise = (r1 a d i) + (totalr a d (i+1) j)

-- Ex 1.10
allEqual :: Eq a => [a] -> Bool
allEqual [] = True
allEqual [_] = True
allEqual (x:xs)
    | x == head xs = allEqual xs
    | otherwise = False

isAS :: Eq a => Num a => Enum a => [a] -> Bool
isAS (x:x':xs) = allEqual $ zipWith (-) (map ((*)(x' - x)) [1..]) (x:x':xs)

-- Ex 1.11
allRowsEquallyLong :: Num a => [[a]] -> Bool
allRowsEquallyLong xss = allEqual $ map myLength xss

rowTotals :: Num a => [[a]] -> [a]
rowTotals xss = map sum xss

mytranspose :: Num a => [[a]] -> [[a]]
mytranspose [] = []
mytranspose ([]:xss) = mytranspose xss
mytranspose ((x:xs):xss) = (x : [y | (y:_) <- xss]) : mytranspose (xs : [ys | (_:ys) <- xss])

colTotals :: Num a => [[a]] -> [a]
colTotals xss = map sum (mytranspose xss)

-- Ex 12
myfilter :: (a -> Bool) -> [a] -> [a]
myfilter p xs = [ x | x <- xs, p x]

myfoldl :: (b -> a -> b) -> b -> [a] -> b
myfoldl _ c [] = c
myfoldl f c (x:xs) = myfoldl f (f c x) xs

myfoldr :: (a -> b -> b) -> b -> [a] -> b
myfoldr _ c [] = c
myfoldr f c (x:xs) = x `f` (myfoldr f c xs)

myzipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
myzipWith _ [] _ = []
myzipWith _ _ [] = []
myzipWith f (x:xs) (y:ys) = f x y : myzipWith f xs ys

-- Ex 13
type Name = String
type Age = Int
type Sex = String
type Res = String

type Person = (Name, Age, Sex, Res)

type Database = [Person]

db :: Database
db = [p1, p2, p3, p10, p11, p12, p13]

p1 :: Person
p1 = ("Denys", 22, "Male", "Barneveld")
p2 :: Person
p2 = ("Myrthe", 20, "Female", "Barneveld")
p3 :: Person
p3 = ("Sjoerd", 21, "Male", "Veenendaal")

p10 :: Person
p10 = ("a1", 29, "Female", "Barneveld")
p11 :: Person
p11 = ("a2", 30, "Female", "Barneveld")
p12 :: Person
p12 = ("a3", 40, "Female", "Barneveld")
p13 :: Person
p13 = ("a4", 41, "Female", "Barneveld")


getName :: Person -> Name
getName (name, _, _, _) = name

getAge :: Person -> Age
getAge (_, age, _, _) = age

getSex :: Person -> Sex
getSex (_, _, sex, _) = sex

getRes :: Person -> Res
getRes (_, _, _, res) = res

increaseAgeRec :: Age -> Database -> Database
increaseAgeRec _ [] = []
increaseAgeRec n ((d1, age, d3, d4):xss) = (d1, age + n, d3, d4) : (increaseAgeRec n xss)

increaseAgeLC :: Age -> Database -> Database
increaseAgeLC n db = [ (d1, age + n, d3, d4) | (d1, age, d3, d4) <- db]

increaseAgeHF :: Age -> Database -> Database
increaseAgeHF n db = map (\(d1, age, d3, d4) -> (d1, age + n, d3, d4)) db

getWomen3040Rec :: Database -> [Name]
getWomen3040Rec [] = []
getWomen3040Rec ((name, age, sex, d4):xss)
    | 30 <= age && age <= 40 && sex == "Female" = name : (getWomen3040Rec xss)
    | otherwise = getWomen3040Rec xss

getWomen3040LC :: Database -> [Name]
getWomen3040LC db = [ name | (name, age, sex, _) <- db, 30 <= age && age <= 40 && sex == "Female"]

getWomen3040HF :: Database-> [Name]
getWomen3040HF db = map (\(name, _, _, _) -> name) $ myfilter (\(_, age, sex, _) ->
                                                    (30 <= age && age <= 40 && sex == "Female")) db

getAgeByName :: Name -> Database -> Age
getAgeByName _ [] = error "name not in database"
getAgeByName s ((name, age, _, _):xss)
    | map toLower s == map toLower name = age
    | otherwise = getAgeByName (map toLower s) xss

sortByAge :: Database -> Database
sortByAge db = map f $ sort $ map f db
    where f = (\(d1, d2, d3, d4) -> (d2, d1, d3, d4))

-- Ex 14
sieve :: [Int]
sieve = f [2..]
    where f (x:xs) = x : f [y | y <- xs, y `mod` x /= 0]

isPrime :: Int -> Bool
isPrime n = f n sieve
    where f n (x:xs)
            | n == x = True
            | n < x = False
            | otherwise = f n xs

nPrimes :: Int -> [Int]
nPrimes n = take n sieve

primesUnderN :: Int -> [Int]
primesUnderN n = takeWhile (<n) sieve
-- primesUnderN n = f n sieve
--     where f n (x:xs)
--             | x < n = x : f n xs
--             | otherwise = []

dividers :: Int -> [Int]
dividers m = [x | x <- [1..(m `div` 2)], m `mod` x == 0] ++ [m]

isPrimeAlt :: Int -> Bool
isPrimeAlt n = length(dividers n) == 2

-- Ex 15
pyth :: Int -> [(Int, Int, Int)]
pyth n = [(a, b, c) | a <- [1..n-1], b <- [1..n-1], c <- [1..n-1], (a^2 + b^2) == c^2]

-- Ex 16
increasing :: Ord a => [a] -> Bool
increasing [_] = True
increasing (x:x':xs)
    | x' > x = increasing (x':xs)
    | otherwise = False

weaklyIncreasing :: Ord a => Fractional a => [a] -> Bool
weaklyIncreasing [] = False
weaklyIncreasing (x:xs) = f [x] xs
    where
        f _ [] = True
        f ps (x:xs)
            | x > ((mysum ps) / realToFrac(myLength ps)) = f (x:ps) xs
            | otherwise = False

-- Ex 17
sublist :: Eq a => [a] -> [a] -> Bool
sublist _ [] = False
sublist [] _ = True
sublist xs ys
    | xs == mytake (myLength xs) ys = True
    | otherwise = sublist xs (tail ys)

partialSublist :: Eq a => [a] -> [a] -> Bool
partialSublist [] _ = True
partialSublist _ [] = False
partialSublist (x:xs) (y:ys)
    | x == y = partialSublist xs ys
    | otherwise = partialSublist (x:xs) (ys)

-- Ex 18
bsort :: Ord a => [a] -> [a]
bsort [] = []
bsort xs
    | xs == bubble xs = xs
    | otherwise = bsort $ bubble xs
    where
        bubble :: Ord a => [a] -> [a]
        bubble [x] = [x]
        bubble (x:x':xs)
            | x > x' = x' : bubble (x:xs)
            | otherwise = x : bubble (x':xs)

prop_bsort :: Ord a => [a] -> Bool
prop_bsort xs = bsort xs == sort xs

mmsort :: Ord a => [a] -> [a]
mmsort [] = []
mmsort [x] = [x]
mmsort xs = (minimum xs) : (mmsort (xs \\ [minimum xs, maximum xs])) ++ [maximum xs]

prop_mmsort :: Ord a => [a] -> Bool
prop_mmsort xs = mmsort xs == sort xs

isort :: Ord a => [a] -> [a]
isort [] = []
isort xs = foldl ins [] xs
    where
        ins :: Ord a => [a] -> a -> [a]
        ins [] e = [e]
        ins (x:xs) e
            | e <= x = (e:x:xs)
            | otherwise = x : (ins xs e)

prop_isort :: Ord a => [a] -> Bool
prop_isort xs = isort xs == sort xs

msort :: Ord a => [a] -> [a]
msort [] = []
msort [x] = [x]
msort xs = merge (msort (take ((length xs) `div` 2) xs)) (msort (drop ((length xs) `div` 2) xs))
    where
        merge :: Ord a => [a] -> [a] -> [a]
        merge [] ys = ys
        merge xs [] = xs
        merge (x:xs) (y:ys)
            | x <= y = x : merge xs (y:ys)
            | otherwise = y : merge (x:xs) ys

prop_msort :: Ord a => [a] -> Bool
prop_msort xs = msort xs == sort xs

qsort :: Ord a => [a] -> [a]
qsort [] = []
qsort (x:xs) = qsort (myfilter (<=x) xs) ++ [x] ++ qsort (myfilter (>x) xs)

prop_qsort :: Ord a => [a] -> Bool
prop_qsort xs = qsort xs == sort xs

-- Ex 19
myflip :: (a -> b -> c) -> (b -> a -> c)
myflip = (\f x y -> f y x)

myflip' :: (a -> b -> c) -> (b -> a -> c)
myflip' f x y = f y x

-- Ex 20
transform :: String -> String
transform =  reverse . map toUpper . filter isLetter
