In this assignment, you'll get some practice at building effective unit tests. Using the example from our videos, you'll be developing tests for the Demo class, including the isTriangle() and main() methods.

Deliverable
Your task is to create a file, DemoTest.java, which properly tests the Demo class to ensure it is working properly. The correct Demo.java file is provided for your use in one of the packages below (.zip for Windows users and .tgz for the Linux/UNIX/macOS users):

IntroToUnitTesting.zip
IntroToUnitTesting.tgz
See the included README.md file for instructions on building the packages from command line or for importing them into eclipse.

How will this be graded?
In order to measure the adequacy of your tests, we will be using mutation analysis. We have generated many variations of the Demo.java file, each with a single fault introduced. These faults (mutations) include things like swapping a binary operator for another (e.g., '+' instead of '-'), or changing the variable used for another variable of the same type (e.g., 'a' substituted for 'b').

You will get half credit for submitting tests which accurately report a working correct implementation. Then, you get incremental credit for each mutant your tests successfully identify as not correct.