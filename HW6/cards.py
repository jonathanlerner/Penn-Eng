'''Submission for Jonathan Lerner and Zaks Lubin'''
import random  # needed for shuffling a Deck
import string  # needed for some string functions

class Card(object):
    #the card has a suit which is one of 'S','C','H' or 'D'
    #the card has a rank 
    
    def __init__(self, r, s):
        '''where r is the rank, s is suit'''
        if type(r) == str:
            r = string.upper(r)
        self.r = r
        s = string.upper(s)
        self.s = s

        
    def __str__(self):
        return str(self.r) + str(self.s) 

    def get_rank(self):
        return self.r

    def get_suit(self):
        return self.s

class Deck():
    '''Denote a deck to play cards with'''
     
    def __init__(self):
        """Initialize deck as a list of all 52 cards:
           13 cards in each of 4 suits"""
        self.deck = []
        for rank in range(2,11)+['A','K','Q','J']:
            for suit in ['C','D','H','S']:
                self.deck.append(Card(rank,suit))

    def shuffle(self):
        """Shuffle the deck"""
        random.shuffle(self.deck)

    def get_deck(self):
        return self.deck

    def deal(self):
        # get the last card in the deck
        # simulates a pile of cards and getting the top one
        deal = self.deck.pop()
        return deal
    
    def __str__(self):
        """Represent the whole deck as a string for printing -- very useful during code development"""
       #the deck is a list of cards
        deck_str = ''
        for i in range(0,len(self.deck)):
            deck_str += (str(self.get_deck()[i]) + '\n')
        return deck_str


        

