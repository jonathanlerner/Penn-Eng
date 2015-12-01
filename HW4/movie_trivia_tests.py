from movie_trivia import *
import unittest

class TestMovies(unittest.TestCase):

    movieDb = {}
    ratingDb = {}

    def setUp(self):
        self.movieDb = create_actors_DB('my_test_actors.txt'):
        self.ratingDb = create_ratings_DB('my_ratings.csv')

    def testselect_where_movie_is(self):
        #write test code here using self.ratingDb and self.movieDb
        actors = select_where_movie_is('blablabla', self.movieDb)
        #make some assertion about these actors

    #write unit tests for every function.
                
unittest.main()
