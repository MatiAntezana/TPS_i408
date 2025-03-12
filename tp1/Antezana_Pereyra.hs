module Palet(Palet, newP, destinationP, netP) where

data Palet = Pal String Int deriving (Eq, Show)
newP :: (String, Int) -> Palet
newP (city, weight) = Pal city weight

destinationP :: Palet -> String
destinationP (Pal name_city _) = name_city

netP :: Palet -> Int
netP (Pal _ x) = x


-- module Stack (Stack, newS, freeCellsS, stackS, netS, holdsS, popS) where
--     data Stack = Sta[Palet] int deriving (Eq, Show)
--     newS :: int -> Stack
--     freeCellsS :: Stack -> int
--     stackS :: Stack -> Palet -> Stack
--     netS :: Stack -> int
--     holdsS :: Stack -> Palet -> Route -> bool
--     popS :: Stack -> string -> Stack

-- module Truck (Truck, newT, freeCellsT, loadT, unloadT, netT) where
--     data Truck = Tru[Stack] Route  -> Truck
--     freeCellsT :: Truck -> int
--     loadT :: Truck -> Palet -> Truck
--     unloadT :: Truck -> string -> Truck
--     netT :: Truck -> int