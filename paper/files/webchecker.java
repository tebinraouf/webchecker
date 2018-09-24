//Import WebChecker Package

public class TestDriver {

    public static void main(String[] args) {

        // Step 1: Get the source to be validated
        String html = "webchecker.html";

        // Step 2: Write your validation using Epsilon Validation Language
        String evl = "webchecker.evl";

        // Step 3: Check the validation against the html in step 1
        WebChecker checker = new WebChecker();
        checker.setSource(html);
        checker.setValidation(evl);
        checker.check();

        // Step 4: Check the result
        System.out.println("Does the html code pass validation? " + checker.isValid());
        List<String> errors = checker.errors();
        System.out.println("Validation Errors:");
        for (String error : errors) {
            System.out.println(error);
        }

    }

}
