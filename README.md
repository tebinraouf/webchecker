# Epsilon - HTML Page Validation Plugin (WebChecker)

Eclipse plugins that extend Epsilon's Model Connectivity (EMC) layer with support for validating static and dynamic (generated) HTML pages by using [Epsilon Validation Language (EVL)](https://www.eclipse.org/epsilon/doc/evl/) for validation, [Selenium Web Driver](https://www.seleniumhq.org/) for HTML code extraction, and [jsoup: Java HTML Parser](https://jsoup.org/) for parsing HTML code.


## How to run

* Install the latest interim version of [Epsilon](https://www.eclipse.org/epsilon/download) or clone its Eclipse Epsilon Git repo and import all projects under /plugins to your Eclipse workspace.
* Clone this repo.
* Run the `webchecker` project as a Java Application.  

## Extra Rules & Examples
The **rules** branch has more EVL constraints and examples. When they are stable, the branch will be merged with the **master** branch. **files/Bootstrap/Bootstrap.html** has the HTML examples and **files/Bootstrap/Bootstrap.evl** has a list of EVL constraints.

## Plugin Components
Like any other Epsilon plugins, there are two main parts or eclipse projects:

1. WebChecker: this project contains all the required classes. WebCheckerModel extends CachedModel abstract class to interact with Epsilon Validation Language (EVL). 
2. WebChecker.dt: this project creates the GUI for the plugin if run as Eclipse Application.
3. WebChecker.test: this project tests the WebChecker plugin by using HTML and EVL as code.  

## Code Explanation
### WebChecker
Main classes include:

* WebCheckerModel


    As explained above, `WebCheckerModel` implements `CachedModel` abstract class. This is required for Epsilon to know there is a new plugin. `CachedModel` is a generic class of type `jsoup element`.  


* WebCheckerPropertyGetter

    `WebCheckerPropertyGetter` gets the properties of the EVL. For example, consider this EVL:
        
    ```Java
    context t_div {
        constraint InButtonGroup {
            guard : self.class.includes("btn-large")
            check : self.parent.classes.includes("btn-group")
            message : "btn-large divs should be contained under btn-group"
        }
    }
    ```
    `class` is a property. In `WebCheckerPropertyGetter`, this property is captured. Then, an instance of `GuardedElement` is returned. GuardedElement class has `includes(String className)` method, which return true if the condition is met; false otherwise.

    Once the `guard` statement of the EVL is captured, Next is to capture the `check` statement. The `check` statement includes

    ```Java
    self.parent.classes.includes("btn-group")
    ```

    `parent` and `classes` are a property of `Parent` and `Classes` respectively. `includes(String className)` is a method of `Classes`.

* Browser

    This class abstracts Selenium Web Driver. Currently, I only consider FireFox, but the other browsers can be easily added. 

* WebChecker

    This class is just a wrapper and convenient class for the WebCheckerModel. 

    ```Java
    WebChecker checker = new WebChecker(); //[1]
    checker.setSource(html); //[2]
    checker.setValidation(evl); //[3]
    checker.check(); //[4]
    ```  
    [1] Create an instance with the default constructor.

    [2] Set the modal source (i.e. the html file) either URI or code as string.

    [3] Set the EVL source either URI or code as string. 

    [4] Check the HTML file or code against the EVL validation.

    To check the result of the validation, use `isValid()` method on the WebChecker instance. To get any produced error messages, use `errors()`, which returns a `List<string>`.

### Example
This example can be found in the `TestDriver.class` 

```Java
//Step 1: Get the source to be validated
Browser browser = new Browser();
WebDriver driver = browser.getDriver();
driver.get("https://www.eclipse.org/epsilon/");		
WebElement element = driver.findElement(By.className("span5"));
String html = element.getAttribute("outerHTML");

//Step 2: Write your validation using Epsilon Validation Language
String evl = "files/sampleEVL.evl";

//Step 3: Check the validation against the html in step 1
WebChecker checker = new WebChecker();
checker.setSource(html);
checker.setValidation(evl);
checker.check();

//Step 4: Check the result
System.out.println("Does the html code pass validation? " + checker.isValid());
List<String> errors = checker.errors();
System.out.println("Validation Errors:");
for (String error : errors) {
    System.out.println(error);
}		
driver.quit();

/*
//Output
Does the html code pass validation? false
Validation Errors:
btn-large divs should be contained under btn-group
*/
```
 

### Future Work
- Developing a domain-specific language to create and parse rules.
- Creating a specific type for HTML elements. Currently, this plugin depends on [jsoup: Java HTML Parse](https://jsoup.org/). Although jsoup provides very useful methods to interact with HTML elements, the syntax is long. For example, `self.type.exists()` could be `self.exist()`. Here, `type` is repititive.
