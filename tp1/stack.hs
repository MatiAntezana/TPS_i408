module Stack(Stack, newS, freeCellsS, stackS, netS, holdsS, popS) where
import Route
import Palet

data Stack = Sta[Palet] Int deriving (Eq, Show)

newS :: Int -> Stack
newS maxPalets  | maxPalets <= 0 = error "Error: La capacidad de la pila debe ser positiva."
                | otherwise = Sta [] maxPalets  

freeCellsS :: Stack -> Int
freeCellsS (Sta palets maxPalets) = maxPalets - length palets

stackS :: Stack -> Palet -> Stack
stackS (Sta palets maxPalets) palet = Sta (palet : palets) maxPalets

netS :: Stack -> Int
netS (Sta palets _) = sum (map netP palets)

holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta palets maxPalets) palet ruta  | freeCellsS (Sta palets maxPalets) <=0 = False
                                          | (netS (Sta palets maxPalets) + netP palet) > 10 = False
                                          | not (all (\p -> inOrderR ruta (destinationP palet) (destinationP p)) palets) = False
                                          | otherwise = True

popS :: Stack -> String -> Stack
popS (Sta [] maxPalets) _ = Sta [] maxPalets
popS (Sta (palet:palets) maxPalets) destinationCity   | destinationP palet == destinationCity = popS (Sta palets maxPalets) destinationCity
                                                      | otherwise = Sta (palet : palets) maxPalets
