package com.gfd.notekeeper

// using object instead of class makes
// this a static singleton
object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    // no params, no way to call init
    // it runs automatically as part of instance creation
    // Does have access to constructor params and properties
    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeCourses() {
        var course = CourseInfo("android_intents", "Android Programming with Intents")
        courses[course.courseId] = course

        course = CourseInfo("android_async","Android Async Programming and Services")
        courses[course.courseId] = course

        course = CourseInfo(title="Java Fundamentals: The Java Language", courseId = "java_lang")
        courses[course.courseId] = course

        course = CourseInfo("java_core","Java Fundamentals: The Core Platform")
        courses[course.courseId] = course
    }

    private fun initializeNotes() {
        val note = NoteInfo(courses.getValue("android_intents"),
            "My Note",
            "Lorem Ipsum"
        )
        notes.add(note)
    }
}