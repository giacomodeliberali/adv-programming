import qualified Ex1
import Test.HUnit

-- Abstract data type of a MultiSet, with the Eq type class constraints 
-- are imposed by the concrete implementation ListBag

class MultiSet ms where
    empty :: ms a
    singleton :: a -> ms a
    fromList :: Eq a => [a] -> ms a
    isEmpty :: Eq a => ms a -> Bool
    mul :: Eq a => a -> ms a -> Int
    toList :: ms a -> [a]
    sumBag :: Eq a => ms a -> ms a -> ms a

-- ################

-- Instance of MultiSet by ListBag

instance MultiSet Ex1.ListBag where
  empty = Ex1.empty
  singleton = Ex1.singleton
  fromList = Ex1.fromList
  isEmpty = Ex1.isEmpty
  mul = Ex1.mul
  toList = Ex1.toList
  sumBag = Ex1.sumBag
  
-- ################

-- Another concrete implementation of a MultiSet

data MultiSetList a = MSL [a] deriving (Show)

-- ################

-- Return an empty list
emptyMSL :: MultiSetList ms
emptyMSL = MSL []

-- ################

-- Return the item in a list
singletonMSL :: a -> MultiSetList a
singletonMSL a = MSL [a]

-- ################

-- Return a MultiSetList with the given list
fromListMSL :: [a] -> MultiSetList a
fromListMSL x = MSL x

-- ################

-- Return True oif the MultiSetList is empty
isEmptyMSL :: Eq a => MultiSetList a -> Bool
isEmptyMSL (MSL m) = m == []

-- ################

-- Returns the multiplicity of v in the MSL
mulMSL :: Eq a => a -> MultiSetList a -> Int
mulMSL v (MSL ms2) = length (filter (\x -> (x==v)) ms2)

-- ################

-- Return the list of the MSL
toListMSL :: MultiSetList ms -> [ms]
toListMSL (MSL ms2) = ms2

-- ################

-- Concatenate two MSL
sumBagMSL :: MultiSetList ms -> MultiSetList ms -> MultiSetList ms
sumBagMSL (MSL a) (MSL b) = MSL (a ++ b)

-- ################

-- Create an instance of the MultiSet with the new concrete type MultiSetList
instance MultiSet MultiSetList where
  empty = emptyMSL
  singleton = singletonMSL
  fromList = fromListMSL
  isEmpty = isEmptyMSL
  mul = mulMSL
  toList = toListMSL
  sumBag = sumBagMSL

-- ################

-- Map the f to the elements, then joins results
bindMSL :: MultiSetList a -> (a -> MultiSetList ms) -> MultiSetList ms
bindMSL a f = foldr sumBagMSL (MSL[]) (map f (toListMSL a))

-- ################

-- Applies the list of function to the list of elements, then concatenates results
applicativeMSL :: MultiSetList (a1 -> a2) -> MultiSetList a1 -> MultiSetList a2
applicativeMSL (MSL []) b = emptyMSL
applicativeMSL (MSL (x:xs)) (MSL b) = sumBagMSL (MSL (map x b)) (applicativeMSL (MSL xs) (MSL b))

-- ################

instance Foldable MultiSetList where
    foldr f acc (MSL a) = foldr f acc a

-- ################

instance Applicative MultiSetList where
    pure  = singletonMSL
    (<*>) = applicativeMSL

-- ################

instance Functor MultiSetList where
    fmap f (MSL a) = MSL (map f a)

-- ################

-- Every Monad should be instance of Foldable, Functor and Applicative

instance Monad MultiSetList where
    return = singletonMSL
    (>>=)  = bindMSL

-- Eg of bind in this monad:
-- (fromListMSL [1,2,3]) >>= (\x -> singletonMSL (x*2)) >>= (\x -> singletonMSL (x*2))

-- ################

-- Simple function that can be applied to both concrete types of MultiSet (replicate + flatMap)
simpleFunctionFlatMap :: (MultiSet ms, Eq a)  => ms a -> [a]
simpleFunctionFlatMap a = (toList a) >>= \x -> [x,x]

testSimpleFunctionFlatMapLB = TestCase $ assertEqual "simpleFunctionFlatMap over ListBag" (simpleFunctionFlatMap (Ex1.fromList [1,2,3])) [1,1,2,2,3,3]
testSimpleFunctionFlatMapMSL = TestCase $ assertEqual "simpleFunctionFlatMap over MultiSetList" (simpleFunctionFlatMap (fromListMSL [1,2,3])) [1,1,2,2,3,3]

testList = TestList [
    TestLabel "testSimpleFunctionFlatMapLB" testSimpleFunctionFlatMapLB,
    TestLabel "testSimpleFunctionFlatMapMSL" testSimpleFunctionFlatMapMSL
  ]

main = do
  runTestTT testList
  return ()