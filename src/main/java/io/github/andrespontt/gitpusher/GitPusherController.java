package io.github.andrespontt.gitpusher;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitPusherController {
    
    @GetMapping(value="/api/test/{param}", produces = "application/json")
    public Response apiTest(@PathVariable String param) {
        Response response = new Response();
        response.setMessage(param);
        return response;
    }

}