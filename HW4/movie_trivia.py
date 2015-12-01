import csv
def create_actors_DB(actor_file):
    '''Create a dictionary keyed on actors from a text file'''
    f = open(actor_file)
    movieInfo = {}
    for line in f:
        line = line.rstrip().lstrip()
        actorAndMovies = line.split(',')
        actor = actorAndMovies[0]
        movies = [x.lstrip().rstrip() for x in actorAndMovies[1:]]
        movieInfo[actor] = set(movies)
    f.close()
    return movieInfo

def create_ratings_DB(ratings_file):
    '''make a dictionary from the rotten tomatoes csv file'''
    scores_dict = {}
    with open(ratings_file, 'r') as csvfile:
        reader = csv.reader(csvfile)
        reader.next()
        for row in reader:
            scores_dict[row[0]] = [row[1], row[2]]
    return scores_dict

def insert_actor_info(actor, movies, movie_Db):
    '''Insert/update the actor's movie information into a movie database'''
    movieRaw = movies.split(',')
    movieSet = set([x.lstrip().rstrip() for x in movieRaw])
    
    if actor in movie_Db:
        movie_Db[actor]= movie_Db[actor].union(movieList)
    else: 
        movie_Db[actor] = movieList
        
def insert_rating(movie, ratings, ratings_Db):
    '''Insert/update the movie's rating information'''
    ratings_Db[movie] = ratings
    
def delete_movie(movie, movie_Db, ratings_Db):
    '''delete all information from the database corresponding to the movie'''
    for key in movie_Db:
        if movie in movie_Db[key]:
            movie_Db[key].discard(movie)
    if movie in ratings_Db:
        del ratings_Db[movie]
    
        
def select_where_actor_is(actorName, movie_Db):
    '''given an actor, return the list of all movies.'''
    return list(movie_Db.get(actorName, []))

def select_where_movie_is(movieName, movie_Db):
    '''given a movie, return the list of all actors.'''
    actor_list=[]
    all_actors=movie_Db.keys()
    for this_actor in all_actors:
        if movieName in movie_Db[this_actor]:
            actor_list.append(this_actor)
    return actor_list


def num_comp(num1, num2, string_comp):
    '''helper function that takes in two numbers and a string, and returns the mathematical comparison as a bool'''
    if string_comp == ">":
        return num1 > num2
    elif string_comp == "=":
        return num1 == num2
    elif string_comp == "<":
        return num1 < num2

    
def select_where_rating_is(targeted_rating, comparison, is_critic, ratings_Db):
    '''returns a list of movies satisfying a the comparison test. comparison is either ">", "<", or "="'''
    final_list = []
    for movie in ratings_Db:
        if is_critic:
            if num_comp(int(ratings_Db[movie][0]), targeted_rating, comparison):
                final_list.append(movie)
        else:
            if num_comp(int(ratings_Db[movie][1]), targeted_rating, comparison):
                final_list.append(movie)
    return final_list
    
    
def get_co_actors(actorName, movie_Db):
    '''returns a list of all actors a particular actorName has worked with'''
    co_actor_set=set([])
    movie_list=movie_Db.get(actorName)
    for this_movie in movie_list:
        co_actor_set = co_actor_set.union(set(select_where_movie_is(this_movie, movie_Db)))
    co_actor_set.discard(actorName)
    return list(co_actor_set)

def get_common_movie(actor1, actor2, movie_Db):
    '''returns the movies where both actors were cast'''
    actor1_movies = set(select_where_actor_is(actor1, movie_Db))
    actor2_movies = set(select_where_actor_is(actor2, movie_Db))
    common_movies = actor1_movies.intersection(actor2_movies)
    return list(common_movies)
    
    
def critics_darling(movie_Db, ratings_Db):
    '''find the actor whose movies have the highest average rotten tomoatoes rating per critics'''
    curr_darling = []
    curr_darling_avg = 0
    '''cycle through all the actors, remembering which actor has the highest avg so far'''
    for actor in movie_Db.keys():
        movie_list = select_where_actor_is(actor, movie_Db)
        rating_sum = 0
        rating_count = 0
        for movie in movie_list:
            '''make sure the movie is actually in the ratings database. otherwise ignore'''
            if movie in ratings_Db.keys():
                rating_sum += int(ratings_Db[movie][0])
                rating_count += 1
        actor_avg = rating_sum / float(rating_count)
        if actor_avg == curr_darling_avg:
            curr_darling.append(actor)
        elif actor_avg > curr_darling_avg:
            curr_darling = []
            curr_darling.append(actor)
            curr_darling_avg = actor_avg
    return curr_darling
    
    
    
