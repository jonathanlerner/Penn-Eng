'''Submission by Jonathan Lerner'''
import string

'''Section 1. This section is functions that read in the resume text file and read/write the html file'''
def resume_data_read(filename):
    '''takes the filename as an input, and returns a list of lines from that file'''
    '''(each of which is a string)'''
    f = open(filename)
    resume_data = []
    for line in f:
        resume_data.append(line.rstrip())
    f.close()
    return resume_data
    

def open_and_init_resume_html(html_file_name):
    '''this function opens the original resume html file, does proper initial formatting, and then returns the file object'''
    '''from here one can continue writing the resume'''
    f = open(html_file_name, 'r+')
    lines = f.readlines()
    f.seek(0)
    f.truncate()
    del lines[-1]
    del lines[-1]
    f.writelines(lines)
    return f

def write_resume_body(html_file_object, html_text):
    '''this function writes the html_text to the file object. note that the file object pointer must be at the point'''
    '''where the html_text is to be written'''
    html_file_object.write(html_text)
    html_file_object.close()
    

'''Section 2. This section is functions that detect resume data from the txt file'''    
def detect_name(resume_data):
    '''Assume we are passed a list which is not empty. name is just the first line'''
    name = resume_data[0]
    if name[0] not in string.ascii_uppercase:
        '''raise error, as per the HW5 specifications'''
        raise Exception('The first line of the resume should just be the name with proper capitalization.')       
    return name

def email_check(s):
    '''check if a particular string s is an email address (according to the homework specs)'''
    '''returns True if it is a proper email address, False if not'''
    
    '''condition 1: last four letters are '.com' or '.edu' '''
    if not (s[-4:] == '.com' or s[-4:] == '.edu'):
        return False
    i = 0
    amp_location = -1
    for i in range(0,len(s)):
        if s[i] == '@':
                '''the string meets the first condition'''
                amp_location = i
                break
    if amp_location == -1:
        '''no ampresand detected, return False'''
        return False
    if s[amp_location + 1] not in string.ascii_lowercase:
        return False

    '''all conditions met, we have a proper email address here!'''
    return True
        

def detect_email(resume_data):
    '''returns the email address line in the resume_data, otherwise -1'''
    for line in resume_data:
        if email_check(line):
            '''this is our email address'''
            return line
    '''if we get here then no email was detected'''
    return -1

def detect_courses(resume_data):
    '''returns the string of course names in the resume'''
    for line in resume_data:
        if line[:7] == 'Courses':
            '''this is the Courses line'''
            for i in range(7,len(line)):
                '''search for the first letter after courses (ignoring punctuation)'''
                if line[i] in string.ascii_letters:
                    return line[i:]
    return -1

def detect_projects(resume_data):
    '''returns a list of projects in the resume_data, otherwise -1'''
    project_list = []
    for i, line in enumerate(resume_data):
        '''iterate by line number in resume_data'''
        if line[:8] == 'Projects':
            '''this is the beginning of our set of projects'''
            end_line = reduce(lambda x, y: x+y, ['-' for x in range(0,10)])
            for project_line in resume_data[i+1:]:
                if project_line[0:10] == end_line:
                    '''arrived at the end line'''
                    break
                if not project_line == '':
                    '''skip the empty lines'''
                    project_list.append(project_line)
            return project_list
    '''if we get here then no projects were detected'''
    return -1

def detect_education(resume_data):
    education_str = ['University']
    degree_str = ['Bachelor', 'Master', 'Doctor']
    education_list = []
    for line in resume_data:
        '''test if an element in education_str is in the line; if so test degree_str'''
        if any([True for e in education_str if e in line]):
            if any([True for d in degree_str if d in line]):
                '''found a line with both an education and a degree. add to the list'''
                education_list.append(line)
    if education_list == []:
        '''no education data found'''
        return -1
    else:
        return education_list
                
    

def surround_block(tag,text):
    '''this is a helper function that surrounds a body of text'''
    '''with the appropriately formated open and close tag'''
    if type(tag) != str or type(text) != str:
        return '0'
    else:
        return '<'+tag+'>\n'+text+'\n</'+tag+'>\n'


'''Section 3. This section is functions that return html sections to be written to the html file'''
def res_intro():
    '''text at the very beginning of the resume'''
    return '<div id ="page-wrap">\n'

def res_outro():
    '''text at the very end of the resume'''
    return '</div>\n</body>\n</html>'

def basic_info(name, email):
    '''returns an html formatted string of the basic resume name/email info'''
    '''don't include email address if one wasn't detected (code is -1)'''
    if email != -1:
        return surround_block('div',surround_block('h1',name)+surround_block('p','Email: '+email))
    else:
        return surround_block('div',surround_block('h1',name))

def education(edu_data):
    '''returns an html formatted string of the education section of the resume'''
    edu_str = ''
    for degree in edu_data:
        edu_str += surround_block('li',degree)
    edu_str = surround_block('ul', edu_str)
    return surround_block('div',surround_block('h2','Education')+edu_str)


def project(proj_data):
    '''returns an html formatted string of the projects section of the resume'''
    proj_str = ''
    for project in proj_data:
        proj_str += surround_block('ul',surround_block('li',surround_block('p',project)))
    return surround_block('div',surround_block('h2','Projects')+proj_str)

def courses(course_str):
    '''returns an html formatted string of the courses section of the resume'''
    return surround_block('div',surround_block('h3','Courses')+surround_block('span',course_str))

'''Section 4. This is the main function'''
def main():
    '''This file reads in the data from resume_txt_file'''
    '''then writes it in the correct html formatting to resume_html_file'''
    resume_txt_filename = 'resume.txt'
    resume_html_filename = 'resume.html'

    '''read in resume txt, parse into all the desired data'''
    resume_data = resume_data_read(resume_txt_filename)
    name = detect_name(resume_data)
    email = detect_email(resume_data)
    course_str = detect_courses(resume_data)
    project_list = detect_projects(resume_data)
    education_list = detect_education(resume_data)

    '''create the resume html string'''
    resume_html = res_intro() + basic_info(name, email)
    if education_list != -1:
        resume_html += education(education_list)
    if project_list != -1:
        resume_html += project(project_list)
    if course_str != -1:
        resume_html += courses(course_str)
    resume_html += res_outro()

    '''write the html to the resume html file'''
    f = open_and_init_resume_html(resume_html_filename)
    write_resume_body(f, resume_html)



if __name__ == "__main__":
    main()
