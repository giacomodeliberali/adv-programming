import Data.List
import Data.Char

-- Exercise 1
myReplicate n v = map (const v) [1 .. n]

myReplicateRec 0 v = []
myReplicateRec n v = v : myReplicateRec (n-1) v

-- Exercise 2
sumOddRec [] = 0
sumOddRec (x:xs) = if odd(x) then x + sumOddRec xs else sumOddRec(xs)

sumOdd x = foldl (+) 0 (filter odd x)

-- Exercise 3 [1,2,3] 3 => [1,1,1,2,2,2,3,3,3]
replRec [] n = []
replRec (x:xs) n = myReplicateRec n x ++ replRec xs n

repl (x:xs) n = foldl (++) [] (map (myReplicateRec n) (x:xs))

-- Exercise 4
totalLengthRec [] = 0
totalLengthRec (x:xs) = if "A" `isPrefixOf` x then (length x) + totalLengthRec xs else totalLengthRec xs

totalLength (x:xs) = foldl (+) 0 (map length (filter ("A" `isPrefixOf`) (x:xs)))

-- Exercise 5
filterOddRecAux [] index = []
filterOddRecAux (x:xs) index = if (index `mod` 2 == 0) then x : filterOddRecAux xs (index+1) else filterOddRecAux xs (index+1)
filterOddRec x = filterOddRecAux x 0

-- . = function composition like f(g(x))
-- zip [1,2,3] [8,7,6] => [(1,8),(2,7),(3,6)]
filterOdd x = map snd (filter (odd . fst) (zip [1 .. ] x))

-- Exercise 6

capitalizeFirstChar (x:xs) = toUpper x : xs

titleCase s = unwords (map capitalizeFirstChar (words s))

titleCaseRecAux [] = []
titleCaseRecAux (x:xs) = capitalizeFirstChar x : titleCaseRecAux xs
titleCaseRec s = unwords (titleCaseRecAux (words s))

-- Exercise 7: countVowelPali ["anna", "banana", "civic", "mouse"] = 4

isPali xs = xs == reverse xs

countVowels [] = 0
countVowels (x:xs) = if (elem x "aeiou") then 1 + countVowels xs else countVowels xs

countVowelPaliRec [] = 0
countVowelPaliRec (x:xs) = if isPali x then (countVowels x + (countVowelPaliRec xs)) else countVowelPaliRec xs

countVowelPali (x:xs) = foldl (+) 0 (map countVowels (filter isPali (x:xs)))

-- Exercise 8
map' f x = foldl (\acc x -> acc ++ [f x]) [] x

-- Exercise 9
data IntTree = Leaf Int | Node (Int, IntTree, IntTree) deriving (Show)

tmap f (Leaf t) = Leaf(f t)
tmap f (Node(r,left,right)) = Node(f r, tmap f left, tmap f right)

l = Leaf(1)
r = Leaf(2)
root = Node(0,l,r)

-- Exercise 10

trMapAux acc [] f = reverse acc
trMapAux acc (h:t) f = trMapAux (f h : acc) t f

trMap f x = trMapAux [] x f
    

trFilterAux acc [] f = reverse acc
trFilterAux acc (h:t) f = trFilterAux (if f h then h : acc else acc) t f

trFilter f x = trFilterAux [] x f

-- Exercise 10

-- foldr (+) 0 [1..1000000]

-- 1 + (foldr (+) 0 [2..1000000]) -->
-- 1 + (2 + (foldr (+) 0 [3..1000000])) -->
-- 1 + (2 + (3 + (foldr (+) 0 [4..1000000]))) -->
-- 1 + (2 + (3 + (4 + (foldr (+) 0 [5..1000000])))) -->
-- stack overflow

-- foldl (+) 0 [1..1000000]

-- (foldl (+) 0 [2..1000000]) + 1
-- ((foldl (+) 0 [3..1000000]) + 2) + 1 -->
-- (((foldl (+) 0 [4..1000000]) + 3) + 2) + 1 -->
-- stack overflow

-- foldl' (+) 0 [1..1000000]

-- foldl' (+) 0 [1..1000000] -->
-- foldl' (+) 1 [2..1000000] -->
-- foldl' (+) 3 [3..1000000] -->
-- foldl' (+) 6 [4..1000000] -->
-- foldl' (+) 10 [5..1000000] -->
-- ...
-- ... You see that the stack doesn't overflow
-- ...
-- foldl' (+) 499999500000 [1000000] -->
-- foldl' (+) 500000500000 [] -->
-- 500000500000