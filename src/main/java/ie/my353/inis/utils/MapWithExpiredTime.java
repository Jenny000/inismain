package ie.my353.inis.utils;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Administrator
 * date 2019/3/16 0016.
 * 带有效期map
 */
@Component
public class MapWithExpiredTime<K,V> extends HashMap<K,V> {

    private static final long serialVersionUID = 1L;

    /**
     * default expiry time 2m
     */
    private long EXPIRY = 1000 * 60 * 5;

    private HashMap<K, Long> expiryMap = new HashMap<>();

    public MapWithExpiredTime(){
        super();
    }

    public MapWithExpiredTime(long defaultExpiryTime){
        this(1 << 4, defaultExpiryTime);
    }
    public MapWithExpiredTime(int initialCapacity, long defaultExpiryTime){
        super(initialCapacity);
        this.EXPIRY = defaultExpiryTime;
    }
    public V put(K key, V value) {
        expiryMap.put(key, System.currentTimeMillis() + EXPIRY);
        return super.put(key, value);
    }

    public boolean containsKey(Object key) {
        return !checkExpiry(key, true) && super.containsKey(key);
    }

    /**
     * @param key
     * @param value
     * @param expiryTime 键值对有效期 毫秒
     * @return
     */

    public V put(K key, V value, long expiryTime) {
        expiryMap.put(key, System.currentTimeMillis() + expiryTime);
        return super.put(key, value);
    }

    public int size() {
        return entrySet().size();
    }
    public boolean isEmpty() {
        return entrySet().size() == 0;
    }
    public boolean containsValue(Object value) {
        if (value == null) return Boolean.FALSE;
        Set<Entry<K, V>> set = super.entrySet();
        Iterator<Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<K, V> entry = iterator.next();
            if(value.equals(entry.getValue())){
                if(checkExpiry(entry.getKey(), false)) {
                    iterator.remove();
                    return Boolean.FALSE;
                }else return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public Collection<V> values() {

        Collection<V> values = super.values();

        if(values == null || values.size() < 1) return values;

        Iterator<V> iterator = values.iterator();

        while (iterator.hasNext()) {
            V next = iterator.next();
            if(!containsValue(next)) iterator.remove();
        }
        return values;
    }
    public V get(Object key) {
        if (key == null)
            return null;
        if(checkExpiry(key, true))
            return null;
        return super.get(key);
    }
    /**
     *
     * @Description: 是否过期
     * @param key
     * @return null:不存在或key为null -1:过期  存在且没过期返回value 因为过期的不是实时删除，所以稍微有点作用
     */
    public Object isInvalid(Object key) {
        if (key == null)
            return null;
        if(!expiryMap.containsKey(key)){
            return null;
        }
        long expiryTime = expiryMap.get(key);

        boolean flag = System.currentTimeMillis() > expiryTime;

        if(flag){
            super.remove(key);
            expiryMap.remove(key);
            return -1;
        }
        return super.get(key);
    }
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            expiryMap.put(e.getKey(), System.currentTimeMillis() + EXPIRY);
        super.putAll(m);
    }
    public Set<Map.Entry<K,V>> entrySet() {
        Set<java.util.Map.Entry<K, V>> set = super.entrySet();
        Iterator<java.util.Map.Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<K, V> entry = iterator.next();
            if(checkExpiry(entry.getKey(), false)) iterator.remove();
        }

        return set;
    }
    /**
     *
     * @Description: 是否过期
     * @author: qd-ankang
     * @param isRemoveSuper true super删除
     * @return
     */
    private boolean checkExpiry(Object key, boolean isRemoveSuper){

        if(!expiryMap.containsKey(key)){
            return Boolean.FALSE;
        }
        long expiryTime = expiryMap.get(key);

        boolean flag = System.currentTimeMillis() > expiryTime;

        if(flag){
            if(isRemoveSuper)
                super.remove(key);
            expiryMap.remove(key);
        }
        return flag;
    }






}
