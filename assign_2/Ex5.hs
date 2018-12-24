import Ex1
import Test.HUnit

-- Return an item inside a ListBag
returnLB :: a -> ListBag a
returnLB x = singleton x

-- ################

-- Map the f to the elements, then joins results: bindLB (fromList [1,2,2,3]) (\x -> singleton (x*2))
bindLB :: Eq a1 => ListBag a2 -> (a2 -> ListBag a1) -> ListBag a1
bindLB a f = foldr sumBag (LB []) (map f (toList a))

-- bindLB (fromList [1,2,2,3]) (\x -> singleton (x*2))

-- Since every Monad should be also Functor, but we already saw in the previous exercise that
-- the fmap cannot be implemented due to a too strict bond in the Eq parameter in the ListBag, 
-- we cannot implement the Monad class.
-- To implement the Functor, and thus the Monad, we should first relax the equality constraint in the concrete type.

--instance Monad ListBag where
-- return = returnLB
-- (>>=) = bindLB

-- ################

testReturnLB = TestCase $ assertBool "wf (returnLB 1) == True" (wf (returnLB 1))
testBindLB = TestCase $ assertEqual "bindLB (fromList [1,2,2,3]) (\\x -> singleton (x*2)) == 10" (toList (bindLB (fromList [1,2,2,3]) (\x -> singleton (x*2)))) [2,4,4,6]

testList = TestList [
    TestLabel "testReturnLB" testReturnLB,
    TestLabel "testBindLB" testBindLB
  ]

main = do
  runTestTT testList
  return ()