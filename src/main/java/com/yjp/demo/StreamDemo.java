package com.yjp.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * stream流中间操作
 */
public class StreamDemo {

    public static void main(String[] args) {
        /**
         * Java 8 中的 Stream 俗称为流，它与 java.io 包里的 InputStream 和 OutputStream 是完全不同的概念。
         * Stream 用于对集合对象进行各种非常便利、高效的聚合操作，或者大批量数据操作。Stream API 借助于Lambda 表达式，
         * 极大的提高编程效率和程序可读性。同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势。
         * 通过下面的例子我们可以初步体会到使用 Stream 处理集合的便利性。
         */
        //有如下一个List，现要从中筛选出以J开头的元素，然后转换为大写，最后输出结果。Java 8之前我们是这样做的
        List<String> list = Arrays.asList("Java", "JavaScript", "python", "PHP", "C#", "Golang", "Swift");
        List<String> filterList = new ArrayList<>();
        for (String str : list) {
            if (str.startsWith("J")) {
                filterList.add(str.toUpperCase());
            }
        }
        for (String str : filterList) {
            System.out.println(str);
        }
        //为了筛选集合我们进行了两次外部迭代，并且还创建了一个用来临时存放筛选元素的集合对象。借助Java 8中的Stream我们可以极大的简化这个处理过程：
        list.stream()
                .filter(s -> s.startsWith("J"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
        /**
         * 是不是很方便？上面的例子中，集合使用stream方法创建了一个流，然后使用filter和map方法来处理这个集合，它们统称为中间操作。
         *
         * 中间操作都会返回另一个流，以便于将各种对集合的操作连接起来形成一条流水线。
         *
         * 最后我们使用了forEach方法迭代筛选结果，这种位于流的末端，对流进行处理并且生成结果的方法称为终端操作。
         *
         * 总而言之，流的使用一般包括三件事情：
         *
         * 一个数据源（如集合）来执行一个查询；
         *
         * 一个中间操作链，形成一条流的流水线；
         *
         * 一个终端操作，执行流水线，并能生成结果。
         *
         * 下表列出了流中常见的中间操作和终端操作：
         *
         * 操作	        类型	返回类型	使用的类型/函数式接口	函数描述符
         * filter	    中间	Stream<T>	Predicate<T>	T -> boolean
         * distinct	    中间	Stream<T>
         * skip	        中间	Stream<T>	long
         * limit	    中间	Stream<T>	long
         * map	        中间	Stream<R>	Function<T, R>	T -> R
         * flatMap	    中间	Stream<R>	Function<T, Stream<R>>	T -> Stream<R>
         * sorted	    中间	Stream<T>	Comparator<T>	(T, T) -> int
         * anyMatch	    终端	boolean	    Predicate<T>	T -> boolean
         * noneMatch	终端	boolean	    Predicate<T>	T -> boolean
         * allMatch	    终端	boolean	    Predicate<T>	T -> boolean
         * findAny	    终端	Optional<T>
         * findFirst	终端	Optional<T>
         * forEach	    终端	void	    Consumer<T>	T -> void
         * collect	    终端	R	        Collector<T, A, R>
         * reduce	    终端	Optional<T>	BinaryOperator<T>	(T, T) -> T
         * count	    终端	long
         */
        //filter
        //Streams接口支持·filter方法，该方法接收一个Predicate<T>，函数描述符为T -> boolean，用于对集合进行筛选，返回所有满足的元素
        list.stream()
                .filter(s -> s.contains("#"))
                .forEach(System.out::println);
        //结果输出C#。

        //distinct
        //distinct方法用于排除流中重复的元素，类似于SQL中的distinct操作。比如筛选中集合中所有的偶数，并排除重复的结果
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
        //结果输出2 4。

        //skip(n)方法用于跳过流中的前n个元素，如果集合元素小于n，则返回空流。比如筛选出以J开头的元素，并排除第一个：
        list.stream()
            .filter(s -> s.startsWith("J"))
            .skip(1)
            .forEach(System.out::println);
        //结果输出JavaScript。

        //limit(n)方法返回一个长度不超过n的流，比如下面的例子将输出Java JavaScript python：
        list.stream()
            .limit(3)
            .forEach(System.out::println);

        //map方法接收一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素。如：
        list.stream()
            .map(String::length)
            .forEach(System.out::println);
        //结果输出4 10 6 3 2 6 5 3 4。

        //map还支持将流特化为指定原始类型的流，如通过mapToInt，mapToDouble和mapToLong方法，可以将流转换为IntStream，DoubleStream和LongStream。特化后的流支持sum，min和max方法来对流中的元素进行计算。比如：
        IntStream intStream = numbers.stream().mapToInt(a -> a);
        System.out.println(intStream.sum()); // 16
        //也可以通过下面的方法，将IntStream转换为Stream：
        //Stream<Integer> s = intStream.boxed();

        //flatMap用于将多个流合并成一个流，俗称流的扁平化。这么说有点抽象，举个例子，比如现在需要将list中的各个元素拆分为一个个字母，并过滤掉重复的结果，你可能会这样做：
        list.stream()
           .map(s -> s.split(""))
           .distinct()
           .forEach(System.out::println);
        //输出如下：
        //[Ljava.lang.String;@e9e54c2
        //[Ljava.lang.String;@65ab7765
        //[Ljava.lang.String;@1b28cdfa
        //[Ljava.lang.String;@eed1f14
        //[Ljava.lang.String;@7229724f
        //[Ljava.lang.String;@4c873330
        //[Ljava.lang.String;@119d7047
        //[Ljava.lang.String;@776ec8df
        //[Ljava.lang.String;@4eec7777
        //这明显不符合我们的预期。实际上在map(s -> s.split(""))操作后，返回了一个Stream<String[]>类型的流，所以输出结果为每个数组对象的句柄，而我们真正想要的结果是Stream<String>！
        //在Stream中，可以使用Arrays.stream()方法来将数组转换为流，改造上面的方法：
        list.stream()
            .map(s -> s.split(""))
            .map(Arrays::stream)
            .distinct()
            .forEach(System.out::println);
        //输出如下：
        //java.util.stream.ReferencePipeline$Head@eed1f14
        //java.util.stream.ReferencePipeline$Head@7229724f
        //java.util.stream.ReferencePipeline$Head@4c873330
        //java.util.stream.ReferencePipeline$Head@119d7047
        //java.util.stream.ReferencePipeline$Head@776ec8df
        //java.util.stream.ReferencePipeline$Head@4eec7777
        //java.util.stream.ReferencePipeline$Head@3b07d329
        //java.util.stream.ReferencePipeline$Head@41629346
        //java.util.stream.ReferencePipeline$Head@404b9385
        //因为上面的流经过map(Arrays::stream)处理后，将每个数组变成了一个新的流，返回结果为流的数组Stream<String>[]，所以输出是各个流的句柄。我们还需将这些新的流连接成一个流，使用flatMap来改写上面的例子：
        list.stream()
            .map(s -> s.split(""))
            .flatMap(Arrays::stream)
            .distinct()
            .forEach(s -> System.out.print(s + " "));
        //输出如下：
        //J a v S c r i p t y h o n P H C # G l g w f + R u b
        //和map类似，flatMap方法也有相应的原始类型特化方法，如flatMapToInt等。
    }

}
