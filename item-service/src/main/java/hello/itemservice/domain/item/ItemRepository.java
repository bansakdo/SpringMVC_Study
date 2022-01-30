package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // 동시에 여러 곳에서 접근할 수 있는 멀티스레드 환경에서는 HashMap을 사용하면 안된다.
    // HashMap이 아닌 ConcurrentHashMap을 사용해야 한다.
    // long 또한 atomic?Long?을 사용해야 한다.
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L;      // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
