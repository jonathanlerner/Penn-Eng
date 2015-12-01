from movie_trivia import *
import unittest

class TestMovies(unittest.TestCase):

    movieDb = {}
    ratingDb = {}

    def setUp(self):
        self.movieDb = create_actors_DB('movies.txt')
        self.ratingDb = create_ratings_DB('moviescores.csv')
    def test_insert_actor_info(self):
        insert_actor_info('Kevin Spacey', 'Seven', self.movieDb)
        self.assertTrue('Seven' in self.movieDb['Kevin Spacey'])
        insert_actor_info('Brad Pitt','Oceans Eleven', self.movieDb)
        self.assertTrue('Oceans Eleven' in self.movieDb['Brad Pitt'])
    def test_insert_rating(self):
        test_movie = insert_rating('Oceans Eleven', (12,13), self.ratingDb)
        self.assertTrue(self.ratingDb['Oceans Eleven']==(12,13))
        test_movie2 = insert_rating('Gobledygook', (100,0), self.ratingDb)
        self.assertTrue(self.ratingDb['Gobledygook']==(100,0))
    def test_delete_movie(self):
        delete_movie('Oceans Eleven', self.movieDb, self.ratingDb)
        self.assertTrue('Oceans Eleven' not in self.movieDb['Brad Pitt'])
        self.assertTrue('Oceans Eleven' not in self.ratingDb.keys())    
    def test_select_where_actor_is(self):
        actor = select_where_actor_is('Zaks Lubin', self.movieDb)
        self.assertEqual(actor, [])
        actor = select_where_actor_is('Woody Allen', self.movieDb)
        self.assertEqual(actor, ['Annie Hall', 'Mighty Aphrodite'])
    def test_select_where_movie_is(self):
        movie = select_where_movie_is('Zaks Lubin', self.movieDb)
        self.assertEqual(movie, [])
        self.assertEqual(select_where_movie_is('Apollo 13', self.movieDb), ['Kevin Bacon', 'Tom Hanks'])
    def test_get_co_actors(self):
        actors = get_co_actors('George Clooney', self.movieDb)
        self.assertEqual(actors, ['Catherine Zeta Jones', 'Brad Pitt', 'Julia Roberts'])
        self.assertEqual(get_co_actors('Zaks Lubin', self.movieDb),'This actor is not in the database')
    def test_select_where_rating_is(self):
        test_rating = select_where_rating_is(35, '=', True, self.ratingDb)
        self.assertEqual(test_rating, ['Species', 'National Treasure', 'Cobra'])
        self.assertEqual(select_where_rating_is(0,'<',False,self.ratingDb), [])
    def test_get_common_movie(self):
        common = get_common_movie('George Clooney', 'Catherine Zeta Jones', self.movieDb)
        self.assertEqual(common, ['Intolerable Cruelty'])
        self.assertEqual(get_common_movie("Jon Lerner", "Woody Allen",self.movieDb),[])
        self.assertEqual(get_common_movie("Jon Lerner", "Zaks Lubin",self.movieDb),[])
    def test_critics_darling(self):
        Cdarling = critics_darling(self.movieDb, self.ratingDb)
        self.assertEqual(Cdarling, ['Joan Fontaine'])
        insert_actor_info('Calvin', 'The CIT590 Story', self.movieDb)
        insert_rating('The CIT590 Story', (100,0),self.ratingDb)
        self.assertTrue('Calvin' in critics_darling(self.movieDb,self.ratingDb))
    def test_audience_darling(self):
        Adarling = audience_darling(self.movieDb, self.ratingDb)
        self.assertEqual(Adarling, ['Diane Keaton'])
        insert_actor_info('Calvin', 'The CIT590 Story', self.movieDb)
        insert_rating('The CIT590 Story', (0,100),self.ratingDb)
        self.assertTrue('Calvin' in audience_darling(self.movieDb,self.ratingDb))

    def test_good_movies(self):
        Good_list = good_movies(self.ratingDb)
        self.assertTrue('Anchorman' not in Good_list)
        self.assertTrue('JFK' in Good_list)
        
        insert_rating('Super Amazing Movie', (85,85), self.ratingDb)
        insert_rating('Super Amazing Movie 2', (85,86), self.ratingDb)
        insert_rating('Super Amazing Movie 3', (86,85), self.ratingDb)
        insert_rating('Super Amazing Movie 4', (100,100), self.ratingDb)
        Great_list = good_movies(self.ratingDb)
        self.assertTrue('Super Amazing Movie' in Great_list)
        self.assertTrue('Super Amazing Movie 2' in Great_list)
        self.assertTrue('Super Amazing Movie 3' in Great_list)
        self.assertTrue('Super Amazing Movie 4' in Great_list)
                
        
    def test_get_common_actors(self):
        common_actors = get_common_actors('Oceans Eleven', 'Intolerable Cruelty', self.movieDb)
        self.assertEqual(common_actors, ['George Clooney'])
    def test_get_bacon(self):
        Bacon_number = get_bacon('Brad Pitt', self.movieDb)
        self.assertEqual(Bacon_number, 1)
        self.assertEqual(get_bacon('Kevin Bacon', self.movieDb),0)
        self.assertEqual(get_bacon('Shirley Maclaine',self.movieDb),2)
        self.assertEqual(get_bacon(7337,self.movieDb),-1)
        self.assertEqual(get_bacon("Jonathan Lerner", self.movieDb),-1)

'''the below is for reference, to remind us of the functional form of each function'''
#menu_prompt()
#insert_actor_info(actor, movies, movie_Db)
#insert_rating(movie, ratings, ratings_Db)
#delete_movie(movie, movie_Db, ratings_Db)
#select_where_actor_is(actorName, movie_Db)
#num_comp(num1, num2, string_comp)
#select_where_rating_is(targeted_rating, comparison, is_critic, ratings_Db)
#get_common_movie(actor1, actor2, movie_Db)
#critics_darling(movie_Db, ratings_Db)
#audience_darling(movie_Db, ratings_Db)
#good_movies(ratings_Db)
#get_common_actors(movie1, movie2, movie_Db)
#get_bacon(actor, movie_Db)

unittest.main()
