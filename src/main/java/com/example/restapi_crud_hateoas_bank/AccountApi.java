package com.example.restapi_crud_hateoas_bank;

import com.example.restapi_crud_hateoas_bank.Entity.Account;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/accounts")
public class AccountApi {

    private AccountService service;
    private AccountModelAssembler modelAssembler;

    public AccountApi(AccountService accountService, AccountModelAssembler modelAssembler) {
        this.service = accountService;
        this.modelAssembler = modelAssembler;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Account> getOne(@PathVariable("id") Integer id) {
//        try {
//            Account account = service.get(id);
//            account.add(linkTo(methodOn(AccountApi.class).getOne(id)).withSelfRel());
//            account.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
//            return new ResponseEntity<Account>(account, HttpStatus.OK);
//        } catch (NoSuchElementException exception) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //    Spring HATEOAS provides a kind of assembler class  that hepls minimizing such duplication and thus making leaner code
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Account>> getOne(@PathVariable("id") Integer id) {
        try {
            Account account = service.get(id);
            EntityModel<Account> entityModel = modelAssembler.toModel(account);
            return ResponseEntity.ok(entityModel);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping
//    public CollectionModel<Account> listAll() {
//        List<Account> accountCollection = service.listAll();
//        for (Account account : accountCollection) {
//            account.add(linkTo(methodOn(AccountApi.class).getOne(account.getId())).withSelfRel());
//        }
//
//        CollectionModel<Account> collectionModel = CollectionModel.of(accountCollection);
//        collectionModel.add(linkTo(methodOn(AccountApi.class).listAll()).withSelfRel());
//
//        return collectionModel;
//    }

    @GetMapping
    public CollectionModel<EntityModel<Account>> listAll() {
        List<Account> listAccount = service.listAll();
        List<EntityModel<Account>> listEntityModel = listAccount.stream().map(modelAssembler::toModel).collect(Collectors.toList());

        CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(listEntityModel);
        collectionModel.add(linkTo(methodOn(AccountApi.class).listAll()).withSelfRel());
        return collectionModel;
    }

//    @PostMapping
//    public HttpEntity<Account> add(@RequestBody Account account) throws URISyntaxException {
//        Account savedAccount = service.save(account);
//
//        savedAccount.add(linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId())).withSelfRel());
//        savedAccount.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
//
//        URI location = linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId())).toUri();
//
//        return ResponseEntity.created(location).body(savedAccount);
//    }

    @PatchMapping
    public HttpEntity<EntityModel<Account>> add(@RequestBody Account account) {
        Account savedAccount = service.save(account);
        EntityModel<Account> entityModel = modelAssembler.toModel(savedAccount);
        return ResponseEntity.created(linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId())).toUri()).body(entityModel);
    }

//    @PutMapping
//    public HttpEntity<Account> replace(@RequestBody Account account) {
//        Account updatedAccount = service.save(account);
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).getOne(updatedAccount.getId())).withSelfRel());
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
//        return new ResponseEntity(updatedAccount, HttpStatus.OK);
//    }
    @PutMapping
    public HttpEntity<EntityModel<Account>> replace(@RequestBody Account account){
        Account updatedAccount = service.save(account);
        EntityModel<Account> entityModel = modelAssembler.toModel(updatedAccount);
        return ResponseEntity.ok(entityModel);
    }

//    @PatchMapping("/{id}/deposits")
//    public HttpEntity<Account> deposite(@PathVariable("id") Integer id, @RequestBody Amount amount) {
//        Account updatedAccount = service.deposit(amount.getAmount(), id);
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).getOne(updatedAccount.getId())).withSelfRel());
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
//        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
//    }

    @PatchMapping("/{id}/deposits")
    public ResponseEntity<EntityModel<Account>> deposite(@PathVariable("id")Integer id, @RequestBody Amount amount){
        Account updatedAccount = service.deposit(amount.getAmount(), id);
        EntityModel<Account> entityModel = modelAssembler.toModel(updatedAccount);
        return ResponseEntity.ok(entityModel);
    }

//    @PatchMapping("/{id}/withdrawal")
//    public HttpEntity<Account> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount) {
//        Account updatedAccount = service.withdraw(amount.getAmount(), id);
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).getOne(updatedAccount.getId())).withSelfRel());
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
//        return ResponseEntity.ok(updatedAccount);
//    }

    @PatchMapping("/{id}")
    public HttpEntity<EntityModel<Account>> withdraw(@PathVariable("id")Integer id, @RequestBody Amount amount){
        Account updatedAccount = service.withdraw(amount.getAmount(), id);
        EntityModel<Account> entityModel = modelAssembler.toModel(updatedAccount);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
