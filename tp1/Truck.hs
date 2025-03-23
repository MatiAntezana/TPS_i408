--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT, unloadT, netT) where

import Palet
import Stack
import Route
import Text.XHtml (height)

data Truck = Tru [Stack] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck
newT cantStacks maxPalets ruta | cantStacks <= 0 = error "Error: El camión debe tener al menos una bahía."
                               | maxPalets <= 0 = error "Error: La altura de las bahías debe ser positiva."
                               | otherwise = Tru (replicate cantStacks (newS maxPalets)) ruta

freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)

-- La función busca en qué bahía (stack) del camión se puede apilar el palet respetando holdsS.
-- Devuelve el índice de la bahía donde se puede apilar el palet.
-- Si no se puede apilar en ninguna bahía, devuelve -1.
searchStack :: Truck -> Int -> Palet -> Int
searchStack (Tru [] route) idx palet = -1
searchStack (Tru (stack : stacks) route) idx palet | holdsS stack palet route = idx
                                                   | otherwise = searchStack (Tru stacks route) (idx+1) palet

-- La funcicó actualiza el stack correspondiente y devuelve la lista de stack actualizada
updateStack :: [Stack] -> [Stack] -> Int -> Int -> Palet -> [Stack]
updateStack [] newStacks _ _ palet = newStacks
updateStack (stack : oldStacks) newStacks idx_aim new_idx palet | idx_aim == new_idx = newStacks ++ [stackS stack palet] ++ oldStacks
                                                                | otherwise = updateStack oldStacks (newStacks ++ [stack]) idx_aim (new_idx+1) palet

loadT :: Truck -> Palet -> Truck
loadT (Tru stacks route) palet | not (inRouteR route (destinationP palet)) = error "Error: El palet no pertenece a la ruta."
                               | freeCellsT (Tru stacks route) == 0 = error "Error: No hay celdas vacias en el truck"
                               | searchStack (Tru stacks route) 0 palet == -1 = error "Error: No se puede cargar el palet."
                               | otherwise = Tru (updateStack stacks [] (searchStack (Tru stacks route) 0 palet) 0 palet) route

unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city | not (inRouteR route city) = error "Error: La ciudad no pertenece a la ruta."
                                | otherwise = Tru (map (`popS` city) stacks) route


netT :: Truck -> Int
netT (Tru [] _) = 0
netT (Tru (stack : stacks) route) = netS stack + netT (Tru stacks route)
