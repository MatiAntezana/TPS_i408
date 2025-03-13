module Stack(Stack, newS, freeCellsS, stackS, netS, holdsS, popS) where

import Route
import Palet

data Stack = Sta[Palet] Int deriving (Eq, Show)


newS :: Int -> Stack
newS size = Sta [] size

freeCellsS :: Stack -> Int
freeCellsS (Sta palet size) | length palet == size = 0
                            | length palet < size = size - length palet 

-- Agrego al principio el nuevo palet
stackS :: Stack -> Palet -> Stack
stackS (Sta list_palet size) palet = Sta (palet : list_palet) size


netS :: Stack -> Int
netS (Sta (first_palet:list_palet) size) | length list_palet == 0 = netP first_palet
                                         | otherwise = netP first_palet + netS (Sta list_palet size)


-- Dice que se puede apilar si la ciudad destino del último palet de la pila 
-- coincide con la ciudad destino del palet que voy a agregar
holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta (first_palet:list_palet) size) palet route | destinationP first_palet == destinationP palet = True
                                        | otherwise = False


popS :: Stack -> String -> Stack
popS (Sta [] size) _ = Sta [] size
popS (Sta (first_palet:list_palet) size) city_dest | destinationP first_palet == city_dest = popS (Sta list_palet size) city_dest
                                                   | otherwise = Sta ([first_palet] ++ list_palet) (size)

