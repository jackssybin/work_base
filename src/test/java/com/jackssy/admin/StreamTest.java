package com.jackssy.admin;

import org.junit.Test;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/11 20:32
 */

public class StreamTest {

    @Test
    public void testGroup(){
        List<Student> list = new ArrayList();
        Student student  = new Student("1","1","1");
        Student student2  = new Student("2","1","1");
        Student student3  = new Student("3","1","2");
        Student student4  = new Student("4","1","2");
        list.add(student);
        list.add(student2);
        list.add(student3);
        list.add(student4);

        System.out.println(list.stream().collect(Collectors.groupingBy(Student::getGroup)));
        List<StudentGroup> studentGroups = list.stream().collect(Collectors.groupingBy(Student::getGroup))
                .entrySet().stream().map(xx->{
                    StudentGroup studentGroup = new StudentGroup();
                    studentGroup.setGroup(xx.getKey());
                    studentGroup.setStudents(xx.getValue());
                    return studentGroup;
                }).collect(Collectors.toList());
        System.out.println(studentGroups);


    }

    class StudentGroup{
        private String group;
        private List<Student> students;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        @Override
        public String toString() {
            return "StudentGroup{" +
                    "group='" + group + '\'' +
                    ", students=" + students +
                    '}';
        }
    }

    class Student{
        private String id;
        private String name;
        private String group;

        public Student(String id, String name, String group) {
            this.id = id;
            this.name = name;
            this.group = group;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", group='" + group + '\'' +
                    '}';
        }
    }
}