def audience_darling(movie_Db, ratings_Db):
    '''find the actor whose movies have the highest average rotten tomoatoes rating per audiences'''
    '''this is the exact same as critics darling, but we index our queries in ratings_Db to 1, for audience'''
    curr_darling = []
    curr_darling_avg = 0
    '''cycle through all the actors, remembering which actor has the highest avg so far'''
    for actor in movie_Db.keys():
        movie_list = select_where_actor_is(actor, movie_Db)
        rating_sum = 0
        rating_count = 0
        for movie in movie_list:
            '''make sure the movie is actually in the ratings database. otherwise ignore'''
            if movie in ratings_Db.keys():
                rating_sum += int(ratings_Db[movie][1])
                rating_count += 1
        actor_avg = rating_sum / float(rating_count)
        if actor_avg == curr_darling_avg:
            curr_darling.append(actor)
        elif actor_avg > curr_darling_avg:
            curr_darling = []
            curr_darling.append(actor)
            curr_darling_avg = actor_avg
    return curr_darling
     

def good_movies(ratings_Db):
    '''finds all movies with ratings greater than or equal to 85 from both databases'''

    '''create a list for critics of ratings equal to 85 combined with ratings greater than 85. do same for audience ratings'''
    list1 = select_where_rating_is(85,'=',True,ratings_Db)
    list1.extend(select_where_rating_is(85,'>',True, ratings_Db))
    list2 = select_where_rating_is(85,'=',False,ratings_Db)
    list2.extend(select_where_rating_is(85,'>',False,ratings_Db))

    set1 = set(list1)
    set2 = set(list2)

    final_set = set1.intersection(set2)
    return final_set

def get_common_actors(movie1, movie2, movie_Db):
    '''given a pair of movies, this returns a list of actors that are in both'''
    #create a set of actors in movie1
    #create a set of actors in movie2
    #return the intersection
    movie1_set=set(select_where_movie_is(movie1, movie_Db))
    movie2_set=set(select_where_movie_is(movie2, movie_Db))
    common_actors=[]
    if len(movie1_set.intersection(movie2_set))>0:
           common_actors=movie1_set.intersection(movie2_set)
           return list(common_actors)
    else:
        return common_actors

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
    

def main():
    '''main function that compiles the program'''
    movie_Db = create_actors_DB('movies.txt')
    ratings_Db = create_ratings_DB('moviescores.csv')
    #print movie_Db
    #print select_where_actor_is("Brad Pitt", movie_Db)
    #print get_common_actors("Oceans Eleven", "Seven", movie_Db)
    #print good_movies(ratings_Db)
    #print get_bacon("Gene Hackman", movie_Db)

#movie1=raw_input("which movie would you like to enter?")
#    movie2=raw_input("which movie would you like to enter?")
#    print get_common_actors(movie1, movie2, movie_Db)
#    actor1=raw_input("which actor would you like to enter?")
#    actor2=raw_input("which actor would you like to enter?")
#    print get_common_movie(actor1, actor2, movie_Db)
#    movieName=raw_input("which movie to look up?")
#    print select_where_movie_is(movieName, movie_Db)
#    actorName = raw_input("which actor would you like to enter?")
#    print get_co_actors(actorName, movie_Db)
#    movie=raw_input("which movie would you like to delete?")
#    delete_movie(movie, movie_Db, ratings_Db)
#    movieName=raw_input("which movie to look up?")
#    print select_where_movie_is(movieName, movie_Db)
#    actor = raw_input("which actor would you like to enter?")
#    movies = raw_input("what movies was this actor in?")
#    insert_actor_info(actor, movies, movie_Db)
#    actorName= raw_input("which actor to look up?")
#    select_where_actor_is(actorName, movie_Db)
#    movieName=raw_input("which movie to look up?")
#    select_where_movie_is(movieName, movie_Db)
#    print get_co_actors(actorName, movie_Db)'''
    
    
