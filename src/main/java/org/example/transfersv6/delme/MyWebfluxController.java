package org.example.transfersv6.delme;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MyWebfluxController {

    @GetMapping("/aa/{id}")
    public Mono<String> getEmployeeById(@PathVariable String id) {
        return Mono.just("hi").log();
    }
    @GetMapping("/bb/{id}")
    public Flux<String> getEmployeeById2(@PathVariable String id) {
        return Flux.just("qweqwe", "qweqwe", "qweqwe");
    }

    public void doIt() {
        getEmployeeById("aa").subscribe(
                q -> System.out.println("aa " + q),
                e -> System.out.println("bb " + e.getMessage()),
                () -> System.out.println("done")
        );
    }

    public static void main(String[] args) {
        try {
            new MyWebfluxController().doIt();
            new MyWebfluxController().getEmployeeById2("tt").subscribe(q -> System.out.println("aa " + q));
        } catch (Exception e) {
            System.out.println("qqqq");
        }
    }
}
