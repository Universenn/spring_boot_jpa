package com.example.springbootjpa1.service;

import com.example.springbootjpa1.domain.item.Book;
import com.example.springbootjpa1.domain.item.Item;
import com.example.springbootjpa1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // Transactional 안에서 db를 조회 해야 영속상태로 전환이 되고
        findItem.setName(name);                         // 그 후 값을 변경해야 변경감지(dirty checking)가 일어난다)
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
//        findItem.set
        return findItem;
        //... 다 추가해 준다. 단 save 처리를 안해줘도 영속성 컨테스트로 바뀌었기 때문에 db에 알아서 저장이 된다
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
