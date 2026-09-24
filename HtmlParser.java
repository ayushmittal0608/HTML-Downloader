import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.nio.file.Files;
import java.nio.file.Path;

public class HtmlParser {

    public static void main(String[] args) throws Exception {

        // Read HTML from file
        String html = Files.readString(
                Path.of("google.html")
        );

        // Parse HTML
        Document document = Jsoup.parse(html);

        // Print DOM tree
        printTree(document, 0);
    }

    static void printTree(Element element, int depth) {

        System.out.println("  ".repeat(depth) + "<" + element.tagName() + ">");

        for (Element child : element.children()) {
            printTree(child, depth + 1);
        }

        System.out.println("  ".repeat(depth) + "</" + element.tagName() + ">");
    }
}