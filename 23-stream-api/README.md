# 15 - Lambda Va Stream

## Can nam

- Lambda la cach viet ngan gon cho functional interface.
- Functional interface la interface co mot abstract method.
- Stream dung de xu ly collection theo pipeline.
- Method hay dung: `filter`, `map`, `sorted`, `forEach`, `collect`, `reduce`.

## Vi du

```java
List<String> names = List.of("An", "Binh", "Cuong");
names.stream()
    .filter(name -> name.length() > 2)
    .forEach(System.out::println);
```

## Tu kiem tra

- Lambda khac anonymous class nhu the nao?
- `map` va `filter` khac nhau ra sao?
- Stream co lam thay doi collection goc khong?

## Ghi chu cua minh

- 
