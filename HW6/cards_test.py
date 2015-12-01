'''submission for Jonathan Lerner and Zaks Lubin'''
from cards import *
import unittest

class TestCards(unittest.TestCase):

    def test_card_init(self):
        card1 = Card(5,'S')
        card2 = Card('A','C')
        card3 = Card(3,'d')
        card4 = Card('a','H')
        self.assertEqual(5, card1.r)
        self.assertEqual('S', card1.s)
        self.assertEqual('A', card2.r)
        self.assertEqual('C', card2.s)
        self.assertEqual(3, card3.r)
        self.assertEqual('D', card3.s)
        self.assertEqual('A', card4.r)
        self.assertEqual('H', card4.s)
        
        
    def test_card_str(self):
        card1 = Card(10,'D')
        self.assertEqual('10D', str(card1))
        card2 = Card('K','S')
        self.assertEqual('KS', str(card2))

    def test_card_get_rank(self):
        card1 = Card(2,'H')
        card2 = Card('Q', 'D')
        self.assertEqual(2, card1.get_rank())
        self.assertEqual('Q', card2.get_rank())

    def test_card_get_suit(self):
        card1 = Card(2,'H')
        card2 = Card('Q', 'D')
        self.assertEqual('H', card1.get_suit())
        self.assertEqual('D', card2.get_suit())

    def test_deck_init(self):
        d = Deck().get_deck()
        self.assertEqual(str(d[0]), str(Card(2,'C')))
        self.assertEqual(str(d[1]), str(Card(2,'D')))
        self.assertEqual(str(d[51]), str(Card('J','S')))

        self.assertTrue(len(d)==52)

    def test_deck_shuffle(self):
        d1 = Deck()
        deck1 = d1.get_deck()
        deck1_str = str(d1)
        d2 = Deck()
        d2.shuffle()
        deck2 = d2.get_deck()
        deck2_str = str(d2)
        self.assertEqual(len(deck2), 52)
        self.assertNotEqual(deck1_str, deck2_str)   #should _ALMOST_ always work!
        for i in range(0,52):
            self.assertTrue(str(deck1[i]) in deck2_str)

    def test_deck_get_deck(self):
        d = Deck()
        self.assertEqual(d.deck, d.get_deck())

    def test_deck_deal(self):
        d1 = Deck()
        d2 = d1
        for i in range(0,52):
            self.assertEqual(str(d2.get_deck()[52-1-i]),str(d1.deal()))

    def test_deck_str(self):
        d = Deck()
        deck_str = str(d)
        '''let's spot check some parts of the string'''
        self.assertEqual('2',deck_str[0])
        self.assertEqual('C',deck_str[1])
        self.assertEqual('\n',deck_str[2])
        self.assertEqual('\n',deck_str[-1])
        self.assertEqual('S',deck_str[-2])
        self.assertEqual('J',deck_str[-3])
        self.assertEqual('10',deck_str[108:110])
        self.assertEqual(3*52+4,len(deck_str))
        
        
        

unittest.main()
