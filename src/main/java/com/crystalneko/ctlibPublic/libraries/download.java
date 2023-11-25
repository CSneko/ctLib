package com.crystalneko.ctlibPublic.libraries;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class download {
    private static final String SEARCH_API_URL = "https://search.maven.org/solrsearch/select?q=g:%22{group}%22&rows=20&wt=json";
    public static Boolean isDownload(String pack){
        return !getJarPath(pack).equalsIgnoreCase("null.jar");
    }
    public static void downloadJar(String pack){
        String url="null";
        try {
            url= getLatestVersionDownloadUrl(pack);
        } catch (IOException e) {
            System.out.println("Unable to get jar url:" + e);
        }
        if(url == null || url.equalsIgnoreCase("null")){return;}
        String StrPath = pack.replace('.','/');
        Path path = Paths.get(StrPath);
        Path libPath = Paths.get("ctlib/libraries/"+StrPath);
        if(!Files.exists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Unable to create path:"+e);
            }
        }
        try {
            downloadFile(url, "ctlib/libraries/" +StrPath + "/",getJarName(pack));
        } catch (IOException e) {
            System.out.println("Unable to get jar name:"+e);
        } catch (InterruptedException e) {
            System.out.println("Unable to get jar name:"+e);
        }

    }

    public static String getJarName(String groupId) throws IOException, InterruptedException {
        String searchUrl = SEARCH_API_URL.replace("{group}", groupId);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode responseNode = rootNode.get("response");
        JsonNode docsArray = responseNode.get("docs");
        JsonNode firstDoc = docsArray.get(0);

        String artifactId = firstDoc.get("a").asText();
        String jarName = artifactId + ".jar";
        return jarName;
    }
    public static String getJarPath(String pack){
        String StrPath = pack.replace('.','/');
        Path path = Paths.get(StrPath);
        //文件夹是否存在
        if(!Files.exists(path)){
            return "null.jar";
        }
        //从mvn中心仓库获取jar名称
        String jarName = "null.jar";
        try {
            jarName = getJarName(pack);
        } catch (IOException e) {
            System.out.println("Unable to get jar info form mvn repo:"+e);
        } catch (InterruptedException e) {
            System.out.println("Unable to get jar info form mvn repo:"+e);
        }
        //是否存在这个jar
        File jarFile = new File("ctlib/libraries/" + StrPath + "/" + jarName);
        if(jarFile.exists()){
            return "ctlib/libraries/" + StrPath + "/" + jarName;
        }else {
            return "null.jar";
        }
    }
    public static String getLatestVersionDownloadUrl(String groupId) throws IOException {
        String apiUrl = "https://search.maven.org/solrsearch/select?q=g:%22"
                + groupId
                + "%22&rows=1&wt=json";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new URL(apiUrl));

        JsonNode responseNode = rootNode.path("response");
        if (responseNode.has("numFound") && responseNode.get("numFound").asInt() > 0) {
            JsonNode docNode = responseNode.path("docs").get(0);
            String latestVersion = docNode.path("latestVersion").asText();

            String downloadUrl = "https://repo1.maven.org/maven2/"
                    + groupId.replace('.', '/')
                    + "/"
                    + docNode.path("a").asText()
                    + "/"
                    + latestVersion
                    + "/"
                    + docNode.path("a").asText()
                    + "-"
                    + latestVersion
                    + ".jar";

            return downloadUrl;
        }

        return null;
    }
    public static void downloadFile(String fileUrl, String targetDirectory, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        Path targetPath = Paths.get(targetDirectory, fileName);

        try (InputStream in = url.openStream()) {
            Files.copy(in, targetPath);
        }
    }
}
