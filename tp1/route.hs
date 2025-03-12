module Route(Route, newR, inOrderR) where

data Route = Rou[String] deriving (Eq, Show)
newR :: [String] -> Route
newR (list_citys) = Rou [list_citys]

inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou (first_element:list_city)) city1 city2 | first_element == city1 = True
                           | first_element == city2 = False
                           | tail(list_city) = inOrderR(Rou (list_city) city1 city2)