--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT, netT) where

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

build_list_stack :: Truck -> Int -> Int -> Truck
build_list_stack (Tru list_stack route_) nro_bahias size | length list_stack == nro_bahias = Tru list_stack route_
                                                           | otherwise = build_list_stack (Tru (list_stack ++ [newS size])route_) nro_bahias size
newT :: Int -> Int -> Route -> Truck
newT nro_bahias altura ruta | nro_bahias <= 0 = error "Error: El camión debe tener al menos una bahía."
                            | altura <= 0 = error "Error: La altura de las bahías debe ser positiva."
                            | otherwise = Tru (replicate nro_bahias (newS altura)) ruta


-- La función debe responder cuántas celdas libres hay en todo el camión, sumando las celdas libres de cada bahía (Stack).
-- map: aplica una función a cada elemento de una lista y devuelve una nueva lista con los resultados. 
freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)


search_stack :: Truck -> Int -> Palet -> Int
search_stack (Tru [] route_) idx palet_ = -1
search_stack (Tru (stack_:list_stack) route_) idx palet_ | holdsS stack_ palet_ route_ == True = idx
                                                    | otherwise = search_stack (Tru list_stack route_) (idx+1) palet_

update_stack :: [Stack] -> [Stack] -> Int -> Int -> Palet -> [Stack]
update_stack [] new_list_stack _ _ palet_ = new_list_stack 
update_stack (stack_:old_list_stack) new_list_stack idx_aim new_idx palet_| idx_aim == new_idx = new_list_stack ++ [(stackS stack_ palet_)] ++ old_list_stack
                                                        | otherwise = update_stack old_list_stack (new_list_stack ++ [stack_]) idx_aim (new_idx+1) palet_

-- La función loadT debe cargar un palet en el camión, es decir, ubicarlo en alguna de sus bahías (Stack) respetando las restricciones.
loadT :: Truck -> Palet -> Truck
loadT (Tru list_stack route_) palet_| inRouteR route_ (destinationP palet_) == False = Tru list_stack route_
                                                    | search_stack (Tru list_stack route_) 0 palet_ == -1 = Tru [] route_
                                                    | otherwise = Tru (update_stack list_stack [] (search_stack (Tru list_stack route_) 0 palet_ ) 0 palet_)  route_



-- unloadT :: Truck -> string -> Truck

-- (esta no la vi todavia)
netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack_:list_stack) route_) | length list_stack == 0 = netS stack_
                                      | otherwise = netS stack_ + netT (Tru list_stack route_)
