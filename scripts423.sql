SELECT student.name, student.age, f.name FROM student
LEFT JOIN faculty f on f.id = student.faculty_id;

select student.name from student
INNER JOIN avatar a on student.id = a.student_id