package com.example.restapi_crud_hateoas_bank;

import com.example.restapi_crud_hateoas_bank.Entity.Account;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
    @Override
    public EntityModel<Account> toModel(Account entity) {
        EntityModel<Account> accountEntityModel = EntityModel.of(entity);

        accountEntityModel.add(linkTo(methodOn(AccountApi.class).getOne(entity.getId())).withSelfRel().withType("GET"));
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION).withType("GET"));
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).withdraw(entity.getId(), null)).withRel("withdrawal").withType("PATCH"));
        accountEntityModel.add(linkTo(methodOn(AccountApi.class).deposite(entity.getId(), null)).withRel("deposites"));
        accountEntityModel.add(Link.of("https://spring.io/projects/spring-hateoas", "docs"));
        return accountEntityModel;
    }
}
