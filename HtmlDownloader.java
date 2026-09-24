import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;

public class HtmlDownloader {
    private final HttpClient client;
    public HtmlDownloader() {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).followRedirects(HttpClient.Redirect.NORMAL).build();
    }
    public String download(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .timeout(Duration.ofSeconds(10))
                                .header("User-Agent", "MyHTMLDownloader/1.0")
                                .header("Accept", "text/html")
                                .GET()
                                .build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP Error: " + response.statusCode());
        }

        return response.body();
    }
    public static void main(String[] args){
        HtmlDownloader downloader = new HtmlDownloader();
        try {
            String html = downloader.download("https://google.com");
            Files.writeString(Path.of("google.html"), html);
            System.out.println("Downloaded " + html.length() + " characters");
        }
        catch(Exception e){
            System.out.println("Download Failed: " + e.getMessage());
        }
    }
};