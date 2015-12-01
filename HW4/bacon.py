def get_bacon(actor, movieDb):
    '''this function takes in an actor and returns his or her Kevin Bacon number (in the context of the database)!!!!!'''
    '''if the actor in question does not connect to Kevin Bacon from our database's data, return -1'''
    THEBIGGUY = "Kevin Bacon"
    
    if actor == THEBIGGUY:
        return 0
    
    '''create a set of the actors you've visited so far so that you don't create redundant tree branches'''
    actors_visited = [THEBIGGUY]
    '''two variables. the first is the current bacon number, and it will count up'''
    '''the second is a set of all the actors that are at the current bacon level'''
    current_bacon_number = 0
    prev_level_bacon = [THEBIGGUY]

    '''create a while loop. each time you iterate the while loop represents one higher bacon level'''
    while True:
        current_bacon_number += 1
        curr_level_bacon = []
        for element in prev_level_bacon:
            co_actors = get_co_actors(element, movieDb)
            for each_co_actor in co_actors:
                if each_co_actor == actor:
                    '''We found our actor in question!!!!'''
                    return current_bacon_number
                if not each_co_actor in actors_visited:
                    curr_level_bacon.append(each_co_actor)
                    actors_visited.append(each_co_actor)
        '''if we've traversed the entire tree and there are no actors left that haven't already been visited'''
        if curr_level_bacon == []:
            return -1
        else:
            '''set the previous bacon level list equal to this level's, then start the next while loop'''
            prev_level_bacon = curr_level_bacon
    
