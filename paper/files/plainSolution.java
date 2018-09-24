//Import Jsoup pakcage


public class TestDriver {

    public static void main(String[] args) {
        // This is an example of using plain Java to check constraints. This is used to
        // compare Plain Java vs. WebChecker driver
        File input = new File("files/bootstrap/newCheck.html");
        try {
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements elments = doc.getElementsByTag("div");
            for (Element element : elments) {
                if (element.hasClass("col-sm-4")) {
                    if (!element.parent().hasClass("row")) {
                        System.out.println("A div element with class col should have a parent element with class row");
                    } else {
                        if (!element.parent().parent().hasClass("container")) {
                            System.out.println(
                                    "A div element with class col should have a parent element with class row, which has a parent with class container.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
