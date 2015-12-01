'''submission for Jonathan Lerner and Zaks Lubin'''
from cards import *

class Blackjack():
    
    def __init__(self):
        self.table = {}
        for i in range(0,2):
            self.table['row' + str(i+1)]=[5*i+1,5*i+2,5*i+3,5*i+4,5*i+5]
        for i in range(0,2):
            self.table['row' + str(i+3)]=[3*i+11,3*i+12,3*i+13]
        self.discardList = [17,18,19,20]
        self.deck = Deck()
        
    def intro(self):
        print 'Hello and welcome to Blackjack Solitaire!'
        print 'A labor of love created by Jonathan Lerner and Zaks Lubin\n'
    
    def get_table(self):
        return self.table

    def get_discard(self):
        return self.discardList

    def shuffle_game(self):
        self.deck.shuffle()

    def display_state(self):
        '''displays the current state of the board'''
        board = self.get_table()
        discard = self.get_discard()
        print "The current board is:\n"
        for i in range(0,4):
            row = ""
            if i > 1:
                row += "\t"
            for j in range(0,len(board['row' + str(i+1)])):
                row += str(board['row' + str(i+1)][j]) + '\t'
            print row + '\n'

        print 'And the discard spots are:\n'
        d = ""
        for i in range(0,len(discard)):
            d += str(discard[i]) + '\t'
        print d

    def open_board_slots(self):
        '''returns the list of open slots in the game board (slots 1-16)'''
        return filter(lambda x: x in range(1,17), self.flatten(self.table.values()))

    def open_discard_slots(self):
        '''returns the list of open discard slots (slots 17-20)'''
        return filter(lambda x: x in range(17,21), self.discardList)

    def flatten(self, lst):
        '''flattens a list of lists into a combined list of elements (as in HW7)'''
        if lst == []:
            return []
        else:
            return lst[0] + self.flatten(lst[1:])

    def no_move_error(self, move):
        '''returns False if there is an error; for a valid move returns True'''
        '''if there is an error prints an appropriate error message prior to return'''
        try:
            move=int(move)
        except:
            print 'Please enter an integer between 1 and 20'
            return False
        if move < 1 or move > 20:
            print 'Please enter an integer between 1 and 20.'
            return False
        valid_slots = self.open_board_slots() + self.open_discard_slots()
        if not move in valid_slots:
            print 'Please enter a valid open spot in the board or in the discard pile.'
            return False
        return True

    def card_replace(self, move, card):
        '''replaces the board with "card" in slot "move"'''
        if  move in range(1,17):
            for i in range(1,3):
                for j in range (0,5):
                    if self.table['row'+str(i)][j]==move:
                        self.table['row'+str(i)][j]=card
            for i in range(3,5):
                for j in range (0,3):
                    if self.table['row'+str(i)][j]==move:
                        self.table['row'+str(i)][j]=card
        else:
            for i in range(0,4):
                if move==self.discardList[i]:
                    self.discardList[i]=card

    def make_hands(self):
        '''returns a list of all the hands from the board (each hand is itself a list)'''
        all_hands=[]
        for i in range(1,5):
            all_hands.append(self.table['row'+str(i)])
        for i in range(1,2):
            all_hands.append([self.table['row'+str(i)][0],self.table['row'+str(i+1)][0]])
            all_hands.append([self.table['row'+str(i)][4],self.table['row'+str(i+1)][4]])
        for j in range(1,4):
            all_hands.append([self.table['row'+str(1)][j],self.table['row'+str(2)][j],self.table['row'+str(3)][j-1],self.table['row'+str(4)][j-1]])
        return all_hands

    def score_hand(self, hand):
        '''returns the score for an individual hand'''
        hand_rank = [card.get_rank() for card in hand]
        hand_rank = [10 if (card_rank in ['J','Q','K']) else card_rank for card_rank in hand_rank ]
        non_aces = filter(lambda card_rank: card_rank != "A", hand_rank)
        aces = filter(lambda card_rank: card_rank == 'A', hand_rank)
        num_aces = len(aces)
        if len(non_aces) == 1 and len(aces) == 1:
            if non_aces[0] == 10:
                '''Blackjack!'''
                return 10
        prelim_score = sum(non_aces) + num_aces
        if num_aces > 0:
            if prelim_score + 10 <= 21:
                prelim_score += 10
        if prelim_score >= 22:
                '''BUST'''
                return 0
        elif prelim_score <= 16:
            return 1
        else:
            '''create a dictionary that maps normal BJ hand scores to this game's'''
            return {21:7, 20:5, 19:4, 18:3, 17:2}[prelim_score]


    def high_score(self,score):
        '''checks if a new high score has been achieved, displays appropriate message and updates high score if necessary'''
        scoreboard=open('highscore.txt','a+')
        scoreboard.seek(0)
        line=scoreboard.readlines()
        if line == []:
            line = [0]
        scoreboard.close()
        high_score = line[0]
        if score>=int(high_score):
            scoreboard=open('highscore.txt','w')
            scoreboard.write(str(score))
            print "Congrats! You have the high score!"
        if score<int(high_score):
            print "Good game! The high score is " + str(high_score) + '.'
    
    def play(self):
        '''the main function that plays the game'''
        self.intro()
        self.shuffle_game()
        self.display_state()
        #while rows in self.table contain integers:
        while(self.open_board_slots()!= []):
            card = self.deck.deal()
            print '\n', 'The card dealt is', str(card)
            move = raw_input("Which open slot would you like to place the card in? ")
            while self.no_move_error(move)==False:    
                move = raw_input("Which open slot would you like to place the card in? ")
            self.card_replace(int(move), card)
            print '\n'
            self.display_state()        
        final_score = sum([self.score_hand(h) for h in self.make_hands()])
        print "\nThat's the end of the game! Your score is " + str(final_score) + '.'
        self.high_score(final_score)

if __name__ == "__main__":
    bj_solitare = Blackjack()
    bj_solitare.play()
        
