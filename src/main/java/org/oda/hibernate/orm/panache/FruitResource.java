package org.oda.hibernate.orm.panache;

import static jakarta.ws.rs.core.Response.Status.CREATED;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/fruits")
@ApplicationScoped
public class FruitResource {

    @GET
    public Uni<List<Fruit>> get(){
        return Fruit.listAll(Sort.by("name"));
    }

    @GET
    @Path("/{id}")
    public Uni<Fruit> getSingle(Long id){
        return Fruit.findById(id);
    }

    @POST
    public Uni<Response> create(Fruit fruit){
        return Panache.withTransaction(fruit::persist)
                      .replaceWith(Response.ok(fruit).status(CREATED)::build);
    }

    
}
