public interface IBounceHashFunction<K> {
    int hash1(K key, int tablesize);

    int hash2(K key, int tablesize);

    void changeHashFn(int tablesize);
}
