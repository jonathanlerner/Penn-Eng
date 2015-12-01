'''submission for Jonathan Lerner and Zaks Lubin'''
from SoloBlackJack import Blackjack
from cards import *

import unittest

class TestCards(unittest.TestCase):

    def test_init(self):
        bj = Blackjack()
        bj_table = bj.table
        bj_discard = bj.discardList
        bj_deck = bj.deck
        d = Deck()
        self.assertEqual(bj_table['row1'],[1,2,3,4,5])
        self.assertEqual(bj_table['row2'],[6,7,8,9,10])
        self.assertEqual(bj_table['row3'],[11,12,13])
        self.assertEqual(bj_table['row4'],[14,15,16])
        self.assertEqual(bj_discard,[17,18,19,20])
        self.assertEqual(str(bj_deck), str(d))

    def test_get_table(self):
        bj = Blackjack()
        self.assertEqual(bj.table, bj.get_table())

    def test_get_discard(self):
        bj = Blackjack()
        self.assertEqual(bj.discardList, bj.get_discard())

    def test_no_move_error(self):
        bj = Blackjack()
        bj_table = bj.get_table()
        bj_discard = bj.get_discard()
        bj_table['row1'][0] = Card(4,'H')
        bj_table['row1'][4] = Card('Q','D')
        bj_table['row4'][2] = Card('A','C')
        bj_discard[3] = Card(9,'D')
        
        self.assertFalse(bj.no_move_error(0))
        self.assertFalse(bj.no_move_error(21))
        self.assertFalse(bj.no_move_error(1))
        self.assertTrue(bj.no_move_error(2))
        self.assertTrue(bj.no_move_error(3))
        self.assertTrue(bj.no_move_error(4))
        self.assertFalse(bj.no_move_error(5))
        self.assertTrue(bj.no_move_error(15))
        self.assertFalse(bj.no_move_error(16))
            
        self.assertTrue(bj.no_move_error(17))
        self.assertFalse(bj. no_move_error(20))

    def test_card_replace(self):
        c1 = Card(2,'S')
        c2 = Card('Q','D')
        c3 = Card('A','S')
        c4 = Card(5,'C')
        c5 = Card(6,'C')
        bj = Blackjack()
        bj.card_replace(1,c1)

        self.assertEqual(str(bj.get_table()['row1'][0]),'2S')
        
        bj.card_replace(7,c2)
        bj.card_replace(16,c3)
        bj.card_replace(20,c4)
        self.assertEqual(str(bj.get_table()['row2'][1]),'QD')
        self.assertEqual(str(bj.get_table()['row4'][2]),'AS')
        self.assertEqual(str(bj.get_discard()[3]),'5C')

        bj.card_replace(1,c5)
        self.assertEqual(str(bj.get_table()['row1'][0]),'2S')
        
    def test_open_board_slots(self):
        bj = Blackjack()
        c1 = Card(2,'S')
        c2 = Card('Q','D')
        c3 = Card('A','S')
        c4 = Card(5,'C')
        self.assertEqual(bj.open_board_slots(),range(1,17))

        bj.card_replace(1,c1)
        bj.card_replace(7,c2)
        bj.card_replace(16,c3)
        bj.card_replace(20,c4)
        self.assertEqual(bj.open_board_slots(),[2,3,4,5,6,8,9,10,11,12,13,14,15])


    def test_open_discard_slots(self):
        bj = Blackjack()
        c1 = Card(2,'S')
        c2 = Card('Q','D')
        c3 = Card('A','S')
        c4 = Card(5,'C')
        bj.card_replace(17,c1)
        self.assertEqual(bj.open_discard_slots(),[18,19,20])
        bj.card_replace(18,c2)
        bj.card_replace(19,c3)
        bj.card_replace(20,c4)
        self.assertEqual(bj.open_discard_slots(),[])

    def test_flatten(self):
        bj = Blackjack()
        lst1 = []
        lst3 = [[1,2,3],[1,'hello',4]]
        lst4 = [[1,2,3],[1,2,4],[8,9,10]]

        self.assertEqual(bj.flatten(lst1),[])
        self.assertEqual(bj.flatten(lst3),[1,2,3,1,'hello',4])
        self.assertEqual(bj.flatten(lst4),[1,2,3,1,2,4,8,9,10])

    def test_make_hands(self):
        bj = Blackjack()
        all_hands = bj.make_hands()

        self.assertEqual(len(all_hands),9)
        my_hands = [range(1,6),range(6,11),range(11,14),range(14,17),
                    [1,6],[5,10],[2,7,11,14],[3,8,12,15],[4,9,13,16]]
        self.assertEqual(all_hands, my_hands)

    def test_score_hand(self):
        bj = Blackjack()
        c = [Card(x,'D') for x in range(2,11)+['J','Q','K','A']]

        '''single ace cases'''
        a1 = [[x,c[12]] for x in c]
        a1_score = [bj.score_hand(x) for x in a1]
        self.assertEqual(a1_score,[1,1,1,1,2,3,4,5,10,10,10,10,1])

        '''double ace cases'''
        a2 = [[c[12],c[12],x] for x in c]
        a2_score = [bj.score_hand(x) for x in a2]
        self.assertEqual(a2_score,[1,1,1,2,3,4,5,7,1,1,1,1,1])

        '''triple ace cases'''
        a3 = [[c[12],c[12],x,c[12]] for x in c]
        a3_score = [bj.score_hand(x) for x in a3]
        self.assertEqual(a3_score,[1,1,2,3,4,5,7,1,1,1,1,1,1])

        '''double ace cases with 2 other cards'''
        a4 = [[c[12],x,c[12],c[0]] for x in c]
        a4_score = [bj.score_hand(x) for x in a4]
        self.assertEqual(a4_score,[1,2,3,4,5,7,1,1,1,1,1,1,1])
        a5 = [[c[8],x,c[12],c[12]] for x in c]
        a5_score = [bj.score_hand(x) for x in a5]
        self.assertEqual(a5_score,[1,1,1,2,3,4,5,7,0,0,0,0,1])

        '''triple ace cases with 2 other cards'''
        a6 = [[c[12],c[12],x,c[12],c[0]] for x in c]    #constant card is 2
        a6_score = [bj.score_hand(x) for x in a6]
        self.assertEqual(a6_score,[2,3,4,5,7,1,1,1,1,1,1,1,1])
        a7 = [[c[12],c[4],x,c[12],c[12]] for x in c]    #constant card is 6
        a7_score = [bj.score_hand(x) for x in a7]
        self.assertEqual(a7_score,[7,1,1,1,1,1,2,3,4,4,4,4,5])
        a8 = [[c[12],c[6],x,c[12],c[12]] for x in c]    #constant card is 8
        a8_score = [bj.score_hand(x) for x in a8]
        self.assertEqual(a8_score,[1,1,1,1,2,3,4,5,7,7,7,7,1])
        
        
        '''hands of 2-5 elements'''
        h1 = [c[x:x+2] for x in range(0,12)]
        h2 = [c[x:x+3] for x in range(0,11)]
        h3 = [c[x:x+4] for x in range(0,10)]
        h4 = [c[x:x+5] for x in range(0,9)]
        h1_score = [bj.score_hand(x) for x in h1]
        h2_score = [bj.score_hand(x) for x in h2]
        h3_score = [bj.score_hand(x) for x in h3]
        h4_score = [bj.score_hand(x) for x in h4]
        self.assertEqual(h1_score, [1,1,1,1,1,1,2,4,5,5,5,10])
        self.assertEqual(h2_score, [1,1,1,3,7,0,0,0,0,0,7])
        self.assertEqual(h3_score, [1,3,0,0,0,0,0,0,0,0])
        self.assertEqual(h4_score, [5,0,0,0,0,0,0,0,0])

        '''make sure ordering doesn't matter'''
        h1.reverse()
        h2.reverse()
        h3.reverse()
        h4.reverse()
        h1r_score = [bj.score_hand(x) for x in h1]
        h2r_score = [bj.score_hand(x) for x in h2]
        h3r_score = [bj.score_hand(x) for x in h3]
        h4r_score = [bj.score_hand(x) for x in h4]
        self.assertEqual(h1r_score, [10, 5, 5, 5, 4, 2, 1, 1, 1, 1, 1, 1])
        self.assertEqual(h2r_score, [7, 0, 0, 0, 0, 0, 7, 3, 1, 1, 1])
        self.assertEqual(h3r_score, [0, 0, 0, 0, 0, 0, 0, 0, 3, 1])
        self.assertEqual(h4r_score, [0, 0, 0, 0, 0, 0, 0, 0, 5])

        '''check duplicate elements'''
        dup1 = [[c[x],c[x]] for x in range(0,13)]
        dup2 = [[c[x],c[x],c[x]] for x in range(0,13)]
        dup3 = [[c[x],c[x],c[x],c[x]] for x in range(0,13)]
        dup4 = [[c[x],c[x],c[x],c[x],c[x]] for x in range(0,13)]
        dup1_score = [bj.score_hand(x) for x in dup1]
        dup2_score = [bj.score_hand(x) for x in dup2]
        dup3_score = [bj.score_hand(x) for x in dup3]
        dup4_score = [bj.score_hand(x) for x in dup4]
        self.assertEqual(dup1_score, [1,1,1,1,1,1,1,3,5,5,5,5,1])
        self.assertEqual(dup2_score, [1,1,1,1,3,7,0,0,0,0,0,0,1])
        self.assertEqual(dup3_score, [1,1,1,5,0,0,0,0,0,0,0,0,1])
        self.assertEqual(dup4_score, [1,1,5,0,0,0,0,0,0,0,0,0,1])
        

unittest.main()
