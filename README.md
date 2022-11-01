#Installation

I've used TestNG for the different tests.
If the auto installation doesn't work via Maven, you can install it from the Eclipse Marketplace (if Eclipse is your IDE)

#How to run the tests
To run the tests:
* right click on SeleniumExerciseTest.java
* Run As TestNG Test

# Info about the tests
I've created 7 tests of which:
* #5 will fail because the whole search phrase is not present in each Title (that's what I've understood from the test description)
* #7 will fail because I think that having a blank page if you search with an empty string is not acceptable
I've used the Page Object Model to retrieve the elements of each page. It's a sort of mapping of the elements present in each page using the right selector.

# Suggestions for devs
 *  Don't use the 'input' element for the texts in the dialog window
 *  Use an ID for elements in the dialog window (Released On, Popularity, etc.)
 *  Use ID for "Learn more" button for Titles