'''By Jonathan Lerner

This is a fun program I wrote in Python in Spring 2015 for a Penn Engineering Course.
Rack-O is a popular family card game invented in the 50s, where the goal is to arrange
ten cards from your hand in order, pulling from a deck of 60 cards numbered 1-60. Your
cards are put into fixed slots on your rack and once placed cannot be moved within the
rack. Each move a player can either pull the top card from the discard pile or a fresh
card from the deck, potentially using it to replace one of the cards in his/her rack.
See more at https://en.wikipedia.org/wiki/Rack-O

The program I've written is single player Rack-O against an intelligent computer opponent.'''




import random

'''initialize randomness seed'''
random.seed()

'''initialize global variables'''
deck = []    #list representing the deck
discard = [] #list representing the discard pile

def shuffle():
    global deck
    global discard
    '''This function decides which deck to shuffle and shuffles the deck or discard pile'''
    if deck == []:
        '''shuffle the deck'''
        if discard == []:
            '''beginning of the game, so create a new shuffled deck'''
            deck = range(1,61)
            random.shuffle(deck)          
        else:
            '''create a new deck only from members of the discard pile'''
            print "Out of cards. Shuffling the deck..."
            deck = random.shuffle(discard)
            discard = []
    elif discard == []:
        '''this means that it's not the beginning of the game, but the deck was either just dealt for the first time or just shuffled'''
        '''thus we need to add the top card in the deck to the discard pile'''
        '''note that the two function used in the next line are defined below'''
        add_card_to_discard(deal_card())
    else:
        '''nothing to shuffle here'''
        return
            
        

def check_racko(rack):
    '''Check if Racko has been achieved in a deck'''
    '''Remember that the convention is that the first item in a rack list is the back, the last item is the front'''
    '''This means that every item in a list must be larger than the next'''
    '''Returns boolean'''
    for i in range(0,9):
        '''check 10 items in each rack through 9 comparisons'''
        if rack[i] < rack[i+1]:
            return False
    return True

def deal_card():
    global deck
    return deck.pop()

def deal_initial_hands():
    global deck
    '''note that this returns two racks with the convention that the first is computer's, second is user's'''
    user_rack = []
    comp_rack = []
    for i in range(1,11):
        comp_rack.append(deal_card())
        user_rack.append(deal_card())
    return (comp_rack, user_rack)


def does_user_begin():
    '''return a 1 for the user, a 0 for the computer'''
    if random.randint(0,1) == 1:
        return True
    else:
        return False

def print_top_to_bottom(rack):
    for i in range(0,10):
        print rack[i]

def find_and_replace(newCard, cardToBeReplaced, hand):
    global discard
    if hand.count(cardToBeReplaced) == 0:
        print cardToBeReplaced, "isn't in your hand, please try again."
        return hand
    else:
        hand[hand.index(cardToBeReplaced)] = newCard
        discard.append(cardToBeReplaced)
        return hand            

def add_card_to_discard(card):
    global discard
    discard.append(card)

def computer_play(hand):
    global discard
    global deck
    '''Computer's basic strategy is to divide the 60 cards evenly into the 10 slots and then place cards in there'''
    '''However, we try to be smarter by checking for helpful monotonic sequences at either end'''
    '''if we find them, we redivide the remaining inside range appropriately, and place cards there'''

    print "\nComputer's turn."
    absolute_low = 1
    absolute_high = 60
    num_slots = 10

    '''create a list containing the current low considered, high considered, and number that divides slots. size will be num_slots + 1'''
    '''Note that convention will be that a number in consideration should be attributed to a particular slot if it is greater than the previous'''
    ''' slot element, and less than or equal to the next slot element'''
    '''the below is a special case of the slot_divider function, and returns one even range'''
    slots = slot_divider(absolute_low - 1, absolute_low - 1, absolute_high, absolute_high, 0, 0, num_slots)
 
    '''Now check if there is some sequence of monotonic rising numbers from the low end of a players hand that fits within the current range'''
    '''by 'fits' we mean that for the number of elements in that monotonic sequence it would fit within the same number of elements in'''
    '''the slot divider. for example, if the range is 0-60, the initial slot divider is [0, 6, 12, 18,...]. if we had a sequence in our hand '''
    '''from the low end of [13, 14, 15, ...], that would be useful for a three element sequence'''
    num_useful_elements_increasing = function_num_useful_elem_increasing(hand, slots)


    '''now do the same thing but from the upper end'''
    num_useful_elements_decreasing = function_num_useful_elem_decreasing(hand, slots)

    '''Now, get a new set of slot dividers'''
    '''On the lower end, it divides the cards from 1 to the highest card of a monotonically increasing sequence'''
    '''On the higher end, it divides the cards from the lowest card of a monotonically decreasing sequence to 60'''
    '''The "meat in the middle" is then divided as well. This sets up a "target slot" for any new card that comes in'''
    

    '''find the numbers in the hand that divide the ranges'''
    if num_useful_elements_increasing > 0:
        range1_high = hand[-num_useful_elements_increasing]
    else:
        range1_high = absolute_low - 1
    if num_useful_elements_decreasing > 0:
        range2_high = hand[num_useful_elements_decreasing - 1]
    else:
        range2_high = absolute_high
 
    slots = slot_divider(absolute_low - 1, range1_high, range2_high, absolute_high, num_useful_elements_increasing, num_useful_elements_decreasing, num_slots)

    '''Now, the idea is to preserve the good sequences we have at the beginning and end, while concentrating on the meat in the middle'''
    '''We try to be reasonable however by preserving monotonic sequencing when appropriate'''
    '''We will try to use the discard pile in the middle range. If it does not fall in the middle range, pick another card.'''
    '''Then try to use that new card in the middle range. If it's not in the middle range, see if it can improve our lower or upper range'''

    curr_card = discard[-1]
    if (curr_card > range1_high) and (curr_card <= range2_high):
        '''we're in the middle of the hand's range Let's try to place this card in its slot if its slot is not yet filled'''
        assigned_slot = find_slot(curr_card, slots)
        '''is the current card in that slot appropriate? if so let's not take the discard and try a new card from the deck'''
        if find_slot(hand[10 - assigned_slot], slots) == assigned_slot:
            return comp_uses_deck(hand, slots, range1_high, range2_high)
        else:
            '''replace this slot with the discard'''
            print "Computer takes the top card from the discard pile: " + str(discard[-1]) + "."
            discard[-1] = hand[10 - assigned_slot]
            hand[10 - assigned_slot] = curr_card
            return hand
    else:
        '''we're in the low or high range in the hand. let's try to take a card from the deck to work on the meat in the middle'''
        return comp_uses_deck(hand, slots, range1_high, range2_high)

