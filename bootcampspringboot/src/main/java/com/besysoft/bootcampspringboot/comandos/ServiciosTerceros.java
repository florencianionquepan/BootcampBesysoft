package com.besysoft.bootcampspringboot.comandos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ServiciosTerceros implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void run(String... args) throws Exception {
        //RestTemplate restTemplate=new RestTemplate();
        String url="https://jsonplaceholder.typicode.com/post";

        String forObject = this.restTemplate.getForObject(url,String.class);
/*        System.out.println("JSONPLACEHOLDER en String");
        System.out.println(forObject);*/

        ResponseEntity<Post[]> forEntity=this.restTemplate.getForEntity(url,Post[].class);
        List<Post> postList= Arrays.asList(forEntity.getBody());

        System.out.println("Coleccion de post");
        postList.forEach(System.out::println);

        Post post=new Post();
        post.setUserId(1);
        post.setTitle("titulo del nuevo post");
        post.setBody("esto corresponse al body del nuevo post");

    }
}
