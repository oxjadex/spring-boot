package com.example.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

//ItemService.java
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MembersRepositry memberRepository;

    public void saveItem(String title, Integer price){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void editItem(Integer id, String title, Integer price){
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public List<Item> findAllItem(){
        List<Item> result;
        result = itemRepository.findAll();
        return result;
    }

    public Optional<Item> findItemById(Integer id){
        return itemRepository.findById(id);
    }

    public void deleteItemById(Integer id){
        itemRepository.deleteById(id);
    }

    public void login(String username, String password, String displayName){
        Members member = new Members();
        member.setUsername(username);
        member.setPassword(password);
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }
}