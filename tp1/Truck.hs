module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
data Truck = Tru [Stack] Route deriving (Eq, Show)

build_list_stack :: Truck -> Int -> Truck
build_list_stack (Tru list_stack route_) nro_bahias size | length list_stack == nro_bahias = Tru list_stack route_
                                                           | otherwise = build_list_stack (Tru list_stack ++ [newS size]) nro_bahias size

newT :: Int -> Int -> Route -> Truck
newT nro_bahias height route_ = build_list_stack (Tru [] route_) nro_bahias height

freeCellsT :: Truck -> Int
freeCellsT (Tru [] route_) = 0
freeCellsT (Tru (stack_:list_stack) route_) | length list_stack == 0 = freeCellsS stack_
                                            | otherwise = freeCellsT (Tru list_stack route_)

-- loadT :: Truck -> Palet -> Truck
-- loadT (Tru [sta])
--     unloadT :: Truck -> string -> Truck

netT :: Truck -> Int
netT (Tru [] _) = 0 -- Caso borde de que no haya ningun stack
netT (Tru (stack_:list_stack) _) | length list_stack == 0 = newS stack_
                                 | otherwise = netS stack_ + netT list_stack _