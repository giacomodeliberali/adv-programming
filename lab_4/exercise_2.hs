data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a) | Div (Expr a) (Expr a) 



{- safeEval (Const a) = Just a
safeEval (Div a b) = case safeEval a of
                        Nothing -> Nothing
                        Just aa -> case safeEval b of
                            Nothing -> Nothing
                            Just bb -> safeDiv aa bb -}


--safeEval (Div a b) = (safeEval a) >>= (\first -> (safeEval b) >>= (\second -> safeDiv first second))


safeDiv a b = if b == 0 then Nothing else Just (a / b)
safeEval (Const a) = Just a
safeEval (Div a b) = do 
    first <- safeEval a
    second <- safeEval b
    safeDiv first second

safeEval (Sum a b) = do 
    first <- safeEval a
    second <- safeEval b
    Just (first + second)

safeEval (Mul a b) = do 
    first <- safeEval a
    second <- safeEval b
    Just (first * second)