module Palet(Palet, newP, destinationP, netP) where

data Palet = Pal String Int deriving (Eq, Show)
newP :: (String, Int) -> Palet
newP (city, weight) = Pal city weight

destinationP :: Palet -> String
destinationP (Pal name_city _) = name_city

netP :: Palet -> Int
netP (Pal _ x) = x

module Route(Route, newR, inOrderR) where

data Route = Rou[String] deriving (Eq, Show)
newR :: [String] -> Route
newR (list_citys) = Rou [list_citys]

inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou city1 city2) | head(Rou) == city1 = True
                           | head(Rou) == city2 = False
                           | tail(Rou) = inOrderR(Rou city1 city2)

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