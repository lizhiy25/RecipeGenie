## Recipe Genie
Group 116 decides to develop a program that recommends recipes tailored to user preferences and call it Recipe Genie.

Group Members: 
* Brandon
* Willis
* Zhiyu

## 0. How to Download and Run the Recipe Genie
**The Internet connection is required to run this program, and it will not run successfully without the Internet.**
* There are two ways to download the Recipe Genie. You can either click the **green icon** "code", then download the zip document, unzip the file, or copy the link and clone the repository.

* Maven is required in this project. You need to click File -> Project Structure. Click the "Modules" -> "Dependences" and add json-20230227.jar (located in /lib) and apply it. Then, you can use JAVA with JDK(The development environment is JDK17)to open the program.
  
* The main class is located at src/main/java/Main.java
* Run the main program, then you can login/signup and search for food recipes and calories.

## 1. Introducing Recipe Genie
**Login/Signup**: You need to create a new account by providing a username and password, or login with the username and password. When you created an account, you can login next time since the data is stored in the system.

**Search Recipes**: You can see the nutrients by typing in the name of the recipes. Then, you will see the result for recommended recipes based on keywords you entered. It contains the ingredients and the instructions on how to make it.
<img src="https://i.postimg.cc/ZYWbk92q/search-Recipes.png" width="800" height="600">

You can also view the nutrition data by clicking the "Nutrition" button at the bottom.
<img src="https://i.postimg.cc/7LBkdDN7/searchrecipes2.png" width="800" height="600">

If you like the recipes, simply click button "Save Recipe" at the bottom, then you can view it on Saved Recipes.



**View Saved Recipes**: In this function, you can view the nutrients of the recipes you saved, and remove it by clicking "Delete" button on the right side.

<img src="https://i.postimg.cc/RCJBskJ6/viewsaved.png" width="800" height="600">


**Weight Loss Calculator**: When you use this function, you have to enter your Weight(kg), Height(cm), Age, and Gender first. Then, when you click "Calculate Daily Calorie Requirement" button, you will see filter recipes by low calorie content and dietary preferences, to reate a sustainable, healthy meal plan

**Account Settings**: You can view your account profile here. You can also change your username and password here.


## 2.Feedback，and Issues
If you encounter any issues or have suggestions while using Recipe Genie, please click the "Issue" button in the top-right corner of the GitHub page, then select "New Issue." After that, please provide a title and a detailed description of the problem or suggestion. We greatly appreciate your feedback!

## 3. Contributions:
This project was a collaborative effort. Below is a breakdown of the roles and responsibilities of each contributor:
* Zhiyu:
  * Designed panel and created the code framework following the SOLID principles of maintainable and extensible design.
  * Developed the signup/login function, change password feature within the account settings.
  * Completed README.md

* Brandon:
  * Completed API.java section，API can be called more easily.
  * Built the initial implementation of the Search Recipes feature
  * Completed View Saved Recipes section, allowing users to save their favorite recipes.
 
* Willis:
  * Enhanced and completed the Search Recipes feature, making it more comprehensive and robust.
  * Completed Weight Loss Calculator function
  * Refined and cleaned up the codebase, improving readability and maintainability.

## 4. Future：
Although most of Recipe Genie's features have been implemented, there are still a few that could be improved for a better user experience. We would update more functions in the future, including:
* Better Layout
* Adding image alongside recipes



