package student;

public class Student {
	
	String student_name;
	int total_hw_score;
	int num_hws;
	
	/**
	 * Constructor
	 * @param student_name
	 */
	public Student(String student_name) {
		this.student_name = student_name;
		this.total_hw_score = 0;
		this.num_hws = 0;
	}
	/**
	 * Returns the Student's name
	 * @return
	 */
	String getName() {
		return this.student_name;
	}
	
	/**
	 * Adds a new score to the student's existing total homework score
	 * @param new_score
	 */
	void addHWScore(int new_score) {
		this.total_hw_score += new_score;
		this.num_hws += 1;
	}
	
	/**
	 * Gets the student's total homework score
	 * @return
	 */
	int getTotalScore() {
		return this.total_hw_score;
	}
	
	/**
	 * Returns the student's average homework score.
	 * If no assignments have been completed yet, returns -1
	 * @return
	 */
	double getAverageScore() {
		if (num_hws == 0) {
			return -1;
		}
		else {
			return (total_hw_score * 1.0) / num_hws;
		}
	}
	
	/*
	 * returns a string representation of a Student object
	 */
	public String toString() {
		return this.student_name + " : " + this.total_hw_score;
	}

}
