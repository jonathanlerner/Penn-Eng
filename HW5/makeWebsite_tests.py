'''Submission by Jonathan Lerner'''
from makeWebsite import *

import unittest

class Test_makeWebsite(unittest.TestCase):

    '''note that I do not test the functions from Section 1 as they are'''
    '''pretty trivial (and one is taken verbatim from the assignment'''
    '''additionally I want to avoid creating files for the unittest'''
    def test_detect_name(self):
        data1 = ['Jonathan Lerner','Line2','Line3','Line4']
        self.assertEqual(detect_name(data1),'Jonathan Lerner')
        data2 = ['jonathan lerner','Line2','Line3','Line4']
        self.assertRaises(Exception, lambda: detect_name(data2))
        data3 = [' Jonathan Lerner','Line2']
        self.assertRaises(Exception, lambda: detect_name(data3))
        data4 = ['J']
        self.assertEqual(detect_name(data4),'J')

    def test_email_check(self):
        #below should all be True
        s1 = 'lernerjo@wharton.upenn.edu'
        s2 = 'a@a.com'
        s3 = '@joseph.com'
        s4 = 'joe@joe.edu'

        #below should all be False
        s5 = 'joe@joe.eduu'
        s6 = 'joe@joe.co'
        s7 = 'joseph.com'
        s8 = '@.com'
        s9 = ''
        s10 = 'joe@joe.COM'
        s11 = 'hi@@hi.com'
        s12 = 'hi@hi.comm'
        
        self.assertTrue(all([email_check(s) for s in [s1,s2,s3,s4]]))
        self.assertTrue(all([not email_check(s) for s in [s5,s6,s7,s8,s9,s10,s11,s12]]))

    def test_detect_email(self):
        data1 = ['Jonathan Lerner','lernerjo@wharton.upenn.edu','Line3','Line4']
        data2 = ['Jonathan Lerner','Line2','lernerjo@wharton.upenn.edu','Line4','Line5']
        self.assertEqual(detect_email(data1),'lernerjo@wharton.upenn.edu')
        self.assertEqual(detect_email(data2),'lernerjo@wharton.upenn.edu')
        data3 = ['Jonathan Lerner','Line2','lernerjo@wharton.upenn.edu','another@email.com','Line5']
        self.assertEqual(detect_email(data3),'lernerjo@wharton.upenn.edu')
        data4 = ['Jonathan Lerner','Is','One','Awesome Dude']
        self.assertEqual(detect_email(data4),-1)

    def test_detect_courses(self):
        data1 = ['Jonathan Lerner','lernerjo@wharton.upenn.edu','Courses - Course1','Line4']
        self.assertEqual(detect_courses(data1),'Course1')
        data2 = ['Jonathan Lerner','lernerjo@wharton.upenn.edu','Courses---  :: !!! ;;;MyCourse1 2 3','Line4']
        self.assertEqual(detect_courses(data2),'MyCourse1 2 3')
        data3 = ['J L','Blah','BLAAAAAAh','Courses : 123','Line5']

    def test_detect_projects(self):
        data1 = ['Jonathan Lerner','Projects','P1','----------']
        self.assertEqual(detect_projects(data1),['P1'])
        data2 = ['Jonathan Lerner','Projects','P1','P2','----------------------------']
        self.assertEqual(detect_projects(data2),['P1','P2'])
        data3 = ['J L','Blah','BLAAAAAAh','Projects']
        self.assertEqual(detect_projects(data3),[])
        data4 = ['J L','Blah','BLAAAAAAh','Projects','P1','','P2','----','-------------------blaaaaaa']
        self.assertEqual(detect_projects(data4),['P1','P2','----'])
        data5 = ['J L','Blah','BLAAAAAAh','Projets','P1','','P2','----','-------------------blaaaaaa']
        self.assertEqual(detect_projects(data5),-1)

    def test_detect_education(self):
        data1 = ['J L','Blah','BLAAAAAAh','University of Blah','P1','','P2','----','-------------------blaaaaaa']
        self.assertEqual(detect_education(data1),-1)
        data2 = ['J L','Blah','BLAAAAAAh','Master of Technology','P1','','P2','----','-------------------blaaaaaa']
        self.assertEqual(detect_education(data2),-1)
        data3 = ['J L','Blah','BLAAAAAAh','University of Bachelor in Math']
        self.assertEqual(detect_education(data3),['University of Bachelor in Math'])
        data4 = ['J L','Blah','BLAAAAAAh','University of Bachelor in Math','Blah2','TexasUniversity Doctor of Phils']
        self.assertEqual(detect_education(data4), ['University of Bachelor in Math','TexasUniversity Doctor of Phils'])

    def test_surround_block(self):
        self.assertEqual(surround_block('tag','text'),'<tag>\ntext\n</tag>\n')
        self.assertEqual(surround_block(123,'text'),'0')
        self.assertEqual(surround_block('tag',123),'0')
        self.assertEqual(surround_block('tag','text1\ntext2'),'<tag>\ntext1\ntext2\n</tag>\n')

    '''for all the functions that output strings, testing should be obvious from the output'''
        



unittest.main()
