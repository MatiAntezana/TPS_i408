module Truck ( Truck, newT, freeCellsT, netT ) where

import Stack
import Route
import Palet

data Truck = Tru [Stack] Route deriving (Eq, Show)

build_list_stack :: Truck -> Int -> Int -> Truck
build_list_stack (Tru list_stack route_) nro_bahias size | length list_stack == nro_bahias = Tru list_stack route_
                                                           | otherwise = build_list_stack (Tru (list_stack ++ [newS size])route_) nro_bahias size
newT :: Int -> Int -> Route -> Truck
newT nro_bahias height route_ = build_list_stack (Tru [] route_) nro_bahias height

freeCellsT :: Truck -> Int
freeCellsT (Tru [] route_) = 0
freeCellsT (Tru (stack_:list_stack) route_) | length list_stack == 0 = freeCellsS stack_
                                            | otherwise = freeCellsS stack_ + freeCellsT (Tru list_stack route_)

-- loadT :: Truck -> Palet -> Truck
-- loadT (Tru [sta])
--     unloadT :: Truck -> string -> Truck

netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack_:list_stack) route_) | length list_stack == 0 = netS stack_
                                 | otherwise = netS stack_ + netT (Tru list_stack route_)