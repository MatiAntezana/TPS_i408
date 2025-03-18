--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT) where

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
newT nro_bahias altura ruta | nro_bahias <= 0 = error "Error: El camión debe tener al menos una bahía."
                            | altura <= 0 = error "Error: La altura de las bahías debe ser positiva."
                            | otherwise = Tru (replicate nro_bahias (newS altura)) ruta


-- La función debe responder cuántas celdas libres hay en todo el camión, sumando las celdas libres de cada bahía (Stack).
-- map: aplica una función a cada elemento de una lista y devuelve una nueva lista con los resultados. 
freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)


-- La función busca en qué bahía (stack) del camión se puede apilar el palet respetando holdsS.
-- Devuelve el índice de la bahía donde se puede apilar el palet.
-- Si no se puede apilar en ninguna bahía, devuelve -1.
searchStack :: Truck -> Int -> Palet -> Int
searchStack (Tru [] route) idx palet = -1
searchStack (Tru (stack : stacks) route) idx palet | holdsS stack palet route = idx
                                                   | otherwise = searchStack (Tru stacks route) (idx+1) palet


-- Recorre la lista de stacks y apila el palet en la bahía correcta.
-- Usa idx_aim para saber en qué posición insertar el nuevo stack modificado.
-- Devuelve la lista de stacks actualizada.
updateStack :: [Stack] -> [Stack] -> Int -> Int -> Palet -> [Stack]
updateStack [] new_list_stack _ _ palet = new_list_stack
updateStack (stack : old_list_stack) new_list_stack idx_aim new_idx palet | idx_aim == new_idx = new_list_stack ++ [stackS stack palet] ++ old_list_stack
                                                                          | otherwise = updateStack old_list_stack (new_list_stack ++ [stack]) idx_aim (new_idx+1) palet

-- La función loadT debe cargar un palet en el camión, es decir, ubicarlo en alguna de sus bahías (Stack) respetando las restricciones.
loadT :: Truck -> Palet -> Truck
loadT (Tru stacks route) palet | not (inRouteR route (destinationP palet)) = error "Error: El palet no pertenece a la ruta."
                               | searchStack (Tru stacks route) 0 palet == -1 = error "Error: No se puede cargar el palet."
                               | otherwise = Tru (updateStack stacks [] (searchStack (Tru stacks route) 0 palet) 0 palet) route
                            

-- Esta función elimina todos los palets con un destino específico de la pila.
-- Se descarga todo lo que tenga como destino la ciudad dada.
-- Las demás bahías del camión se mantienen igual.
-- La estructura del camión no cambia, solo los stacks se actualizan.
unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city | not (inRouteR route city) = error "Error: La ciudad no pertenece a la ruta."
                                | otherwise = Tru (map (`popS` city) stacks) route


-- La función netT debe calcular el peso neto de los palets que están cargados en el camión. Es decir, debemos sumar el peso de todos los palets que están en las bahías del camión (las pilas de palets).
netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack : stacks) route) = netS stack + netT (Tru stacks route)
