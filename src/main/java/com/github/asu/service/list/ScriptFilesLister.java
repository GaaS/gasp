package com.github.asu.service.list;

import org.apache.maven.plugin.MojoExecutionException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ScriptFilesLister {

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public ScriptFilesLister(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
    }

    public ScriptFiles listFiles(String projectId) throws MojoExecutionException {
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<ScriptFiles> responseEntity =
                restTemplate.exchange(listPath(projectId), HttpMethod.GET, requestEntity, ScriptFiles.class);
        return responseEntity.getBody();
    }

    private URI listPath(String projectId) throws MojoExecutionException {
        try {
            return new URI("https://script.google.com/feeds/download/export?id=" + projectId + "&format=json");
        } catch (URISyntaxException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }
}