def function_num_useful_elem_increasing(hand, slots):
    '''this hand tells you how many monotonically increasing 'useful' elements there are in a hand'''
    num_elems = 0
    for i in range (1, 11):  #go through elements of hand
        if i == 1:
            '''if it's the first element, just test if it fits within the first slot'''
            if hand[-1] <= slots[1]:
                num_elems = 1
        else:
            '''if it's a subsequent element test both if it's monotonically increasing and if it fits within the i'th slot'''
            if hand[-i] > hand[-i+1]:
                '''hand is monotonically increasing from the low end'''
                if hand[-i] <= slots[i]:
                    '''it also is helpful in that it fits within the first i slots!'''
                    num_elems = i
            else:
                '''no longer monotonically increasing, so we're done'''
                break
    return num_elems

def function_num_useful_elem_decreasing(hand, slots):
    '''this function tells you how many monotonically decreasing 'useful' elements there are in a hand'''
    num_elems = 0
    for i in range (0, 10):  #go through elements of hand
        if i == 0:
            '''if it's the first element, just test if it fits within the first slot'''
            if hand[0] > slots[-2]:
                num_elems = 1
        else:
            '''if it's a subsequent element test both if it's monotonically decreasing and if it fits within the i'th slot'''
            if hand[i] < hand[i-1]:
                '''hand is monotonically decreasing from the high end'''
                if hand[i] > slots[-i-2]:
                    '''it also is helpful in that it fits within the last i slots!'''
                    num_elems = i+1
            else:
                '''no longer monotonically decreasing, so we're done'''
                break
    return num_elems

    
def slot_divider(total_low, range1_high, range2_high, total_high, num_elem1, num_elem2, num_slots):
    '''This is a helper function that divides a num_slots elemenent "hand" into three ranges (with potentially different increments)'''
    '''It outputs a monotonically increasing array of num_slots + 1 elements that represent the boundaries of num_slots slots'''
    '''we assume input in the form of total_low < range1_high < range2_high < total_high'''
    '''we also assume that num_elem1 and num_elem2 are positive integers, and their sum is less than num_slots'''

    final = []
    '''create the first range'''
    final.append(total_low)
    for i in range(1, num_elem1 + 1):
        final.append(total_low + (range1_high - (total_low))/float(num_elem1) * i)
    '''create the middle range'''
    num_middle_elem = num_slots - num_elem1 - num_elem2
    for i in range(1, num_middle_elem + 1):
        final.append(range1_high + (range2_high - range1_high)/float(num_middle_elem)*i)
    '''create the high range'''
    for i in range(1, num_elem2 + 1):
        final.append(range2_high + (total_high - range2_high)/float(num_elem2) * i)
        
    return final

def find_slot(card, slots):
    '''Helper function. Given a card and a slot array, tells us which slot this card would be assigned to'''
    for i in range(1,len(slots)):
        if card <= slots[i]:
            return i
    '''should never get here'''

