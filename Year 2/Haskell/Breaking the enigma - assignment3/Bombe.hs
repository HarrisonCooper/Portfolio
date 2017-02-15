module Bombe where

 import EnigmaAndMenu
 import Data.Maybe
 import Data.Tuple
 import Data.List
 
 type SteckerPair = (Char,Char)
 
 -- Main function, that takes the crib.
 -- From the crib, it produces the menu to follow and also the initial stecker.
 -- Calls breakEA with the initialStecker and initialOffset (0,0,0).
 breakEnigma :: Crib -> Maybe (Offsets, Stecker)
 breakEnigma crib 
  | implication /= Nothing = implication
  | otherwise = Nothing
  where
   findMenu = longestMenu crib
   initialLetter = fst $ crib!!(head findMenu)
   initialStecker = [(initialLetter, initialLetter)]
   implication = breakEA crib findMenu initialStecker (0,0,0)
 
 -- Try each initial offset setting in turn untill one works or reach (25,25,25)
 -- If the result of using the offset is Nothing, it recurses with the stepped offset.
 breakEA :: Crib -> Menu -> Stecker -> Offsets -> Maybe (Offsets, Stecker)
 breakEA crib menu initialStecker offset 
  | offset == finalOffset = Nothing
  | implication == Nothing = breakEA crib menu initialStecker nextOffset
  | otherwise = Just (offset, (fromJust implication))
  where 
   implication = findStecker crib menu initialStecker offset
   finalOffset = (25,25,25)
   nextOffset = offset_step offset
 
 -- Searchs for a stecker which matches the crib given the current offsets.
 -- It takes the initial stecker and if no that produces no results, it recurses
 -- untill all 26 initial steckers have been tried. If this produces nothing, the 
 -- inital offsets get incremented in breakEA.
 -- To increment the stecker, I take the letter to the right of the current letter, and 
 -- put it into the second element of the tuple. (e.g. [('E','E')] -> [('E','F)]).
 findStecker :: Crib -> Menu -> Stecker -> Offsets -> Maybe Stecker
 findStecker crib menu stecker offsets 
  | nextLetter == fst (head stecker) = Nothing
  | implication == Nothing = findStecker crib menu nextStecker offsets
  | otherwise = implication
  where
   implication = followMenu crib menu stecker offsets
   cycledAlphabet = cycle alphabet
   currentLetterPosition = elemIndex (snd (head stecker)) cycledAlphabet
   nextLetter = cycledAlphabet!!((fromJust currentLetterPosition) +1)
   nextStecker = [(fst (head stecker),nextLetter)]
 
 -- Takes the crib and tries to form a stecker board by following the menu.
 -- If a stecker pair is compatiable with the current steckerList, followMenu
 -- recurses with this, seeing if a solution is possible with the set offsets
 -- and initial stecker.
 followMenu :: Crib -> Menu -> Stecker -> Offsets -> Maybe Stecker
 followMenu _ [] steckerList _ = Just steckerList
 followMenu crib menu steckerList offsets
  | implication /= Nothing = followMenu crib (tail menu) (fromJust implication) offsets
  | otherwise = Nothing
  where
   i = head menu
   p = fst(crib!!i)
   q = stecker p steckerList
   r = enigmaEncodeA q (SimpleEnigma rotor1 rotor2 rotor3 reflectorB) (offsetN offsets (i+1))
   c = snd (crib!!i)
   pair = (r,c)
   implication = steckerAdd pair steckerList
   steppedOffsets = offset_step offsets
 
 -- checks to see if the pair is in the stecker list, both forwards
 -- and backwards. If the pair is already in the list, it returns
 -- the orignial stecker list. Else it adds the new pair to the list.
 steckerAdd :: SteckerPair -> Stecker -> Maybe Stecker
 steckerAdd (r,c) stecker 
  | elem (r,c) stecker == True = Just stecker
  | elem (swap (r,c)) stecker == True = Just stecker
  | inStecker == [] = Just (stecker++[(r,c)])
  | otherwise = Nothing
  where
    inStecker = filter(\(a,b) -> a==r || b==r || a==c || b==c) stecker