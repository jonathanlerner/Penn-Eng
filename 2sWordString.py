# Jonathan Lerner, 1:23am on May 18, 2016. Completed efficient version of Two Sigma Code Test Question 2
#
# The problem is to take in an array of words and determine the maximum possible "word chain"
# A word chain is a chain between words such that any bigger link in the chain is made up of the previous link's word with one
# character inserted in any position

def main():

    # One example of an array of words -- our word chain search space
    # This program is optimized to scale efficiently for MUCH MUCH larger arrays
    word_arr = ["a",
                "ho",
                "b",
                "bca",
                "cab",
                "dab",
                "helo",
                "hello",
                "hlo",
                "Mzemllowz13",
                "yellow1",
                "yellow3",
                "yellow13",
                "yemllow13",
                "Mzemllow13"]

    print longestChain(word_arr)




def  longestChain(words):
    '''This efficient algorithm is implemented in the following steps:
    1: Sort the array of words from shortest to longest length. We do this to take advantage of the fact that any subsequent
        steps in a string chain are of longer length words
    2: First, make one pass through the word list, mapping the length of each word to a new length list
    3: [block_detector function] Now, look through the length matrix, identifying all possible chain blocks. A chain block is a block of
        words where there exists incremental letter lengths throughout the block (we call this a continuous length block). For example,
        if we had words of length 3, followed by words of length 4, followed by words of length 5 (but no words of length 2 or 6),
        that would be a block. Save the beginning and ending indexes for each block, along with the maximum chain length,
        in a num_blocks x 3 list.
        Each block element is: (max_block_chain_possible, beg_idx, end_idx)
    4: [block_detector function] Now, sort the block list by max_block_chain_possible from largest to smallest.
    5: [block_max_chain function] Search each continuous length block for its largest chain. This is made faster by only passing that
        block's section of the overall word array
    6: Save the overall_max created from the algorithm running on this block (updating if it's greater than the existing overall_max).
        If the overall_max is greater than the next largest block's max_block_chain_possible, then return overall_max and we're done.
        Otherwise, keep testing any blocks whose max_block_chain_possible is greater than the current overall_max
'''

    '''Step 1'''
    words = sorted(words, key=len)

    '''Step 2'''
    word_lens = map(len, words)
    
    '''Step 3 & 4'''
    sorted_blocks = block_detector(word_lens)
    
    '''Step 5 & 6'''
    total_max = 0
    for block in sorted_blocks:
        if block[0] <= total_max:
            # We've achieved the longest possible chain already
            break
        else:
            block_beg_idx = block[1]
            block_end_idx = block[2]

            block_max = block_max_chain(words[block_beg_idx:block_end_idx+1])
            if block_max > total_max:
                total_max = block_max
                
    return total_max


def block_max_chain(words_block):
    '''Given a word block guaranteed to have continuous length (ie, aside from the smallest word, for any word there exists a word of length one less
    than the word in question), this returns the largest chain in the block'''
    
    chain_num = [0]*len(words_block)
    
    sub_beg = -1         # Beginning Index of the submatrix to examine for the current word's subwords
    sub_end = -1         # End Index of submatrix
    overall_max = 0      # Largest Chain Length seen so far

    prev_len = -1        # Length of previously-seen element
    
    for i in xrange(len(words_block)):
        curr_word = words_block[i]    # The current word being examined for word subchains
        curr_len = len(curr_word)
        if sub_end == -1 and curr_len == prev_len:
            # Examining shortest words in block
            chain_num[i] = 1
        else:
            # Not examining shortest words in block
            if (curr_len == prev_len + 1):
                #We've moved on to words with one larger letter than previous section. Create new sub_matrix indexes from previous section
                sub_beg = sub_end + 1   # New submatrix begins one element after end of old one
                sub_end = i - 1         # Previous element

                # For efficiency's sake, let's also check if it's even possible to create a longer chain than what we've already seen from previous
                # words examined
                max_prev_submatrix = max(chain_num[sub_beg:sub_end+1])
                # Are there even big enough words left to find a new overall_max?
                if (len(words_block[-1]) - prev_len + max_prev_submatrix < overall_max):
                    break
        
            #Loop through the appropriate sub-matrix looking for sub-words of the current word
            curr_max = 0
            for j in xrange(len(curr_word)):
                sub_word = curr_word[:j] + curr_word[j+1:]  #slice off the j'th letter of curr_word
                for k in xrange(sub_beg, sub_end+1):
                    # If sub_word is in the sub_matrix, and that sub-word's chain number is the largest seen so far,
                    # then update curr_max
                    if (sub_word == words_block[k]) and (chain_num[k] > curr_max):
                        curr_max = chain_num[k]
            
            chain_num[i] = curr_max + 1   #increment the previous largest chain by one to account for the current word
        if (chain_num[i] > overall_max):
            overall_max = chain_num[i]
        prev_len = curr_len
        
    return overall_max



def block_detector(word_lens):
    '''This function takes a sorted list of word_lengths and returns an n x 3 list of length-continuous blocks within the the word list. This
    list is sorted from greatest to smallest by max_possible_chain in each list row (see below)
    
    n is the number of blocks identified. For each block it returns a list of the form [max_possible_chain, beg_idx, end_idx].
    max_possible chain: The largest size chain that can be created from this block
    beg_idx:            Beginning index of the block in the words list
    end_idx:            Ending index of the block in the words list'''

    block_list = []
    # Strategy is to loop through the word_lens, identifying blocks and appending them to our block list
    beg_idx = 0
    
    for i in xrange(1,len(word_lens)):
        #start from the second element (index 1)
        if word_lens[i] > word_lens[i-1] + 1:
            # Chain is broken; record previous block maximum chain length, and indexes
            block_list.append([word_lens[i-1] - word_lens[beg_idx] + 1, beg_idx, i-1])

            # Reset beginning counter
            beg_idx = i

    # Add the last block that reached the end
    block_list.append([word_lens[-1] - word_lens[beg_idx] + 1, beg_idx, len(word_lens)-1])

    return sorted(block_list, key = lambda x: -x[0])


            
if __name__ == '__main__':
    main()
    