def try_to_place(card, hand, slots, range1_high, range2_high):
    '''helper this function tries its hardest to place a card in a hand. returns a new hand and the card replaced'''
    '''in this scenario we only do not place a card if we think it would hurt the hand'''
    '''if nothing is replaced, returns the original hand and 0 for the card replaced'''
    '''returns in the form (hand, replaced_card'''

    if (card > range1_high) and (card < range2_high):
        '''we're in the middle of the hand's range. Let's try to place this card in its slot if its slot is not yet filled'''
        assigned_slot = find_slot(card, slots)
        '''is the current card in that slot appropriate? if so let's not take the discard and try a new card from the deck'''
        if find_slot(hand[10 - assigned_slot], slots) == assigned_slot:
            '''the current card in that slot is appropriate. don't do anything for now. one day I'll create smarter AI'''
            return (hand, 0)
        else:
            replaced_card = hand[10 - assigned_slot]
            hand[10 - assigned_slot] = card
            return (hand, replaced_card)
               
    else:
        '''we're in one of the ends. let's use this card to at least try to "open up the middle"'''
        if card < range1_high:
            '''we're in the lower range. replace the smallest card in our hand that is bigger than current card'''
            for i in range(0, 10 - hand.index(range1_high)):
                if card < hand[-i-1]:
                    replaced_card = hand[-i-1]
                    hand[-i-1] = card
                    break
        if card >= range2_high:
            '''we're in the top end of the range. replace the biggest card in our hand that is smaller than current card'''
            if card == 60:
                replaced_card = hand[-1]
                hand[-1] = card
            else:
                for i in range(0, hand.index(range2_high) + 1):
                    if card > hand[i]:
                        replaced_card = hand[i]
                        hand[i] = card
                        break
        return (hand, replaced_card)
                            

def comp_uses_deck(hand, slots, range1_high, range2_high):
    '''this is the function for when the computer takes a card from the deck. it uses try_to_place to do its best with the deck card'''
    '''returns the hand'''
    print "Computer takes a card from the deck..."
    curr_card = deal_card()
    hand, replaced_card = try_to_place(curr_card, hand, slots, range1_high, range2_high)
    if replaced_card == 0:
        print "Computer places the card in the discard pile."
        discard.append(curr_card)
        return hand
    else:
        print "Computer uses the card from the deck."
        discard.append(replaced_card)
        return hand


def user_play(hand):
    global discard
    print "\nYour turn! Your hand is:"
    print_top_to_bottom(hand)
    print "The card at the top of the discard pile is: " + str(discard[-1]) + "."
    print "You can take this card or pull a new card from the top of the deck."
    while True:
        first_choice = raw_input("Would you like to take the card at the top of the discard pile? (y/n) ")
        if first_choice =="y":
            original_hand = hand[:]
            while original_hand == hand:
                card_to_replace = input("Which number card in your hand would you like to replace? ")
                hand = find_and_replace(discard[-1], card_to_replace, hand)
            break
        elif first_choice == "n":
            card = deal_card()
            print "The card you are dealt from the deck is: " + str(card) + "."
            second_choice = raw_input("Do you want to keep this card? (y/n) ")
            if second_choice == "y":
                original_hand = hand[:]
                while original_hand == hand:
                    card_to_replace = input("Which number card in your hand would you like to replace? ")
                    hand = find_and_replace(card, card_to_replace, hand)
            else:
                discard.append(card)
            break
        else:
            print "Please input y or n"
    return hand

            
def main():
    print "Welcome to Racko!"
    shuffle()
    comp_rack, user_rack = deal_initial_hands()
    
    raw_input("Let's flip a coin to see who goes first! [Hit any key to flip!]")
    UserTurn = does_user_begin()

    if UserTurn:
        raw_input("Heads! You go first!")
    else:
        raw_input("Tails! The computer goes first!")
        


    '''See if there are any Racko's from the beginning'''
    if check_racko(user_rack):
        if check_racko(comp_rack):
            print "AMAZING!!!!!! BOTH YOU AND THE COMPUTER HAVE RACKO!!!! TIE GAME!!! INCREDIBLE!!!"
            print "Your hand was:"
            print_top_to_bottom(user_rack)
            return
        else:
            print "YOU WIN RIGHT OFF THE BAT! AMAZING!"
            print "Your hand was:"
            print_top_to_bottom(user_rack)
            return
    elif check_racko(comp_rack):
        print "COMPUTER WINS RIGHT OFF THE BAT! TOUGH LUCK!"
        return

    while (not check_racko(user_rack)) and (not check_racko(comp_rack)):
        shuffle()
        if UserTurn:
            user_rack = user_play(user_rack)
            UserTurn = not UserTurn
        else:
            comp_rack = computer_play(comp_rack)
            UserTurn = not UserTurn
            raw_input("[Hit any key to continue]")


    if check_racko(user_rack):
        print "\nRacko! Well done! Congratulations!"
        print "Your final hand was:"
        print_top_to_bottom(user_rack)
        print "The Computer's final hand was:"
        print_top_to_bottom(comp_rack)
    else:
        print "\nThe Computer got Racko. Better luck next time."
        print "The Computer's final hand was:"
        print_top_to_bottom(comp_rack)






if __name__ == '__main__':
    main()
