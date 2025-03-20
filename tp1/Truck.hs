--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT, unloadT, netT) where

import Palet
import Stack
import Route
import Text.XHtml (height)

-- Defino el tipo de dato Truck, que es un constructor de datos que acepta una lista de Stacks y una Ruta.
-- La lista de Stacks representa las bahías del camión, y la Ruta representa el recorrido que debe seguir el camión.
data Truck = Tru [Stack] Route deriving (Eq, Show)


-- La función newT debe crear un camión con:
-- Una cantidad de bahías (nro_bahias), que serán las pilas (Stack).
-- Una altura máxima por bahía (altura), que representa la cantidad máxima de palets que puede apilarse en cada una.
-- Una ruta (Route) que el camión debe seguir.
-- replicate :: Int -> a -> [a]
-- Replicate es una función que toma un entero y un elemento, y devuelve una lista con el elemento repetido la cantidad de veces del entero.

newT :: Int -> Int -> Route -> Truck
newT cant_stacks max_palets ruta | cant_stacks <= 0 = error "Error: El camión debe tener al menos una bahía."
                            | max_palets <= 0 = error "Error: La altura de las bahías debe ser positiva."
                            | otherwise = Tru (replicate cant_stacks (newS max_palets)) ruta


-- La función debe responder cuántas celdas libres hay en todo el camión, sumando las celdas libres de cada bahía (Stack).
-- map: aplica una función a cada elemento de una lista y devuelve una nueva lista con los resultados. 
freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)

-- La función busca en qué bahía (stack) del camión se puede apilar el palet respetando holdsS.
-- Devuelve el índice de la bahía donde se puede apilar el palet.
-- Si no se puede apilar en ninguna bahía, devuelve -1.
searchStack :: Truck -> Int -> Palet -> Int
searchStack (Tru [] route) idx palet = -1
searchStack (Tru (stack : stacks) route) idx palet | holdsS stack palet route == True = idx
                                                   | otherwise = searchStack (Tru stacks route) (idx+1) palet

updateStack :: [Stack] -> [Stack] -> Int -> Int -> Palet -> [Stack]
updateStack [] new_list_stack _ _ palet = new_list_stack
updateStack (stack : old_list_stack) new_list_stack idx_aim new_idx palet | idx_aim == new_idx = new_list_stack ++ [stackS stack palet] ++ old_list_stack
                                                                          | otherwise = updateStack old_list_stack (new_list_stack ++ [stack]) idx_aim (new_idx+1) palet


-- La función loadT debe cargar un palet en el camión, es decir, ubicarlo en alguna de sus bahías (Stack) respetando las restricciones.
loadT :: Truck -> Palet -> Truck
loadT (Tru list_stack route_) palet_| inRouteR route_ (destinationP palet_) == False = error "Error: El palet no pertenece a la ruta."
                                                    | freeCellsT (Tru list_stack route_) == 0 = error "Error: No hay celdas vacias en el truck"
                                                    | searchStack (Tru list_stack route_) 0 palet_ == -1 = error "Error: No se puede cargar el palet."
                                                    | otherwise = Tru (updateStack list_stack [] (searchStack (Tru list_stack route_) 0 palet_ ) 0 palet_)  route_

unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city | not (inRouteR route city) = error "Error: La ciudad no pertenece a la ruta."
                                | otherwise = Tru (map (`popS` city) stacks) route


netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack : stacks) route) = netS stack + netT (Tru stacks route)