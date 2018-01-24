ДЗ-11: my cache engine

Напишите свой cache engine с soft references.
Добавьте кэширование в DBService из заданий myORM или Hibernate ORM

Simple cache engine
Map<Key, Value> is the most simple cache.
It supports operations:
• put
• get
• size
Additional operations, not supported by Map:
• max_size, evict old value if size == max_size
• set time of life, evict by timeout
• set time of idle, evict if not accessed
• get hits count
• get misses count